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

import com.cempresarial.recursos.Password;
import com.cempresarial.rest.generic.AbstractClient;
import com.cempresarial.rest.generic.ApplicationEndpoint;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.admin.model.entity.Usuario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author DIGETBI 05
 */
public class UsuarioClient extends AbstractClient {

	private static final Logger log = Logger.getLogger(UsuarioClient.class.getName());

	public UsuarioClient(String url, String contextPath) {
		super(url, contextPath);
	}

	public List<Usuario> listar() throws ServiceException {
		List<Usuario> lista = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.listar());
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			String resultado = response.readEntity(String.class);
			lista = new Gson().fromJson(resultado, new TypeToken<ArrayList<Usuario>>() {
			}.getType());
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return lista;
	}

	public Usuario actualizar(Long id, Usuario entidad) {

		Password pass = new Password();
		String bcrypt = pass.hashPassword(entidad.getClave());
		entidad.setClave(bcrypt);

		WebTarget client = createClient(ApplicationEndpoint.actualizar(id));

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(entidad, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		Usuario obj = new Gson().fromJson(resultado, Usuario.class);

		return obj;
	}

	public Usuario restablecer(Long id, Usuario entidad) {

		Password pass = new Password();
		String bcrypt = pass.hashPassword(entidad.getClave());
		entidad.setClave(bcrypt);

		WebTarget client = createClient(ApplicationEndpoint.restablecer(id));

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(entidad, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		Usuario obj = new Gson().fromJson(resultado, Usuario.class);

		return obj;
	}

	public void eliminar(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.eliminar(id));
		Invocation.Builder invocationBuilder = client.request();
		invocationBuilder.delete();

	}

	public Usuario crear(Usuario entidad) {

		Password pass = new Password();
		String bcrypt = pass.hashPassword(entidad.getClave());
		entidad.setClave(bcrypt);
		WebTarget client = createClient(ApplicationEndpoint.insertar());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(entidad, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		Usuario obj = new Gson().fromJson(resultado, Usuario.class);

		return obj;
	}

	public Usuario findByNombreUsuario(Usuario entidad) {

		try {
			WebTarget client = createClient(ApplicationEndpoint.findByNombreUsuario());

			Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
			Response response = invocationBuilder.post(Entity.entity(entidad, MediaType.APPLICATION_JSON));

			String resultado = response.readEntity(String.class);

			Usuario obj = new Gson().fromJson(resultado, Usuario.class);

			return obj;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	public Usuario findByNombreUsuarioAndClave(Usuario entidad) {

		WebTarget client = createClient(ApplicationEndpoint.findByNombreUsuarioAndClave());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(entidad, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		Usuario obj = new Gson().fromJson(resultado, Usuario.class);

		return obj;
	}

	public Usuario buscarPorId(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.buscarPorId(id));

		Response response = client.request().get();

		String resultado = response.readEntity(String.class);

		Usuario obj = new Gson().fromJson(resultado, Usuario.class);

		return obj;
	}

}
