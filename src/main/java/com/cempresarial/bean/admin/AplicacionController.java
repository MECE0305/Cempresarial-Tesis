package com.cempresarial.bean.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.cempresarial.recursos.SesionController;
import com.cempresarial.recursos.Utilitarios;
import com.cempresarial.rest.client.service.AplicacionService;
import com.cempresarial.rest.client.service.MenuService;
import com.cempresariales.servicio.commons.admin.model.entity.Aplicacion;
import com.cempresariales.servicio.commons.admin.model.entity.Menu;



@ManagedBean(name = "aplicacionController")
@SessionScoped
public class AplicacionController extends SesionController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AplicacionController() {
		super();
	}

	@Inject
	private AplicacionService aplicacionDAO;
	@Inject
	private MenuService menuDAO;

	// VARIABLES
	private Utilitarios util;
	private Aplicacion aplicacion;
	private List<Aplicacion> listAplicacion;
	private List<Aplicacion> listAplicacionSelect;
	private List<Menu> listMenu;

	private boolean renderGuardar;

	public String mensajeGuardar = "";

	// METODOS
	@PostConstruct
	private void init() {
		clear();
	}

	public void clear() {
		try {
			util = new Utilitarios();
			aplicacion = new Aplicacion();
			listMenu = new ArrayList<>();
			listAplicacion = aplicacionDAO.listar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void eliminarAplicacion(Aplicacion app) {
		try {
			listMenu = menuDAO.findByAplicacion(app);

			if (listMenu.size() > 0) {
				util.mostrarMensaje("APLICACION", "No se puede eliminar, Aplicación en USO", "w");
			} else {
				aplicacionDAO.eliminar(app.getIdAplicacion());
				util.mostrarMensaje("APLICACION", "Aplicación eliminado exitosamente", "i");
			}
			listAplicacion = aplicacionDAO.listar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void activaDesactivaAplicacion(Aplicacion app) {
		try {

			if (app.getActivo()) {
				app.setActivo(false);
				util.mostrarMensaje("APLCIACION", "Aplicación DESACTIVADO correctamente.!", "f");
			} else {
				app.setActivo(true);
				util.mostrarMensaje("APLCIACION", "Aplicación ACTIVADO correctamente.!", "w");
			}
			aplicacionDAO.actualizar(app.getIdAplicacion(), app);
			listAplicacion = aplicacionDAO.listar();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizarAplicacion() {
		try {
			if (aplicacion.getIdAplicacion() != 0) {
				renderGuardar = verificarVacios();
				if (renderGuardar) {
					aplicacionDAO.actualizar(aplicacion.getIdAplicacion(), aplicacion);
					listAplicacion = aplicacionDAO.listar();
					util.mostrarMensaje("APLICACION", "La aplicación ha sido actualizada correctamente.!", "i");
				} else {
					mensajeGuardar = "No se ha podido actualziar la APLICACION, existen datos vacíos o no asigandos";
					util.mostrarMensaje("APLICACION", mensajeGuardar, "E");
				}
			} else {
				util.mostrarMensaje("APLICACION",
						"No se puede actualizar la aplicación. Id no identificado o no existe!", "F");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void crearAplicacion() {

		try {
			int valorItem = aplicacionDAO.listar().size();

			if (verificarPaquete(6, valorItem)) {
				if (renderGuardar) {
					aplicacionDAO.insertar(aplicacion);
					util.mostrarMensaje("APLICACION", "Se ha guardado la APLICACION exitosamente", "E");
					clear();
				} else {
					mensajeGuardar = "No se ha podido guardar la APLICACION";
					util.mostrarMensaje("APLICACION", mensajeGuardar, "E");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void verificarAtesGuardar() {
		try {
			if (verificarVacios()) {
				if (verificarSimilar()) {
					mensajeGuardar = "Desea GUARDAR la Aplicación?";
					util.mostrarMensaje("APLICACION", mensajeGuardar, "w");
					renderGuardar = true;
				} else {
					mensajeGuardar = "Existen Datos similares, por favor, verificar antes de GUARDAR";
					util.mostrarMensaje("APLICACION", mensajeGuardar, "w");
					renderGuardar = false;
				}
			} else {
				mensajeGuardar = "Existen Datos vacíos, por favor, verificar antes de GUARDAR";
				util.mostrarMensaje("APLICACION", mensajeGuardar, "w");
				renderGuardar = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean verificarSimilar() {
		try {
			List<Aplicacion> appbdd = aplicacionDAO.listar();
			boolean valor = false;
			for (Aplicacion ap : appbdd) {
				if (ap.getIdAplicacion() == aplicacion.getIdAplicacion() || (ap.getDescripcion().equals(aplicacion.getDescripcion())
						|| ap.getNombre().equals(aplicacion.getNombre()) || ap.getUrl().equals(aplicacion.getUrl()))) {
					valor = false;
					break;
				} else {
					valor = true;
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
			if (aplicacion.getActivo() == null || aplicacion.getUrl().equals("")
					|| aplicacion.getDescripcion().equals("") || aplicacion.getNombre().equals("")) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// SETTERS Y GETTERS
	public Utilitarios getUtil() {
		return util;
	}

	public void setUtil(Utilitarios util) {
		this.util = util;
	}

	public Aplicacion getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(Aplicacion aplicacion) {
		this.aplicacion = aplicacion;
	}

	public List<Aplicacion> getListAplicacion() {
		return listAplicacion;
	}

	public void setListAplicacion(List<Aplicacion> listAplicacion) {
		this.listAplicacion = listAplicacion;
	}

	public boolean isRenderGuardar() {
		return renderGuardar;
	}

	public void setRenderGuardar(boolean renderGuardar) {
		this.renderGuardar = renderGuardar;
	}

	public String getMensajeGuardar() {
		return mensajeGuardar;
	}

	public void setMensajeGuardar(String mensajeGuardar) {
		this.mensajeGuardar = mensajeGuardar;
	}

	public List<Aplicacion> getListAplicacionSelect() {
		return listAplicacionSelect;
	}

	public void setListAplicacionSelect(List<Aplicacion> listAplicacionSelect) {
		this.listAplicacionSelect = listAplicacionSelect;
	}

}
