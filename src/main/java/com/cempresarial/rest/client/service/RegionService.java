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
import com.cempresarial.rest.client.endpoint.RegionClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.model.entity.Region;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class RegionService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_REGIONES();
	//final String URL = "https://servicioregiones.herokuapp.com/";
	final String PATH = "/region";
	private RegionClient client = new RegionClient(URL, PATH);

	public List<Region> listar() {
		List<Region> lista = new ArrayList<>();
		try {
			lista.addAll(client.listar());

		} catch (ServiceException ex) {
			ex.toString();
		}

		return lista;
	}

	public void insertar(Region Region) {
		client.crear(Region);
	}

	public void actualizar(Long id, Region entidad) {
		Region obj;
		obj = client.actualizar(id, entidad);
	}

	public Region buscarPorId(Long id) {
		Region obj;
		obj = client.buscarPorId(id);
		return obj;
	}

	public void eliminar(Long id) {
		client.eliminar(id);
	}

}
