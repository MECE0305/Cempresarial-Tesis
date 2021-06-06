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
import com.cempresarial.rest.client.endpoint.ZonaEstructuralClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.model.entity.ZonaEstructural;
import com.cempresariales.servicio.commons.model.entity.ZonaEstructuralHasCiudad;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class ZonaEstructuralService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_REGIONES();
	//final String URL = "https://servicioregiones.herokuapp.com/";
	final String PATH = "/zona-estructural";
	private ZonaEstructuralClient client = new ZonaEstructuralClient(URL, PATH);

	public List<ZonaEstructural> listar() {
		List<ZonaEstructural> list = new ArrayList<>();
		try {
			list.addAll(client.listar());
		} catch (ServiceException ex) {
			ex.toString();
		}
		return list;
	}

	public List<ZonaEstructural> listarByCiudad(Long id) {
		List<ZonaEstructural> list = new ArrayList<>();
		try {
			list.addAll(client.listarXCiudad(id));
		} catch (ServiceException ex) {
			ex.toString();
		}
		return list;
	}

	public List<ZonaEstructuralHasCiudad> listarZHCByZonaEstructural(Long id) {
		List<ZonaEstructuralHasCiudad> list = new ArrayList<>();
		try {
			list.addAll(client.listarZHCXZonaEstructural(id));
		} catch (ServiceException ex) {
			ex.toString();
		}
		return list;
	}

	public void insertar(ZonaEstructural entidad) {
		client.crear(entidad);
	}

	public void actualizar(Long id, ZonaEstructural entidad) {
		ZonaEstructural obj;
		obj = client.actualizar(id, entidad);
	}

	public ZonaEstructural buscarPorId(Long id) {
		ZonaEstructural obj;
		obj = client.buscarPorId(id);
		return obj;
	}

	public void eliminar(Long id) {
		client.eliminar(id);
	}

}
