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
import com.cempresariales.servicio.commons.model.entity.Empleado;
import com.cempresariales.servicio.commons.model.entity.Empresa;
import com.cempresariales.servicio.commons.model.entity.Rol;
import com.cempresariales.servicio.commons.model.entity.RolHasEmpleado;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author DIGETBI 05
 */
public class EmpleadoHasRolClient extends AbstractClient {

	private static final Logger log = Logger.getLogger(EmpleadoHasRolClient.class.getName());

	public EmpleadoHasRolClient(String url, String contextPath) {
		super(url, contextPath);
	}

	public List<RolHasEmpleado> listar() throws ServiceException {
		List<RolHasEmpleado> listaRolHasEmpleadoes = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.listar());
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			listaRolHasEmpleadoes = response.readEntity(new GenericType<List<RolHasEmpleado>>() {
			});
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return listaRolHasEmpleadoes;
	}

	public RolHasEmpleado actualizar(Long id, RolHasEmpleado entidad) {

		WebTarget client = createClient(ApplicationEndpoint.actualizar(id));

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(entidad, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		RolHasEmpleado obj = new Gson().fromJson(resultado, RolHasEmpleado.class);

		return obj;
	}

	public void eliminar(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.eliminar(id));
		Invocation.Builder invocationBuilder = client.request();
		invocationBuilder.delete();

	}

	public RolHasEmpleado crear(RolHasEmpleado entidad) {

		WebTarget client = createClient(ApplicationEndpoint.insertar());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(entidad, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		RolHasEmpleado obj = new Gson().fromJson(resultado, RolHasEmpleado.class);

		return obj;
	}

	public RolHasEmpleado buscarPorId(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.buscarPorId(id));

		Response response = client.request().get();

		String resultado = response.readEntity(String.class);

		RolHasEmpleado obj = new Gson().fromJson(resultado, RolHasEmpleado.class);

		return obj;
	}

	public List<RolHasEmpleado> findByEmpresaIdEmpresa(Empresa id) {

		WebTarget client = createClient(ApplicationEndpoint.findByEmpresaIdEmpresa(id));

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(id, MediaType.APPLICATION_JSON));

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String resultado = response.readEntity(String.class);

		List<RolHasEmpleado> obj = new Gson().fromJson(resultado, new TypeToken<ArrayList<RolHasEmpleado>>() {
		}.getType());

		return obj;
	}

	public List<RolHasEmpleado> findRolHasEmpleadoByEmpleado(Empleado entidad) throws ServiceException {

		List<RolHasEmpleado> listaRolHasEmpleadoes = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.findRolHasEmpleadoByEmpleado(entidad.getIdEmpleado()));
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			listaRolHasEmpleadoes = response.readEntity(new GenericType<List<RolHasEmpleado>>() {
			});
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return listaRolHasEmpleadoes;
	}
	
	public List<Empleado> findRolHasEmpleadoByRol(Rol entidad) throws ServiceException {

		List<Empleado> listaRolHasEmpleadoes = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.findByRol(entidad.getIdRol()));
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			listaRolHasEmpleadoes = response.readEntity(new GenericType<List<Empleado>>() {
			});
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return listaRolHasEmpleadoes;
	}

}
