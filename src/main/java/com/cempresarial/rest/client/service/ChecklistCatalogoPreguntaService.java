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
import com.cempresarial.rest.client.endpoint.ChecklistCatalogoPreguntaClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.model.entity.ChecklistHasCatalogoPregunta;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class ChecklistCatalogoPreguntaService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_CHECKLIST();
	//final String URL = "https://serviciochecklist.herokuapp.com/";
	final String PATH = "/checklist-catalogo-pregunta";
	private ChecklistCatalogoPreguntaClient client = new ChecklistCatalogoPreguntaClient(URL, PATH);

	public List<ChecklistHasCatalogoPregunta> listar() {
		List<ChecklistHasCatalogoPregunta> list = new ArrayList<>();
		try {
			list.addAll(client.listar());
		} catch (ServiceException ex) {
			ex.toString();
		}
		return list;
	}

	public List<ChecklistHasCatalogoPregunta> findByChecklistID(Long id) {
		List<ChecklistHasCatalogoPregunta> list = new ArrayList<>();
		try {
			list.addAll(client.findByChecklistID(id));
		} catch (ServiceException ex) {
			ex.toString();
		}
		return list;
	}
	
	public List<ChecklistHasCatalogoPregunta> findByCategoriaChecklist(Long idCategoria, Long idChecklist) {
		List<ChecklistHasCatalogoPregunta> list = new ArrayList<>();
		try {
			list.addAll(client.findByCategoriaChecklist(idCategoria, idChecklist));
		} catch (ServiceException ex) {
			ex.toString();
		}
		return list;
	}
	
	

	public List<ChecklistHasCatalogoPregunta> findByCategoria(Long id) {
		List<ChecklistHasCatalogoPregunta> list = new ArrayList<>();
		try {
			list.addAll(client.findByCategoria(id));
		} catch (ServiceException ex) {
			ex.toString();
		}
		return list;
	}

	public void insertar(ChecklistHasCatalogoPregunta entidad) {
		client.crear(entidad);
	}
	
	public void eliminar(Long idChecklist, Long idCategoria, Long idPregunta,
			Long idPeso) {
		client.eliminar(idChecklist, idCategoria, idPregunta, idPeso);
	}

}
