/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cempresarial.bean;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.cempresarial.rest.client.service.ClienteService;
import com.cempresariales.servicio.commons.model.entity.Cliente;
import com.cempresariales.servicio.commons.model.entity.Empresa;

/**
 *
 * @author DIGETBI 05
 */
@ManagedBean
@SessionScoped
public class ClienteListMB implements Serializable {

	private List<Cliente> listaClientes;
	private List<String> listaNombreCli;

	private Cliente clienteSeleccionado;
	private Empresa empresa;
	private String accion = "";

	@Inject
	private ClienteService clienteService;

	@PostConstruct
	public void init() {
		clienteSeleccionado = new Cliente();
		listaClientes = clienteService.listar();
		listaNombreCli = listaClientes.stream().map(x -> x.getNombreCliente()).sorted().collect(Collectors.toList());
		int m = listaNombreCli.size();
	}

	
	public void guardar() {

		if (clienteSeleccionado.getIdCliente() == null) {
			clienteService.insertar(clienteSeleccionado);

		} else {
			clienteService.actualizar(clienteSeleccionado.getIdCliente(), clienteSeleccionado);

		}
		clienteSeleccionado = new Cliente();
		listaClientes = clienteService.listar();
		this.limpiar();

	}

	public String detalleCliente(Cliente cliente) {

		clienteSeleccionado = cliente;
		String nombre = clienteSeleccionado.getNombreCliente();
		String ceo = clienteSeleccionado.getCeoCliente();
		return "/clienteForm?faces-redirect=true";
	}

	public void limpiar() {

		clienteSeleccionado.setActivoCliente(Boolean.TRUE);
		clienteSeleccionado.setCeoCliente("");
		clienteSeleccionado.setCiCliente("");
		clienteSeleccionado.setDireccionCliente("");
		clienteSeleccionado.setFotoCliente("");
		clienteSeleccionado.setEmpresaList(null);
		clienteSeleccionado.setMailCliente("");
		clienteSeleccionado.setNombreCliente("");
		clienteSeleccionado.setTelefonoCliente("");
		clienteSeleccionado.setUrlCliente("");

	}

	public Cliente iniciar() {
		clienteSeleccionado = new Cliente();
		return clienteSeleccionado;

	}

	public void eliminar() {

		Long id = clienteSeleccionado.getIdCliente();
		clienteService.eliminar(clienteSeleccionado.getIdCliente());
		clienteSeleccionado = new Cliente();
		listaClientes = clienteService.listar();
	}

	public Cliente clienteId(Long id) {
		Cliente clienteId = new Cliente();

		clienteId = clienteService.buscarPorId(id);
		return clienteId;
	}
	
	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public Cliente getClienteSeleccionado() {
		return clienteSeleccionado;
	}

	public void setClienteSeleccionado(Cliente clienteSeleccionado) {
		this.clienteSeleccionado = clienteSeleccionado;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public List<String> getListaNombreCli() {
		return listaNombreCli;
	}

	public void setListaNombreCli(List<String> listaNombreCli) {
		this.listaNombreCli = listaNombreCli;
	}

}
