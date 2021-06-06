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
import com.cempresariales.servicio.commons.admin.model.entity.Aplicacion;
import com.cempresariales.servicio.commons.admin.model.entity.Menu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author DIGETBI 05
 */
public class MenuClient extends AbstractClient {

	private static final Logger log = Logger.getLogger(MenuClient.class.getName());

	public MenuClient(String url, String contextPath) {
		super(url, contextPath);
	}

	public List<Menu> listar() throws ServiceException {
		List<Menu> lista = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.listar());
		Response response = client.request().get();
		Integer status = response.getStatus();
		if (Status.OK.getStatusCode() == status) {
			String resultado = response.readEntity(String.class);
			lista = new Gson().fromJson(resultado, new TypeToken<ArrayList<Menu>>() {
			}.getType());
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return lista;
	}

	public Menu actualizar(Long id, Menu entidad) {

		Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

		String json = gson.toJson(entidad);

		WebTarget client = createClient(ApplicationEndpoint.actualizar(id));

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(json, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		Menu obj = new Gson().fromJson(resultado, Menu.class);

		return obj;
	}

	public void eliminar(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.eliminar(id));
		Invocation.Builder invocationBuilder = client.request();
		invocationBuilder.delete();

	}

	public Menu crear(Menu entidad) {

		Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

		String json = gson.toJson(entidad);
		WebTarget client = createClient(ApplicationEndpoint.insertar());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		Menu obj = new Gson().fromJson(resultado, Menu.class);

		return obj;
	}

	public List<Menu> findByAplicacion(Aplicacion entidad) {

		List<Menu> lista = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.findByAplicacion());

		Invocation.Builder invocationBuilder = client.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(entidad, MediaType.APPLICATION_JSON));

		String resultado = response.readEntity(String.class);

		lista = new Gson().fromJson(resultado, new TypeToken<ArrayList<Menu>>() {
		}.getType());

		return lista;
	}

	public List<Menu> obtenerMenusPorRolAplicativo(String rol, long idAplicativo) {

		List<Menu> lista = new ArrayList<>();
		try {

			WebTarget client = createClient(ApplicationEndpoint.obtenerMenusPorRolAplicativo(rol, idAplicativo));
			Response response = client.request().get();

			String resultado = response.readEntity(String.class);
			lista = new Gson().fromJson(resultado, new TypeToken<ArrayList<Menu>>() {
			}.getType());

			return lista;
		} catch (Exception e) {
			// TODO: handle exception
			return lista;
		}

	}

	public List<Menu> obtenerMenusHijo(String rol, long idMenuPadre, String opcion, String opcion2) {

		List<Menu> lista = new ArrayList<>();
		try {

			WebTarget client = createClient(ApplicationEndpoint.obtenerMenusHijo(rol, idMenuPadre, opcion, opcion2));
			Response response = client.request().get();

			String resultado = response.readEntity(String.class);
			lista = new Gson().fromJson(resultado, new TypeToken<ArrayList<Menu>>() {
			}.getType());

			return lista;
		} catch (Exception e) {
			// TODO: handle exception
			return lista;
		}

	}

	public List<Menu> findMenuPadreByAplicacion(Long id) {

		List<Menu> lista = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.findMenuPadreByAplicacion(id));
		Response response = client.request().get();
		Integer status = response.getStatus();

		String resultado = response.readEntity(String.class);
		lista = new Gson().fromJson(resultado, new TypeToken<ArrayList<Menu>>() {
		}.getType());

		return lista;
	}

	public List<Menu> findByMenuPadre(Long id) {

		List<Menu> lista = new ArrayList<>();
		WebTarget client = createClient(ApplicationEndpoint.findByMenuPadre(id));
		Response response = client.request().get();
		Integer status = response.getStatus();

		String resultado = response.readEntity(String.class);
		lista = new Gson().fromJson(resultado, new TypeToken<ArrayList<Menu>>() {
		}.getType());

		return lista;
	}

	public List<Menu> findOpcionesByAplicacionAndMenuPadre(Long idAplicacion, Long idMenuPadre) {

		List<Menu> lista = new ArrayList<>();
		WebTarget client = createClient(
				ApplicationEndpoint.findOpcionesByAplicacionAndMenuPadre(idAplicacion, idMenuPadre));
		Response response = client.request().get();
		Integer status = response.getStatus();

		String resultado = response.readEntity(String.class);
		lista = new Gson().fromJson(resultado, new TypeToken<ArrayList<Menu>>() {
		}.getType());

		return lista;
	}

	public Menu buscarPorId(Long id) {

		WebTarget client = createClient(ApplicationEndpoint.buscarPorId(id));

		Response response = client.request().get();

		String resultado = response.readEntity(String.class);

		Menu obj = new Gson().fromJson(resultado, Menu.class);

		return obj;
	}

}
