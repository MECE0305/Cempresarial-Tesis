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

import com.cempresarial.rest.generic.AbstractClient;
import com.cempresarial.rest.generic.ApplicationEndpoint;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.model.entity.Area;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author DIGETBI 05
 */
public class AreaClient extends AbstractClient {

	private static final Logger log = Logger.getLogger(AreaClient.class.getName());

	public AreaClient(String url, String contextPath) {
		super(url, contextPath);
	}

	public List<Area> listar() throws ServiceException {
		List<Area> list = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.listar());
		Response response = client.request().get();
		Area result = null;
		Integer status = response.getStatus();

		if (200 == status) {

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String resultado = response.readEntity(String.class);

			list = new Gson().fromJson(resultado, new TypeToken<ArrayList<Area>>() {
			}.getType());

		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return list;
	}
	
	
	public List<Area> findAreasByRoles(List<Long> lista) {
		try {

			List<Area> list = new ArrayList<>();

			Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

			String json = gson.toJson(lista);

			WebTarget client = createClient(ApplicationEndpoint.findAreasByRoles());

			Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
			Response response = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));

			list = response.readEntity(new GenericType<List<Area>>() {
			});

			return list;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	public Area actualizar(Long id, Area entidad) {


		WebTarget client = createClient(ApplicationEndpoint.actualizar(id));
		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(entidad, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		Area obj = new Gson().fromJson(resultado, Area.class);

		return obj;
	}

	public void eliminar(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.eliminar(id));

		Invocation.Builder invocationBuilder = client.request();
		invocationBuilder.delete();

	}

	public Area crear(Area entidad) {

		WebTarget client = createClient(ApplicationEndpoint.insertar());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(entidad, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		Area obj = new Gson().fromJson(resultado, Area.class);

		return obj;
		

	}

	public Area buscarPorId(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.buscarPorId(id));

		Response response = client.request().get();
		
		String resultado = response.readEntity(String.class);

		Area obj = new Gson().fromJson(resultado,  Area.class);
		
		return obj;
		
	}

}
