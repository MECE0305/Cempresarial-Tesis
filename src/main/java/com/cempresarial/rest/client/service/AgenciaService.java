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
import com.cempresarial.rest.client.endpoint.AgenciaClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.model.entity.Agencia;
import com.cempresariales.servicio.commons.model.entity.Empresa;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class AgenciaService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_AGENCIAS();
	//final String URL = "https://servicioagencias.herokuapp.com/";
	final String PATH = "/agencia";
	private AgenciaClient client = new AgenciaClient(URL, PATH);

	public List<Agencia> listar() {
		List<Agencia> list = new ArrayList<>();
		try {
			list.addAll(client.listar());
		} catch (ServiceException ex) {
			ex.toString();
		}
		return list;
	}

	public void insertar(Agencia entidad) {
		client.crear(entidad);
	}

	public void actualizar(Long id, Agencia entidad) {
		Agencia obj;
		obj = client.actualizar(id, entidad);
	}

	public Agencia buscarPorId(Long id) {
		Agencia obj;
		obj = client.buscarPorId(id);
		return obj;
	}

	public void eliminar(Long id) {
		client.eliminar(id);
	}

	public List<Agencia> findByEmpresaIdEmpresa(Empresa empresa) {
		try {
			AgenciaClient client = new AgenciaClient(URL, PATH);
			return client.findByEmpresaIdEmpresa(empresa);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
