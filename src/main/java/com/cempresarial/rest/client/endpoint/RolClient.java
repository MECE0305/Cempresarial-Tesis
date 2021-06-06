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
import com.cempresariales.servicio.commons.model.entity.Rol;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author DIGETBI 05
 */
public class RolClient extends AbstractClient {

	private static final Logger log = Logger.getLogger(RolClient.class.getName());

	public RolClient(String url, String contextPath) {
		super(url, contextPath);
	}

	public List<Rol> listar() throws ServiceException {
		List<Rol> listaRoles = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.listar());
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			listaRoles = response.readEntity(new GenericType<List<Rol>>() {
			});
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return listaRoles;
	}
	
	public List<Rol> findRolByArea(Long idArea) throws ServiceException {
		List<Rol> lista = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.findRolByArea(idArea));
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			String resultado = response.readEntity(String.class);
			lista = new Gson().fromJson(resultado, new TypeToken<ArrayList<Rol>>() {
			}.getType());
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return lista;
	}
	
	public List<Rol> findRolByEmpleados(List<Long> lista) {
		try {
			
			List<Rol> list = new ArrayList<>();
			
			
			Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

			String json = gson.toJson(lista);

			WebTarget client = createClient(ApplicationEndpoint.findRolByEmpleados());

			Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
			Response response = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));

			list = response.readEntity(new GenericType<List<Rol>>() {
			});

			return list;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
	

	public Rol actualizar(Long id, Rol Rol) {

		WebTarget client = createClient(ApplicationEndpoint.actualizar(id));

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(Rol, MediaType.APPLICATION_JSON));

		Rol obj = response.readEntity(Rol.class);

		return obj;
	}

	public void eliminar(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.eliminar(id));
		Invocation.Builder invocationBuilder = client.request();
		invocationBuilder.delete();

	}

	public Rol crear(Rol Rol) {

		WebTarget client = createClient(ApplicationEndpoint.insertar());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(Rol, MediaType.APPLICATION_JSON));

		Rol obj = response.readEntity(Rol.class);

		return obj;
	}

	public Rol buscarPorId(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.buscarPorId(id));

		Response response = client.request().get();

		String resultado = response.readEntity(String.class);

		Rol obj = new Gson().fromJson(resultado,  Rol.class);

		return obj;
	}

}
