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
import com.cempresarial.rest.client.service.EmpresaService;
import com.cempresarial.rest.client.service.RangoService;
import com.cempresariales.servicio.commons.model.entity.Empresa;
import com.cempresariales.servicio.commons.model.entity.RangoDesempenio;

/**
 *
 * @author DIGETBI 05
 */
@ManagedBean
@SessionScoped
public class RangoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * SERVICIOS
	 */
	@Inject
	private RangoService rangoService;
	@Inject
	private EmpresaService empresaService;

	/**
	 * VARIABLES
	 */
	private List<RangoDesempenio> listaRango;
	private List<Empresa> listEmpresa;

	private Long idEmpresa;

	private RangoDesempenio rango;

	private Utilitarios utils;

	/**
	 * METODOS
	 */
	@PostConstruct
	public void init() {
		clear();
	}

	public void clear() {

		listaRango = rangoService.listar();
		listEmpresa = empresaService.listar();
		rango = new RangoDesempenio();
		idEmpresa = new Long("0");
		utils = new Utilitarios();

	}

	public void actualizarLista() {
		listaRango = rangoService.listar();
	}

	public void guardar() {
		if (idEmpresa != 0) {
			rango.setEmpresa(empresaService.buscarPorId(idEmpresa));
			if (rango.getIdRango() == null) {
				rango.setCrearango(new Date());
				rangoService.insertar(rango);
			} else {
				rango.setActualizaRango(new Date());
				rangoService.actualizar(rango.getIdRango(), rango);
			}

			utils.mostrarMensaje("Rango Desempeño", "Guardado Exitosamente", "I");
			clear();
		} else {
			utils.mostrarMensaje("Rango Desempeño", "Inconveniente al Guardar", "W");
		}
	}

	public void switchActiva(RangoDesempenio entidad) {

		rango.setActualizaRango(new Date());
		rangoService.actualizar(entidad.getIdRango(), entidad);
		if (entidad.getActivoRango())
			utils.mostrarMensaje("Rango " + entidad.getNombreRango(), "Activada Exitosamente", "I");
		else
			utils.mostrarMensaje("Rango " + entidad.getNombreRango(), "Desactivada Exitosamente", "w");
		actualizarLista();
	}

	public void eliminar() {
		try {
			rangoService.eliminar(rango.getIdRango());
			rango = new RangoDesempenio();
			this.actualizarLista();
			utils.mostrarMensaje("Rango", "Eliminada Exitosamente !", "I");

		} catch (Exception e) {
			utils.mostrarMensaje("Eliminación", "Imposible Eliminar, Rango en USO", "w");
		}
	}

	public void pasarDatoseditar(RangoDesempenio entidad) {
		rango = entidad;
		idEmpresa = entidad.getEmpresa().getIdEmpresa();
	}

	/**
	 * SETTEGRS Y GETTERS
	 */

	public List<RangoDesempenio> getListaRango() {
		return listaRango;
	}

	public List<Empresa> getListEmpresa() {
		return listEmpresa;
	}

	public void setListEmpresa(List<Empresa> listEmpresa) {
		this.listEmpresa = listEmpresa;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public void setListaRango(List<RangoDesempenio> listaRango) {
		this.listaRango = listaRango;
	}

	public RangoDesempenio getRango() {
		return rango;
	}

	public void setRango(RangoDesempenio rango) {
		this.rango = rango;
	}

}
