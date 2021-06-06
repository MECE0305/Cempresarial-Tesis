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
import com.cempresariales.servicio.commons.model.entity.Sector;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author DIGETBI 05
 */
public class SectorClient extends AbstractClient {

	private static final Logger log = Logger.getLogger(SectorClient.class.getName());

	public SectorClient(String url, String contextPath) {
		super(url, contextPath);
	}

	public List<Sector> listar() throws ServiceException {
		List<Sector> list = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.listar());
		Response response = client.request().get();
		Sector result = null;
		Integer status = response.getStatus();

		if (200 == status) {

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String resultado = response.readEntity(String.class);

			list = new Gson().fromJson(resultado, new TypeToken<ArrayList<Sector>>() {
			}.getType());

		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return list;
	}

	public Sector actualizar(Long id, Sector entidad) {
		
	

		WebTarget client = createClient(ApplicationEndpoint.actualizar(id));

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(entidad, MediaType.APPLICATION_JSON));

		Sector obj = response.readEntity(Sector.class);

		return obj;
	}

	public void eliminar(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.eliminar(id));

		Invocation.Builder invocationBuilder = client.request();
		invocationBuilder.delete();

	}

	public Sector crear(Sector entidad) {
		
		

		WebTarget client = createClient(ApplicationEndpoint.insertar());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(entidad, MediaType.APPLICATION_JSON));

		Sector obj = response.readEntity(Sector.class);

		return obj;
	}

	public Sector buscarPorId(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.buscarPorId(id));

		Response response = client.request().get();
		
		String resultado = response.readEntity(String.class);

		Sector obj = new Gson().fromJson(resultado,  Sector.class);

		return obj;
	}

}
