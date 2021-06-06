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

import com.cempresarial.recursos.Rutas;
import com.cempresarial.rest.client.endpoint.PerfilClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.admin.model.entity.Perfil;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class PerfilService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_ADMIN();
	//final String URL = "https://servicio-cemp-admin.herokuapp.com/";
	final String PATH = "/perfil";
	private PerfilClient client = new PerfilClient(URL, PATH);

	public List<Perfil> listar() {
		List<Perfil> lista = new ArrayList<>();
		try {
			lista.addAll(client.listar());

		} catch (ServiceException ex) {
			ex.toString();
		}

		return lista;
	}

	public List<Perfil> obtenerPerfilesUsuario(String nombreUsuario) {
		List<Perfil> lista = new ArrayList<>();

		lista.addAll(client.obtenerPerfilesUsuario(nombreUsuario));

		return lista;
	}

	public Perfil insertar(Perfil entidad) {
		return client.crear(entidad);
	}

	public void actualizar(Long id, Perfil entidad) {
		Perfil obj;
		obj = client.actualizar(id, entidad);
	}

	public Perfil buscarPorId(Long id) {
		Perfil obj;
		obj = client.buscarPorId(id);
		return obj;
	}

	public void eliminar(Long id) {
		client.eliminar(id);
	}

	public Perfil findPerfilByNombre(String nombre) {
		Perfil obj;
		obj = client.findPerfilByNombre(nombre);
		return obj;

	}

}
