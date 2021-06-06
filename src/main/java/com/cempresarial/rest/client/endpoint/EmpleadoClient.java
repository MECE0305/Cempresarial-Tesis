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
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.cempresarial.rest.generic.AbstractClient;
import com.cempresarial.rest.generic.ApplicationEndpoint;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.model.entity.Agencia;
import com.cempresariales.servicio.commons.model.entity.Empleado;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author DIGETBI 05
 */
public class EmpleadoClient extends AbstractClient {

	private static final Logger log = Logger.getLogger(EmpleadoClient.class.getName());

	public EmpleadoClient(String url, String contextPath) {
		super(url, contextPath);
	}

	public List<Empleado> listar() throws ServiceException {
		List<Empleado> listaEmpleadoes = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.listar());
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String resultado = response.readEntity(String.class);

			listaEmpleadoes = new Gson().fromJson(resultado, new TypeToken<ArrayList<Empleado>>() {
			}.getType());
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return listaEmpleadoes;
	}
	
	
	public List<Empleado> findEmpleadoByAgencias(List<Long> lista) {
		try {
			
			List<Empleado> list = new ArrayList<>();
			
			
			Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

			String json = gson.toJson(lista);

			WebTarget client = createClient(ApplicationEndpoint.findEmpleadoByAgencias());

			Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
			Response response = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));

			list = response.readEntity(new GenericType<List<Empleado>>() {
			});

			return list;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
	

	public Empleado actualizar(Long id, Empleado Empleado) {

		WebTarget client = createClient(ApplicationEndpoint.actualizar(id));

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(Empleado, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		Empleado obj = new Gson().fromJson(resultado,  Empleado.class);
		
		return obj;
	}

	public void eliminar(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.eliminar(id));
		Invocation.Builder invocationBuilder = client.request();
		invocationBuilder.delete();

	}

	public Empleado crear(Empleado Empleado) {

		WebTarget client = createClient(ApplicationEndpoint.insertar());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(Empleado, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		Empleado obj = new Gson().fromJson(resultado,  Empleado.class);
		
		return obj;
	}

	public Empleado buscarPorId(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.buscarPorId(id));

		Response response = client.request().get();

		String resultado = response.readEntity(String.class);

		Empleado obj = new Gson().fromJson(resultado,  Empleado.class);
		
		return obj;
	}

	public List<Empleado> findByAgenciaIdAgencia(Agencia id) {

		WebTarget client = createClient(ApplicationEndpoint.findByAgenciaIdAgencia(id));

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(id, MediaType.APPLICATION_JSON));

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String resultado = response.readEntity(String.class);

		List<Empleado> obj = new Gson().fromJson(resultado, new TypeToken<ArrayList<Empleado>>() {
		}.getType());
		

		return obj;
	}

}
