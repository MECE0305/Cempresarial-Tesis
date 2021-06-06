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
import com.cempresarial.rest.client.endpoint.CategoriaClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.model.entity.Categoria;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class CategoriaService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_CHECKLIST();
	//final String URL = "https://serviciochecklist.herokuapp.com/";
	final String PATH = "/categoria";

	public List<Categoria> listar() {
		List<Categoria> listaCategorias = new ArrayList<>();
		try {
			CategoriaClient client = new CategoriaClient(URL, PATH);
			listaCategorias.addAll(client.listar());

		} catch (ServiceException ex) {
			ex.toString();
		}

		return listaCategorias;
	}

	public List<Categoria> listarByCheck(Long id) {
		List<Categoria> listaCategorias = new ArrayList<>();
		try {
			CategoriaClient client = new CategoriaClient(URL, PATH);
			listaCategorias.addAll(client.listarByCheck(id));

		} catch (ServiceException ex) {
			ex.toString();
		}

		return listaCategorias;
	}

	public List<Categoria> findCategoriasByChecklist(List<Long> lista) {
		List<Categoria> list = new ArrayList<>();
		try {
			CategoriaClient client = new CategoriaClient(URL, PATH);
			list.addAll(client.findCategoriasByChecklist(lista));
		} catch (Exception ex) {
			ex.toString();
		}
		return list;
	}

	public void insertar(Categoria categoria) {
		CategoriaClient client = new CategoriaClient(URL, PATH);
		client.crear(categoria);
	}

	public void actualizar(Long id, Categoria categoria) {
		Categoria obj;
		CategoriaClient client = new CategoriaClient(URL, PATH);
		obj = client.actualizar(id, categoria);
	}

	public Categoria buscarPorId(Long id) {
		Categoria obj;
		CategoriaClient client = new CategoriaClient(URL, PATH);
		obj = client.buscarPorId(id);
		return obj;
	}

	public void eliminar(Long id) {
		CategoriaClient client = new CategoriaClient(URL, PATH);
		client.eliminar(id);
	}

}
