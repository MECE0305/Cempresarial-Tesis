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
import com.cempresarial.rest.client.endpoint.PreguntaClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.model.entity.Pregunta;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class PreguntaService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_CHECKLIST();
	//final String URL = "https://serviciochecklist.herokuapp.com/";
	final String PATH = "/pregunta";
	private PreguntaClient client = new PreguntaClient(URL, PATH);

	public List<Pregunta> listar() {
		List<Pregunta> list = new ArrayList<>();
		try {
			list.addAll(client.listar());
		} catch (ServiceException ex) {
			ex.toString();
		}
		return list;
	}
	
	public List<Pregunta> findPreguntasByCategorias(List<Long> lista) {
		List<Pregunta> list = new ArrayList<>();
		try {
			list.addAll(client.findPreguntasByCategorias(lista));
		} catch (Exception ex) {
			ex.toString();
		}
		return list;
	}

	public void insertar(Pregunta entidad) {
		client.crear(entidad);
	}

	public void actualizar(Long id, Pregunta entidad) {
		Pregunta obj;
		obj = client.actualizar(id, entidad);
	}

	public Pregunta buscarPorId(Long id) {
		Pregunta obj;
		obj = client.buscarPorId(id);
		return obj;
	}

	public void eliminar(Long id) {
		PreguntaClient client = new PreguntaClient(URL, PATH);
		client.eliminar(id);
	}
}
