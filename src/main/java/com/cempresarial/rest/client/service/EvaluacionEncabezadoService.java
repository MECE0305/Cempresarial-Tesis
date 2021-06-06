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
import com.cempresarial.rest.client.endpoint.EvaluacionEncabezadoClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.model.entity.EvaluacionHasEncabezado;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class EvaluacionEncabezadoService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_EVALUACIONES();
	//final String URL = "https://servicioevaluaciones.herokuapp.com/";
	final String PATH = "/evaluacion-encabezado";
	private EvaluacionEncabezadoClient client = new EvaluacionEncabezadoClient(URL, PATH);

	public List<EvaluacionHasEncabezado> listar() {
		List<EvaluacionHasEncabezado> list = new ArrayList<>();
		try {
			list.addAll(client.listar());
		} catch (ServiceException ex) {
			ex.toString();
		}
		return list;
	}

	public List<EvaluacionHasEncabezado> findByEncabezado(Long id) {
		List<EvaluacionHasEncabezado> list = new ArrayList<>();
		try {
			list.addAll(client.findByEncabezado(id));
		} catch (ServiceException ex) {
			ex.toString();
		}
		return list;
	}

	public List<EvaluacionHasEncabezado> findByEvaluacion(Long id) {
		List<EvaluacionHasEncabezado> list = new ArrayList<>();
		try {
			list.addAll(client.findByEvaluacion(id));
		} catch (ServiceException ex) {
			ex.toString();
		}
		return list;
	}

	public void insertar(EvaluacionHasEncabezado entidad) {
		client.crear(entidad);
	}

	public void actualizar(Long idTabla1, Long idTabla2, EvaluacionHasEncabezado entidad) {
		EvaluacionHasEncabezado obj;
		obj = client.actualizar(idTabla1, idTabla2, entidad);
	}

	public void eliminar(Long idEvaluacion, Long idEncabezado) {
		client.eliminar(idEvaluacion, idEncabezado);
	}

}
