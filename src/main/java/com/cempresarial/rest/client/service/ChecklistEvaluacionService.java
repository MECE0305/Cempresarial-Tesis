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
import com.cempresarial.rest.client.endpoint.ChecklistEvaluacionClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.model.entity.Checklist;
import com.cempresariales.servicio.commons.model.entity.ChecklistHasEvaluacion;
import com.cempresariales.servicio.commons.model.entity.Evaluacion;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class ChecklistEvaluacionService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_EVALUACIONES();
	//final String URL = "https://servicioevaluaciones.herokuapp.com/";
	final String PATH = "/checklistevaluacion";
	private ChecklistEvaluacionClient client = new ChecklistEvaluacionClient(URL, PATH);

	public List<ChecklistHasEvaluacion> listar() {
		List<ChecklistHasEvaluacion> listaChecklistHasEvaluacions = new ArrayList<>();
		try {
			listaChecklistHasEvaluacions.addAll(client.listar());

		} catch (ServiceException ex) {
			ex.toString();
		}

		return listaChecklistHasEvaluacions;
	}

	public List<ChecklistHasEvaluacion> findByCheckListEvaluacion(Long idChecklist, Long idEvaluacion) {
		List<ChecklistHasEvaluacion> listaChecklistHasEvaluacions = new ArrayList<>();
		try {
			listaChecklistHasEvaluacions.addAll(client.findByCheckListEvaluacion(idChecklist, idEvaluacion));

		} catch (ServiceException ex) {
			ex.toString();
		}

		return listaChecklistHasEvaluacions;
	}

	public List<ChecklistHasEvaluacion> findCheckListEvaluacionByCheckList(Long idChecklist) throws ServiceException {
		List<ChecklistHasEvaluacion> list = new ArrayList<>();
		try {
			list.addAll(client.findCheckListEvaluacionByCheckList(idChecklist));

		} catch (ServiceException ex) {
			ex.toString();
		}

		return list;
	}

	public List<ChecklistHasEvaluacion> findCheckListEvaluacionByEvaluacion(Long idEvaluacion) throws ServiceException {
		List<ChecklistHasEvaluacion> list = new ArrayList<>();
		try {
			list.addAll(client.findCheckListEvaluacionByEvaluacion(idEvaluacion));

		} catch (ServiceException ex) {
			ex.toString();
		}

		return list;
	}

	public List<ChecklistHasEvaluacion> findCheckListEvaluacionByEvaluaciones(List<Long> listaAgencias) {
		List<ChecklistHasEvaluacion> list = new ArrayList<>();
		try {
			list.addAll(client.findCheckListEvaluacionByEvaluaciones(listaAgencias));
		} catch (Exception ex) {
			ex.toString();
		}
		return list;
	}

	public List<Evaluacion> findEvaluacionByIdsRol(List<Long> listRol) {
		List<Evaluacion> list = new ArrayList<>();
		try {
			list.addAll(client.findEvaluacionByIdsRol(listRol));
		} catch (Exception ex) {
			ex.toString();
		}
		return list;
	}

	public List<Evaluacion> findEvaluacionByIdsChecklist(List<Long> listChecklist) {
		List<Evaluacion> list = new ArrayList<>();
		try {
			list.addAll(client.findEvaluacionByIdsChecklist(listChecklist));
		} catch (Exception ex) {
			ex.toString();
		}
		return list;
	}

	public List<Evaluacion> findEvaluacionByCheckList(Long idChecklist) throws ServiceException {
		List<Evaluacion> list = new ArrayList<>();
		try {
			list.addAll(client.findEvaluacionByCheckList(idChecklist));

		} catch (ServiceException ex) {
			ex.toString();
		}

		return list;
	}

	public List<Checklist> findCheckListByEvaluacion(Long idEvaluacion) throws ServiceException {
		List<Checklist> list = new ArrayList<>();
		try {
			list.addAll(client.findCheckListByEvaluacion(idEvaluacion));

		} catch (ServiceException ex) {
			ex.toString();
		}

		return list;
	}

	public void insertar(ChecklistHasEvaluacion entidad) {
		client.crear(entidad);
	}

}
