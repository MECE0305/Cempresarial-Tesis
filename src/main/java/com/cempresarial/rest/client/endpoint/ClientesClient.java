/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cempresarial.rest.client.endpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cempresarial.rest.generic.AbstractClient;
import com.cempresarial.rest.generic.ApplicationEndpoint;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.model.entity.Cliente;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author DIGETBI 05
 */
public class ClientesClient extends AbstractClient {

	private static final Logger log = Logger.getLogger(ClientesClient.class.getName());

	public ClientesClient(String url, String contextPath) {
		super(url, contextPath);
	}

	public List<Cliente> listar() throws ServiceException {
		List<Cliente> list = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.listar());
		Response response = client.request().get();
		Cliente result = null;
		Integer status = response.getStatus();

		if (200 == status) {

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String resultado = response.readEntity(String.class);

			list = new Gson().fromJson(resultado, new TypeToken<ArrayList<Cliente>>() {
			}.getType());

		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return list;
	}

	public Cliente actualizar(Long id, Cliente cliente) {

		WebTarget client = createClient(ApplicationEndpoint.actualizar(id));

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(cliente, MediaType.APPLICATION_JSON));

		Cliente obj = response.readEntity(Cliente.class);

		return obj;
	}

	public boolean eliminar(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.eliminar(id));

		Invocation.Builder invocationBuilder = client.request();
		Response response = invocationBuilder.delete();

		if (response.getStatus() == 202)
			return true;
		else
			return false;

	}

	public Cliente crear(Cliente cliente) {

		WebTarget client = createClient(ApplicationEndpoint.insertar());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(cliente, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		Cliente obj = new Gson().fromJson(resultado, Cliente.class);

		return obj;

	}

	public Cliente buscarPorId(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.buscarPorId(id));

		Response response = client.request().get();

		String resultado = response.readEntity(String.class);

		Cliente obj = new Gson().fromJson(resultado, Cliente.class);

		return obj;

	}

}
