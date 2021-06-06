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
import com.cempresariales.servicio.commons.model.entity.ZonaEstructural;
import com.cempresariales.servicio.commons.model.entity.ZonaEstructuralHasCiudad;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author DIGETBI 05
 */
public class ZonaEstructuralClient extends AbstractClient {

	private static final Logger log = Logger.getLogger(ZonaEstructuralClient.class.getName());

	public ZonaEstructuralClient(String url, String contextPath) {
		super(url, contextPath);
	}

	public List<ZonaEstructural> listar() throws ServiceException {
		List<ZonaEstructural> list = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.listar());
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String resultado = response.readEntity(String.class);

			list = new Gson().fromJson(resultado, new TypeToken<ArrayList<ZonaEstructural>>() {
			}.getType());
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return list;
	}
	
	public List<ZonaEstructural> listarXCiudad(Long id) throws ServiceException {
		List<ZonaEstructural> list = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.findZonaEstructuralByCiudad(id));
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String resultado = response.readEntity(String.class);

			list = new Gson().fromJson(resultado, new TypeToken<ArrayList<ZonaEstructural>>() {
			}.getType());
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return list;
	}
	
	public List<ZonaEstructuralHasCiudad> listarZHCXZonaEstructural(Long id) throws ServiceException {
		List<ZonaEstructuralHasCiudad> list = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.findZonaEstructuralCiudadByZonaEstructura(id));
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String resultado = response.readEntity(String.class);

			list = new Gson().fromJson(resultado, new TypeToken<ArrayList<ZonaEstructuralHasCiudad>>() {
			}.getType());
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return list;
	}

	public ZonaEstructural actualizar(Long id, ZonaEstructural entidad) {
		
		
		Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

		String json = gson.toJson(entidad);

		WebTarget client = createClient(ApplicationEndpoint.actualizar(id));

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(json, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		ZonaEstructural obj = new Gson().fromJson(resultado,  ZonaEstructural.class);
		
		return obj;
	}

	public void eliminar(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.eliminar(id));
		Invocation.Builder invocationBuilder = client.request();
		invocationBuilder.delete();

	}

	public ZonaEstructural crear(ZonaEstructural entidad) {

		WebTarget client = createClient(ApplicationEndpoint.insertar());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(entidad, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		ZonaEstructural obj = new Gson().fromJson(resultado,  ZonaEstructural.class);
		
		return obj;
	}

	public ZonaEstructural buscarPorId(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.buscarPorId(id));

		Response response = client.request().get();

		String resultado = response.readEntity(String.class);

		ZonaEstructural obj = new Gson().fromJson(resultado, ZonaEstructural.class);

		return obj;
	}

}
