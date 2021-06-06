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
import com.cempresarial.rest.client.endpoint.PaquetesClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.admin.model.entity.Paquetes;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class PaquetesService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_ADMIN();	
	//final String URL = "https://servicio-cemp-admin.herokuapp.com/";
	final String PATH = "/paquetes";
	private PaquetesClient client = new PaquetesClient(URL, PATH);

	public List<Paquetes> listar() {
		List<Paquetes> lista = new ArrayList<>();
		try {
			lista.addAll(client.listar());

		} catch (ServiceException ex) {
			ex.toString();
		}

		return lista;
	}

	public void insertar(Paquetes entidad) {
		client.crear(entidad);
	}

	public void actualizar(Long id, Paquetes entidad) {
		Paquetes obj;
		obj = client.actualizar(id, entidad);
	}

	public Paquetes buscarPorId(Long id) {
		Paquetes obj;
		obj = client.buscarPorId(id);
		return obj;
	}

	public void eliminar(Long id) {
		client.eliminar(id);
	}

}
