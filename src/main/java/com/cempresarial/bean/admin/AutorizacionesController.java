package com.cempresarial.bean.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.omnifaces.util.Faces;

import com.cempresarial.entities.DTO.PerfilDTO;
import com.cempresarial.recursos.SesionController;
import com.cempresarial.recursos.Utilitarios;
import com.cempresarial.rest.client.service.AplicacionService;
import com.cempresarial.rest.client.service.AutorizacionService;
import com.cempresarial.rest.client.service.MenuService;
import com.cempresarial.rest.client.service.PerfilService;
import com.cempresariales.servicio.commons.admin.model.entity.Aplicacion;
import com.cempresariales.servicio.commons.admin.model.entity.Autorizacion;
import com.cempresariales.servicio.commons.admin.model.entity.Menu;
import com.cempresariales.servicio.commons.admin.model.entity.Perfil;

@ManagedBean(name = "autorizacionesController")
@SessionScoped
public class AutorizacionesController extends SesionController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AutorizacionesController() {
		super();
	}

	@Inject
	private AplicacionService aplicacionDAO;
	@Inject
	@EJB
	private MenuService menuDAO;
	@Inject
	private AutorizacionService autorizaDAO;
	@Inject
	private PerfilService perfilDAO;

	// VARIABLES
	private Utilitarios util;

	private Menu menuPadre;
	private Menu menuHijo;
	private Aplicacion aplicacion;
	private Autorizacion autorizacion;

	private Aplicacion app;
	private List<Perfil> listPerfil;
	private List<PerfilDTO> listPerfilDTO;

	private List<Aplicacion> listAplicacion;

	private List<Menu> listMenuPadre;
	private List<Menu> listMenuHijo;
	private List<Autorizacion> listAutorizacion;
	private List<Autorizacion> listAutorizacionGuardar;
	private List<Autorizacion> listAutorizacionSelect;

	private String mensajeGuardar;
	private boolean activoApp;
	private boolean activoMenu;
	private boolean activoOpcion;

	private boolean renderGuardar;

	private String[] permisosSelect;

	// METODOS
	@PostConstruct
	private void init() {
		clear();

	}

	public void clear() {
		try {
			util = new Utilitarios();

			menuPadre = new Menu();
			menuHijo = new Menu();
			autorizacion = new Autorizacion();
			listMenuHijo = new ArrayList<>();
			listMenuPadre = new ArrayList<>();

			String urlAplicacion = Faces.getExternalContext().getInitParameter("urlAplicacion");
			app = aplicacionDAO.findByUrl(urlAplicacion);

			activoApp = false;
			activoMenu = false;
			activoOpcion = false;

			aplicacion = new Aplicacion();

			listAplicacion = aplicacionDAO.listar();
			listAutorizacion = autorizaDAO.listar();
			construirSeleccionPerfil();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void construirSeleccionPerfil() {
		try {
			listPerfil = perfilDAO.listar();
			listPerfilDTO = new ArrayList<>();
			for (Perfil p : listPerfil) {
				PerfilDTO pdto = new PerfilDTO();
				pdto.setPerfil(p);
				pdto.setCheck(false);
				listPerfilDTO.add(pdto);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void actualizarListAplicaciones() {
		try {
			listAplicacion = aplicacionDAO.listar();
			util.mostrarMensaje("APLICACIONES", "Se ha actualziado el LISTADO DE APLICACIONES", "w");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizarListPerfiles() {
		try {
			listPerfil = perfilDAO.listar();
			util.mostrarMensaje("PERFILES", "Se ha actualziado el LISTADO DE PERFILES", "f");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void llenarOpciones() {
		try {
			listMenuHijo = menuDAO.findByMenuPadre(menuPadre.getIdMenu());
			activoMenu = menuPadre.getActivo();
			activoApp = menuPadre.getAplicacion().getActivo();
			activoOpcion = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void llenarMenus() {
		try {
			listMenuPadre = menuDAO.findMenuPadreByAplicacion(aplicacion.getIdAplicacion());
			activoApp = aplicacion.getActivo();
			activoMenu = false;
			activoOpcion = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void llenarActivos() {
		try {
			activoApp = menuHijo.getAplicacion().getActivo();
			activoMenu = menuHijo.getMenuPadre().getActivo();
			activoOpcion = menuHijo.getActivo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizarAutorizacion() {
		try {
			if (renderGuardar) {
				for (Autorizacion au : listAutorizacionGuardar) {
					autorizaDAO.actualizar(au.getIdAutorizacion(), au);
				}
				util.mostrarMensaje("PERMISOS", "Se ha actualizado el PERMISO exitosamente", "E");
				clear();
			} else {
				mensajeGuardar = "No se ha podido actualizar el PERMISO";
				util.mostrarMensaje("PERMISO", mensajeGuardar, "E");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void crearAutorizacion() {

		try {

			int valorItem = autorizaDAO.listar().size();

			if (verificarPaquete(4, valorItem)) {
				if (renderGuardar) {
					crearListaAutorizacionesGuardar();

					System.out.println("AUTORIZXAION LISTA: " + listAutorizacionGuardar.size());

					for (Autorizacion au : listAutorizacionGuardar) {
						System.out.println("DATOS: " + au.getPermisos());
						System.out.println("DATOS: " + au.getPerfil().getIdPerfil());
						System.out.println("DATOS: " + au.getMenu().getIdMenu());

						autorizaDAO.insertar(au);
					}
					util.mostrarMensaje("PERMISOS", "Se ha guardado el PERMISO exitosamente", "E");
					clear();
				} else {
					mensajeGuardar = "No se ha podido guardar el PERMISO";
					util.mostrarMensaje("PERMISO", mensajeGuardar, "E");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void crearListaAutorizacionesActualiza() {
		try {
			listAutorizacionGuardar = new ArrayList<>();
			int valorSelec = 0;
			for (Perfil p : listPerfil) {

				if (menuPadre.getIdMenu() != 0) {
					autorizacion.setMenu(menuPadre);
				}
				if (menuHijo.getIdMenu() != 0) {
					autorizacion.setMenu(menuHijo);
				}
				String permisos = "";
				for (int x = 0; x < permisosSelect.length; x++) {
					permisos += permisosSelect[x] + " ";
				}
				autorizacion.setPermisos(permisos);
				autorizacion.setPerfil(p);
				listAutorizacionGuardar.add(autorizacion);

				// if (p.getSeleccionado().equals("SI")) {
				valorSelec++;
				// }

			}

			if (valorSelec == 1) {
				renderGuardar = true;
				mensajeGuardar = "Desea ACTUALIZAR el PERMISO ? ";
				util.mostrarMensaje("PERMISOS", mensajeGuardar, "i");
			} else {
				renderGuardar = false;
				mensajeGuardar = "No se puede actualizar, por favor, identifique un SOLO PERFIL para actualizar";
				util.mostrarMensaje("PERMISOS", mensajeGuardar, "w");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void crearListaAutorizacionesGuardar() {
		try {
			listAutorizacionGuardar = new ArrayList<>();

			System.out.println("MENUS A INGRESAR: " + menuPadre.getIdMenu());
			System.out.println("MENUS A INGRESAR: " + menuHijo.getIdMenu());

			for (Perfil p : listPerfil) {
				if (menuPadre.getIdMenu() != null) {
					autorizacion.setMenu(menuPadre);
				}
				if (menuHijo.getIdMenu() != null) {
					autorizacion.setMenu(menuHijo);
				}
				String permisos = "";
				for (int x = 0; x < permisosSelect.length; x++) {
					permisos += permisosSelect[x] + " ";
				}
				autorizacion.setPermisos(permisos);
				autorizacion.setPerfil(p);
				listAutorizacionGuardar.add(autorizacion);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void verificarAtesActualizar() {
		try {
			if (verificarVacios()) {
				if (verificarPerfilesSeleccionados()) {

					mensajeGuardar = "Desea GUARDAR los PERMISOS?";
					util.mostrarMensaje("PERMISOS", mensajeGuardar, "w");
					renderGuardar = true;
					crearListaAutorizacionesActualiza();

				} else {
					mensajeGuardar = "Perfil no Seleccionado, por favor, verificar antes de ACTUALIZAR";
					util.mostrarMensaje("PERMISOS", mensajeGuardar, "w");
					renderGuardar = false;
				}
			} else {
				mensajeGuardar = "Existen Datos vacíos, por favor, verificar antes de ACTUALIZAR";
				util.mostrarMensaje("PERMISOS", mensajeGuardar, "w");
				renderGuardar = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void verificarAtesGuardar() {
		try {
			if (verificarVacios()) {
				if (verificarPerfilesSeleccionados()) {
					if (verificarSimilar()) {
						mensajeGuardar = "Desea GUARDAR los PERMISOS?";
						util.mostrarMensaje("PERMISOS", mensajeGuardar, "w");
						renderGuardar = true;
					} else {
						mensajeGuardar = "Existen Datos similares, por favor, verificar antes de GUARDAR";
						util.mostrarMensaje("PERMISOS", mensajeGuardar, "w");
						renderGuardar = false;
					}
				} else {
					mensajeGuardar = "Perfil no Seleccionado, por favor, verificar antes de GUARDAR";
					util.mostrarMensaje("PERMISOS", mensajeGuardar, "w");
					renderGuardar = false;
				}
			} else {
				mensajeGuardar = "Existen Datos vacíos, por favor, verificar antes de GUARDAR";
				util.mostrarMensaje("PERMISOS", mensajeGuardar, "w");
				renderGuardar = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean verificarSimilar() {
		try {

			System.out.println("MENUS A INGRESAR: " + menuPadre);
			System.out.println("MENUS A INGRESAR: " + menuHijo);

			List<Autorizacion> autorizabdd = new ArrayList<>();
			if (menuPadre.getIdMenu() != null) {
				autorizabdd = autorizaDAO.findByMenu(menuPadre);
				autorizacion.setMenu(menuPadre);
			}
			if (menuHijo.getIdMenu() != null) {
				autorizabdd = autorizaDAO.findByMenu(menuHijo);
				autorizacion.setMenu(menuHijo);
			}

			boolean valor = false;
			if (autorizabdd.isEmpty())
				valor = true;
			for (Autorizacion au : autorizabdd) {
				for (Perfil p : listPerfil) {

					System.out.println("DATOS1: " + au.getIdAutorizacion() + "***" + autorizacion.getIdAutorizacion());
					System.out.println("DATOS2: " + au.getPerfil().getIdPerfil() + "***" + p.getIdPerfil());
					System.out.println(
							"DATOS3: " + au.getMenu().getIdMenu() + "***" + autorizacion.getMenu().getIdMenu());

					if (au.getPerfil().getIdPerfil() == p.getIdPerfil()
							&& au.getMenu().getIdMenu() == autorizacion.getMenu().getIdMenu()) {
						valor = false;
					} else {
						valor = true;
					}
				}
			}

			return valor;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean verificarVacios() {
		try {

			if (menuPadre == null || menuHijo == null || permisosSelect.length == 0) {
				if (menuPadre == null && autorizacion.getMenu() != null) {
					if (autorizacion.getMenu().getTipo().equals("menu")) {
						menuPadre = autorizacion.getMenu();
					} else {
						menuHijo = autorizacion.getMenu();
					}
				}
				if (menuHijo == null && autorizacion.getMenu() != null) {
					if (autorizacion.getMenu().getTipo().equals("opcion")) {
						menuHijo = autorizacion.getMenu();
					} else {
						menuPadre = autorizacion.getMenu();
					}
				}
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean verificarPerfilesSeleccionados() {
		try {
			int valorNull = 0;
			int valorNo = 0;
			List<Perfil> listPerfilGuardar = new ArrayList<>();
			// :TODO falta verificar cuando selecciono perfiles para autorizacion

			for (PerfilDTO p : listPerfilDTO) {

				if (!p.isCheck()) {
					valorNo++;
				} else {
					listPerfilGuardar.add(p.getPerfil());
				}

			}

			if (valorNo + valorNull == listPerfil.size()) {
				return false;
			} else {
				listPerfil = listPerfilGuardar;
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void eliminarAutorizacion(Autorizacion autoriza) {
		try {

			if (autoriza.getActivo().equals("NO")) {
				autorizaDAO.eliminar(autoriza.getIdAutorizacion());
				listAutorizacion = autorizaDAO.listar();
				util.mostrarMensaje("AUTORIZACION", "La Autorización ha sido eliminado correctamente.!", "i");
			} else {
				util.mostrarMensaje("AUTORIZACION", "Autorización en USO. No se puede eliminar!", "i");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void activaDesactivaAutorizacion(Autorizacion autoriza) {
		try {

			if (autoriza.getActivo()) {
				autoriza.setActivo(false);
				util.mostrarMensaje("PERMISO", "Permiso DESACTIVADO correctamente.!", "i");
			} else {
				autoriza.setActivo(true);
				util.mostrarMensaje("PERMISO", "Permiso ACTIVADO correctamente.!", "i");
			}
			autorizaDAO.actualizar(autoriza.getIdAutorizacion(), autoriza);
			listAutorizacion = autorizaDAO.listar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void llenarForaneosAntesActualizar(Autorizacion autorizacion) {
		try {

			aplicacion = new Aplicacion();
			menuPadre = new Menu();
			menuHijo = new Menu();
			this.autorizacion = autorizacion;

			if (autorizacion.getMenu().getTipo().trim().equals("menu")) {
				menuPadre = autorizacion.getMenu();
			} else {
				menuHijo = autorizacion.getMenu();
				menuPadre = menuHijo.getMenuPadre();
			}
			aplicacion = autorizacion.getMenu().getAplicacion();

			activoApp = aplicacion.getActivo();
			activoMenu = menuPadre.getActivo();
			activoOpcion = menuHijo.getActivo();

			StringTokenizer tokens = new StringTokenizer(autorizacion.getPermisos(), " ");
			int nDatos = tokens.countTokens();
			permisosSelect = new String[nDatos];
			int i = 0;
			while (tokens.hasMoreTokens()) {
				String str = tokens.nextToken();
				permisosSelect[i] = str;
				System.out.println(permisosSelect[i]);
				i++;
			}

			listPerfil = perfilDAO.listar();
			listPerfilDTO = new ArrayList<>();

			for (int x = 0; x < listPerfil.size(); x++) {
				Perfil pu = listPerfil.get(x);
				PerfilDTO pdto = new PerfilDTO();
				pdto.setPerfil(pu);
				pdto.setCheck(false);
				if (autorizacion.getPerfil().getIdPerfil() == pu.getIdPerfil()) {
					// : TODO verificar un select para perfles en pagina
					// pu.setSeleccionado("SI");
					listPerfil.set(x, pu);
					pdto.setCheck(true);
				}
				listPerfilDTO.add(pdto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// SETTERS Y GETTERS
	public Utilitarios getUtil() {
		return util;
	}

	public void setUtil(Utilitarios util) {
		this.util = util;
	}

	public Menu getMenuPadre() {
		return menuPadre;
	}

	public void setMenuPadre(Menu menuPadre) {
		this.menuPadre = menuPadre;
	}

	public Menu getMenuHijo() {
		return menuHijo;
	}

	public void setMenuHijo(Menu menuHijo) {
		this.menuHijo = menuHijo;
	}

	public Aplicacion getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(Aplicacion aplicacion) {
		this.aplicacion = aplicacion;
	}

	public Autorizacion getAutorizacion() {
		return autorizacion;
	}

	public void setAutorizacion(Autorizacion autorizacion) {
		this.autorizacion = autorizacion;
	}

	public List<Perfil> getListPerfil() {
		return listPerfil;
	}

	public void setListPerfil(List<Perfil> listPerfil) {
		this.listPerfil = listPerfil;
	}

	public List<Menu> getListMenuPadre() {
		return listMenuPadre;
	}

	public void setListMenuPadre(List<Menu> listMenuPadre) {
		this.listMenuPadre = listMenuPadre;
	}

	public List<Menu> getListMenuHijo() {
		return listMenuHijo;
	}

	public void setListMenuHijo(List<Menu> listMenuHijo) {
		this.listMenuHijo = listMenuHijo;
	}

	public List<Autorizacion> getListAutorizacion() {
		return listAutorizacion;
	}

	public void setListAutorizacion(List<Autorizacion> listAutorizacion) {
		this.listAutorizacion = listAutorizacion;
	}

	public String getMensajeGuardar() {
		return mensajeGuardar;
	}

	public void setMensajeGuardar(String mensajeGuardar) {
		this.mensajeGuardar = mensajeGuardar;
	}

	public boolean isRenderGuardar() {
		return renderGuardar;
	}

	public void setRenderGuardar(boolean renderGuardar) {
		this.renderGuardar = renderGuardar;
	}

	public String[] getPermisosSelect() {
		return permisosSelect;
	}

	public void setPermisosSelect(String[] permisosSelect) {
		this.permisosSelect = permisosSelect;
	}

	public List<Aplicacion> getListAplicacion() {
		return listAplicacion;
	}

	public void setListAplicacion(List<Aplicacion> listAplicacion) {
		this.listAplicacion = listAplicacion;
	}

	public List<Autorizacion> getListAutorizacionSelect() {
		return listAutorizacionSelect;
	}

	public void setListAutorizacionSelect(List<Autorizacion> listAutorizacionSelect) {
		this.listAutorizacionSelect = listAutorizacionSelect;
	}

	public boolean getActivoApp() {
		return activoApp;
	}

	public void setActivoApp(boolean activoApp) {
		this.activoApp = activoApp;
	}

	public boolean getActivoMenu() {
		return activoMenu;
	}

	public void setActivoMenu(boolean activoMenu) {
		this.activoMenu = activoMenu;
	}

	public boolean getActivoOpcion() {
		return activoOpcion;
	}

	public void setActivoOpcion(boolean activoOpcion) {
		this.activoOpcion = activoOpcion;
	}

	public List<PerfilDTO> getListPerfilDTO() {
		return listPerfilDTO;
	}

	public void setListPerfilDTO(List<PerfilDTO> listPerfilDTO) {
		this.listPerfilDTO = listPerfilDTO;
	}

}
