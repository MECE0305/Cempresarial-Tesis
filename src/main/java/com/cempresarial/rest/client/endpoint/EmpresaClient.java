/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cempresarial.rest.client.endpoint;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cempresarial.rest.generic.AbstractClient;
import com.cempresarial.rest.generic.ApplicationEndpoint;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.model.entity.Cliente;
import com.cempresariales.servicio.commons.model.entity.Empresa;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author DIGETBI 05
 */
public class EmpresaClient extends AbstractClient {

	public EmpresaClient(String url, String contextPath) {
		super(url, contextPath);
	}

	public List<Empresa> listar() throws ServiceException {
		List<Empresa> listaEmpresas = new ArrayList<>();

		String aaa = ApplicationEndpoint.listar();
		WebTarget client = createClient(ApplicationEndpoint.listar());
		Response response = client.request().get();

		Empresa result = null;
		Integer status = response.getStatus();

		if (200 == status) {
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String resultado = response.readEntity(String.class);

			listaEmpresas = new Gson().fromJson(resultado, new TypeToken<ArrayList<Empresa>>() {
			}.getType());

			

		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return listaEmpresas;
	}

	public Empresa actualizar(Long id, Empresa cliente) {
		
		Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

		String json = gson.toJson(cliente);

		WebTarget client = createClient(ApplicationEndpoint.actualizar(id));

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(json, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		Empresa obj = new Gson().fromJson(resultado,  Empresa.class);
		
		return obj;
	}

	public void eliminar(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.eliminar(id));

		Invocation.Builder invocationBuilder = client.request();
		invocationBuilder.delete();

	}

	public Empresa crear(Empresa cliente) {
		
		Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

		String json = gson.toJson(cliente);

		WebTarget client = createClient(ApplicationEndpoint.insertar());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		Empresa obj = new Gson().fromJson(resultado,  Empresa.class);
		
		return obj;
		

		
	}

	public Empresa buscarPorId(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.buscarPorId(id));

		Response response = client.request().get();
		
		String resultado = response.readEntity(String.class);

		Empresa obj = new Gson().fromJson(resultado,  Empresa.class);

		return obj;
	}

	public List<Empresa> findByClienteIdCliente(Cliente id) {

		WebTarget client = createClient(ApplicationEndpoint.findByClienteIdCliente(id));

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(id, MediaType.APPLICATION_JSON));
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String resultado = response.readEntity(String.class);

		List<Empresa> obj = new Gson().fromJson(resultado, new TypeToken<ArrayList<Empresa>>() {
		}.getType());

		return obj;
	}

}
