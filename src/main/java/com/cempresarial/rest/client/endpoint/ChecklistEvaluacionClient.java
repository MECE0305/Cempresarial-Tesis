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
import com.cempresariales.servicio.commons.model.entity.Checklist;
import com.cempresariales.servicio.commons.model.entity.ChecklistHasEvaluacion;
import com.cempresariales.servicio.commons.model.entity.Evaluacion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author DIGETBI 05
 */
public class ChecklistEvaluacionClient extends AbstractClient {

	private static final Logger log = Logger.getLogger(ChecklistEvaluacionClient.class.getName());

	public ChecklistEvaluacionClient(String url, String contextPath) {
		super(url, contextPath);
	}

	public List<ChecklistHasEvaluacion> listar() throws ServiceException {
		List<ChecklistHasEvaluacion> list = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.listar());
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			list = response.readEntity(new GenericType<List<ChecklistHasEvaluacion>>() {
			});
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return list;
	}

	public List<ChecklistHasEvaluacion> findByCheckListEvaluacion(Long idChecklist, Long idEvaluacion)
			throws ServiceException {
		List<ChecklistHasEvaluacion> list = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.findByCheckListEvaluacion(idChecklist, idEvaluacion));
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			String resultado = response.readEntity(String.class);
			list = new Gson().fromJson(resultado, new TypeToken<ArrayList<ChecklistHasEvaluacion>>() {
			}.getType());
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return list;
	}

	public List<ChecklistHasEvaluacion> findCheckListEvaluacionByCheckList(Long idChecklist) throws ServiceException {
		List<ChecklistHasEvaluacion> list = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.findCheckListEvaluacionByCheckList(idChecklist));
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			String resultado = response.readEntity(String.class);
			list = new Gson().fromJson(resultado, new TypeToken<ArrayList<ChecklistHasEvaluacion>>() {
			}.getType());
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return list;
	}
	
	public List<ChecklistHasEvaluacion> findCheckListEvaluacionByEvaluacion(Long idEvaluacion) throws ServiceException {
		List<ChecklistHasEvaluacion> list = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.findCheckListEvaluacionByEvaluacion(idEvaluacion));
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			String resultado = response.readEntity(String.class);
			list = new Gson().fromJson(resultado, new TypeToken<ArrayList<ChecklistHasEvaluacion>>() {
			}.getType());
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return list;
	}
	
	public List<Evaluacion> findEvaluacionByCheckList(Long idChecklist) throws ServiceException {
		List<Evaluacion> list = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.findEvaluacionByCheckList(idChecklist));
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			String resultado = response.readEntity(String.class);
			list = new Gson().fromJson(resultado, new TypeToken<ArrayList<Evaluacion>>() {
			}.getType());
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return list;
	}
	
	public List<Checklist> findCheckListByEvaluacion(Long idEvaluacion) throws ServiceException {
		List<Checklist> list = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.findCheckListByEvaluacion(idEvaluacion));
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			String resultado = response.readEntity(String.class);
			list = new Gson().fromJson(resultado, new TypeToken<ArrayList<Checklist>>() {
			}.getType());
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return list;
	}
	
	
	public List<ChecklistHasEvaluacion> findCheckListEvaluacionByEvaluaciones(List<Long> listaEvaluaciones) {
		try {
			
			List<ChecklistHasEvaluacion> list = new ArrayList<>();
			
			
			Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

			String json = gson.toJson(listaEvaluaciones);

			WebTarget client = createClient(ApplicationEndpoint.findCheckListEvaluacionByEvaluaciones());

			Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
			Response response = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));

			list = response.readEntity(new GenericType<List<ChecklistHasEvaluacion>>() {
			});

			return list;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
	
	public List<Evaluacion> findEvaluacionByIdsRol(List<Long> listaRoles) {
		try {
			
			List<Evaluacion> list = new ArrayList<>();
			
			
			Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

			String json = gson.toJson(listaRoles);

			WebTarget client = createClient(ApplicationEndpoint.findEvaluacionByIdsRol());

			Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
			Response response = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));

			list = response.readEntity(new GenericType<List<Evaluacion>>() {
			});

			return list;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
	
	public List<Evaluacion> findEvaluacionByIdsChecklist(List<Long> listaChecklist) {
		try {
			
			List<Evaluacion> list = new ArrayList<>();
			
			
			Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

			String json = gson.toJson(listaChecklist);

			WebTarget client = createClient(ApplicationEndpoint.findEvaluacionByIdsChecklist());

			Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
			Response response = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));

			list = response.readEntity(new GenericType<List<Evaluacion>>() {
			});

			return list;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	public ChecklistHasEvaluacion crear(ChecklistHasEvaluacion entidad) {

		WebTarget client = createClient(ApplicationEndpoint.insertar());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(entidad, MediaType.APPLICATION_JSON));

		ChecklistHasEvaluacion obj = response.readEntity(ChecklistHasEvaluacion.class);

		return obj;
	}

}
