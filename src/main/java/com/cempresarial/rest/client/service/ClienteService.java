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
import com.cempresarial.rest.client.endpoint.ClientesClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.model.entity.Cliente;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class ClienteService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_CLIENTES();
	//final String URL = "https://servicioclientes.herokuapp.com/";
	final String PATH = "/cliente";

	public List<Cliente> listar() {
		List<Cliente> listaClientes = new ArrayList<>();
		try {
			ClientesClient client = new ClientesClient(URL, PATH);
			listaClientes.addAll(client.listar());

		} catch (ServiceException ex) {
			ex.toString();
		}

		return listaClientes;
	}

	public void insertar(Cliente cliente) {
		ClientesClient client = new ClientesClient(URL, PATH);
		client.crear(cliente);
	}

	public void actualizar(Long id, Cliente cliente) {
		Cliente obj;
		ClientesClient client = new ClientesClient(URL, PATH);
		obj = client.actualizar(id, cliente);
	}

	public Cliente buscarPorId(Long id) {
		Cliente obj;
		ClientesClient client = new ClientesClient(URL, PATH);
		obj = client.buscarPorId(id);
		return obj;
	}

	public boolean eliminar(Long id) {
		ClientesClient client = new ClientesClient(URL, PATH);
		return client.eliminar(id);
	}

}
