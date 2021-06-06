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
import com.cempresarial.rest.client.endpoint.AreaClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.model.entity.Area;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class AreaService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_AGENCIAS();
	//final String URL = "https://servicioagencias.herokuapp.com/";
	final String PATH = "/area";
	private AreaClient client = new AreaClient(URL, PATH);

	public List<Area> listar() {
		List<Area> lista = new ArrayList<>();
		try {
			lista.addAll(client.listar());

		} catch (ServiceException ex) {
			ex.toString();
		}

		return lista;
	}
	
	public List<Area> findAreasByRoles(List<Long> lista) {
		List<Area> list = new ArrayList<>();
		try {
			list.addAll(client.findAreasByRoles(lista));
		} catch (Exception ex) {
			ex.toString();
		}
		return list;
	}
	

	public void insertar(Area entidad) {
		client.crear(entidad);
	}

	public void actualizar(Long id, Area entidad) {
		Area obj;
		obj = client.actualizar(id, entidad);
	}

	public Area buscarPorId(Long id) {
		Area obj;
		obj = client.buscarPorId(id);
		return obj;
	}

	public void eliminar(Long id) {
		client.eliminar(id);
	}

}
