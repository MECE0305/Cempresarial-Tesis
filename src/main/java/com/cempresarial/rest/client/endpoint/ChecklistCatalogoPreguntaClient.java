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
import com.cempresariales.servicio.commons.model.entity.ChecklistHasCatalogoPregunta;
import com.google.gson.Gson;

/**
 *
 * @author DIGETBI 05
 */
public class ChecklistCatalogoPreguntaClient extends AbstractClient {

	private static final Logger log = Logger.getLogger(ChecklistCatalogoPreguntaClient.class.getName());

	public ChecklistCatalogoPreguntaClient(String url, String contextPath) {
		super(url, contextPath);
	}

	public List<ChecklistHasCatalogoPregunta> listar() throws ServiceException {
		List<ChecklistHasCatalogoPregunta> listaChecklistes = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.listar());
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			listaChecklistes = response.readEntity(new GenericType<List<ChecklistHasCatalogoPregunta>>() {
			});
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return listaChecklistes;
	}

	public List<ChecklistHasCatalogoPregunta> findByChecklistID(Long id) throws ServiceException {
		List<ChecklistHasCatalogoPregunta> listaChecklistes = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.findByChecklistID(id));
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			listaChecklistes = response.readEntity(new GenericType<List<ChecklistHasCatalogoPregunta>>() {
			});
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return listaChecklistes;
	}

	public List<ChecklistHasCatalogoPregunta> findByCategoria(Long id) throws ServiceException {
		List<ChecklistHasCatalogoPregunta> listaChecklistes = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.findByCategoria(id));
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			listaChecklistes = response.readEntity(new GenericType<List<ChecklistHasCatalogoPregunta>>() {
			});
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return listaChecklistes;
	}

	public List<ChecklistHasCatalogoPregunta> findByCategoriaChecklist(Long idCategoria, Long idChecklist)
			throws ServiceException {
		List<ChecklistHasCatalogoPregunta> listaChecklistes = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.findByCategoriaChecklist(idCategoria, idChecklist));
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			listaChecklistes = response.readEntity(new GenericType<List<ChecklistHasCatalogoPregunta>>() {
			});
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return listaChecklistes;
	}

	public void eliminar(Long idChecklist, Long idCategoria, Long idPregunta, Long idPeso) {

		WebTarget client = createClient(
				ApplicationEndpoint.eliminarCheckCatalogoPregunta(idChecklist, idCategoria, idPregunta, idPeso));

		Invocation.Builder invocationBuilder = client.request();
		invocationBuilder.delete();

	}

	public ChecklistHasCatalogoPregunta crear(ChecklistHasCatalogoPregunta entidad) {

		WebTarget client = createClient(ApplicationEndpoint.insertar());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(entidad, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		ChecklistHasCatalogoPregunta obj = new Gson().fromJson(resultado, ChecklistHasCatalogoPregunta.class);

		return obj;
	}

}
