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
import com.cempresarial.rest.client.endpoint.CiudadClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.model.entity.Ciudad;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class CiudadService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_REGIONES();
	//final String URL = "https://servicioregiones.herokuapp.com/";
	final String PATH = "/ciudad";
	private CiudadClient client = new CiudadClient(URL, PATH);

	public List<Ciudad> listar() {
		List<Ciudad> list = new ArrayList<>();
		try {
			list.addAll(client.listar());
		} catch (ServiceException ex) {
			ex.toString();
		}
		return list;
	}
	
	
	public List<Ciudad> findCiudadesByAgencias(List<Long> lista) {
		List<Ciudad> list = new ArrayList<>();
		try {
			list.addAll(client.findCiudadesByAgencias(lista));
		} catch (Exception ex) {
			ex.toString();
		}
		return list;
	}
	

	public void insertar(Ciudad entidad) {
		client.crear(entidad);
	}

	public void actualizar(Long id, Ciudad entidad) {
		Ciudad obj;
		obj = client.actualizar(id, entidad);
	}

	public Ciudad buscarPorId(Long id) {
		Ciudad obj;
		obj = client.buscarPorId(id);
		return obj;
	}

	public void eliminar(Long id) {
		client.eliminar(id);
	}

}
