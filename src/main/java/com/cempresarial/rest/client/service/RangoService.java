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
import com.cempresarial.rest.client.endpoint.RangoClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.model.entity.RangoDesempenio;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class RangoService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_CLIENTES();
	//final String URL = "https://servicioclientes.herokuapp.com/";
	final String PATH = "/rango";
	private RangoClient client = new RangoClient(URL, PATH);

	public List<RangoDesempenio> listar() {
		List<RangoDesempenio> lista = new ArrayList<>();
		try {
			lista.addAll(client.listar());

		} catch (ServiceException ex) {
			ex.toString();
		}

		return lista;
	}
	
	public List<RangoDesempenio> findByEmpresa(Long idEmpresa) {
		List<RangoDesempenio> lista = new ArrayList<>();
		try {
			lista.addAll(client.findByEmpresa(idEmpresa));

		} catch (ServiceException ex) {
			ex.toString();
		}

		return lista;
	}
	
	

	public void insertar(RangoDesempenio entidad) {
		client.crear(entidad);
	}

	public void actualizar(Long id, RangoDesempenio entidad) {
		RangoDesempenio obj;
		obj = client.actualizar(id, entidad);
	}

	public RangoDesempenio buscarPorId(Long id) {
		RangoDesempenio obj;
		obj = client.buscarPorId(id);
		return obj;
	}
	
	
	public RangoDesempenio findByRangoAndEmpresa(float rango, Long idEmpresa) {
		RangoDesempenio obj;
		obj = client.findByRangoAndEmpresa(rango, idEmpresa);
		return obj;
	}
	
	
	

	public void eliminar(Long id) {
		client.eliminar(id);
	}

}
