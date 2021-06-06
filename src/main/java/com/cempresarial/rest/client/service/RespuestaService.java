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
import com.cempresarial.rest.client.endpoint.RespuestaClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.model.entity.ChecklistHasEvaluacion;
import com.cempresariales.servicio.commons.model.entity.Respuesta;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class RespuestaService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_EVALUACIONES();
	//final String URL = "https://servicioevaluaciones.herokuapp.com/";
	final String PATH = "/respuesta";
	private RespuestaClient client = new RespuestaClient(URL, PATH);

	public List<Respuesta> listar() {
		List<Respuesta> list = new ArrayList<>();
		try {
			list.addAll(client.listar());
		} catch (ServiceException ex) {
			ex.toString();
		}
		return list;
	}
	
	
	
	public List<Respuesta> findRespuestaByChecklistEvaluacion(List<ChecklistHasEvaluacion> lista) {
		List<Respuesta> list = new ArrayList<>();
		try {
			list.addAll(client.findRespuestaByChecklistEvaluacion(lista));
		} catch (Exception ex) {
			ex.toString();
		}
		return list;
	}
	
	public List<Respuesta> findRespuestaByCategoria(Long idCategoria) {
		List<Respuesta> list = new ArrayList<>();
		try {
			list.addAll(client.findRespuestaByCategoria(idCategoria));
		} catch (ServiceException ex) {
			ex.toString();
		}
		return list;
	}

	public void insertar(Respuesta entidad) {
		client.crear(entidad);
	}

	public void actualizar(Long id, Respuesta entidad) {
		Respuesta obj;
		obj = client.actualizar(id, entidad);
	}

	public Respuesta buscarPorId(Long id) {
		Respuesta obj;
		obj = client.buscarPorId(id);
		return obj;
	}

	public void eliminar(Long id) {
		RespuestaClient client = new RespuestaClient(URL, PATH);
		client.eliminar(id);
	}
	
	public List<Respuesta> findByIdEvaluacionIdChecklist(Long idEvaluacion, Long idChecklist) {
		List<Respuesta> list = new ArrayList<>();
		try {
			list.addAll(client.findByIdEvaluacionIdChecklist(idEvaluacion,idChecklist));
			
			System.out.println("VALORES SERVICE: "+idEvaluacion+"*****"+idChecklist);
			System.out.println("VALORES DE LISTA: "+list.size());
			
		} catch (ServiceException ex) {
			ex.toString();
		}
		return list;
	}
}
