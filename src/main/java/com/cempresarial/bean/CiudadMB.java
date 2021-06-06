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
import com.cempresarial.rest.client.service.CiudadService;
import com.cempresarial.rest.client.service.ProvinciaService;
import com.cempresariales.servicio.commons.model.entity.Ciudad;
import com.cempresariales.servicio.commons.model.entity.Provincia;

/**
 *
 * @author DIGETBI 05
 */
@ManagedBean
@SessionScoped
public class CiudadMB implements Serializable {

	/**
	 * SERVICIOS
	 **/
	@Inject
	private CiudadService ciudadService;
	@Inject
	private ProvinciaService provinciaService;

	/**
	 * VARIABLES
	 **/
	private Ciudad ciudad;

	private List<Ciudad> listaCiudad;
	private List<Provincia> listaProvincia;

	private Long idProvincia;

	private Utilitarios utils;

	/**
	 * METODOS
	 **/
	@PostConstruct
	public void init() {
		clear();
	}

	public void clear() {

		listaCiudad = ciudadService.listar();
		listaProvincia = provinciaService.listar();
		ciudad = new Ciudad();
		idProvincia = new Long(0);
		utils = new Utilitarios();

	}

	public void actualizarLista() {
		listaCiudad = ciudadService.listar();
	}

	public void guardar() {

		if (ciudad.getIdCiudad() == null) {
			if (idProvincia != 0) {
				ciudad.setCreaCiudad(new Date());
				ciudad.setProvinciaIdProvincia(provinciaService.buscarPorId(idProvincia));
				ciudadService.insertar(ciudad);
				utils.mostrarMensaje("Ciudad", "Guardado Exitosamente", "I");
			} else {
				utils.mostrarMensaje("Ciudad", "Debe escoger una Provincia primero", "W");
			}
		} else {
			if (idProvincia != ciudad.getProvinciaIdProvincia().getIdProvincia()) {
				ciudad.setProvinciaIdProvincia(provinciaService.buscarPorId(idProvincia));
			}
			ciudadService.actualizar(ciudad.getIdCiudad(), ciudad);
			utils.mostrarMensaje("Ciudad", "Actualizado Exitosamente", "I");
		}
		this.clear();
	}

	public void sideActualiza() {
		if (idProvincia != ciudad.getProvinciaIdProvincia().getIdProvincia()) {
			ciudad.setProvinciaIdProvincia(provinciaService.buscarPorId(idProvincia));
		}
		ciudadService.actualizar(ciudad.getIdCiudad(), ciudad);
		utils.mostrarMensaje("Ciudad", "Actualizado Exitosamente", "I");
		actualizarLista();
	}

	public void switchActiva(Ciudad entidad) {

		ciudadService.actualizar(entidad.getIdCiudad(), entidad);
		if (entidad.getActivoCiudad())
			utils.mostrarMensaje("Ciudad" + ciudad.getNombreCiudad(), "Activado Exitosamente", "I");
		else
			utils.mostrarMensaje("Ciudad" + ciudad.getNombreCiudad(), "Desactivado Exitosamente", "w");

		actualizarLista();
	}

	public void pasarDatoseditar(Ciudad entidad) {
		try {
			ciudad = entidad;
			idProvincia = entidad.getProvinciaIdProvincia().getIdProvincia();
			listaProvincia = provinciaService.listar();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void eliminar() {
		try {
			ciudadService.eliminar(ciudad.getIdCiudad());
			ciudad = new Ciudad();
			this.actualizarLista();
			utils.mostrarMensaje("Ciudad", "Eliminado Exitosamente !", "I");

		} catch (Exception e) {
			utils.mostrarMensaje("Eliminación", "Imposible Eliminar, Ciudad en USO", "w");
		}
	}

	/**
	 * SETTERS y GETTERS
	 **/
	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	public List<Ciudad> getListaCiudad() {
		return listaCiudad;
	}

	public void setListaCiudad(List<Ciudad> listaCiudad) {
		this.listaCiudad = listaCiudad;
	}

	public List<Provincia> getListaProvincia() {
		return listaProvincia;
	}

	public void setListaProvincia(List<Provincia> listaProvincia) {
		this.listaProvincia = listaProvincia;
	}

	public Long getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(Long idProvincia) {
		this.idProvincia = idProvincia;
	}

}
