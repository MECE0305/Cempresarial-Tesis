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
import com.cempresarial.rest.client.service.ProvinciaService;
import com.cempresarial.rest.client.service.RegionService;
import com.cempresarial.rest.client.service.ZonaService;
import com.cempresariales.servicio.commons.model.entity.Region;
import com.cempresariales.servicio.commons.model.entity.RegionHasZona;
import com.cempresariales.servicio.commons.model.entity.RegionHasZonaPK;
import com.cempresariales.servicio.commons.model.entity.Zona;

/**
 *
 * @author DIGETBI 05
 */
@ManagedBean
@SessionScoped
public class RegionMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * SERVICIOS
	 */
	@Inject
	private RegionService regionService;
	@Inject
	private ZonaService zonaService;
	@Inject
	private ProvinciaService provinciaService;
	@Inject
	private CiudadService ciudadService;

	/**
	 * VARIABLES
	 */
	private List<Region> listaRegiones;
	private List<Zona> listaZonas;

	private List<Region> listaRegionesCheck;
	private List<Long> listaZonasCheck;

	private List<RegionHasZona> listaRegionhasZona;

	private Region region;
	private Zona zona;

	private Utilitarios utils;

	/**
	 * METODOS
	 */
	@PostConstruct
	public void init() {
		clear();
	}

	public void clear() {
		listaRegiones = regionService.listar();
		listaRegionesCheck = regionService.listar();
		listaZonas = zonaService.listar();

		listaZonasCheck = new ArrayList<>();
		listaRegionhasZona = new ArrayList<>();

		region = new Region();
		zona = new Zona();

		utils = new Utilitarios();

	}

	public void actualizarLista() {
		listaRegiones = regionService.listar();
	}

	public void guardar() {
		if (region.getIdRegion() == null) {
			region.setCreaRegion(new Date());
			regionService.insertar(region);
		} else {
			regionService.actualizar(region.getIdRegion(), region);
		}

		clear();
	}

	public void sideActualiza() {
		try {
			List<RegionHasZona> rz = new ArrayList<>();
			boolean match = false;

			// esta en base pero no en check, quiere decir que le puso false
			match = false;
			for (int y = 0; y < listaRegionhasZona.size(); y++) {
				match = false;
				RegionHasZona obj = listaRegionhasZona.get(y);
				for (int x = 0; x < listaZonasCheck.size(); x++) {
					System.out.println("Valor zona check: " + listaZonasCheck.get(x));
					Long id = listaZonasCheck.get(x);
					if (obj.getRegionHasZonaPK().getZonaIdZona() == id) {
						match = true;
						obj.setActivo(new Short("0001"));
						rz.add(obj);
						break;
					}
				}
				if (!match) {
					obj.setActivo(new Short("0000"));
					rz.add(obj);
				}
			}

			// esta en check pero no en base, quiere decir que agrega un nuevo registro
			for (Long idStr : listaZonasCheck) {
				match = false;
				for (int x = 0; x < listaRegionhasZona.size(); x++) {
					Long id = listaRegionhasZona.get(x).getRegionHasZonaPK().getZonaIdZona();
					RegionHasZona obj = new RegionHasZona();
					obj = listaRegionhasZona.get(x);
					if (idStr == id) {
						match = true;
						break;
					}
				}
				if (!match) {
					Zona zona = zonaService.buscarPorId(idStr);
					RegionHasZona rhz = new RegionHasZona();
					RegionHasZonaPK pk = new RegionHasZonaPK();
					pk.setRegionIdRegion(region.getIdRegion());
					pk.setZonaIdZona(zona.getIdZona());
					// rhz.setRegion(region);
					// rhz.setZona(zona);
					rhz.setRegionHasZonaPK(pk);
					rhz.setActivo(new Short("0001"));
					rz.add(rhz);
				}
			}

			region.setRegionHasZonaList(rz);
			regionService.actualizar(region.getIdRegion(), region);
			actualizarLista();

			utils.mostrarMensaje("Región", "Se han agregado #" + listaZonasCheck.size() + " zonas a la Región", "W");

			pasarDatoseditar(regionService.buscarPorId(region.getIdRegion()));

		} catch (Exception e) {
			e.printStackTrace();
			utils.mostrarMensaje("Región - Zona", "Zonas en existencia", "E");
		}
	}

	public void switchActiva(Region region) {

		regionService.actualizar(region.getIdRegion(), region);
		if (region.getActivoRegion())
			utils.mostrarMensaje("Region" + region.getNombreRegion(), "Activado Exitosamente", "I");
		else
			utils.mostrarMensaje("Region " + region.getNombreRegion(), "Desactivado Exitosamente", "w");

		actualizarLista();
	}

	public void eliminar() {
		try {
			regionService.eliminar(region.getIdRegion());
			region = new Region();
			this.actualizarLista();
			utils.mostrarMensaje("Region", "Eliminado Exitosamente !", "I");

		} catch (Exception e) {
			utils.mostrarMensaje("Eliminación", "Imposible Eliminar, Region en USO", "w");
		}
	}

	public void pasarDatoseditar(Region entidad) {
		region = entidad;
		listaZonasCheck = new ArrayList<>();

		listaRegionhasZona = entidad.getRegionHasZonaList();

		Short valor = new Short("0001");
		if (entidad.getRegionHasZonaList().size() > 0) {
			for (int x = 0; x < region.getRegionHasZonaList().size(); x++) {
				if (region.getRegionHasZonaList().get(x).getActivo().equals(valor)) {
					listaZonasCheck.add(
							region.getRegionHasZonaList().get(x).getRegionHasZonaPK().getZonaIdZona());
				}
			}
		}
	}

	/**
	 * SETTEGRS Y GETTERS
	 */

	public List<Region> getListaRegiones() {
		return listaRegiones;
	}

	public void setListaRegiones(List<Region> listaRegiones) {
		this.listaRegiones = listaRegiones;
	}

	public List<Zona> getListaZonas() {
		return listaZonas;
	}

	public void setListaZonas(List<Zona> listaZonas) {
		this.listaZonas = listaZonas;
	}

	public List<Region> getListaRegionesCheck() {
		return listaRegionesCheck;
	}

	public void setListaRegionesCheck(List<Region> listaRegionesCheck) {
		this.listaRegionesCheck = listaRegionesCheck;
	}

	public List<Long> getListaZonasCheck() {
		return listaZonasCheck;
	}

	public void setListaZonasCheck(List<Long> listaZonasCheck) {
		this.listaZonasCheck = listaZonasCheck;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Zona getZona() {
		return zona;
	}

	public void setZona(Zona zona) {
		this.zona = zona;
	}

}
