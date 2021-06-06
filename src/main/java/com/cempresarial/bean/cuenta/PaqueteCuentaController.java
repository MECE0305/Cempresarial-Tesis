package com.cempresarial.bean.cuenta;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.cempresarial.recursos.SesionController;
import com.cempresarial.recursos.Utilitarios;
import com.cempresarial.rest.client.service.PaquetesService;
import com.cempresariales.servicio.commons.admin.model.entity.Paquetes;

@ManagedBean(name = "paqueteCuentaController")
@SessionScoped
public class PaqueteCuentaController extends SesionController implements Serializable {

	private static final long serialVersionUID = -3841566840680152460L;

	public PaqueteCuentaController() {
		super();
	}

	@Inject
	private PaquetesService paqueteDAO;

	// VARIABLES
	private Utilitarios util;
	private Paquetes paquete;

	private List<Paquetes> listPaquete;
	private List<Paquetes> listPaqueteFilter;

	// METODOS
	@PostConstruct
	private void init() {
		clear();
	}

	public void clear() {
		try {
			util = new Utilitarios();
			paquete = new Paquetes();
			listPaquete = paqueteDAO.listar();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void guardar() {
		try {
			if (paquete.getIdPaquete() != null) {
				if (paquete.getIdPaquete() != 0) {
					util.mostrarMensaje("INFO.",
							"Está intentando guardar un Paquete existente, presione NUEVO y reingrese los datos!.",
							"W");
				}
			} else {
				
				paqueteDAO.insertar(paquete);

				util.mostrarMensaje("INFO.", "PAQUETE guardado exitosamente!.", "I");
				clear();
			}

		} catch (Exception e) {
			e.printStackTrace();
			util.mostrarMensaje("ERROR.", "ERROR AL GUARDAR!.", "E");
		}
	}

	public void actualizar() {
		try {
			if (paquete.getIdPaquete() != null) {
				paqueteDAO.actualizar(paquete.getIdPaquete(), paquete);
				util.mostrarMensaje("INFO.", "PAQUETE actualizada exitosamente!.", "I");
				clear();
			} else
				util.mostrarMensaje("INFO.", "No existe el paquete, imposible actualizar!.", "f");

		} catch (Exception e) {
			e.printStackTrace();
			util.mostrarMensaje("ERROR.", "ERROR AL ACTUALIZAR!.", "E");
		}
	}

	public void eliminar() {
		try {
			if (paquete.getIdPaquete() != null) {
				paqueteDAO.eliminar(paquete.getIdPaquete());
				util.mostrarMensaje("INFO.", "PAQUETE eliminada exitosamente!.", "I");
				clear();
			} else
				util.mostrarMensaje("INFO.", "No existe el paquete de cuenta, imposible eliminar!.", "f");

		} catch (Exception e) {
			e.printStackTrace();
			util.mostrarMensaje("ERROR.", "ERROR AL ELIMINAR!.", "E");
		}
	}

	// Setters y Getters
	public List<Paquetes> getListPaqueteFilter() {
		return listPaqueteFilter;
	}

	public void setListPaqueteFilter(List<Paquetes> listPaqueteFilter) {
		this.listPaqueteFilter = listPaqueteFilter;
	}

	public Paquetes getPaquete() {
		return paquete;
	}

	public void setPaquete(Paquetes paquete) {
		this.paquete = paquete;
	}

	public List<Paquetes> getListPaquete() {
		return listPaquete;
	}

	public void setListPaquete(List<Paquetes> listPaquete) {
		this.listPaquete = listPaquete;
	}

}
