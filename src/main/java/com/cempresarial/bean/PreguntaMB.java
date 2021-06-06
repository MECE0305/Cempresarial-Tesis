/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cempresarial.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.cempresarial.recursos.Utilitarios;
import com.cempresarial.rest.client.endpoint.CatalogoPreguntaClient;
import com.cempresarial.rest.client.service.CatalogoPreguntaService;
import com.cempresarial.rest.client.service.PreguntaService;
import com.cempresariales.servicio.commons.model.entity.CatalogoPregunta;
import com.cempresariales.servicio.commons.model.entity.Pregunta;

/**
 *
 * @author DIGETBI 05
 */
@ManagedBean
@SessionScoped
public class PreguntaMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * SERVICIOS
	 */
	@Inject
	private PreguntaService preguntaService;
	@Inject
	private CatalogoPreguntaService cpService;

	private static final Logger log = Logger.getLogger(CatalogoPreguntaClient.class.getName());

	/**
	 * VARIABLES
	 */
	private List<Pregunta> listaPregunta;
	private List<Pregunta> listaSubPregunta;
	private List<Pregunta> listaSuperPregunta;
	private List<CatalogoPregunta> listaCatalogoPregunta;

	private Pregunta pregunta;

	private Long idPregunta;
	private Long idSuperPregunta;

	private Utilitarios utils;

	/**
	 * METODOS
	 */
	@PostConstruct
	public void init() {
		clear();
	}

	public void clear() {

		listaPregunta = preguntaService.listar();

		listaSubPregunta = new ArrayList<>();
		listaSuperPregunta = new ArrayList<>();
		listaCatalogoPregunta = new ArrayList<>();
		pregunta = new Pregunta();
		utils = new Utilitarios();
		idPregunta = new Long("0");
		idSuperPregunta = new Long("0");

	}

	public void actualizarLista() {
		listaPregunta = preguntaService.listar();
	}

	public void guardar() {

		if (pregunta.getIdPregunta() == null) {
			pregunta.setCreaPregunta(new Date());
			if (idPregunta != null) {
				Pregunta catbd = preguntaService.buscarPorId(idPregunta);
				pregunta.setPreguntaIdPregunta(catbd);
			}
			preguntaService.insertar(pregunta);
			utils.mostrarMensaje("Pregunta " + pregunta.getNombrePregunta(), "Guardada Exitosamente", "I");
		} else {
			System.out.println("DATOS EXTRAS_ " + idPregunta);

			if (idPregunta != null)
				pregunta.setPreguntaIdPregunta(preguntaService.buscarPorId(idPregunta));
			preguntaService.actualizar(pregunta.getIdPregunta(), pregunta);
			utils.mostrarMensaje("Pregunta" + pregunta.getNombrePregunta(), "Activada Exitosamente", "I");
		}

		clear();
	}

	public void cambiaIdSuperPregunta() {
		idSuperPregunta = idPregunta;

		System.out.println("DATOS: " + idSuperPregunta);
	}

	public void sideActualiza() {
		try {
			if (idSuperPregunta != 0)
				pregunta.setPreguntaIdPregunta(preguntaService.buscarPorId(idSuperPregunta));
			preguntaService.actualizar(pregunta.getIdPregunta(), pregunta);
			actualizarLista();
			utils.mostrarMensaje("Pregunta " + pregunta.getNombrePregunta(), "Guardada Exitosamente", "I");
		} catch (Exception e) {
			e.printStackTrace();
			utils.mostrarMensaje("Pregunta ", "Inconveniente en actualizar registro", "E");
		}
	}

	public void switchActiva(Pregunta entidad) {

		preguntaService.actualizar(entidad.getIdPregunta(), entidad);
		if (entidad.getActivoPregunta())
			utils.mostrarMensaje("Rol " + entidad.getNombrePregunta(), "Activada Exitosamente", "I");
		else
			utils.mostrarMensaje("Rol " + entidad.getNombrePregunta(), "Desactivada Exitosamente", "w");
		actualizarLista();
	}

	public void eliminar() {
		try {
			preguntaService.eliminar(pregunta.getIdPregunta());
			pregunta = new Pregunta();
			this.actualizarLista();
			utils.mostrarMensaje("Pregunta ", "Eliminada Exitosamente !", "I");
		} catch (Exception e) {
			utils.mostrarMensaje("Eliminación", "Imposible Eliminar, Pregunta en USO", "w");
		}
	}

	public void pasarDatoseditar(Pregunta entidad) {
		pregunta = entidad;
		listaPregunta = preguntaService.listar();
		listaSuperPregunta = new ArrayList<>();
		listaCatalogoPregunta = new ArrayList<>();
		listaSubPregunta = new ArrayList<>();
		idPregunta = new Long("0");

		if (pregunta.getPreguntaIdPregunta() != null)
			idPregunta = pregunta.getPreguntaIdPregunta().getIdPregunta();

		for (CatalogoPregunta obj : cpService.listar())
			if (obj.getCatalogoPreguntaPK().getPreguntaIdPregunta() == pregunta.getIdPregunta())
				listaCatalogoPregunta.add(obj);

		for (Pregunta cat : listaPregunta)
			if (cat.getPreguntaIdPregunta() != null) {
				if (cat.getPreguntaIdPregunta().getIdPregunta() == pregunta.getIdPregunta())
					listaSubPregunta.add(cat);
			} else {
				listaSuperPregunta.add(cat);
			}

		System.out.println("DATOS EXTRAS_ " + pregunta.getPreguntaIdPregunta());
		System.out.println("DATOS EXTRAS_ " + listaCatalogoPregunta.size());
		System.out.println("DATOS EXTRAS_ " + listaSubPregunta.size());
		System.out.println("DATOS EXTRAS_ " + idPregunta);
	}

	/**
	 * SETTEGRS Y GETTERS
	 */

	public List<Pregunta> getListaPregunta() {
		return listaPregunta;
	}

	public List<Pregunta> getListaSuperPregunta() {
		return listaSuperPregunta;
	}

	public void setListaSuperPregunta(List<Pregunta> listaSuperPregunta) {
		this.listaSuperPregunta = listaSuperPregunta;
	}

	public Long getIdPregunta() {
		return idPregunta;
	}

	public void setIdPregunta(Long idPregunta) {
		this.idPregunta = idPregunta;
	}

	public void setListaPregunta(List<Pregunta> listaPregunta) {
		this.listaPregunta = listaPregunta;
	}

	public List<Pregunta> getListaSubPregunta() {
		return listaSubPregunta;
	}

	public void setListaSubPregunta(List<Pregunta> listaSubPregunta) {
		this.listaSubPregunta = listaSubPregunta;
	}

	public List<CatalogoPregunta> getListaCatalogoPregunta() {
		return listaCatalogoPregunta;
	}

	public void setListaCatalogoPregunta(List<CatalogoPregunta> listaCatalogoPregunta) {
		this.listaCatalogoPregunta = listaCatalogoPregunta;
	}

	public Pregunta getPregunta() {
		return pregunta;
	}

	public void setPregunta(Pregunta Pregunta) {
		this.pregunta = Pregunta;
	}

}
