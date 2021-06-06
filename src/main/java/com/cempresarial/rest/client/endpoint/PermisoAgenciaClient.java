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
import com.cempresariales.servicio.commons.admin.model.entity.PermisoAgencia;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author DIGETBI 05
 */
public class PermisoAgenciaClient extends AbstractClient {

	private static final Logger log = Logger.getLogger(PermisoAgenciaClient.class.getName());

	public PermisoAgenciaClient(String url, String contextPath) {
		super(url, contextPath);
	}

	public List<PermisoAgencia> listar() throws ServiceException {
		List<PermisoAgencia> lista = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.listar());
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			String resultado = response.readEntity(String.class);
			lista = new Gson().fromJson(resultado, new TypeToken<ArrayList<PermisoAgencia>>() {
			}.getType());
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return lista;
	}
	
	
	public List<PermisoAgencia> findPermisosAgeciaByPerfil(Long id) throws ServiceException {
		List<PermisoAgencia> lista = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.findPermisosAgeciaByPerfil(id));
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			String resultado = response.readEntity(String.class);
			lista = new Gson().fromJson(resultado, new TypeToken<ArrayList<PermisoAgencia>>() {
			}.getType());
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return lista;
	}
	
	
	

	public PermisoAgencia actualizar(Long idTabla1, PermisoAgencia entidad) {

		Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

		String json = gson.toJson(entidad);

		WebTarget client = createClient(ApplicationEndpoint.actualizar(idTabla1));

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(json, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		PermisoAgencia obj = new Gson().fromJson(resultado, PermisoAgencia.class);

		return obj;
	}

	public void eliminar(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.eliminar(id));
		Invocation.Builder invocationBuilder = client.request();
		invocationBuilder.delete();

	}

	public PermisoAgencia crear(PermisoAgencia entidad) {

		Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

		String json = gson.toJson(entidad);

		System.out.println("JSON DE PERMISO AGENCIA: " + json);

		WebTarget client = createClient(ApplicationEndpoint.insertar());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		PermisoAgencia obj = new Gson().fromJson(resultado, PermisoAgencia.class);

		return obj;
	}

	public PermisoAgencia buscarPorId(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.buscarPorId(id));

		Response response = client.request().get();

		String resultado = response.readEntity(String.class);

		PermisoAgencia obj = new Gson().fromJson(resultado, PermisoAgencia.class);

		return obj;
	}

}
