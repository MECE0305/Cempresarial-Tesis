package com.cempresarial.bean.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.omnifaces.util.Faces;

import com.cempresarial.recursos.SesionController;
import com.cempresarial.recursos.Utilitarios;
import com.cempresarial.rest.client.service.AplicacionService;
import com.cempresarial.rest.client.service.AutorizacionService;
import com.cempresarial.rest.client.service.MenuService;
import com.cempresariales.servicio.commons.admin.model.entity.Aplicacion;
import com.cempresariales.servicio.commons.admin.model.entity.Autorizacion;
import com.cempresariales.servicio.commons.admin.model.entity.Menu;

@ManagedBean(name = "menuController")
@SessionScoped
public class MenuController extends SesionController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MenuController() {
		super();
	}

	@Inject
	private AplicacionService aplicacionDAO;
	@Inject
	private MenuService menuDAO;
	@Inject
	private AutorizacionService autorizaDAO;

	// VARIABLES
	private Utilitarios util;

	private Menu menu;
	private Menu menuPadre;
	private Menu opcion;
	private Aplicacion aplicacion;

	private Aplicacion app;

	private List<Menu> listMenu;
	private List<Menu> listMenuSelect;

	private List<Menu> listMenuPadre;
	private List<Aplicacion> listAplicacion;
	private List<Autorizacion> listAutorizacion;

	private String mensajeGuardarMenu;
	private String mensajeGuardarOpcion;

	private boolean renderGuardarMenu;
	private boolean renderGuardarOpcion;

	private Long idAplicacionMenu;
	private Long idAplicacionOpcion;
	private Long idMenuPadre;

	// METODOS
	@PostConstruct
	private void init() {
		clear();

	}

	public void clear() {
		try {
			util = new Utilitarios();
			listAplicacion = aplicacionDAO.listar();
			listMenu = menuDAO.listar();
			listMenuPadre = menuDAO.listar();

			menu = new Menu();
			menu.setTipo("menu");
			opcion = new Menu();
			opcion.setTipo("opcion");
			aplicacion = new Aplicacion();
			String urlAplicacion = Faces.getExternalContext().getInitParameter("urlAplicacion");
			app = aplicacionDAO.findByUrl(urlAplicacion);

			idAplicacionMenu = new Long(0);
			idMenuPadre = new Long(0);
			idAplicacionOpcion = new Long(0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizarLista() {
		try {
			listMenu = menuDAO.listar();
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

	public void actualizarListAPlicacionesenuPadre() {
		try {
			actualizarListAplicaciones();
			listMenuPadre = menuDAO.findByAplicacion(app);
			util.mostrarMensaje("APLICACIONES", "Se ha actualziado el LISTADO DE MENUS PADRES", "w");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void activaDesactivaMenu(Menu men) {
		try {

			if (men.getActivo()) {
				men.setActivo(false);
				util.mostrarMensaje("MENU", "Menu DESACTIVADO correctamente.!", "f");
			} else {
				men.setActivo(true);
				util.mostrarMensaje("MENU", "Menu ACTIVADO correctamente.!", "w");
			}
			menuDAO.actualizar(men.getIdMenu(), men);
			listMenu = menuDAO.listar();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void pasarIdsMenu() {
		try {
			idAplicacionMenu = menu.getAplicacion().getIdAplicacion();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void pasarIdsOpcion() {
		try {
			idAplicacionOpcion = opcion.getAplicacion().getIdAplicacion();
			idMenuPadre = opcion.getMenuPadre().getIdMenu();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void eliminarMenu(Menu men) {
		try {
			listAutorizacion = autorizaDAO.findByMenu(men);
			List<Menu> listMenuHijos = new ArrayList<>();
			if (men.getTipo().equals("menu")) {
				listMenuHijos = menuDAO.findOpcionesByAplicacionAndMenuPadre(men.getAplicacion().getIdAplicacion(),
						men.getIdMenu());
			}

			if (listAutorizacion.size() > 0 || listMenuHijos.size() > 0) {
				util.mostrarMensaje("MENU", "No se puede eliminar, Menu en USO", "w");
			} else {
				menuDAO.eliminar(men.getIdMenu());
				util.mostrarMensaje("MENU", "Menu eliminado exitosamente", "i");
			}
			listMenu = menuDAO.listar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizarMenu() {
		try {
			if (menu.getIdMenu() != 0) {
				renderGuardarMenu = verificarVaciosMenu();
				if (renderGuardarMenu) {
					menu.setAplicacion(aplicacionDAO.buscarPorId(idAplicacionMenu));
					menuDAO.actualizar(menu.getIdMenu(), menu);
					listMenu = menuDAO.listar();
					util.mostrarMensaje("MENU", "El menu ha sido actualizado correctamente.!", "i");
				} else {
					mensajeGuardarMenu = "No se ha podido actualziar el MENU, existen datos vacíos o no asigandos";
					util.mostrarMensaje("MENU", mensajeGuardarMenu, "E");
				}
			} else {
				util.mostrarMensaje("MENU", "No se puede actualizar el menu. Id no identificado o no existe!", "F");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizarOpcion() {
		try {
			if (opcion.getIdMenu() != 0) {
				renderGuardarOpcion = verificarVaciosOpcion();
				if (renderGuardarOpcion) {
					opcion.setAplicacion(aplicacionDAO.buscarPorId(idAplicacionOpcion));
					opcion.setMenuPadre(menuDAO.buscarPorId(idMenuPadre));
					menuDAO.actualizar(opcion.getIdMenu(), opcion);
					listMenu = menuDAO.listar();
					util.mostrarMensaje("MENU", "La opción ha sido actualizado correctamente.!", "i");
				} else {
					mensajeGuardarOpcion = "No se ha podido actualziar la OPCIÓN, existen datos vacíos o no asigandos";
					util.mostrarMensaje("MENU", mensajeGuardarOpcion, "E");
				}
			} else {
				util.mostrarMensaje("MENU", "No se puede actualizar la opción. Id no identificado o no existe!", "f");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void crearMenu() {

		try {

			int valorItem = menuDAO.listar().size();

			if (verificarPaquete(5, valorItem)) {
				if (renderGuardarMenu) {
					menu.setAplicacion(aplicacionDAO.buscarPorId(idAplicacionMenu));
					menuDAO.insertar(menu);
					util.mostrarMensaje("MENU", "Se ha guardado el MENU exitosamente", "E");
					clear();
				} else {
					mensajeGuardarMenu = "No se ha podido guardar el MENU";
					util.mostrarMensaje("MENU", mensajeGuardarMenu, "E");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void crearOpcion() {

		try {
			int valorItem = menuDAO.listar().size();

			if (verificarPaquete(5, valorItem)) {
				if (renderGuardarOpcion) {
					opcion.setAplicacion(aplicacionDAO.buscarPorId(idAplicacionOpcion));
					opcion.setMenuPadre(menuDAO.buscarPorId(idMenuPadre));
					menuDAO.insertar(opcion);
					util.mostrarMensaje("MENU", "Se ha guardado la OPCIÓN exitosamente", "E");
					clear();
				} else {
					mensajeGuardarMenu = "No se ha podido guardar la OPCIÓN";
					util.mostrarMensaje("MENU", mensajeGuardarMenu, "E");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void verificarAtesGuardarMenu() {
		try {
			if (verificarVaciosMenu()) {
				if (verificarSimilarMenu()) {
					mensajeGuardarMenu = "Desea GUARDAR el Menú?";
					util.mostrarMensaje("MENU", mensajeGuardarMenu, "w");
					renderGuardarMenu = true;
				} else {
					mensajeGuardarMenu = "Existen Datos similares, por favor, verificar antes de GUARDAR";
					util.mostrarMensaje("MENU", mensajeGuardarMenu, "w");
					renderGuardarMenu = false;
				}
			} else {
				mensajeGuardarMenu = "Existen Datos vacíos, por favor, verificar antes de GUARDAR";
				util.mostrarMensaje("MENU", mensajeGuardarMenu, "w");
				renderGuardarMenu = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void verificarAtesGuardarOpcion() {
		try {
			if (verificarVaciosOpcion()) {
				if (verificarSimilarOpcion()) {
					mensajeGuardarOpcion = "Desea GUARDAR la Opción?";
					util.mostrarMensaje("MENU", mensajeGuardarOpcion, "w");
					renderGuardarOpcion = true;
				} else {
					mensajeGuardarOpcion = "Existen Datos similares, por favor, verificar antes de GUARDAR";
					util.mostrarMensaje("MENU", mensajeGuardarOpcion, "w");
					renderGuardarOpcion = false;
				}
			} else {
				mensajeGuardarOpcion = "Existen Datos vacíos, por favor, verificar antes de GUARDAR";
				util.mostrarMensaje("MENU", mensajeGuardarOpcion, "w");
				renderGuardarOpcion = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean verificarVaciosMenu() {
		try {
			if (idAplicacionMenu == 0 || menu.getDescripcion().equals("") || menu.getNombre().equals("")
					|| menu.getOrden() == 0) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean verificarVaciosOpcion() {
		try {
			if (idAplicacionOpcion == 0 || idMenuPadre == 0 || opcion.getUrl().equals("")
					|| opcion.getDescripcion().equals("") || opcion.getNombre().equals("") || opcion.getOrden() == 0) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean verificarSimilarMenu() {
		try {
			List<Menu> menubdd = menuDAO.findByAplicacion(aplicacionDAO.buscarPorId(idAplicacionMenu));

			boolean valor = true;
			for (Menu m : menubdd) {
				if (menu.getIdMenu() == m.getIdMenu() || menu.getNombre().equals(m.getNombre())) {
					valor = false;
					break;
				}
			}

			return valor;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean verificarSimilarOpcion() {
		try {

			List<Menu> menubdd = menuDAO.findOpcionesByAplicacionAndMenuPadre(idAplicacionOpcion, idMenuPadre);

			boolean valor = true;
			for (Menu m : menubdd) {
				if (opcion.getIdMenu() == m.getIdMenu()
						|| (opcion.getNombre().equals(m.getNombre()) || opcion.getUrl().equals(m.getUrl()))) {
					valor = false;
					break;
				}
			}

			return valor;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// SETTERS Y GETTERS
	public String getMensajeGuardarMenu() {
		return mensajeGuardarMenu;
	}

	public void setMensajeGuardarMenu(String mensajeGuardarMenu) {
		this.mensajeGuardarMenu = mensajeGuardarMenu;
	}

	public String getMensajeGuardarOpcion() {
		return mensajeGuardarOpcion;
	}

	public void setMensajeGuardarOpcion(String mensajeGuardarOpcion) {
		this.mensajeGuardarOpcion = mensajeGuardarOpcion;
	}

	public boolean isRenderGuardarMenu() {
		return renderGuardarMenu;
	}

	public void setRenderGuardarMenu(boolean renderGuardarMenu) {
		this.renderGuardarMenu = renderGuardarMenu;
	}

	public boolean isRenderGuardarOpcion() {
		return renderGuardarOpcion;
	}

	public void setRenderGuardarOpcion(boolean renderGuardarOpcion) {
		this.renderGuardarOpcion = renderGuardarOpcion;
	}

	public Utilitarios getUtil() {
		return util;
	}

	public void setUtil(Utilitarios util) {
		this.util = util;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Menu getOpcion() {
		return opcion;
	}

	public void setOpcion(Menu opcion) {
		this.opcion = opcion;
	}

	public Aplicacion getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(Aplicacion aplicacion) {
		this.aplicacion = aplicacion;
	}

	public List<Menu> getListMenu() {
		return listMenu;
	}

	public void setListMenu(List<Menu> listMenu) {
		this.listMenu = listMenu;
	}

	public List<Menu> getListMenuPadre() {
		return listMenuPadre;
	}

	public void setListMenuPadre(List<Menu> listMenuPadre) {
		this.listMenuPadre = listMenuPadre;
	}

	public List<Aplicacion> getListAplicacion() {
		return listAplicacion;
	}

	public void setListAplicacion(List<Aplicacion> listAplicacion) {
		this.listAplicacion = listAplicacion;
	}

	public List<Autorizacion> getListAutorizacion() {
		return listAutorizacion;
	}

	public void setListAutorizacion(List<Autorizacion> listAutorizacion) {
		this.listAutorizacion = listAutorizacion;
	}

	public List<Menu> getListMenuSelect() {
		return listMenuSelect;
	}

	public void setListMenuSelect(List<Menu> listMenuSelect) {
		this.listMenuSelect = listMenuSelect;
	}

	public Menu getMenuPadre() {
		return menuPadre;
	}

	public void setMenuPadre(Menu menuPadre) {
		this.menuPadre = menuPadre;
	}

	public Long getIdAplicacionMenu() {
		return idAplicacionMenu;
	}

	public void setIdAplicacionMenu(Long idAplicacionMenu) {
		this.idAplicacionMenu = idAplicacionMenu;
	}

	public Long getIdAplicacionOpcion() {
		return idAplicacionOpcion;
	}

	public void setIdAplicacionOpcion(Long idAplicacionOpcion) {
		this.idAplicacionOpcion = idAplicacionOpcion;
	}

	public Long getIdMenuPadre() {
		return idMenuPadre;
	}

	public void setIdMenuPadre(Long idMenuPadre) {
		this.idMenuPadre = idMenuPadre;
	}

}
