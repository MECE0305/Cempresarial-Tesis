/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cempresarial.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.cempresarial.recursos.Utilitarios;
import com.cempresarial.rest.client.endpoint.CatalogoPreguntaClient;
import com.cempresarial.rest.client.service.SectorService;
import com.cempresariales.servicio.commons.model.entity.Sector;

/**
 *
 * @author DIGETBI 05
 */
@ManagedBean
@SessionScoped
public class SectorMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * SERVICIOS
	 */
	@Inject
	private SectorService sectorService;

	private static final Logger log = Logger.getLogger(CatalogoPreguntaClient.class.getName());

	/**
	 * VARIABLES
	 */
	private List<Sector> listaSector;
	private Sector sector;

	private Utilitarios utils;


	/**
	 * METODOS
	 */
	@PostConstruct
	public void init() {
		clear();
	}

	public void clear() {
		listaSector = sectorService.listar();
		sector = new Sector();
		utils = new Utilitarios();
	}

	
	
	public void actualizarLista() {
		listaSector = sectorService.listar();
	}

	public void guardar() {
		if (sector.getIdSector() == null) {
			sector.setCreaSector(new Date());
			sectorService.insertar(sector);
			utils.mostrarMensaje("Sector " + sector.getNombreSector(), "Guardado Exitosamente", "I");
		} else {
			sector.setActualizaSector(new Date());
			sectorService.actualizar(sector.getIdSector(), sector);
			utils.mostrarMensaje("Sector " + sector.getNombreSector(), "Activado Exitosamente", "I");

		}

		clear();
	}

	public void sideActualiza() {
		try {
			sector.setActualizaSector(new Date());
			sectorService.actualizar(sector.getIdSector(), sector);
			actualizarLista();
			utils.mostrarMensaje("Sector " + sector.getNombreSector(), "Guardado Exitosamente", "I");

		} catch (Exception e) {
			utils.mostrarMensaje("Sector", "Inconveniente en actualizar registro", "E");
		}
	}

	public void switchActiva(Sector entidad) {

		entidad.setActualizaSector(new Date());
		sectorService.actualizar(entidad.getIdSector(), entidad);
		if (entidad.getActivoSector())
			utils.mostrarMensaje("Sector " + entidad.getNombreSector(), "Activada Exitosamente", "I");
		else
			utils.mostrarMensaje("Sector " + entidad.getNombreSector(), "Desactivada Exitosamente", "w");
		actualizarLista();
	}

	public void eliminar() {
		try {
			sectorService.eliminar(sector.getIdSector());
			sector = new Sector();
			this.actualizarLista();
			utils.mostrarMensaje("Sector", "Eliminado Exitosamente !", "I");

		} catch (Exception e) {
			utils.mostrarMensaje("Eliminación", "Imposible Eliminar, Sector en USO", "w");
		}
	}
	
	/**
	 * SETTEGRS Y GETTERS
	 */


	public void pasarDatoseditar(Sector entidad) {
		sector = entidad;
	}

	public List<Sector> getListaSector() {
		return listaSector;
	}

	public void setListaSector(List<Sector> listaSector) {
		this.listaSector = listaSector;
	}

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	
	

}
