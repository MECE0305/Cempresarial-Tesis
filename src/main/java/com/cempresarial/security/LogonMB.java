package com.cempresarial.security;

import static com.github.adminfaces.starter.util.Utils.addDetailMessage;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Specializes;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Faces;

import com.cempresarial.recursos.Utilitarios;
import com.cempresarial.rest.client.service.PerfilService;
import com.cempresarial.rest.client.service.UsuarioService;
import com.cempresariales.servicio.commons.admin.model.entity.Perfil;
import com.cempresariales.servicio.commons.admin.model.entity.Usuario;
import com.github.adminfaces.template.config.AdminConfig;
import com.github.adminfaces.template.session.AdminSession;

/**
 * Created by rmpestano on 12/20/14.
 *
 * This is just a login example.
 *
 * AdminSession uses isLoggedIn to determine if user must be redirect to login
 * page or not. By default AdminSession isLoggedIn always resolves to true so it
 * will not try to redirect user.
 *
 * If you already have your authorization mechanism which controls when user
 * must be redirect to initial page or logon you can skip this class.
 */
@Named
@SessionScoped
@Specializes
public class LogonMB extends AdminSession implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Utilitarios util = new Utilitarios();

	private String currentUser;
	private String email;
	private String password;
	private boolean remember;
	@Inject
	private AdminConfig adminConfig;
	@Inject
	private UsuarioService userI;
	@Inject
	private PerfilService perfilI;
	private Perfil perfil;

	public void login() throws IOException {
		currentUser = email;
		perfil = new Perfil();

		Usuario us = new Usuario();
		us.setNombreUsuario(currentUser);
		us.setClave(password);

		Usuario usuario = userI.findByNombreUsuarioAndClave(us);
		if (usuario != null) {

			FacesContext fc = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
			HttpSession session = request.getSession();
			session.setAttribute("currentUser", usuario);

			addDetailMessage("Logged in successfully as <b>" + email + "</b>");
			Faces.getExternalContext().getFlash().setKeepMessages(true);

			for (int i = 0; i < usuario.getPerfilHasUsuarioList().size(); i++) {
				perfil = perfilI.buscarPorId(
						usuario.getPerfilHasUsuarioList().get(i).getPerfilHasUsuarioPK().getPerfilIdPerfil());
			}

			if (perfil.getNombre().toUpperCase().equals("ADMIN")) {
				Faces.redirect(adminConfig.getIndexPage());

			} else if (perfil.getNombre().toUpperCase().equals("EMPLEADO")) {
				Faces.redirect(adminConfig.getIndexPage());
				System.out.println("empleado");
			} else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/listamediciones.jsf");
				System.out.println("OTRO TIPO DE PERFIL");
			}

		} else
			errorSession();

	}

	public void errorSession() {
		try {
			String urlAplicacion = Faces.getExternalContext().getInitParameter("urlAplicacion");
			util.mostrarMensaje("CREDENCIALES INCORRECTAS", "Error en Logeo, Verificar Datos", "E");
			HttpServletRequest request = (HttpServletRequest) Faces.getExternalContext().getRequest();
			HttpSession session = request.getSession();
			session.invalidate();
			Faces.getExternalContext().getFlash().setKeepMessages(true);
			Faces.redirect(urlAplicacion);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isLoggedIn() {
		return currentUser != null;

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isRemember() {
		return remember;
	}

	public void setRemember(boolean remember) {
		this.remember = remember;
	}

	public String getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}
}
