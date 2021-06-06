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
import com.cempresarial.rest.client.endpoint.CuentaClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.admin.model.entity.Cuenta;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class CuentaService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_ADMIN();	
	//final String URL = "https://servicio-cemp-admin.herokuapp.com/";
	final String PATH = "/cuenta";
	private CuentaClient client = new CuentaClient(URL, PATH);

	public List<Cuenta> listar() {
		List<Cuenta> lista = new ArrayList<>();
		try {
			lista.addAll(client.listar());

		} catch (ServiceException ex) {
			ex.toString();
		}

		return lista;
	}

	public void insertar(Cuenta entidad) {
		client.crear(entidad);
	}

	public void actualizar(Long id, Cuenta entidad) {
		Cuenta obj;
		obj = client.actualizar(id, entidad);
	}

	public Cuenta buscarPorId(Long id) {
		Cuenta obj;
		obj = client.buscarPorId(id);
		return obj;
	}

	public void eliminar(Long id) {
		client.eliminar(id);
	}

}
