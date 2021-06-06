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

import com.cempresarial.entities.DTO.BuscadorDTO;
import com.cempresarial.rest.generic.AbstractClient;
import com.cempresarial.rest.generic.ApplicationEndpoint;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.model.entity.Evaluacion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author DIGETBI 05
 */
public class EvaluacionClient extends AbstractClient {

	private static final Logger log = Logger.getLogger(EvaluacionClient.class.getName());

	public EvaluacionClient(String url, String contextPath) {
		super(url, contextPath);
	}

	public List<Evaluacion> listar() throws ServiceException {
		List<Evaluacion> listaEvaluaciones = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.listar());
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			listaEvaluaciones = response.readEntity(new GenericType<List<Evaluacion>>() {
			});
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return listaEvaluaciones;
	}

	public List<Evaluacion> findBySegmentacion(Long idRegion, Long idZona, Long idProvincia, Long idCiudad,
			Long idZonaEstructural) {
		try {
			List<Evaluacion> listaEvaluaciones = new ArrayList<>();
			WebTarget client = createClient(
					ApplicationEndpoint.findBySegmentacion(idRegion, idZona, idProvincia, idCiudad, idZonaEstructural));
			Response response = client.request().get();
			String resultado = response.readEntity(String.class);
			listaEvaluaciones = new Gson().fromJson(resultado, new TypeToken<ArrayList<Evaluacion>>() {
			}.getType());
			return listaEvaluaciones;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	public List<Evaluacion> findEvaByAgencias(List<Long> listaAgencias) {
		try {

			List<Evaluacion> listaEvaluaciones = new ArrayList<>();

			Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

			String json = gson.toJson(listaAgencias);			
			WebTarget client = createClient(ApplicationEndpoint.findEvaByAgencias());

			Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
			Response response = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));

			listaEvaluaciones = response.readEntity(new GenericType<List<Evaluacion>>() {
			});

			return listaEvaluaciones;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	public List<Evaluacion> findByFiltroTabClienteAndRol(Long idCliente, Long idEmpresa, Long idSector, Long idAgencia,
			Long idEmpleado, Long idRol, Long idArea) {
		try {
			List<Evaluacion> listaEvaluaciones = new ArrayList<>();
			WebTarget client = createClient(ApplicationEndpoint.findByFiltroTabClienteAndRol(idCliente, idEmpresa,
					idSector, idAgencia, idEmpleado, idRol, idArea));
			Response response = client.request().get();
			String resultado = response.readEntity(String.class);
			listaEvaluaciones = new Gson().fromJson(resultado, new TypeToken<ArrayList<Evaluacion>>() {
			}.getType());
			return listaEvaluaciones;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	public List<Evaluacion> findByEvaluacionCategoria(Long idEvaluacion, Long idCategoria) {
		try {
			List<Evaluacion> listaEvaluaciones = new ArrayList<>();
			WebTarget client = createClient(ApplicationEndpoint.findByEvaluacionCategoria(idEvaluacion, idCategoria));
			Response response = client.request().get();
			String resultado = response.readEntity(String.class);
			listaEvaluaciones = new Gson().fromJson(resultado, new TypeToken<ArrayList<Evaluacion>>() {
			}.getType());
			return listaEvaluaciones;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	public List<Evaluacion> findByParams(BuscadorDTO buscador) {

		Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

		String json = gson.toJson(buscador);
		List<Evaluacion> listaEvaluaciones = new ArrayList<>();

		WebTarget client = createClient(ApplicationEndpoint.findByParams());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));

		listaEvaluaciones = response.readEntity(new GenericType<List<Evaluacion>>() {
		});

		return listaEvaluaciones;
	}

	public Evaluacion actualizar(Long id, Evaluacion Evaluacion) {

		Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

		String json = gson.toJson(Evaluacion);

		WebTarget client = createClient(ApplicationEndpoint.actualizar(id));

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(Evaluacion, MediaType.APPLICATION_JSON));

		Evaluacion obj = response.readEntity(Evaluacion.class);

		return obj;
	}

	public void eliminar(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.eliminar(id));
		Invocation.Builder invocationBuilder = client.request();
		invocationBuilder.delete();

	}

	public Evaluacion crear(Evaluacion Evaluacion) {

		Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

		String json = gson.toJson(Evaluacion);

		WebTarget client = createClient(ApplicationEndpoint.insertar());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));

		Evaluacion obj = response.readEntity(Evaluacion.class);

		return obj;
	}

	public Evaluacion buscarPorId(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.buscarPorId(id));

		Response response = client.request().get();

		Evaluacion obj = response.readEntity(Evaluacion.class);

		return obj;
	}

}
