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
import com.cempresarial.rest.client.endpoint.TipoCuentaClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.admin.model.entity.TipoCuenta;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class TipoCuentaService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_ADMIN();
	//final String URL = "https://servicio-cemp-admin.herokuapp.com/";
	final String PATH = "/tipo-cuenta";
	private TipoCuentaClient client = new TipoCuentaClient(URL, PATH);

	public List<TipoCuenta> listar() {
		List<TipoCuenta> lista = new ArrayList<>();
		try {
			lista.addAll(client.listar());

		} catch (ServiceException ex) {
			ex.toString();
		}

		return lista;
	}

	public void insertar(TipoCuenta entidad) {
		client.crear(entidad);
	}

	public void actualizar(Long id, TipoCuenta entidad) {
		TipoCuenta obj;
		obj = client.actualizar(id, entidad);
	}

	public TipoCuenta buscarPorId(Long id) {
		TipoCuenta obj;
		obj = client.buscarPorId(id);
		return obj;
	}

	public void eliminar(Long id) {
		client.eliminar(id);
	}

}
