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
public class ProvinciaMB implements Serializable {

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

	private List<Provincia> listaProvinciaCheck;
	private List<Long> listaZonaCheck;

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
		listaProvinciaCheck = provinciaService.listar();
		listaProvincia = provinciaService.listar();

		listaProvinciaCheck = new ArrayList<>();
		listaZonaHasProvincia = new ArrayList<>();

		provincia = new Provincia();
		zona = new Zona();

		utils = new Utilitarios();

	}

	public void actualizarLista() {
		listaProvincia = provinciaService.listar();
	}

	public void guardar() {
		if (provincia.getIdProvincia() == null) {
			provincia.setCreaProvincia(new Date());
			provinciaService.insertar(provincia);
		} else {
			provinciaService.actualizar(provincia.getIdProvincia(), provincia);
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
				for (int x = 0; x < listaZonaCheck.size(); x++) {
					Long id = listaZonaCheck.get(x);
					if (obj.getZonaHasProvinciaPK().getZonaIdZona() == id) {
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
			for (Long idStr : listaZonaCheck) {
				match = false;
				for (int x = 0; x < listaZonaHasProvincia.size(); x++) {
					Long id = listaZonaHasProvincia.get(x).getZonaHasProvinciaPK().getZonaIdZona();
					if (idStr == id) {
						match = true;
						break;
					}
				}
				if (!match) {
					Zona zon = zonaService.buscarPorId(idStr);
					ZonaHasProvincia zhp = new ZonaHasProvincia();
					ZonaHasProvinciaPK pk = new ZonaHasProvinciaPK();
					pk.setProvinciaIdProvincia(provincia.getIdProvincia());
					pk.setZonaIdZona(zon.getIdZona());
					// rhz.setRegion(region);
					// rhz.setZona(zona);
					zhp.setZonaHasProvinciaPK(pk);
					zhp.setActivo(new Short("0001"));
					rz.add(zhp);
				}
			}

			provincia.setZonaHasProvinciaList(rz);
			provinciaService.actualizar(provincia.getIdProvincia(), provincia);
			actualizarLista();

			utils.mostrarMensaje("Zona", "Se han agregado #" + listaProvinciaCheck.size() + " provincias a la Zona",
					"W");

			pasarDatoseditar(provinciaService.buscarPorId(provincia.getIdProvincia()));

		} catch (Exception e) {
			utils.mostrarMensaje("Zona - Provincia", "Provincia en existencia", "E");
		}
	}

	public void switchActiva(Provincia entidad) {

		provinciaService.actualizar(entidad.getIdProvincia(), entidad);
		if (entidad.getActivoProvincia())
			utils.mostrarMensaje("Provincia " + entidad.getNombreProvincia(), "Activada Exitosamente", "I");
		else
			utils.mostrarMensaje("Provincia " + entidad.getNombreProvincia(), "Desactivada Exitosamente", "w");
		actualizarLista();
	}

	public void eliminar() {
		try {
			provinciaService.eliminar(provincia.getIdProvincia());
			provincia = new Provincia();
			this.actualizarLista();
			utils.mostrarMensaje("Provincia", "Eliminada Exitosamente !", "I");

		} catch (Exception e) {
			utils.mostrarMensaje("Eliminación", "Imposible Eliminar, Provincia en USO", "w");
		}
	}

	public void pasarDatoseditar(Provincia entidad) {
		provincia = entidad;
		listaZonaCheck = new ArrayList<>();

		listaZonaHasProvincia = entidad.getZonaHasProvinciaList();

		Short valor = new Short("0001");
		if (entidad.getZonaHasProvinciaList().size() > 0) {
			for (int x = 0; x < provincia.getZonaHasProvinciaList().size(); x++) {
				if (provincia.getZonaHasProvinciaList().get(x).getActivo().equals(valor)) {
					listaZonaCheck.add(
							provincia.getZonaHasProvinciaList().get(x).getZonaHasProvinciaPK().getZonaIdZona());
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

	public List<Provincia> getListaProvinciaCheck() {
		return listaProvinciaCheck;
	}

	public void setListaProvinciaCheck(List<Provincia> listaProvinciaCheck) {
		this.listaProvinciaCheck = listaProvinciaCheck;
	}

	public List<Long> getListaZonaCheck() {
		return listaZonaCheck;
	}

	public void setListaZonaCheck(List<Long> listaZonaCheck) {
		this.listaZonaCheck = listaZonaCheck;
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
