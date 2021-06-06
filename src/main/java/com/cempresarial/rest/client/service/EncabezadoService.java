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
import com.cempresarial.rest.client.endpoint.EncabezadoClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.model.entity.Encabezado;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class EncabezadoService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_EVALUACIONES();
	//final String URL = "https://servicioevaluaciones.herokuapp.com/";
	final String PATH = "/encabezado";

	public List<Encabezado> listar() {
		List<Encabezado> listaEncabezados = new ArrayList<>();
		try {
			EncabezadoClient client = new EncabezadoClient(URL, PATH);
			listaEncabezados.addAll(client.listar());

		} catch (ServiceException ex) {
			ex.toString();
		}

		return listaEncabezados;
	}

	public void insertar(Encabezado entidad) {
		EncabezadoClient client = new EncabezadoClient(URL, PATH);
		client.crear(entidad);
	}

	public void actualizar(Long id, Encabezado entidad) {
		Encabezado obj;
		EncabezadoClient client = new EncabezadoClient(URL, PATH);
		obj = client.actualizar(id, entidad);
	}

	public Encabezado buscarPorId(Long id) {
		Encabezado obj;
		EncabezadoClient client = new EncabezadoClient(URL, PATH);
		obj = client.buscarPorId(id);
		return obj;
	}

	public void eliminar(Long id) {
		EncabezadoClient client = new EncabezadoClient(URL, PATH);
		client.eliminar(id);
	}

}
