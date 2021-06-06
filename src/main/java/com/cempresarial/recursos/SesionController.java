package com.cempresarial.recursos;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Faces;

import com.cempresarial.rest.client.service.UsuarioService;
import com.cempresariales.servicio.commons.admin.model.entity.Usuario;

@ManagedBean(name = "sesionController")
@SessionScoped
public class SesionController implements Serializable {

	private static final long serialVersionUID = -468877955954871485L;
	private FacesContext fc = FacesContext.getCurrentInstance();
	private HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
	private HttpSession session = request.getSession();
	private Usuario currentUser = (Usuario) session.getAttribute("currentUser");

	@Inject
	private UsuarioService userDAO;
	

	public void timeout() throws IOException {
		Utilitarios util = new Utilitarios();
		String urlAplicacion = fc.getExternalContext().getInitParameter("urlAplicacion");
		util.mostrarMensaje("SESION CADUCADA", "Error en Logeo, Verificar Datos", "E");

		session.invalidate();
		Faces.getExternalContext().getFlash().setKeepMessages(true);
		Faces.redirect(urlAplicacion);
	}

	/**
	 * @param=#usuario = 1 @param=#tecnicos = 2 @param=#perfiles =
	 * 3 @param=#permisos = 4 @param=#menus = 5 @param=#aplicacion =
	 * 6 @param=#peril-usuario = 7 @param=#OTs = 8 @param=#incidentes = 9
	 **/
	public boolean verificarPaquete(int valorPaquete, int valorItem) {
		try {

			boolean valor = false;
			Utilitarios util = new Utilitarios();

			currentUser = userDAO.findByNombreUsuario(currentUser);

			switch (valorPaquete) {
			case 1:
				int numUsuarios = currentUser.getCuenta().getTipoCuenta().getPaquete().getNumeroUsuarios();
				if (valorItem < numUsuarios)
					valor = true;
				else
					util.mostrarMensaje("# USUARIOS",
							"Su cuenta no permite más ingresos (" + valorItem + "/" + numUsuarios + ")", "F");
				break;
			case 2:
				int numTecnicos = currentUser.getCuenta().getTipoCuenta().getPaquete().getNumeroTecnicos();
				if (valorItem < numTecnicos)
					valor = true;
				else
					util.mostrarMensaje("# TÉCNICOS",
							"Su cuenta no permite más ingresos (" + valorItem + "/" + numTecnicos + ")", "F");
				break;
			case 3:
				int numPerfil = currentUser.getCuenta().getTipoCuenta().getPaquete().getNumeroPerfiles();
				if (valorItem < numPerfil)
					valor = true;
				else
					util.mostrarMensaje("# PERFILES",
							"Su cuenta no permite más ingresos (" + valorItem + "/" + numPerfil + ")", "F");
				break;
			case 4:
				int numTipoCuenta = currentUser.getCuenta().getTipoCuenta().getPaquete().getNumeroPermisos();
				if (valorItem < numTipoCuenta)
					valor = true;
				else
					util.mostrarMensaje("# PERFILES",
							"Su cuenta no permite más ingresos (" + valorItem + "/" + numTipoCuenta + ")", "F");
				break;
			case 5:
				int numMenus = currentUser.getCuenta().getTipoCuenta().getPaquete().getNumeroMenus();
				if (valorItem < numMenus)
					valor = true;
				else
					util.mostrarMensaje("# PERFILES",
							"Su cuenta no permite más ingresos (" + valorItem + "/" + numMenus + ")", "F");
				break;
			case 6:
				int numAplicacion = currentUser.getCuenta().getTipoCuenta().getPaquete().getNumeroAplicacion();
				if (valorItem < numAplicacion)
					valor = true;
				else
					util.mostrarMensaje("# APLICACIÓN",
							"Su cuenta no permite más ingresos (" + valorItem + "/" + numAplicacion + ")", "F");
				break;
			case 7:
				int numPerilUsuario = currentUser.getCuenta().getTipoCuenta().getPaquete().getNumeroPerfilUsuario();
				if (valorItem < numPerilUsuario)
					valor = true;
				else
					util.mostrarMensaje("# PERIL USUARIO",
							"Su cuenta no permite más ingresos (" + valorItem + "/" + numPerilUsuario + ")", "F");
				break;
			case 8:
				int numOTs = currentUser.getCuenta().getTipoCuenta().getPaquete().getNumeroOrdenesTrabajo();
				if (valorItem < numOTs)
					valor = true;
				else
					util.mostrarMensaje("# ORDENES DE TRABAJO",
							"Su cuenta no permite más ingresos (" + valorItem + "/" + numOTs + ")", "F");
				break;
			case 9:
				int numIncidete = currentUser.getCuenta().getTipoCuenta().getPaquete().getNumeroIncidentes();
				if (valorItem < numIncidete)
					valor = true;
				else
					util.mostrarMensaje("# INCIDENTES",
							"Su cuenta no permite más ingresos (" + valorItem + "/" + numIncidete + ")", "F");
				break;
			default:
				valor = false;
				break;
			}

			return valor;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public Usuario getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(Usuario currentUser) {
		this.currentUser = currentUser;
	}

}
