/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cempresarial.rest.client.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cempresarial.recursos.Password;
import com.cempresarial.recursos.Rutas;
import com.cempresarial.rest.client.endpoint.UsuarioClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.admin.model.entity.Usuario;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class UsuarioService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_ADMIN();
	//final String URL = "https://servicio-cemp-admin.herokuapp.com/";
	final String PATH = "/usuario";
	private UsuarioClient client = new UsuarioClient(URL, PATH);

	public void verificaCurrentser() {
		Usuario usuario = new Usuario();
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
		HttpSession session = request.getSession();
		usuario = (Usuario) session.getAttribute("currentUser");

		System.out.println("El usuario logeado es:" + usuario);
	}

	public List<Usuario> listar() {
		List<Usuario> lista = new ArrayList<>();
		try {
			lista.addAll(client.listar());

		} catch (ServiceException ex) {
			ex.toString();
		}

		return lista;
	}

	public Usuario insertar(Usuario entidad) {
		return client.crear(entidad);
	}

	public Usuario actualizar(Long id, Usuario entidad) {
		Usuario obj;
		obj = client.actualizar(id, entidad);
		return obj;
	}
	public Usuario restablecer(Long id, Usuario entidad) {
		Usuario obj;
		obj = client.restablecer(id, entidad);
		return obj;
	}

	public Usuario buscarPorId(Long id) {
		Usuario obj;
		obj = client.buscarPorId(id);
		return obj;
	}

	public Usuario findByNombreUsuario(Usuario entidad) {
		try {
			Usuario obj;
			obj = client.findByNombreUsuario(entidad);
			return obj;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}

	}

	public Usuario findByNombreUsuarioAndClave(Usuario entidad) {
		try {
			Usuario obj;
			obj = findByNombreUsuario(entidad);
			Password pass = new Password();
			if (obj != null) {
				if (pass.checkPassword(entidad.getClave(), obj.getClave()))
					return obj;
				else
					return null;
			} else
				return null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}

	}

	public void eliminar(Long id) {
		client.eliminar(id);
	}

}
