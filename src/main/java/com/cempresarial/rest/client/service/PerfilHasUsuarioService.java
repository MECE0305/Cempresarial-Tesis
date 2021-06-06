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
import com.cempresarial.rest.client.endpoint.PerfilHasUsuarioClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.admin.model.entity.Perfil;
import com.cempresariales.servicio.commons.admin.model.entity.PerfilHasUsuario;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class PerfilHasUsuarioService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_ADMIN(); 
	//final String URL = "https://servicio-cemp-admin.herokuapp.com/";
	final String PATH = "/perfil-usuario";
	private PerfilHasUsuarioClient client = new PerfilHasUsuarioClient(URL, PATH);

	public List<PerfilHasUsuario> listar() {
		List<PerfilHasUsuario> lista = new ArrayList<>();
		try {
			lista.addAll(client.listar());

		} catch (ServiceException ex) {
			ex.toString();
		}

		return lista;
	}

	public void insertar(PerfilHasUsuario entidad) {
		client.crear(entidad);
	}

	public void actualizar(Long idTabla1, Long idTabla2, PerfilHasUsuario entidad) {
		PerfilHasUsuario obj;
		obj = client.actualizar(idTabla1, idTabla2, entidad);
	}

	public List<PerfilHasUsuario> findPerfilUsuarioByPerfil(Perfil perfil) {
		List<PerfilHasUsuario> lista = new ArrayList<>();

		lista.addAll(client.findPerfilUsuarioByPerfil(perfil));

		return lista;
	}

	public PerfilHasUsuario buscarPorId(Long idTabla1, Long idTabla2) {
		PerfilHasUsuario obj;
		obj = client.buscarPorId(idTabla1, idTabla2);
		return obj;
	}

	public void eliminar(Long idTabla1, Long idTabla2) {
		client.eliminar(idTabla1, idTabla2);
	}

}
