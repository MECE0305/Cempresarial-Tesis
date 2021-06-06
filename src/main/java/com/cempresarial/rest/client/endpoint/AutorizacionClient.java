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
import com.cempresariales.servicio.commons.admin.model.entity.Autorizacion;
import com.cempresariales.servicio.commons.admin.model.entity.Menu;
import com.cempresariales.servicio.commons.admin.model.entity.Perfil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author DIGETBI 05
 */
public class AutorizacionClient extends AbstractClient {

	private static final Logger log = Logger.getLogger(AutorizacionClient.class.getName());

	public AutorizacionClient(String url, String contextPath) {
		super(url, contextPath);
	}

	public List<Autorizacion> listar() throws ServiceException {
		List<Autorizacion> lista = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.listar());
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			String resultado = response.readEntity(String.class);
			lista = new Gson().fromJson(resultado, new TypeToken<ArrayList<Autorizacion>>() {
			}.getType());
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return lista;
	}

	public Autorizacion actualizar(Long id, Autorizacion entidad) {

		WebTarget client = createClient(ApplicationEndpoint.actualizar(id));

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(entidad, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		Autorizacion obj = new Gson().fromJson(resultado, Autorizacion.class);

		return obj;
	}
	
	public List<Autorizacion> findByMenu(Menu entidad) {

		
		Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

		List<Autorizacion> lista = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.findByMenu());
		
		String json = gson.toJson(entidad.getIdMenu());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);
		
		
		System.out.println("RESPONSE: "+resultado);

		lista = new Gson().fromJson(resultado, new TypeToken<ArrayList<Autorizacion>>() {
		}.getType());

		return lista;
	}
	
public List<Autorizacion> findByPerfil(Perfil entidad) {

		
		List<Autorizacion> lista = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.findByPerfil());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(entidad, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		lista = new Gson().fromJson(resultado, new TypeToken<ArrayList<Autorizacion>>() {
		}.getType());
		
		return lista;
	}

	public void eliminar(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.eliminar(id));
		Invocation.Builder invocationBuilder = client.request();
		invocationBuilder.delete();

	}

	public Autorizacion crear(Autorizacion entidad) {
		
		Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

		String json = gson.toJson(entidad);

		WebTarget client = createClient(ApplicationEndpoint.insertar());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		Autorizacion obj = new Gson().fromJson(resultado, Autorizacion.class);

		return obj;
	}
	
	
	public Autorizacion findByUrlMenu(String urlMenu) {

		WebTarget client = createClient(ApplicationEndpoint.findByUrlMenu());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(urlMenu, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		Autorizacion obj = new Gson().fromJson(resultado, Autorizacion.class);

		return obj;
	}

	public Autorizacion buscarPorId(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.buscarPorId(id));

		Response response = client.request().get();

		String resultado = response.readEntity(String.class);

		Autorizacion obj = new Gson().fromJson(resultado, Autorizacion.class);

		return obj;
	}

}
