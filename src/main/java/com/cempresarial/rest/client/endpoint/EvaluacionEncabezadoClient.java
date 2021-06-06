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
import javax.ws.rs.core.Response.Status;

import com.cempresarial.rest.generic.AbstractClient;
import com.cempresarial.rest.generic.ApplicationEndpoint;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.model.entity.EvaluacionHasEncabezado;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author DIGETBI 05
 */
public class EvaluacionEncabezadoClient extends AbstractClient {

	private static final Logger log = Logger.getLogger(EvaluacionEncabezadoClient.class.getName());

	public EvaluacionEncabezadoClient(String url, String contextPath) {
		super(url, contextPath);
	}

	public List<EvaluacionHasEncabezado> listar() throws ServiceException {
		List<EvaluacionHasEncabezado> list = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.listar());
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			String resultado = response.readEntity(String.class);

			list = new Gson().fromJson(resultado, new TypeToken<ArrayList<EvaluacionHasEncabezado>>() {
			}.getType());
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return list;
	}

	public List<EvaluacionHasEncabezado> findByEncabezado(Long id) throws ServiceException {
		List<EvaluacionHasEncabezado> list = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.findByEncabezado(id));
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			String resultado = response.readEntity(String.class);

			list = new Gson().fromJson(resultado, new TypeToken<ArrayList<EvaluacionHasEncabezado>>() {
			}.getType());
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return list;
	}

	public List<EvaluacionHasEncabezado> findByEvaluacion(Long id) throws ServiceException {
		List<EvaluacionHasEncabezado> list = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.findByEvaluacion(id));
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			String resultado = response.readEntity(String.class);

			list = new Gson().fromJson(resultado, new TypeToken<ArrayList<EvaluacionHasEncabezado>>() {
			}.getType());
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return list;
	}

	public EvaluacionHasEncabezado crear(EvaluacionHasEncabezado entidad) {

		WebTarget client = createClient(ApplicationEndpoint.insertar());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(entidad, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		EvaluacionHasEncabezado obj = new Gson().fromJson(resultado, EvaluacionHasEncabezado.class);

		return obj;
	}

	public void eliminar(Long idEvaluacion, Long idEncabezado) {

		WebTarget client = createClient(ApplicationEndpoint.eliminarEvaluacionEncabezado(idEvaluacion, idEncabezado));

		Invocation.Builder invocationBuilder = client.request();
		invocationBuilder.delete();

	}

	public EvaluacionHasEncabezado actualizar(Long idTabla1, Long idTabla2, EvaluacionHasEncabezado entidad) {

		Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

		String json = gson.toJson(entidad);

		WebTarget client = createClient(ApplicationEndpoint.actualizarPK(idTabla1, idTabla2));
		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(json, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		EvaluacionHasEncabezado obj = new Gson().fromJson(resultado, EvaluacionHasEncabezado.class);

		return obj;
	}

}
