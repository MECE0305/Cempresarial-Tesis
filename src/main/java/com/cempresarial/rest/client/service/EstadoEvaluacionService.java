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
import com.cempresarial.rest.client.endpoint.EstadoEvaluacionClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.model.entity.EstadoEvaluacion;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class EstadoEvaluacionService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_EVALUACIONES();
	//final String URL = "https://servicioevaluaciones.herokuapp.com/";
	final String PATH = "/estado-evaluacion";

	public List<EstadoEvaluacion> listar() {
		List<EstadoEvaluacion> listaEstadoEvaluacions = new ArrayList<>();
		try {
			EstadoEvaluacionClient client = new EstadoEvaluacionClient(URL, PATH);
			listaEstadoEvaluacions.addAll(client.listar());

		} catch (ServiceException ex) {
			ex.toString();
		}

		return listaEstadoEvaluacions;
	}

	public void insertar(EstadoEvaluacion entidad) {
		EstadoEvaluacionClient client = new EstadoEvaluacionClient(URL, PATH);
		client.crear(entidad);
	}

	public void actualizar(Long id, EstadoEvaluacion entidad) {
		EstadoEvaluacion obj;
		EstadoEvaluacionClient client = new EstadoEvaluacionClient(URL, PATH);
		obj = client.actualizar(id, entidad);
	}

	public EstadoEvaluacion buscarPorId(Long id) {
		EstadoEvaluacion obj;
		EstadoEvaluacionClient client = new EstadoEvaluacionClient(URL, PATH);
		obj = client.buscarPorId(id);
		return obj;
	}

	public void eliminar(Long id) {
		EstadoEvaluacionClient client = new EstadoEvaluacionClient(URL, PATH);
		client.eliminar(id);
	}

}
