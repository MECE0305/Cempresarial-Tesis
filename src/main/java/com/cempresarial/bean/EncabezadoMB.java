/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cempresarial.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.cempresarial.recursos.Utilitarios;
import com.cempresarial.rest.client.service.EncabezadoService;
import com.cempresarial.rest.client.service.EvaluacionEncabezadoService;
import com.cempresarial.rest.client.service.EvaluacionService;
import com.cempresariales.servicio.commons.model.entity.Encabezado;
import com.cempresariales.servicio.commons.model.entity.Evaluacion;
import com.cempresariales.servicio.commons.model.entity.EvaluacionHasEncabezado;

/**
 *
 * @author DIGETBI 05
 */
@ManagedBean
@SessionScoped
public class EncabezadoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * SERVICIOS
	 */
	@Inject
	private EncabezadoService encabezadoService;
	@Inject
	private EvaluacionService evaService;
	@Inject
	private EvaluacionEncabezadoService eeService;

	/**
	 * VARIABLES
	 */
	private List<Encabezado> listaencabezado;
	private List<Encabezado> listaencabezadoSelect;

	private List<EvaluacionHasEncabezado> listaEvaluacionesEncabezado;
	private Encabezado encabezado;

	private Utilitarios utils;

	/**
	 * METODOS
	 */
	@PostConstruct
	public void init() {
		clear();
	}

	public void clear() {
		listaencabezado = encabezadoService.listar();

		encabezado = new Encabezado();

		utils = new Utilitarios();

	}

	public void actualizarLista() {
		listaencabezado = encabezadoService.listar();
	}

	public void guardar() {
		if (encabezado.getIdEncabezado() == null) {
			encabezadoService.insertar(encabezado);
		} else {
			encabezadoService.actualizar(encabezado.getIdEncabezado(), encabezado);
		}

		clear();
	}

	public void switchActiva(Encabezado entidad) {

		encabezadoService.actualizar(entidad.getIdEncabezado(), entidad);
		if (entidad.getEstadoEncabezado())
			utils.mostrarMensaje("Encabezado" + entidad.getNombreEncabezado(), "Activado Exitosamente", "I");
		else
			utils.mostrarMensaje("Encabezado" + entidad.getNombreEncabezado(), "Desactivado Exitosamente", "w");

		actualizarLista();
	}

	public void eliminar() {
		try {
			encabezadoService.eliminar(encabezado.getIdEncabezado());
			encabezado = new Encabezado();
			this.actualizarLista();
			utils.mostrarMensaje("Encabezado", "Eliminado Exitosamente !", "I");

		} catch (Exception e) {
			utils.mostrarMensaje("Eliminación", "Imposible Eliminar, Encabezado en USO", "w");
		}
	}

	public void pasarDatoseditar(Encabezado entidad) {
		encabezado = entidad;

		listaEvaluacionesEncabezado = eeService.findByEncabezado(entidad.getIdEncabezado());

		for (int x = 0; x < listaEvaluacionesEncabezado.size(); x++) {
			Encabezado enc = encabezadoService.buscarPorId(
					listaEvaluacionesEncabezado.get(x).getEvaluacionHasEncabezadoPK().getEncabezadoIdEncabezado());
			Evaluacion eva = evaService.buscarPorId(
					listaEvaluacionesEncabezado.get(x).getEvaluacionHasEncabezadoPK().getEvaluacionIdEvaluacion());
			listaEvaluacionesEncabezado.get(x).setEncabezado(enc);
			listaEvaluacionesEncabezado.get(x).setEvaluacion(eva);

		}

	}

	/**
	 * SETTEGRS Y GETTERS
	 */

	public List<Encabezado> getListaencabezadoSelect() {
		return listaencabezadoSelect;
	}

	public void setListaencabezadoSelect(List<Encabezado> listaencabezadoSelect) {
		this.listaencabezadoSelect = listaencabezadoSelect;
	}

	public List<Encabezado> getListaencabezado() {
		return listaencabezado;
	}

	public void setListaencabezado(List<Encabezado> listaencabezado) {
		this.listaencabezado = listaencabezado;
	}

	public List<EvaluacionHasEncabezado> getListaEvaluacionesEncabezado() {
		return listaEvaluacionesEncabezado;
	}

	public void setListaEvaluacionesEncabezado(List<EvaluacionHasEncabezado> listaEvaluacionesEncabezado) {
		this.listaEvaluacionesEncabezado = listaEvaluacionesEncabezado;
	}

	public Encabezado getEncabezado() {
		return encabezado;
	}

	public void setEncabezado(Encabezado encabezado) {
		this.encabezado = encabezado;
	}

}
