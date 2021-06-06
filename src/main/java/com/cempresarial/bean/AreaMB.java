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
import com.cempresarial.rest.client.service.AreaService;
import com.cempresariales.servicio.commons.model.entity.Area;

/**
 *
 * @author DIGETBI 05
 */	
@ManagedBean
@SessionScoped
public class AreaMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * SERVICIOS
	 */
	@Inject
	private AreaService areaService;

	/**
	 * VARIABLES
	 */
	private List<Area> listaArea;

	private Area area;

	private Utilitarios utils;

	/**
	 * METODOS
	 */
	@PostConstruct
	public void init() {
		clear();
	}

	public void clear() {

		listaArea = areaService.listar();
		area = new Area();

		utils = new Utilitarios();

	}

	public void actualizarLista() {
		listaArea = areaService.listar();
	}

	public void guardar() {
		if (area.getIdArea() == null) {
			area.setCreaArea(new Date());
			areaService.insertar(area);
		} else {
			area.setActualizaArea(new Date());
			areaService.actualizar(area.getIdArea(), area);
		}

		clear();
	}

	public void switchActiva(Area entidad) {

		area.setActualizaArea(new Date());
		areaService.actualizar(entidad.getIdArea(), entidad);
		if (entidad.getActivoArea())
			utils.mostrarMensaje("Area " + entidad.getNombreArea(), "Activada Exitosamente", "I");
		else
			utils.mostrarMensaje("Area " + entidad.getNombreArea(), "Desactivada Exitosamente", "w");
		actualizarLista();
	}

	public void eliminar() {
		try {
			areaService.eliminar(area.getIdArea());
			area = new Area();
			this.actualizarLista();
			utils.mostrarMensaje("Area", "Eliminada Exitosamente !", "I");

		} catch (Exception e) {
			utils.mostrarMensaje("Eliminación", "Imposible Eliminar, Area en USO", "w");
		}
	}

	public void pasarDatoseditar(Area entidad) {
		area = entidad;
	}

	/**
	 * SETTEGRS Y GETTERS
	 */

	public List<Area> getListaArea() {
		return listaArea;
	}

	public void setListaArea(List<Area> listaArea) {
		this.listaArea = listaArea;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

}
