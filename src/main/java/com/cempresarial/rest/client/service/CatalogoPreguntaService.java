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
import com.cempresarial.rest.client.endpoint.CatalogoPreguntaClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.model.entity.CatalogoPregunta;
import com.cempresariales.servicio.commons.model.entity.Pregunta;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class CatalogoPreguntaService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_CHECKLIST();
	//final String URL = "https://serviciochecklist.herokuapp.com/";
	final String PATH = "/catalogopregunta";

	public List<CatalogoPregunta> listar() {
		List<CatalogoPregunta> listaCatalogoPreguntas = new ArrayList<>();
		try {
			CatalogoPreguntaClient client = new CatalogoPreguntaClient(URL, PATH);
			listaCatalogoPreguntas.addAll(client.listar());

		} catch (ServiceException ex) {
			ex.toString();
		}

		return listaCatalogoPreguntas;
	}

	public List<CatalogoPregunta> findCatalogoPreguntaByIdCategoria(Long id) {
		List<CatalogoPregunta> listaCatalogoPreguntas = new ArrayList<>();
		try {
			CatalogoPreguntaClient client = new CatalogoPreguntaClient(URL, PATH);
			listaCatalogoPreguntas.addAll(client.findCatalogoPreguntaByIdCategoria(id));

		} catch (ServiceException ex) {
			ex.toString();
		}

		return listaCatalogoPreguntas;
	}

	public List<Pregunta> findPreguntasByCategoria(Long id) {
		List<Pregunta> listaPreguntas = new ArrayList<>();
		try {
			CatalogoPreguntaClient client = new CatalogoPreguntaClient(URL, PATH);
			listaPreguntas.addAll(client.findPreguntasByCategoria(id));

		} catch (ServiceException ex) {
			ex.toString();
		}

		return listaPreguntas;
	}

	public CatalogoPregunta findByIdCatalogoPregunta(Long id) {
		CatalogoPregunta obj;
		CatalogoPreguntaClient client = new CatalogoPreguntaClient(URL, PATH);
		obj = client.findByIdCatalogoPregunta(id);
		return obj;
	}

	public void insertar(CatalogoPregunta CatalogoPregunta) {
		CatalogoPreguntaClient client = new CatalogoPreguntaClient(URL, PATH);
		client.crear(CatalogoPregunta);
	}

	public void actualizar(Long id, CatalogoPregunta CatalogoPregunta) {
		CatalogoPregunta obj;
		CatalogoPreguntaClient client = new CatalogoPreguntaClient(URL, PATH);
		obj = client.actualizar(id, CatalogoPregunta);
	}

	public CatalogoPregunta buscarPorId(Long idCategoria, Long idPregunta, Long idPeso) {
		CatalogoPregunta obj;
		CatalogoPreguntaClient client = new CatalogoPreguntaClient(URL, PATH);
		obj = client.buscarPorId(idCategoria, idPregunta, idPeso);
		return obj;
	}

	public void eliminar(Long id) {
		CatalogoPreguntaClient client = new CatalogoPreguntaClient(URL, PATH);
		client.eliminar(id);
	}

}
