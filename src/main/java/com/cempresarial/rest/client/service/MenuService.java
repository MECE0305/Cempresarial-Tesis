/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cempresarial.rest.client.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import com.cempresarial.recursos.Rutas;
import com.cempresarial.rest.client.endpoint.MenuClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.admin.model.entity.Aplicacion;
import com.cempresariales.servicio.commons.admin.model.entity.Menu;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class MenuService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_ADMIN(); 
	//final String URL = "https://servicio-cemp-admin.herokuapp.com/";
	final String PATH = "/menu";
	private MenuClient client = new MenuClient(URL, PATH);

	public List<Menu> listar() {
		List<Menu> lista = new ArrayList<>();
		try {
			lista.addAll(client.listar());

		} catch (ServiceException ex) {
			ex.toString();
		}

		return lista;
	}

	public List<Menu> obtenerMenusPorRolAplicativo(String rol, long idAplicativo) {
		List<Menu> lista = new ArrayList<>();
		try {
			lista.addAll(client.obtenerMenusPorRolAplicativo(rol, idAplicativo));

		} catch (Exception ex) {
			ex.toString();
		}

		return lista;
	}

	public List<Menu> obtenerMenusHijo(String rol, long idMenuPadre, String opcion, String opcion2) {
		List<Menu> lista = new ArrayList<>();
		try {
			lista.addAll(client.obtenerMenusHijo(rol, idMenuPadre, opcion, opcion2));

		} catch (Exception ex) {
			ex.toString();
		}

		return lista;
	}

	public void insertar(Menu entidad) {
		client.crear(entidad);
	}

	public void actualizar(Long id, Menu entidad) {
		Menu obj;
		obj = client.actualizar(id, entidad);
	}

	public Menu buscarPorId(Long id) {
		Menu obj;
		obj = client.buscarPorId(id);
		return obj;
	}

	public List<Menu> findByAplicacion(Aplicacion entidad) {
		List<Menu> obj;
		obj = client.findByAplicacion(entidad);
		return obj;
	}

	public List<Menu> findByMenuPadre(Long id) {
		List<Menu> obj;
		obj = client.findByMenuPadre(id);
		return obj;
	}

	public List<Menu> findMenuPadreByAplicacion(Long id) {
		List<Menu> obj;
		obj = client.findMenuPadreByAplicacion(id);
		return obj;
	}

	public List<Menu> findOpcionesByAplicacionAndMenuPadre(Long idAplicacion, Long idMenuPadre) {
		List<Menu> obj;
		obj = client.findOpcionesByAplicacionAndMenuPadre(idAplicacion, idMenuPadre);
		return obj;
	}

	public void eliminar(Long id) {
		client.eliminar(id);
	}

}
