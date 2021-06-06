/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cempresarial.rest.client.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cempresarial.recursos.Rutas;
import com.cempresarial.rest.client.endpoint.SectorClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.model.entity.Sector;



/**
 *
 * @author DIGETBI 05
 */
public class SectorService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_CLIENTES();
	//final String URL = "https://servicioclientes.herokuapp.com/";
	final String PATH = "/sector";
	final SectorClient client = new SectorClient(URL, PATH);

	public List<Sector> listar() {
		List<Sector> list = new ArrayList<>();
		try {
			list.addAll(client.listar());

		} catch (ServiceException ex) {
			ex.toString();
		}

		return list;
	}

	public void insertar(Sector entidad) {
		client.crear(entidad);
	}

	public void actualizar(Long id, Sector Sector) {
		Sector obj;
		obj = client.actualizar(id, Sector);
	}

	public Sector buscarPorId(Long id) {
		Sector obj;
		obj = client.buscarPorId(id);
		return obj;
	}

	public void eliminar(Long id) {
		client.eliminar(id);
	}


}
