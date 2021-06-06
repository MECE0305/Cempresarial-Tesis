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
import com.cempresariales.servicio.commons.model.entity.Agencia;
import com.cempresariales.servicio.commons.model.entity.Empresa;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author DIGETBI 05
 */
public class AgenciaClient extends AbstractClient {

	private static final Logger log = Logger.getLogger(AgenciaClient.class.getName());

	public AgenciaClient(String url, String contextPath) {
		super(url, contextPath);
	}

	public List<Agencia> listar() throws ServiceException {
		List<Agencia> listaAgenciaes = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.listar());
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String resultado = response.readEntity(String.class);

			listaAgenciaes = new Gson().fromJson(resultado, new TypeToken<ArrayList<Agencia>>() {
			}.getType());
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return listaAgenciaes;
	}

	public Agencia actualizar(Long id, Agencia Agencia) {

		WebTarget client = createClient(ApplicationEndpoint.actualizar(id));

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(Agencia, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		Agencia obj = new Gson().fromJson(resultado,  Agencia.class);
		
		return obj;
	}

	public void eliminar(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.eliminar(id));
		Invocation.Builder invocationBuilder = client.request();
		invocationBuilder.delete();

	}

	public Agencia crear(Agencia Agencia) {

		WebTarget client = createClient(ApplicationEndpoint.insertar());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(Agencia, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		Agencia obj = new Gson().fromJson(resultado,  Agencia.class);
		
		return obj;
	}

	public Agencia buscarPorId(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.buscarPorId(id));

		Response response = client.request().get();


		String resultado = response.readEntity(String.class);

		Agencia obj = new Gson().fromJson(resultado,  Agencia.class);
		
		return obj;
	}

	public List<Agencia> findByEmpresaIdEmpresa(Empresa id) {

		WebTarget client = createClient(ApplicationEndpoint.findByEmpresaIdEmpresa(id));

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(id, MediaType.APPLICATION_JSON));

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String resultado = response.readEntity(String.class);

		List<Agencia> obj = new Gson().fromJson(resultado, new TypeToken<ArrayList<Agencia>>() {
		}.getType());

		return obj;
	}

}
