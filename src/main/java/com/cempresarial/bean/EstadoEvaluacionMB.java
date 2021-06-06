/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cempresarial.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.cempresarial.recursos.Utilitarios;
import com.cempresarial.rest.client.service.EstadoEvaluacionService;
import com.cempresarial.rest.client.service.EvaluacionService;
import com.cempresariales.servicio.commons.model.entity.EstadoEvaluacion;
import com.cempresariales.servicio.commons.model.entity.Evaluacion;

/**
 *
 * @author DIGETBI 05
 */
@ManagedBean
@SessionScoped
public class EstadoEvaluacionMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * SERVICIOS
	 */
	@Inject
	private EstadoEvaluacionService estadoService;
	@Inject
	private EvaluacionService evaService;

	/**
	 * VARIABLES
	 */
	private List<EstadoEvaluacion> listaEstado;
	private List<Evaluacion> listaEvaluaciones;
	private EstadoEvaluacion estado;

	private Utilitarios utils;

	/**
	 * METODOS
	 */
	@PostConstruct
	public void init() {
		clear();
	}

	public void clear() {
		listaEstado = estadoService.listar();

		estado = new EstadoEvaluacion();

		utils = new Utilitarios();

	}

	public void actualizarLista() {
		listaEstado = estadoService.listar();
	}
	
	public void actualizarListaEvaluaciones() {
		listaEvaluaciones = evaService.listar();
	}

	public void guardar() {
		if (estado.getIdEstado() == null) {
			estado.setCreaEstado(new Date());
			estadoService.insertar(estado);
		} else {
			estadoService.actualizar(estado.getIdEstado(), estado);
		}

		clear();
	}

	public void switchActiva(EstadoEvaluacion entidad) {

		estadoService.actualizar(entidad.getIdEstado(), entidad);
		if (entidad.getActivoEstado())
			utils.mostrarMensaje("Estado Evaluación" + entidad.getNombreEstado(), "Activado Exitosamente", "I");
		else
			utils.mostrarMensaje("Estado Evaluación" + entidad.getNombreEstado(), "Desactivado Exitosamente", "w");

		actualizarLista();
	}

	public void eliminar() {
		try {
			estadoService.eliminar(estado.getIdEstado());
			estado = new EstadoEvaluacion();
			this.actualizarLista();
			utils.mostrarMensaje("Estado Evaluación", "Eliminado Exitosamente !", "I");

		} catch (Exception e) {
			utils.mostrarMensaje("Eliminación", "Imposible Eliminar, Estado evaluación en USO", "w");
		}
	}
	
	public void pasarDatoseditar(EstadoEvaluacion entidad) {
		estado = entidad;
		listaEvaluaciones = estado.getEvaluacionList();
	}

	
	
	/**
	 * SETTEGRS Y GETTERS
	 */
	

	
	public List<EstadoEvaluacion> getListaEstado() {
		return listaEstado;
	}

	public void setListaEstado(List<EstadoEvaluacion> listaEstado) {
		this.listaEstado = listaEstado;
	}

	public List<Evaluacion> getListaEvaluaciones() {
		return listaEvaluaciones;
	}

	public void setListaEvaluaciones(List<Evaluacion> listaEvaluaciones) {
		this.listaEvaluaciones = listaEvaluaciones;
	}

	public EstadoEvaluacion getEstado() {
		return estado;
	}

	public void setEstado(EstadoEvaluacion estado) {
		this.estado = estado;
	}

	

}
