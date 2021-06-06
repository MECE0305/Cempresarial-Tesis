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
import com.cempresarial.rest.client.service.ProvinciaService;
import com.cempresarial.rest.client.service.ZonaService;
import com.cempresariales.servicio.commons.model.entity.Provincia;
import com.cempresariales.servicio.commons.model.entity.Zona;
import com.cempresariales.servicio.commons.model.entity.ZonaHasProvincia;
import com.cempresariales.servicio.commons.model.entity.ZonaHasProvinciaPK;

/**
 *
 * @author DIGETBI 05
 */
@ManagedBean
@SessionScoped
public class ZonaMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * SERVICIOS
	 */
	@Inject
	private ZonaService zonaService;
	@Inject
	private ProvinciaService provinciaService;

	/**
	 * VARIABLES
	 */
	private List<Zona> listaZona;
	private List<Provincia> listaProvincia;

	private List<Zona> listaZonaCheck;
	private List<Long> listaProvinciaCheck;

	private List<ZonaHasProvincia> listaZonaHasProvincia;

	private Zona zona;
	private Provincia provincia;

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
		listaZonaCheck = zonaService.listar();
		listaProvincia = provinciaService.listar();

		listaProvinciaCheck = new ArrayList<>();
		listaZonaHasProvincia = new ArrayList<>();

		provincia = new Provincia();
		zona = new Zona();

		utils = new Utilitarios();

	}

	public void actualizarLista() {
		listaZona = zonaService.listar();
	}

	public void guardar() {
		if (zona.getIdZona() == null) {
			zona.setCreaZona(new Date());
			zonaService.insertar(zona);
		} else {
			zonaService.actualizar(zona.getIdZona(), zona);
		}

		clear();
	}

	public void sideActualiza() {
		try {
			List<ZonaHasProvincia> rz = new ArrayList<>();
			boolean match = false;

			// esta en base pero no en check, quiere decir que le puso false
			match = false;
			for (int y = 0; y < listaZonaHasProvincia.size(); y++) {
				match = false;
				ZonaHasProvincia obj = listaZonaHasProvincia.get(y);
				for (int x = 0; x < listaProvinciaCheck.size(); x++) {
					Long id = listaProvinciaCheck.get(x);
					if (obj.getZonaHasProvinciaPK().getProvinciaIdProvincia() == id) {
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
			for (Long idStr : listaProvinciaCheck) {
				match = false;
				for (int x = 0; x < listaZonaHasProvincia.size(); x++) {
					Long id = listaZonaHasProvincia.get(x).getZonaHasProvinciaPK().getProvinciaIdProvincia();
					ZonaHasProvincia obj = new ZonaHasProvincia();
					obj = listaZonaHasProvincia.get(x);
					if (idStr == id) {
						match = true;
						break;
					}
				}
				if (!match) {
					Provincia prov = provinciaService.buscarPorId(idStr);
					ZonaHasProvincia zhp = new ZonaHasProvincia();
					ZonaHasProvinciaPK pk = new ZonaHasProvinciaPK();
					pk.setProvinciaIdProvincia(prov.getIdProvincia());
					pk.setZonaIdZona(zona.getIdZona());
					// rhz.setRegion(region);
					// rhz.setZona(zona);
					zhp.setZonaHasProvinciaPK(pk);
					zhp.setActivo(new Short("0001"));
					rz.add(zhp);
				}
			}

			zona.setZonaHasProvinciaList(rz);
			zonaService.actualizar(zona.getIdZona(), zona);
			actualizarLista();

			utils.mostrarMensaje("Zona", "Se han agregado #" + listaProvinciaCheck.size() + " provincias a la Zona",
					"W");

			pasarDatoseditar(zonaService.buscarPorId(zona.getIdZona()));

		} catch (Exception e) {
			utils.mostrarMensaje("Zona - Provincia", "Zonas en existencia", "E");
		}
	}

	public void switchActiva(Zona entidad) {

		zonaService.actualizar(entidad.getIdZona(), entidad);
		if (entidad.getActivoZona())
			utils.mostrarMensaje("Zona " + entidad.getNombreZona(), "Activada Exitosamente", "I");
		else
			utils.mostrarMensaje("Zona " + entidad.getNombreZona(), "Desactivada Exitosamente", "w");
		actualizarLista();
	}

	public void eliminar() {
		try {
			zonaService.eliminar(zona.getIdZona());
			zona = new Zona();
			this.actualizarLista();
			utils.mostrarMensaje("Zona", "Eliminada Exitosamente !", "I");

		} catch (Exception e) {
			utils.mostrarMensaje("Eliminación", "Imposible Eliminar, Zona en USO", "w");
		}
	}

	public void pasarDatoseditar(Zona entidad) {
		zona = entidad;
		listaProvinciaCheck = new ArrayList<>();

		listaZonaHasProvincia = entidad.getZonaHasProvinciaList();

		Short valor = new Short("0001");
		if (entidad.getZonaHasProvinciaList().size() > 0) {
			for (int x = 0; x < zona.getZonaHasProvinciaList().size(); x++) {
				if (zona.getZonaHasProvinciaList().get(x).getActivo().equals(valor)) {
					listaProvinciaCheck.add(
							zona.getZonaHasProvinciaList().get(x).getZonaHasProvinciaPK().getProvinciaIdProvincia());
				}
			}
		}
	}

	/**
	 * SETTEGRS Y GETTERS
	 */

	public List<Zona> getListaZona() {
		return listaZona;
	}

	public void setListaZona(List<Zona> listaZona) {
		this.listaZona = listaZona;
	}

	public List<Provincia> getListaProvincia() {
		return listaProvincia;
	}

	public void setListaProvincia(List<Provincia> listaProvincia) {
		this.listaProvincia = listaProvincia;
	}

	public List<Zona> getListaZonaCheck() {
		return listaZonaCheck;
	}

	public void setListaZonaCheck(List<Zona> listaZonaCheck) {
		this.listaZonaCheck = listaZonaCheck;
	}

	public List<Long> getListaProvinciaCheck() {
		return listaProvinciaCheck;
	}

	public void setListaProvinciaCheck(List<Long> listaProvinciaCheck) {
		this.listaProvinciaCheck = listaProvinciaCheck;
	}

	public List<ZonaHasProvincia> getListaZonaHasProvincia() {
		return listaZonaHasProvincia;
	}

	public void setListaZonaHasProvincia(List<ZonaHasProvincia> listaZonaHasProvincia) {
		this.listaZonaHasProvincia = listaZonaHasProvincia;
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public Zona getZona() {
		return zona;
	}

	public void setZona(Zona zona) {
		this.zona = zona;
	}
	
	

}
