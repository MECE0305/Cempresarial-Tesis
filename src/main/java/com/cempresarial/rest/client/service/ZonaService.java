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
import com.cempresarial.rest.client.endpoint.ZonaClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.model.entity.Zona;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class ZonaService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_REGIONES();
	//final String URL = "https://servicioregiones.herokuapp.com/";
	final String PATH = "/zona";
	private ZonaClient client = new ZonaClient(URL, PATH);

	public List<Zona> listar() {
		List<Zona> lista = new ArrayList<>();
		try {
			lista.addAll(client.listar());

		} catch (ServiceException ex) {
			ex.toString();
		}

		return lista;
	}

	public void insertar(Zona entidad) {
		client.crear(entidad);
	}

	public void actualizar(Long id, Zona entidad) {
		Zona obj;
		obj = client.actualizar(id, entidad);
	}

	public Zona buscarPorId(Long id) {
		Zona obj;
		obj = client.buscarPorId(id);
		return obj;
	}

	public void eliminar(Long id) {
		client.eliminar(id);
	}

}
