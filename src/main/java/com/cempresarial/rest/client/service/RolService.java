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
import com.cempresarial.rest.client.endpoint.RolClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.model.entity.Rol;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class RolService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_AGENCIAS();
	//final String URL = "https://servicioagencias.herokuapp.com/";
	final String PATH = "/rol";
	private RolClient client = new RolClient(URL, PATH);

	public List<Rol> listar() {
		List<Rol> list = new ArrayList<>();
		try {
			list.addAll(client.listar());
		} catch (ServiceException ex) {
			ex.toString();
		}
		return list;
	}
	
	public List<Rol> findRolByArea(Long idArea) {
		List<Rol> list = new ArrayList<>();
		try {
			list.addAll(client.findRolByArea(idArea));
		} catch (ServiceException ex) {
			ex.toString();
		}
		return list;
	}
	
	public List<Rol> findRolByEmpleados(List<Long> lista) {
		List<Rol> list = new ArrayList<>();
		try {
			list.addAll(client.findRolByEmpleados(lista));
		} catch (Exception ex) {
			ex.toString();
		}
		return list;
	}
	

	public void insertar(Rol entidad) {
		client.crear(entidad);
	}

	public void actualizar(Long id, Rol entidad) {
		Rol obj;
		obj = client.actualizar(id, entidad);
	}

	public Rol buscarPorId(Long id) {
		Rol obj;
		obj = client.buscarPorId(id);
		return obj;
	}

	public void eliminar(Long id) {
		RolClient client = new RolClient(URL, PATH);
		client.eliminar(id);
	}
}
