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
import com.cempresariales.servicio.commons.model.entity.RangoDesempenio;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author DIGETBI 05
 */
public class RangoClient extends AbstractClient {

	private static final Logger log = Logger.getLogger(RangoClient.class.getName());

	public RangoClient(String url, String contextPath) {
		super(url, contextPath);
	}

	public List<RangoDesempenio> listar() throws ServiceException {
		List<RangoDesempenio> list = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.listar());
		Response response = client.request().get();
		RangoDesempenio result = null;
		Integer status = response.getStatus();

		if (200 == status) {

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String resultado = response.readEntity(String.class);

			list = new Gson().fromJson(resultado, new TypeToken<ArrayList<RangoDesempenio>>() {
			}.getType());

		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return list;
	}
	
	public List<RangoDesempenio> findByEmpresa(Long idEmpresa) throws ServiceException {
		List<RangoDesempenio> list = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.findByEmpresa(idEmpresa));
		Response response = client.request().get();
		Integer status = response.getStatus();

		if (200 == status) {

			String resultado = response.readEntity(String.class);

			list = new Gson().fromJson(resultado, new TypeToken<ArrayList<RangoDesempenio>>() {
			}.getType());

		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return list;
	}
	
	
	

	public RangoDesempenio actualizar(Long id, RangoDesempenio entidad) {

		Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

		String json = gson.toJson(entidad);

		WebTarget client = createClient(ApplicationEndpoint.actualizar(id));
		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(json, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		RangoDesempenio obj = new Gson().fromJson(resultado, RangoDesempenio.class);

		return obj;
	}

	public void eliminar(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.eliminar(id));

		Invocation.Builder invocationBuilder = client.request();
		invocationBuilder.delete();

	}

	public RangoDesempenio crear(RangoDesempenio entidad) {

		Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

		String json = gson.toJson(entidad);

		WebTarget client = createClient(ApplicationEndpoint.insertar());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		RangoDesempenio obj = new Gson().fromJson(resultado, RangoDesempenio.class);

		return obj;

	}

	public RangoDesempenio buscarPorId(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.buscarPorId(id));

		Response response = client.request().get();

		String resultado = response.readEntity(String.class);

		RangoDesempenio obj = new Gson().fromJson(resultado, RangoDesempenio.class);

		return obj;

	}
	
	
	public RangoDesempenio findByRangoAndEmpresa(float rango, Long idEmpresa) {

		WebTarget client = createClient(ApplicationEndpoint.findByRangoAndEmpresa(rango, idEmpresa));

		Response response = client.request().get();

		String resultado = response.readEntity(String.class);

		RangoDesempenio obj = new Gson().fromJson(resultado, RangoDesempenio.class);

		return obj;

	}
	
	

}
