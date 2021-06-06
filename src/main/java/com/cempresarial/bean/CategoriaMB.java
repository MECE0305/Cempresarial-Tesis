/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cempresarial.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.cempresarial.recursos.Utilitarios;
import com.cempresarial.rest.client.endpoint.CatalogoPreguntaClient;
import com.cempresarial.rest.client.service.CatalogoPreguntaService;
import com.cempresarial.rest.client.service.CategoriaService;
import com.cempresariales.servicio.commons.model.entity.CatalogoPregunta;
import com.cempresariales.servicio.commons.model.entity.Categoria;

/**
 *
 * @author DIGETBI 05
 */
@ManagedBean
@SessionScoped
public class CategoriaMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * SERVICIOS
	 */
	@Inject
	private CategoriaService categoriaService;
	@Inject
	private CatalogoPreguntaService cpService;

	private static final Logger log = Logger.getLogger(CatalogoPreguntaClient.class.getName());

	/**
	 * VARIABLES
	 */
	private List<Categoria> listaCategoria;
	private List<Categoria> listaSubCategoria;
	private List<Categoria> listaSuperCategoria;
	private List<CatalogoPregunta> listaCatalogoPregunta;

	private Categoria categoria;

	private Long idCategoria;
	private Long idSuperCategoria;

	private Utilitarios utils;

	/**
	 * METODOS
	 */
	@PostConstruct
	public void init() {
		clear();
	}

	public void clear() {

		listaCategoria = categoriaService.listar();

		listaSubCategoria = new ArrayList<>();
		listaSuperCategoria = new ArrayList<>();
		listaCatalogoPregunta = new ArrayList<>();
		categoria = new Categoria();
		utils = new Utilitarios();
		idCategoria = new Long("0");
		idSuperCategoria = null;

	}

	public void actualizarLista() {
		listaCategoria = categoriaService.listar();
	}

	public void guardar() {

		if (categoria.getIdCategoria() == null) {
			categoria.setCreaCategoria(new Date());
			if (idCategoria != null) {
				Categoria catbd = categoriaService.buscarPorId(idCategoria);
				categoria.setCategoriaIdCategoria(catbd);
			}
			categoriaService.insertar(categoria);
			utils.mostrarMensaje("Categoria " + categoria.getNombreCategoria(), "Guardada Exitosamente", "I");
		} else {
			if (idCategoria != 0)
				categoria.setCategoriaIdCategoria(categoriaService.buscarPorId(idCategoria));
			categoriaService.actualizar(categoria.getIdCategoria(), categoria);
			utils.mostrarMensaje("Categoria" + categoria.getNombreCategoria(), "Activada Exitosamente", "I");
		}

		clear();
	}

	public void cambiaIdSupercategoria() {
		idSuperCategoria = idCategoria;
	}

	public void sideActualiza() {
		try {
			if (idSuperCategoria != 0)
				categoria.setCategoriaIdCategoria(categoriaService.buscarPorId(idSuperCategoria));
			categoriaService.actualizar(categoria.getIdCategoria(), categoria);
			actualizarLista();
			utils.mostrarMensaje("Categoria " + categoria.getNombreCategoria(), "Guardada Exitosamente", "I");
		} catch (Exception e) {
			e.printStackTrace();
			utils.mostrarMensaje("Categoria ", "Inconveniente en actualizar registro", "E");
		}
	}

	public void switchActiva(Categoria entidad) {

		categoriaService.actualizar(entidad.getIdCategoria(), entidad);
		if (entidad.getActivoCategoria())
			utils.mostrarMensaje("Rol " + entidad.getNombreCategoria(), "Activada Exitosamente", "I");
		else
			utils.mostrarMensaje("Rol " + entidad.getNombreCategoria(), "Desactivada Exitosamente", "w");
		actualizarLista();
	}

	public void eliminar() {
		try {
			categoriaService.eliminar(categoria.getIdCategoria());
			categoria = new Categoria();
			this.actualizarLista();
			utils.mostrarMensaje("Categoria ", "Eliminada Exitosamente !", "I");
		} catch (Exception e) {
			utils.mostrarMensaje("Eliminación", "Imposible Eliminar, Categoria en USO", "w");
		}
	}

	public void pasarDatoseditar(Categoria entidad) {
		categoria = entidad;
		listaCategoria = categoriaService.listar();
		listaSuperCategoria = new ArrayList<>();
		listaCatalogoPregunta = new ArrayList<>();
		listaSubCategoria = new ArrayList<>();
		idCategoria = new Long("0");

		if (categoria.getCategoriaIdCategoria() != null)
			idCategoria = categoria.getCategoriaIdCategoria().getIdCategoria();

		for (CatalogoPregunta obj : cpService.listar())
			if (obj.getCatalogoPreguntaPK().getCategoriaIdCategoria() == categoria.getIdCategoria())
				listaCatalogoPregunta.add(obj);

		for (Categoria cat : listaCategoria)
			if (cat.getCategoriaIdCategoria() != null) {
				if (cat.getCategoriaIdCategoria().getIdCategoria() == categoria.getIdCategoria())
					listaSubCategoria.add(cat);
			} else {
				listaSuperCategoria.add(cat);
			}

		System.out.println("DATOS EXTRAS_ " + categoria.getCategoriaIdCategoria());
		System.out.println("DATOS EXTRAS_ " + listaCatalogoPregunta.size());
		System.out.println("DATOS EXTRAS_ " + listaSubCategoria.size());
	}

	/**
	 * SETTEGRS Y GETTERS
	 */

	public List<Categoria> getListaCategoria() {
		return listaCategoria;
	}

	public List<Categoria> getListaSuperCategoria() {
		return listaSuperCategoria;
	}

	public void setListaSuperCategoria(List<Categoria> listaSuperCategoria) {
		this.listaSuperCategoria = listaSuperCategoria;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public void setListaCategoria(List<Categoria> listaCategoria) {
		this.listaCategoria = listaCategoria;
	}

	public List<Categoria> getListaSubCategoria() {
		return listaSubCategoria;
	}

	public void setListaSubCategoria(List<Categoria> listaSubCategoria) {
		this.listaSubCategoria = listaSubCategoria;
	}

	public List<CatalogoPregunta> getListaCatalogoPregunta() {
		return listaCatalogoPregunta;
	}

	public void setListaCatalogoPregunta(List<CatalogoPregunta> listaCatalogoPregunta) {
		this.listaCatalogoPregunta = listaCatalogoPregunta;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}
