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
import com.cempresarial.rest.client.service.AreaService;
import com.cempresarial.rest.client.service.CatalogoPreguntaService;
import com.cempresarial.rest.client.service.CategoriaService;
import com.cempresarial.rest.client.service.ChecklistService;
import com.cempresarial.rest.client.service.EmpleadoHasRolService;
import com.cempresarial.rest.client.service.PreguntaService;
import com.cempresarial.rest.client.service.RolService;
import com.cempresariales.servicio.commons.model.entity.Area;
import com.cempresariales.servicio.commons.model.entity.Checklist;
import com.cempresariales.servicio.commons.model.entity.Empleado;
import com.cempresariales.servicio.commons.model.entity.Pregunta;
import com.cempresariales.servicio.commons.model.entity.Rol;

/**
 *
 * @author DIGETBI 05
 */
@ManagedBean
@SessionScoped
public class RolMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * SERVICIOS
	 */
	@Inject
	private RolService rolService;
	@Inject
	private CatalogoPreguntaService catalogopreguntaService;
	@Inject
	private CategoriaService categoriaService;
	@Inject
	private PreguntaService preguntaService;
	@Inject
	private ChecklistService formularioService;
	@Inject
	private EmpleadoHasRolService ehrService;
	@Inject
	private AreaService areaService;

	private static final Logger log = Logger.getLogger(CatalogoPreguntaClient.class.getName());

	/**
	 * VARIABLES
	 */
	private List<Rol> listaRol;
	private List<Empleado> listaRolEmpleados;
	private List<Checklist> listChecklist;
	private List<Area> listaArea;

	private Rol rol;

	private Long idArea;

	private Utilitarios utils;

	/**
	 * METODOS
	 */
	@PostConstruct
	public void init() {
		clear();
	}

	public void clear() {

		listaRol = rolService.listar();
		rol = new Rol();
		utils = new Utilitarios();

		idArea = new Long("0");
		
		listaArea = areaService.listar();

	}

	public String obtenerNombrePregunta(String id) {
		Pregunta p = new Pregunta();
		p = preguntaService.buscarPorId(Long.parseLong(id));
		return p.getNombrePregunta();
	}

	public void actualizarLista() {
		listaRol = rolService.listar();
	}

	public void guardar() {

		if (idArea != 0) {
			rol.setAreaIdArea(areaService.buscarPorId(idArea));
			if (rol.getIdRol() == null) {
				rol.setCreaRol(new Date());
				rolService.insertar(rol);
				utils.mostrarMensaje("Rol " + rol.getNombreRol(), "Guardado Exitosamente", "I");
			} else {
				rolService.actualizar(rol.getIdRol(), rol);
				utils.mostrarMensaje("Rol " + rol.getNombreRol(), "Activado Exitosamente", "I");

			}

			clear();
		} else {
			utils.mostrarMensaje("Área-Rol", "Debe escojer el Área para el Rol", "W");
		}
	}

	public void sideActualiza() {
		try {

			rol.setAreaIdArea(areaService.buscarPorId(idArea));
			rolService.actualizar(rol.getIdRol(), rol);
			actualizarLista();
			utils.mostrarMensaje("Rol " + rol.getNombreRol(), "Guardado Exitosamente", "I");

		} catch (Exception e) {
			utils.mostrarMensaje("Rol", "Inconveniente en actualizar registro", "E");
		}
	}

	public void switchActiva(Rol entidad) {

		rolService.actualizar(entidad.getIdRol(), entidad);
		if (entidad.getActivoRol())
			utils.mostrarMensaje("Rol " + entidad.getNombreRol(), "Activada Exitosamente", "I");
		else
			utils.mostrarMensaje("Rol " + entidad.getNombreRol(), "Desactivada Exitosamente", "w");
		actualizarLista();
	}

	public void eliminar() {
		try {
			rolService.eliminar(rol.getIdRol());
			rol = new Rol();
			this.actualizarLista();
			utils.mostrarMensaje("Rol", "Eliminado Exitosamente !", "I");

		} catch (Exception e) {
			utils.mostrarMensaje("Eliminación", "Imposible Eliminar, Rol en USO", "w");
		}
	}

	public void pasarDatoseditar(Rol entidad) {
		rol = entidad;
		idArea = entidad.getAreaIdArea().getIdArea();
		listChecklist = formularioService.findByRolIdRol(rol);
		listaRolEmpleados = ehrService.findRolHasEmpleadoByRol(rol);
	}

	/**
	 * SETTEGRS Y GETTERS
	 */
	
	

	public List<Rol> getListaRol() {
		return listaRol;
	}

	

	public List<Area> getListaArea() {
		return listaArea;
	}

	public void setListaArea(List<Area> listaArea) {
		this.listaArea = listaArea;
	}

	public Long getIdArea() {
		return idArea;
	}

	public void setIdArea(Long idArea) {
		this.idArea = idArea;
	}

	public List<Empleado> getListaRolEmpleados() {
		return listaRolEmpleados;
	}

	public void setListaRolEmpleados(List<Empleado> listaRolEmpleados) {
		this.listaRolEmpleados = listaRolEmpleados;
	}

	public List<Checklist> getListChecklist() {
		return listChecklist;
	}

	public void setListChecklist(List<Checklist> listChecklist) {
		this.listChecklist = listChecklist;
	}

	public void setListaRol(List<Rol> listaRol) {
		this.listaRol = listaRol;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

}
