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
import com.cempresariales.servicio.commons.model.entity.ChecklistHasEvaluacion;
import com.cempresariales.servicio.commons.model.entity.Respuesta;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author DIGETBI 05
 */
public class RespuestaClient extends AbstractClient {

	private static final Logger log = Logger.getLogger(RespuestaClient.class.getName());

	public RespuestaClient(String url, String contextPath) {
		super(url, contextPath);
	}

	public List<Respuesta> listar() throws ServiceException {
		List<Respuesta> list = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.listar());
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String resultado = response.readEntity(String.class);

			list = new Gson().fromJson(resultado, new TypeToken<ArrayList<Respuesta>>() {
			}.getType());
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return list;
	}
	
	
	public List<Respuesta> findByIdEvaluacionIdChecklist(Long idEvaluacion, Long idChecklist) throws ServiceException {
		List<Respuesta> list = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.findByIdEvaluacionIdChecklist(idEvaluacion,idChecklist));
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			String resultado = response.readEntity(String.class);
			list = new Gson().fromJson(resultado, new TypeToken<ArrayList<Respuesta>>() {
			}.getType());
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		
		System.out.println("VALORES SERVICE: "+idEvaluacion+"*****"+idChecklist);
		System.out.println("VALORES DE LISTA: "+list.size());
		
		return list;
	}
	
	public List<Respuesta> findRespuestaByCategoria(Long idCategoria) throws ServiceException {
		List<Respuesta> list = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.findRespuestaByCategoria(idCategoria));
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String resultado = response.readEntity(String.class);

			list = new Gson().fromJson(resultado, new TypeToken<ArrayList<Respuesta>>() {
			}.getType());
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return list;
	}

	

	public List<Respuesta> findRespuestaByChecklistEvaluacion(List<ChecklistHasEvaluacion> lista) {
		try {

			List<Respuesta> listaEvaluaciones = new ArrayList<>();

			Gson gson = new GsonBuilder().create();

			String json = gson.toJson(lista);

			WebTarget client = createClient(ApplicationEndpoint.findRespuestaByChecklistEvaluacion());

			Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
			Response response = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));

			listaEvaluaciones = response.readEntity(new GenericType<List<Respuesta>>() {
			});

			return listaEvaluaciones;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	public Respuesta actualizar(Long id, Respuesta entidad) {

		Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

		String json = gson.toJson(entidad);

		WebTarget client = createClient(ApplicationEndpoint.actualizar(id));

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(json, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		Respuesta obj = new Gson().fromJson(resultado, Respuesta.class);

		return obj;
	}

	public void eliminar(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.eliminar(id));
		Invocation.Builder invocationBuilder = client.request();
		invocationBuilder.delete();

	}

	public Respuesta crear(Respuesta entidad) {

		Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

		String json = gson.toJson(entidad);

		WebTarget client = createClient(ApplicationEndpoint.insertar());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		Respuesta obj = new Gson().fromJson(resultado, Respuesta.class);

		return obj;
	}

	public Respuesta buscarPorId(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.buscarPorId(id));

		Response response = client.request().get();

		String resultado = response.readEntity(String.class);

		Respuesta obj = new Gson().fromJson(resultado, Respuesta.class);

		return obj;
	}

}
