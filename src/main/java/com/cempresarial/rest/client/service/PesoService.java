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
import com.cempresarial.rest.client.endpoint.PesoClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.model.entity.Peso;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class PesoService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_CHECKLIST();
	//final String URL = "https://serviciochecklist.herokuapp.com/";
	final String PATH = "/peso";

	public List<Peso> listar() {
		List<Peso> listaPesos = new ArrayList<>();
		try {
			PesoClient client = new PesoClient(URL, PATH);
			listaPesos.addAll(client.listar());

		} catch (ServiceException ex) {
			ex.toString();
		}

		return listaPesos;
	}

	public void insertar(Peso Peso) {
		PesoClient client = new PesoClient(URL, PATH);
		client.crear(Peso);
	}

	public void actualizar(Long id, Peso Peso) {
		Peso obj;
		PesoClient client = new PesoClient(URL, PATH);
		obj = client.actualizar(id, Peso);
	}

	public Peso buscarPorId(Long id) {
		Peso obj;
		PesoClient client = new PesoClient(URL, PATH);
		obj = client.buscarPorId(id);
		return obj;
	}

	public void eliminar(Long id) {
		PesoClient client = new PesoClient(URL, PATH);
		client.eliminar(id);
	}

}
