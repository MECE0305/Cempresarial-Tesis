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
import com.cempresarial.rest.client.endpoint.EmpleadoClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.model.entity.Agencia;
import com.cempresariales.servicio.commons.model.entity.Empleado;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class EmpleadoService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_AGENCIAS();
	//final String URL = "https://servicioagencias.herokuapp.com/";
	final String PATH = "/empleado";
	private EmpleadoClient client = new EmpleadoClient(URL, PATH);

	public List<Empleado> listar() {
		List<Empleado> list = new ArrayList<>();
		try {
			list.addAll(client.listar());
		} catch (ServiceException ex) {
			ex.toString();
		}
		return list;
	}
	
	
	public List<Empleado> findEmpleadoByAgencias(List<Long> lista) {
		List<Empleado> list = new ArrayList<>();
		try {
			list.addAll(client.findEmpleadoByAgencias(lista));
		} catch (Exception ex) {
			ex.toString();
		}
		return list;
	}
	

	public Empleado insertar(Empleado entidad) {
		return client.crear(entidad);
	}

	public void actualizar(Long id, Empleado entidad) {
		Empleado obj;
		obj = client.actualizar(id, entidad);
	}

	public Empleado buscarPorId(Long id) {
		Empleado obj;
		obj = client.buscarPorId(id);
		return obj;
	}

	public void eliminar(Long id) {
		client.eliminar(id);
	}
	
	public List<Empleado> findByAgenciaIdAgencia(Agencia agencia) {
		try {
			return client.findByAgenciaIdAgencia(agencia);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
