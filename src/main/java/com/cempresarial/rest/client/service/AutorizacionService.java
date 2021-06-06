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
import com.cempresarial.rest.client.endpoint.AutorizacionClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.admin.model.entity.Autorizacion;
import com.cempresariales.servicio.commons.admin.model.entity.Menu;
import com.cempresariales.servicio.commons.admin.model.entity.Perfil;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class AutorizacionService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_ADMIN();
	//final String URL = "https://servicio-cemp-admin.herokuapp.com/";
	final String PATH = "/autorizacion";
	private AutorizacionClient client = new AutorizacionClient(URL, PATH);

	public List<Autorizacion> listar() {
		List<Autorizacion> lista = new ArrayList<>();
		try {
			lista.addAll(client.listar());

		} catch (ServiceException ex) {
			ex.toString();
		}

		return lista;
	}

	public List<Autorizacion> findByMenu(Menu menu) {
		List<Autorizacion> lista = new ArrayList<>();

		lista.addAll(client.findByMenu(menu));

		return lista;
	}

	public List<Autorizacion> findByPerfil(Perfil perfil) {
		List<Autorizacion> lista = new ArrayList<>();

		lista.addAll(client.findByPerfil(perfil));

		return lista;
	}

	public void insertar(Autorizacion entidad) {
		client.crear(entidad);
	}

	public void actualizar(Long id, Autorizacion entidad) {
		Autorizacion obj;
		obj = client.actualizar(id, entidad);
	}

	public Autorizacion buscarPorId(Long id) {
		Autorizacion obj;
		obj = client.buscarPorId(id);
		return obj;
	}

	public Autorizacion findByUrlMenu(String urlMenu) {
		Autorizacion obj;
		obj = client.findByUrlMenu(urlMenu);
		return obj;
	}

	public void eliminar(Long id) {
		client.eliminar(id);
	}

}
