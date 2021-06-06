/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cempresarial.rest.client.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import com.cempresarial.recursos.Rutas;
import com.cempresarial.rest.client.endpoint.ChecklistClient;
import com.cempresarial.rest.generic.ServiceException;
import com.cempresariales.servicio.commons.model.entity.Categoria;
import com.cempresariales.servicio.commons.model.entity.Checklist;
import com.cempresariales.servicio.commons.model.entity.Pregunta;
import com.cempresariales.servicio.commons.model.entity.Rol;

/**
 *
 * @author DIGETBI 05
 */
@Stateless
public class ChecklistService extends Rutas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String URL = getURL_CHECKLIST();
	//final String URL = "https://serviciochecklist.herokuapp.com/";
	final String PATH = "/checklist";
	private ChecklistClient client = new ChecklistClient(URL, PATH);

	public List<Checklist> listar() {
		List<Checklist> list = new ArrayList<>();
		try {
			list.addAll(client.listar());
		} catch (ServiceException ex) {
			ex.toString();
		}
		return list;
	}
	
	public List<Checklist> findCheckListByRoles(List<Long> lista) {
		List<Checklist> list = new ArrayList<>();
		try {
			list.addAll(client.findCheckListByRoles(lista));
		} catch (Exception ex) {
			ex.toString();
		}
		return list;
	}

	public Checklist insertar(Checklist entidad) {
		return client.crear(entidad);
	}

	public void actualizar(Long id, Checklist entidad) {
		Checklist obj;
		obj = client.actualizar(id, entidad);
	}

	public Checklist buscarPorId(Long id) {
		Checklist obj;
		obj = client.buscarPorId(id);
		return obj;
	}

	public void eliminar(Long id) {
		client.eliminar(id);
	}

	public List<Checklist> findByRolIdRol(Rol rol) {
		try {
			return client.findByRolIdRol(rol);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int countPreguntasByChecklist(Long id) {
		try {
			return client.countPreguntasByChecklist(id);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int countCategoriaByChecklist(Long id) {
		try {
			return client.countCategoriaByChecklist(id);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public List<Pregunta> listPreguntasByChecklist(Long id) {
		List<Pregunta> list = new ArrayList<>();
		try {
			list.addAll(client.listPreguntasByChecklist(id));
		} catch (ServiceException ex) {
			ex.toString();
		}
		return list;
	}

	public List<Categoria> listCategoriaByChecklist(Long id) {
		List<Categoria> list = new ArrayList<>();
		try {
			list.addAll(client.listCategoriaByChecklist(id));
		} catch (ServiceException ex) {
			ex.toString();
		}
		return list;
	}

}
