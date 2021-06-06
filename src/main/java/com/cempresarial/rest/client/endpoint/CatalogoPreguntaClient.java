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
import com.cempresariales.servicio.commons.model.entity.CatalogoPregunta;
import com.cempresariales.servicio.commons.model.entity.Pregunta;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author DIGETBI 05
 */
public class CatalogoPreguntaClient extends AbstractClient {

	private static final Logger log = Logger.getLogger(CatalogoPreguntaClient.class.getName());

	public CatalogoPreguntaClient(String url, String contextPath) {
		super(url, contextPath);
	}

	public List<CatalogoPregunta> listar() throws ServiceException {
		List<CatalogoPregunta> list = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.listar());
		Response response = client.request().get();
		CatalogoPregunta result = null;
		Integer status = response.getStatus();

		if (200 == status) {

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String resultado = response.readEntity(String.class);

			list = new Gson().fromJson(resultado, new TypeToken<ArrayList<CatalogoPregunta>>() {
			}.getType());

		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return list;
	}
	
	
	
	public List<CatalogoPregunta> findCatalogoPreguntaByIdCategoria(Long id) throws ServiceException {
		List<CatalogoPregunta> list = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.findCatalogoPreguntaByIdCategoria(id));
		Response response = client.request().get();
		Integer status = response.getStatus();

		if (200 == status) {
			String resultado = response.readEntity(String.class);

			list = new Gson().fromJson(resultado, new TypeToken<ArrayList<CatalogoPregunta>>() {
			}.getType());

		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return list;
	}

	public List<Pregunta> findPreguntasByCategoria(Long id) throws ServiceException {
		List<Pregunta> list = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.findPreguntasByCategoria(id));
		Response response = client.request().get();
		Integer status = response.getStatus();

		if (200 == status) {

			String resultado = response.readEntity(String.class);

			list = new Gson().fromJson(resultado, new TypeToken<ArrayList<Pregunta>>() {
			}.getType());

		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return list;
	}

	
	
	
	
	public CatalogoPregunta findByIdCatalogoPregunta(Long id) {

		WebTarget client = createClient(
				ApplicationEndpoint.findByIdCatalogoPregunta(id));

		Response response = client.request().get();

		String resultado = response.readEntity(String.class);

		CatalogoPregunta obj = new Gson().fromJson(resultado, CatalogoPregunta.class);

		return obj;

	}

	public CatalogoPregunta actualizar(Long id, CatalogoPregunta entidad) {

		Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

		String json = gson.toJson(entidad);

		System.out.println("DATOS A ENVIAR: " + json);

		WebTarget client = createClient(ApplicationEndpoint.actualizar(id));
		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(json, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		CatalogoPregunta obj = new Gson().fromJson(resultado, CatalogoPregunta.class);

		return obj;
	}

	public void eliminar(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.eliminar(id));

		Invocation.Builder invocationBuilder = client.request();
		Response r = invocationBuilder.delete();
		
		System.out.println("Hola xxx");
		
		

	}

	public CatalogoPregunta crear(CatalogoPregunta entidad) {

		WebTarget client = createClient(ApplicationEndpoint.insertar());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(entidad, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		CatalogoPregunta obj = new Gson().fromJson(resultado, CatalogoPregunta.class);

		return obj;

	}

	public CatalogoPregunta buscarPorId(Long idCategoria, Long idPregunta, Long idPeso) {

		WebTarget client = createClient(
				ApplicationEndpoint.buscarPorCatalogoPreguntaPK(idCategoria, idPregunta, idPeso));

		Response response = client.request().get();

		String resultado = response.readEntity(String.class);

		CatalogoPregunta obj = new Gson().fromJson(resultado, CatalogoPregunta.class);

		return obj;

	}

}
