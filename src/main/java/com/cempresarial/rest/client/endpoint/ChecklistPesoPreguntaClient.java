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
import javax.ws.rs.core.Response.Status;

import com.cempresarial.rest.generic.AbstractClient;
import com.cempresarial.rest.generic.ApplicationEndpoint;
import com.cempresarial.rest.generic.ServiceException;

/**
 *
 * @author DIGETBI 05
 */
public class ChecklistPesoPreguntaClient extends AbstractClient {

	private static final Logger log = Logger.getLogger(ChecklistPesoPreguntaClient.class.getName());

	public ChecklistPesoPreguntaClient(String url, String contextPath) {
		super(url, contextPath);
	}

	/*public List<ChecklistHasPesoHasPregunta> listar() throws ServiceException {
		List<ChecklistHasPesoHasPregunta> list = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.listar());
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			list = response.readEntity(new GenericType<List<ChecklistHasPesoHasPregunta>>() {
			});
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return list;
	}

	public ChecklistHasPesoHasPregunta actualizar(Long id, ChecklistHasPesoHasPregunta entidad) {

		WebTarget client = createClient(ApplicationEndpoint.actualizar(id));

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(entidad, MediaType.APPLICATION_JSON));

		ChecklistHasPesoHasPregunta object = response.readEntity(ChecklistHasPesoHasPregunta.class);

		return object;
	}

	public void eliminar(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.eliminar(id));
		Invocation.Builder invocationBuilder = client.request();
		invocationBuilder.delete();

	}

	public ChecklistHasPesoHasPregunta crear(ChecklistHasPesoHasPregunta entidad) {

		WebTarget client = createClient(ApplicationEndpoint.insertar());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(entidad, MediaType.APPLICATION_JSON));

		ChecklistHasPesoHasPregunta obj = response.readEntity(ChecklistHasPesoHasPregunta.class);

		return obj;
	}

	public ChecklistHasPesoHasPregunta buscarPorId(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.buscarPorId(id));

		Response response = client.request().get();

		ChecklistHasPesoHasPregunta obj = response.readEntity(ChecklistHasPesoHasPregunta.class);

		return obj;
	}

	public int countPreguntasByChecklist(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.countPreguntasByChecklist(id));

		Response response = client.request().get();

		int obj = response.readEntity(Integer.class);

		return obj;
	}
	
	public int countCategoriaPreguntasByChecklist(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.countCategoriaByChecklist(id));

		Response response = client.request().get();

		int obj = response.readEntity(Integer.class);

		return obj;
	}*/

}
