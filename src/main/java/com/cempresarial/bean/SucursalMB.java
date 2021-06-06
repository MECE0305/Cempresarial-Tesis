package com.cempresarial.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.cempresarial.recursos.Utilitarios;
import com.cempresarial.rest.client.service.AgenciaService;
import com.cempresarial.rest.client.service.CiudadService;
import com.cempresarial.rest.client.service.EmpresaService;
import com.cempresarial.rest.client.service.ZonaEstructuralService;
import com.cempresariales.servicio.commons.model.entity.Agencia;
import com.cempresariales.servicio.commons.model.entity.Ciudad;
import com.cempresariales.servicio.commons.model.entity.Empresa;
import com.cempresariales.servicio.commons.model.entity.ZonaEstructural;

@ManagedBean
@SessionScoped
public class SucursalMB implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Servicios
	 **/
	@Inject
	private EmpresaService empresaService;
	@Inject
	private CiudadService ciudadService;
	@Inject
	private AgenciaService agenciaService;
	@Inject
	private ZonaEstructuralService zonaService;

	/**
	 * Variables
	 **/
	private List<Ciudad> listaCiudad;
	private List<Empresa> listaEmpresas;
	private List<Agencia> listaAgencias;
	private List<ZonaEstructural> listaZonaEstructural;

	private Agencia agencia;

	private Long idEmpresa;
	private Long idCiudad;
	private Long idZonaEstructural;

	private Utilitarios utils;

	@PostConstruct
	public void init() {
		this.clear();
	}

	public void clear() {
		listaCiudad = ciudadService.listar();
		listaEmpresas = empresaService.listar();
		listaAgencias = agenciaService.listar();

		listaZonaEstructural = zonaService.listar();

		agencia = new Agencia();

		idEmpresa = new Long("0");
		idCiudad = new Long("0");
		idZonaEstructural = new Long("0");

		utils = new Utilitarios();

	}

	/**
	 * Metodos
	 **/

	public void listarAgencias() {
		listaAgencias = agenciaService.listar();
	}

	public boolean verificarRequeridos() {
		if (idCiudad == 0 || idEmpresa == 0) {
			utils.mostrarMensaje("Agencia", "Faltan Datos, por favor revisar" + idCiudad + "********" + idEmpresa, "W");
			return false;
		} else
			return true;
	}

	public void guardar() {

		if (verificarRequeridos()) {
			Empresa empresa = empresaService.buscarPorId(idEmpresa);
			Ciudad ciudad = ciudadService.buscarPorId(idCiudad);

			agencia.setCiudadIdCiudad(ciudad);
			agencia.setEmpresaIdEmpresa(empresa);
			agencia.setIdZonaEstructural(idZonaEstructural);

			if (agencia.getIdAgencia() == null) {
				agencia.setCreaAgencia(new Date());
				agenciaService.insertar(agencia);

				utils.mostrarMensaje("Agencia", "Guardada Exitosamente", "I");

			} else {
				agenciaService.actualizar(agencia.getIdAgencia(), agencia);
				utils.mostrarMensaje("Agencia", "Actualizada Exitosamente", "I");
			}
		}

		this.clear();

	}

	public String obtenerNombreZonaEstructural(Long id) {
		ZonaEstructural obj = new ZonaEstructural();
		obj = zonaService.buscarPorId(id);
		return obj.getNombreZonaEstructural();
	}

	public void findZonaEstructuralByCiudad() {
		listaZonaEstructural = zonaService.listarByCiudad(idCiudad);
	}

	public void pasarDatoseditar(Agencia entidad) {
		agencia = entidad;
		idEmpresa = agencia.getEmpresaIdEmpresa().getIdEmpresa();
		idCiudad = agencia.getCiudadIdCiudad().getIdCiudad();
		findZonaEstructuralByCiudad();
		idZonaEstructural = agencia.getIdZonaEstructural();

	}

	public void switchActiva(Agencia entidad) {

		agenciaService.actualizar(entidad.getIdAgencia(), entidad);
		if (entidad.getActivoAgencia())
			utils.mostrarMensaje("Agencia " + entidad.getNombreAgencia(), "Activada Exitosamente", "I");
		else
			utils.mostrarMensaje("Agencia " + entidad.getNombreAgencia(), "Desactivada Exitosamente", "w");

		listarAgencias();
	}

	public void eliminar() {
		empresaService.eliminar(agencia.getIdAgencia());
		agencia = new Agencia();
		listarAgencias();
	}

	/**
	 * Setters y Getters
	 **/

	public Agencia getAgencia() {
		return agencia;
	}

	public List<ZonaEstructural> getListaZonaEstructural() {
		return listaZonaEstructural;
	}

	public void setListaZonaEstructural(List<ZonaEstructural> listaZonaEstructural) {
		this.listaZonaEstructural = listaZonaEstructural;
	}

	public Long getIdZonaEstructural() {
		return idZonaEstructural;
	}

	public void setIdZonaEstructural(Long idZonaEstructural) {
		this.idZonaEstructural = idZonaEstructural;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public List<Ciudad> getListaCiudad() {
		return listaCiudad;
	}

	public void setListaCiudad(List<Ciudad> listaCiudad) {
		this.listaCiudad = listaCiudad;
	}

	public List<Empresa> getListaEmpresas() {
		return listaEmpresas;
	}

	public void setListaEmpresas(List<Empresa> listaEmpresas) {
		this.listaEmpresas = listaEmpresas;
	}

	public List<Agencia> getListaAgencias() {
		return listaAgencias;
	}

	public void setListaAgencias(List<Agencia> listaAgencias) {
		this.listaAgencias = listaAgencias;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public Long getIdCiudad() {
		return idCiudad;
	}

	public void setIdCiudad(Long idCiudad) {
		this.idCiudad = idCiudad;
	}

}
