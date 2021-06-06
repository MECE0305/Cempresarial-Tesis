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
import com.cempresariales.servicio.commons.admin.model.entity.Perfil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author DIGETBI 05
 */
public class PerfilClient extends AbstractClient {

	private static final Logger log = Logger.getLogger(PerfilClient.class.getName());

	public PerfilClient(String url, String contextPath) {
		super(url, contextPath);
	}

	public List<Perfil> listar() throws ServiceException {
		List<Perfil> lista = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.listar());
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			String resultado = response.readEntity(String.class);
			lista = new Gson().fromJson(resultado, new TypeToken<ArrayList<Perfil>>() {
			}.getType());
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return lista;
	}

	public List<Perfil> obtenerPerfilesUsuario(String nombreUsuario) {
		List<Perfil> lista = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.obtenerPerfilesUsuario(nombreUsuario));
		Response response = client.request().get();

		String resultado = response.readEntity(String.class);
		lista = new Gson().fromJson(resultado, new TypeToken<ArrayList<Perfil>>() {
		}.getType());

		return lista;
	}

	public Perfil actualizar(Long id, Perfil entidad) {

		WebTarget client = createClient(ApplicationEndpoint.actualizar(id));

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(entidad, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		Perfil obj = new Gson().fromJson(resultado, Perfil.class);

		return obj;
	}

	public void eliminar(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.eliminar(id));
		Invocation.Builder invocationBuilder = client.request();
		invocationBuilder.delete();

	}

	public Perfil crear(Perfil entidad) {

		WebTarget client = createClient(ApplicationEndpoint.insertar());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(entidad, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		Perfil obj = new Gson().fromJson(resultado, Perfil.class);

		return obj;
	}

	public Perfil buscarPorId(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.buscarPorId(id));

		Response response = client.request().get();

		String resultado = response.readEntity(String.class);

		Perfil obj = new Gson().fromJson(resultado, Perfil.class);

		return obj;
	}

	public Perfil findPerfilByNombre(String nombre) {

		WebTarget client = createClient(ApplicationEndpoint.findPerfilByNombre(nombre));

		Response response = client.request().get();

		String resultado = response.readEntity(String.class);

		Perfil obj = new Gson().fromJson(resultado, Perfil.class);

		return obj;
	}

}
