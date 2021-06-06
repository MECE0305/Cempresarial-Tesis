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
import com.cempresariales.servicio.commons.admin.model.entity.PerfilHasUsuario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author DIGETBI 05
 */
public class PerfilHasUsuarioClient extends AbstractClient {

	private static final Logger log = Logger.getLogger(PerfilHasUsuarioClient.class.getName());

	public PerfilHasUsuarioClient(String url, String contextPath) {
		super(url, contextPath);
	}

	public List<PerfilHasUsuario> listar() throws ServiceException {
		List<PerfilHasUsuario> lista = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.listar());
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			String resultado = response.readEntity(String.class);
			lista = new Gson().fromJson(resultado, new TypeToken<ArrayList<PerfilHasUsuario>>() {
			}.getType());
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return lista;
	}

	public PerfilHasUsuario actualizar(Long idTabla1, Long idTabla2, PerfilHasUsuario entidad) {

		WebTarget client = createClient(ApplicationEndpoint.actualizarPK(idTabla1, idTabla2));

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(entidad, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		PerfilHasUsuario obj = new Gson().fromJson(resultado, PerfilHasUsuario.class);

		return obj;
	}

	public void eliminar(Long idTabla1, Long idTabla2) {

		WebTarget client = createClient(ApplicationEndpoint.eliminarPorPK(idTabla1, idTabla2));
		Invocation.Builder invocationBuilder = client.request();
		Response response = invocationBuilder.delete();
		int resultado = response.getStatus();
		System.out.println("STATUS DE ELIMINACION PERFIL USUARIO: " + resultado);
	}

	public PerfilHasUsuario crear(PerfilHasUsuario entidad) {

		WebTarget client = createClient(ApplicationEndpoint.insertar());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(entidad, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		PerfilHasUsuario obj = new Gson().fromJson(resultado, PerfilHasUsuario.class);

		return obj;
	}

	public List<PerfilHasUsuario> findPerfilUsuarioByPerfil(Perfil entidad) {

		WebTarget client = createClient(ApplicationEndpoint.findPerfilUsuarioByPerfil());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(entidad, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		List<PerfilHasUsuario> lista = new ArrayList<>();
		lista = new Gson().fromJson(resultado, new TypeToken<ArrayList<PerfilHasUsuario>>() {
		}.getType());

		return lista;
	}

	public PerfilHasUsuario buscarPorId(Long idTabla1, Long idTabla2) {

		WebTarget client = createClient(ApplicationEndpoint.buscarPorPK(idTabla1, idTabla2));

		Response response = client.request().get();

		String resultado = response.readEntity(String.class);

		PerfilHasUsuario obj = new Gson().fromJson(resultado, PerfilHasUsuario.class);

		return obj;
	}

}
