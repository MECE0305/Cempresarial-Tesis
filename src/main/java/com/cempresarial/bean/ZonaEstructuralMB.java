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

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.cempresarial.recursos.Utilitarios;
import com.cempresarial.rest.client.service.CiudadService;
import com.cempresarial.rest.client.service.ZonaEstructuralService;
import com.cempresariales.servicio.commons.model.entity.Ciudad;
import com.cempresariales.servicio.commons.model.entity.ZonaEstructural;
import com.cempresariales.servicio.commons.model.entity.ZonaEstructuralHasCiudad;
import com.cempresariales.servicio.commons.model.entity.ZonaEstructuralHasCiudadPK;

/**
 *
 * @author DIGETBI 05
 */
@ManagedBean
@SessionScoped
public class ZonaEstructuralMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * SERVICIOS
	 */
	@Inject
	private ZonaEstructuralService zonaService;
	@Inject
	private CiudadService ciudadService;

	/**
	 * VARIABLES
	 */
	private List<ZonaEstructural> listaZona;
	private List<Ciudad> listaCiudad;

	private List<ZonaEstructural> listaZonaCheck;
	private List<Long> listaCiudadCheck;

	private List<ZonaEstructuralHasCiudad> listaZonaHasCiudad;

	private ZonaEstructural zona;
	private Ciudad ciudad;

	private Utilitarios utils;

	/**
	 * METODOS
	 */
	@PostConstruct
	public void init() {
		clear();
	}

	public void clear() {
		listaZona = zonaService.listar();
		//listaZonaCheck = zonaService.listar();
		listaCiudad = ciudadService.listar();

		listaCiudadCheck = new ArrayList<>();
		listaZonaHasCiudad = new ArrayList<>();

		ciudad = new Ciudad();
		zona = new ZonaEstructural();

		utils = new Utilitarios();

	}

	public void actualizarLista() {
		listaZona = zonaService.listar();
	}

	public void guardar() {
		if (zona.getIdZonaEstructural() == null) {
			zona.setFechaCreacionZonaEstructural(new Date());
			zonaService.insertar(zona);
		} else {
			zona.setFechaActualizaZonaEstructural(new Date());
			zonaService.actualizar(zona.getIdZonaEstructural(), zona);
		}

		clear();
	}

	public void sideActualiza() {
		try {
			List<ZonaEstructuralHasCiudad> rz = new ArrayList<>();
			boolean match = false;

			// esta en base pero no en check, quiere decir que le puso false
			match = false;
			for (int y = 0; y < listaZonaHasCiudad.size(); y++) {
				match = false;
				ZonaEstructuralHasCiudad obj = listaZonaHasCiudad.get(y);
				for (int x = 0; x < listaCiudadCheck.size(); x++) {
					Long id = listaCiudadCheck.get(x);

					if (obj.getZonaEstructuralHasCiudadPK().getZonaEstructuralIdCiudad() ==id) {
						match = true;
						obj.setActivoZonaEstructuralHasCiudad(true);
						rz.add(obj);
						break;
					}
				}
				if (!match) {
					System.out.println("NO ENCUENTRA EN BASE y TANBN EN CHECK:*********"
							+ obj.getZonaEstructuralHasCiudadPK().getZonaEstructuralIdCiudad());

					obj.setActivoZonaEstructuralHasCiudad(false);
					rz.add(obj);
				}
			}

			// esta en check pero no en base, quiere decir que agrega un nuevo registro
			for (Long idStr : listaCiudadCheck) {
				match = false;
				for (int x = 0; x < listaZonaHasCiudad.size(); x++) {
					Long id = listaZonaHasCiudad.get(x).getZonaEstructuralHasCiudadPK().getZonaEstructuralIdCiudad();
					ZonaEstructuralHasCiudad obj = new ZonaEstructuralHasCiudad();
					obj = listaZonaHasCiudad.get(x);
					if (idStr == id) {
						match = true;
						break;
					}
				}
				if (!match) {
					Ciudad ciu = ciudadService.buscarPorId(idStr);
					ZonaEstructuralHasCiudad zhc = new ZonaEstructuralHasCiudad();
					ZonaEstructuralHasCiudadPK pk = new ZonaEstructuralHasCiudadPK();
					pk.setZonaEstructuralIdCiudad(ciu.getIdCiudad());
					pk.setZonaEstructuralIdZonaEstructural(zona.getIdZonaEstructural());
					// rhz.setRegion(region);
					// rhz.setZona(zona);
					zhc.setZonaEstructuralHasCiudadPK(pk);
					zhc.setActivoZonaEstructuralHasCiudad(true);
					rz.add(zhc);
				}
			}

			zona.setZonaEstructuralHasCiudadList(rz);
			zona.setFechaActualizaZonaEstructural(new Date());
			zonaService.actualizar(zona.getIdZonaEstructural(), zona);
			actualizarLista();

			utils.mostrarMensaje("Zona", "Se han agregado #" + listaCiudadCheck.size() + " ciudades a la Zona", "W");

			pasarDatoseditar(zonaService.buscarPorId(zona.getIdZonaEstructural()));

		} catch (Exception e) {
			e.printStackTrace();
			utils.mostrarMensaje("Zona - Ciudad", "Ciudades en existencia", "E");
		}
	}

	public void switchActiva(ZonaEstructural entidad) {

		zona.setFechaActualizaZonaEstructural(new Date());
		zonaService.actualizar(entidad.getIdZonaEstructural(), entidad);
		if (entidad.getActivoZonaEstructural())
			utils.mostrarMensaje("Zona " + entidad.getNombreZonaEstructural(), "Activada Exitosamente", "I");
		else
			utils.mostrarMensaje("Zona " + entidad.getNombreZonaEstructural(), "Desactivada Exitosamente", "w");
		actualizarLista();
	}

	public void eliminar() {
		try {
			zonaService.eliminar(zona.getIdZonaEstructural());
			zona = new ZonaEstructural();
			this.actualizarLista();
			utils.mostrarMensaje("Zona", "Eliminada Exitosamente !", "I");

		} catch (Exception e) {
			utils.mostrarMensaje("Eliminación", "Imposible Eliminar, Zona en USO", "w");
		}
	}

	public void pasarDatoseditar(ZonaEstructural entidad) {
		zona = entidad;
		listaCiudadCheck = new ArrayList<>();

		listaZonaHasCiudad = zonaService.listarZHCByZonaEstructural(entidad.getIdZonaEstructural());
		// listaZonaHasCiudad = entidad.getZonaEstructuralHasCiudadList();
		if (listaZonaHasCiudad.size() > 0) {
			for (int x = 0; x < listaZonaHasCiudad.size(); x++) {
				if (listaZonaHasCiudad.get(x).getActivoZonaEstructuralHasCiudad().equals(true)) {
					listaCiudadCheck.add(
							listaZonaHasCiudad.get(x).getZonaEstructuralHasCiudadPK().getZonaEstructuralIdCiudad());
				}
			}
		}
	}

	/**
	 * SETTEGRS Y GETTERS
	 */

	public List<ZonaEstructural> getListaZona() {
		return listaZona;
	}

	public void setListaZona(List<ZonaEstructural> listaZona) {
		this.listaZona = listaZona;
	}

	public List<Ciudad> getListaCiudad() {
		return listaCiudad;
	}

	public void setListaCiudad(List<Ciudad> listaCiudad) {
		this.listaCiudad = listaCiudad;
	}

	public List<ZonaEstructural> getListaZonaCheck() {
		return listaZonaCheck;
	}

	public void setListaZonaCheck(List<ZonaEstructural> listaZonaCheck) {
		this.listaZonaCheck = listaZonaCheck;
	}

	public List<Long> getListaCiudadCheck() {
		return listaCiudadCheck;
	}

	public void setListaCiudadCheck(List<Long> listaCiudadCheck) {
		this.listaCiudadCheck = listaCiudadCheck;
	}

	public List<ZonaEstructuralHasCiudad> getListaZonaHasCiudad() {
		return listaZonaHasCiudad;
	}

	public void setListaZonaHasCiudad(List<ZonaEstructuralHasCiudad> listaZonaHasCiudad) {
		this.listaZonaHasCiudad = listaZonaHasCiudad;
	}

	public ZonaEstructural getZona() {
		return zona;
	}

	public void setZona(ZonaEstructural zona) {
		this.zona = zona;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

}
