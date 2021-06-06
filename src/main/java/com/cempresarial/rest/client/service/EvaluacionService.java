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

import com.cempresarial.entities.DTO.BuscadorDTO;
import com.cempresarial.recursos.Rutas;
import com.cempresarial.rest.client.endpoint.EvaluacionClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.model.entity.Evaluacion;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class EvaluacionService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_EVALUACIONES();
	//final String URL = "https://servicioevaluaciones.herokuapp.com/";
	final String PATH = "/evaluacion";
	private EvaluacionClient client = new EvaluacionClient(URL, PATH);

	public List<Evaluacion> listar() {
		List<Evaluacion> list = new ArrayList<>();
		try {
			list.addAll(client.listar());
		} catch (ServiceException ex) {
			ex.toString();
		}
		return list;
	}

	public List<Evaluacion> findBySegmentacion(Long idRegion, Long idZona, Long idProvincia, Long idCiudad,
			Long idZonaEstructural) {
		List<Evaluacion> list = new ArrayList<>();
		try {
			list.addAll(client.findBySegmentacion(idRegion, idZona, idProvincia, idCiudad, idZonaEstructural));
		} catch (Exception ex) {
			ex.toString();
		}
		return list;
	}
	
	public List<Evaluacion> findEvaByAgencias(List<Long> listaAgencias) {
		List<Evaluacion> list = new ArrayList<>();
		try {
			list.addAll(client.findEvaByAgencias(listaAgencias));
		} catch (Exception ex) {
			ex.toString();
		}
		return list;
	}
	
	public List<Evaluacion> findByFiltroTabClienteAndRol(Long idCliente, Long idEmpresa, Long idSector, Long idAgencia,
			Long idEmpleado, Long idRol, Long idArea) {
		List<Evaluacion> list = new ArrayList<>();
		try {
			list.addAll(client.findByFiltroTabClienteAndRol(idCliente, idEmpresa, idSector, idAgencia, idEmpleado, idRol, idArea));
		} catch (Exception ex) {
			ex.toString();
		}
		return list;
	}
	
	public List<Evaluacion> findByEvaluacionCategoria(Long idEvaluacion, Long idCategoria) {
		List<Evaluacion> list = new ArrayList<>();
		try {
			list.addAll(client.findByEvaluacionCategoria(idEvaluacion, idCategoria));
		} catch (Exception ex) {
			ex.toString();
		}
		return list;
	}
	
	

	public List<Evaluacion> findByParams(BuscadorDTO buscador) {
		List<Evaluacion> list = new ArrayList<>();
		try {
			list.addAll(client.findByParams(buscador));
		} catch (Exception ex) {
			ex.toString();
		}
		return list;
	}

	public Evaluacion insertar(Evaluacion entidad) {
		return client.crear(entidad);
	}

	public void actualizar(Long id, Evaluacion entidad) {
		Evaluacion obj;
		obj = client.actualizar(id, entidad);
	}

	public Evaluacion buscarPorId(Long id) {
		Evaluacion obj;
		obj = client.buscarPorId(id);
		return obj;
	}

	public void eliminar(Long id) {
		EvaluacionClient client = new EvaluacionClient(URL, PATH);
		client.eliminar(id);
	}
}
