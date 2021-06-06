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
import com.cempresariales.servicio.commons.model.entity.Categoria;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author DIGETBI 05
 */
public class CategoriaClient extends AbstractClient {

	private static final Logger log = Logger.getLogger(CategoriaClient.class.getName());

	public CategoriaClient(String url, String contextPath) {
		super(url, contextPath);
	}

	public List<Categoria> listar() throws ServiceException {
		List<Categoria> listaChecklistes = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.listar());
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String resultado = response.readEntity(String.class);

			listaChecklistes = new Gson().fromJson(resultado, new TypeToken<ArrayList<Categoria>>() {
			}.getType());

			/*
			 * listaChecklistes = response.readEntity(new GenericType<List<Categoria>>() {
			 * });
			 */
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return listaChecklistes;
	}

	public Categoria actualizar(Long id, Categoria categoria) {

		Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

		String json = gson.toJson(categoria);

		WebTarget client = createClient(ApplicationEndpoint.actualizar(id));

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(json, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		Categoria obj = new Gson().fromJson(resultado, Categoria.class);

		return obj;
	}

	public void eliminar(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.eliminar(id));
		Invocation.Builder invocationBuilder = client.request();
		invocationBuilder.delete();

	}

	public Categoria crear(Categoria entidad) {

		Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

		String json = gson.toJson(entidad);

		WebTarget client = createClient(ApplicationEndpoint.insertar());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		Categoria obj = new Gson().fromJson(resultado, Categoria.class);

		return obj;
	}

	public Categoria buscarPorId(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.buscarPorId(id));

		Response response = client.request().get();

		String resultado = response.readEntity(String.class);

		Categoria obj = new Gson().fromJson(resultado, Categoria.class);

		return obj;
	}

	public List<Categoria> listarByCheck(Long id) throws ServiceException {
		List<Categoria> listaChecklistes = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.findByIdChecklist(id));
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {

			String resultado = response.readEntity(String.class);

			listaChecklistes = new Gson().fromJson(resultado, new TypeToken<ArrayList<Categoria>>() {
			}.getType());

			/*
			 * listaChecklistes = response.readEntity(new GenericType<List<Categoria>>() {
			 * });
			 */
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return listaChecklistes;
	}
	
	
	public List<Categoria> findCategoriasByChecklist(List<Long> lista) {
		try {
			
			List<Categoria> list = new ArrayList<>();
			
			
			Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

			String json = gson.toJson(lista);

			WebTarget client = createClient(ApplicationEndpoint.findCategoriasByChecklist());

			Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
			Response response = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));

			list = response.readEntity(new GenericType<List<Categoria>>() {
			});

			return list;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

}
