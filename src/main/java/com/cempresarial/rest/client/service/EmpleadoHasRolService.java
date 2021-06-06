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
import com.cempresarial.rest.client.endpoint.EmpleadoHasRolClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.model.entity.Empleado;
import com.cempresariales.servicio.commons.model.entity.Rol;
import com.cempresariales.servicio.commons.model.entity.RolHasEmpleado;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class EmpleadoHasRolService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_AGENCIAS();
	//final String URL = "https://servicioagencias.herokuapp.com/";
	final String PATH = "/empleadorol";
	private EmpleadoHasRolClient client = new EmpleadoHasRolClient(URL, PATH);

	public List<RolHasEmpleado> listar() {
		List<RolHasEmpleado> list = new ArrayList<>();
		try {
			list.addAll(client.listar());
		} catch (ServiceException ex) {
			ex.toString();
		}
		return list;
	}
	
	
	public List<RolHasEmpleado> findRolHasEmpleadoByEmpleado(Empleado entidad) {
		List<RolHasEmpleado> list = new ArrayList<>();
		try {
			list.addAll(client.findRolHasEmpleadoByEmpleado(entidad));
		} catch (Exception ex) {
			ex.toString();
		}
		return list;
	}

	public List<Empleado> findRolHasEmpleadoByRol(Rol entidad) {
		List<Empleado> list = new ArrayList<>();
		try {
			list.addAll(client.findRolHasEmpleadoByRol(entidad));
		} catch (Exception ex) {
			ex.toString();
		}
		return list;
	}
	
	public void insertar(RolHasEmpleado entidad) {
		client.crear(entidad);
	}

	public void actualizar(Long id, RolHasEmpleado entidad) {
		RolHasEmpleado obj;
		obj = client.actualizar(id, entidad);
	}

	public RolHasEmpleado buscarPorId(Long id) {
		RolHasEmpleado obj;
		obj = client.buscarPorId(id);
		return obj;
	}

	public void eliminar(Long id) {
		client.eliminar(id);
	}

}
