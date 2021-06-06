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
import com.cempresariales.servicio.commons.model.entity.Checklist;
import com.cempresariales.servicio.commons.model.entity.Pregunta;
import com.cempresariales.servicio.commons.model.entity.Rol;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author DIGETBI 05
 */
public class ChecklistClient extends AbstractClient {

	private static final Logger log = Logger.getLogger(ChecklistClient.class.getName());

	public ChecklistClient(String url, String contextPath) {
		super(url, contextPath);
	}

	public List<Checklist> listar() throws ServiceException {
		List<Checklist> listaChecklistes = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.listar());
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			listaChecklistes = response.readEntity(new GenericType<List<Checklist>>() {
			});
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return listaChecklistes;
	}
	
	public List<Checklist> findCheckListByRoles(List<Long> lista) {
		try {
			
			List<Checklist> list = new ArrayList<>();
			
			
			Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

			String json = gson.toJson(lista);

			WebTarget client = createClient(ApplicationEndpoint.findCheckListByRoles());

			Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
			Response response = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));

			list = response.readEntity(new GenericType<List<Checklist>>() {
			});

			return list;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	public Checklist actualizar(Long id, Checklist Checklist) {

		WebTarget client = createClient(ApplicationEndpoint.actualizar(id));

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(Checklist, MediaType.APPLICATION_JSON));

		Checklist object = response.readEntity(Checklist.class);

		return object;
	}

	public void eliminar(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.eliminar(id));
		Invocation.Builder invocationBuilder = client.request();
		invocationBuilder.delete();

	}

	public Checklist crear(Checklist Checklist) {

		WebTarget client = createClient(ApplicationEndpoint.insertar());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(Checklist, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		Checklist obj = new Gson().fromJson(resultado, Checklist.class);

		return obj;
	}

	public List<Checklist> findByRolIdRol(Rol rol) throws ServiceException {

		WebTarget client = createClient(ApplicationEndpoint.findByRolIdRol(rol));

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(rol, MediaType.APPLICATION_JSON));

		List<Checklist> obj = response.readEntity(new GenericType<List<Checklist>>() {
		});

		return obj;
	}

	public Checklist buscarPorId(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.buscarPorId(id));

		Response response = client.request().get();

		Checklist obj = response.readEntity(Checklist.class);

		return obj;
	}

	public int countPreguntasByChecklist(Long id) throws ServiceException {

		WebTarget client = createClient(ApplicationEndpoint.countPreguntasByChecklist(id));
		Response response = client.request().get();
		Integer status = response.getStatus();
		int valor;
		if (Status.OK.getStatusCode() == status) {
			valor = response.readEntity(Integer.class);
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return valor;
	}

	public int countCategoriaByChecklist(Long id) throws ServiceException {

		WebTarget client = createClient(ApplicationEndpoint.countCategoriaByChecklist(id));
		Response response = client.request().get();
		Integer status = response.getStatus();
		int valor;
		if (Status.OK.getStatusCode() == status) {
			valor = response.readEntity(Integer.class);
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return valor;
	}

	public List<Pregunta> listPreguntasByChecklist(Long id) throws ServiceException {
		List<Pregunta> listaChecklistes = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.listPreguntasByChecklist(id));
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			listaChecklistes = response.readEntity(new GenericType<List<Pregunta>>() {
			});
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return listaChecklistes;
	}

	public List<Categoria> listCategoriaByChecklist(Long id) throws ServiceException {
		List<Categoria> listaChecklistes = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.listCategoriaByChecklist(id));
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			listaChecklistes = response.readEntity(new GenericType<List<Categoria>>() {
			});
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return listaChecklistes;
	}

}
