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
import com.cempresarial.rest.client.service.PesoService;
import com.cempresariales.servicio.commons.model.entity.CatalogoPregunta;
import com.cempresariales.servicio.commons.model.entity.Peso;

/**
 *
 * @author DIGETBI 05
 */
@ManagedBean
@SessionScoped
public class PesoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * SERVICIOS
	 */
	@Inject
	private PesoService pesoService;
	@Inject
	private CatalogoPreguntaService cpService;

	private static final Logger log = Logger.getLogger(CatalogoPreguntaClient.class.getName());

	/**
	 * VARIABLES
	 */
	private List<Peso> listaPeso;
	private List<CatalogoPregunta> listaCatalogoPregunta;

	private Peso peso;

	private Utilitarios utils;

	/**
	 * METODOS
	 */
	@PostConstruct
	public void init() {
		clear();
	}

	public void clear() {

		listaPeso = pesoService.listar();
		peso = new Peso();
		utils = new Utilitarios();

	}

	public void actualizarLista() {
		listaPeso = pesoService.listar();
	}

	public void guardar() {

		if (peso.getIdPeso() == null) {
			peso.setCreaPeso(new Date());
			pesoService.insertar(peso);
			utils.mostrarMensaje("Peso " + peso.getValor(), "Guardada Exitosamente", "I");
		} else {

			pesoService.actualizar(peso.getIdPeso(), peso);
			utils.mostrarMensaje("Peso" + peso.getValor(), "Actualizado Exitosamente", "I");
		}

		clear();
	}

	public void sideActualiza() {
		try {
			pesoService.actualizar(peso.getIdPeso(), peso);
			actualizarLista();
			utils.mostrarMensaje("Categoria " + peso.getValor(), "Guardada Exitosamente", "I");
		} catch (Exception e) {
			e.printStackTrace();
			utils.mostrarMensaje("Categoria ", "Inconveniente en actualizar registro", "E");
		}
	}

	public void switchActiva(Peso entidad) {

		pesoService.actualizar(entidad.getIdPeso(), entidad);
		if (entidad.isActivoPeso())
			utils.mostrarMensaje("Rol " + entidad.getValor(), "Activada Exitosamente", "I");
		else
			utils.mostrarMensaje("Rol " + entidad.getValor(), "Desactivada Exitosamente", "w");
		actualizarLista();
	}

	public void eliminar() {
		try {
			pesoService.eliminar(peso.getIdPeso());
			peso = new Peso();
			this.actualizarLista();
			utils.mostrarMensaje("Peso ", "Eliminado Exitosamente !", "I");
		} catch (Exception e) {
			utils.mostrarMensaje("Eliminación", "Imposible Eliminar, Peso en USO", "w");
		}
	}

	public void pasarDatoseditar(Peso entidad) {
		peso = entidad;
		listaCatalogoPregunta = new ArrayList<>();

		for (CatalogoPregunta obj : cpService.listar())
			if (obj.getCatalogoPreguntaPK().getPesoIdPeso() == peso.getIdPeso())
				listaCatalogoPregunta.add(obj);

	}

	/**
	 * SETTEGRS Y GETTERS
	 */
	public List<Peso> getListaPeso() {
		return listaPeso;
	}

	public void setListaPeso(List<Peso> listaPeso) {
		this.listaPeso = listaPeso;
	}

	public List<CatalogoPregunta> getListaCatalogoPregunta() {
		return listaCatalogoPregunta;
	}

	public void setListaCatalogoPregunta(List<CatalogoPregunta> listaCatalogoPregunta) {
		this.listaCatalogoPregunta = listaCatalogoPregunta;
	}

	public Peso getPeso() {
		return peso;
	}

	public void setPeso(Peso peso) {
		this.peso = peso;
	}

}
