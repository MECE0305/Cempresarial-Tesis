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
import com.cempresarial.rest.client.service.TipoCuentaService;
import com.cempresariales.servicio.commons.admin.model.entity.Paquetes;
import com.cempresariales.servicio.commons.admin.model.entity.TipoCuenta;

@ManagedBean(name = "tipoCuentaController")
@SessionScoped
public class TipoCuentaController extends SesionController implements Serializable {

	private static final long serialVersionUID = -3841566840680152460L;

	public TipoCuentaController() {
		super();
	}

	@Inject
	private TipoCuentaService tipoCuentaDAO;
	@Inject
	private PaquetesService paquetDAO;

	// VARIABLES
	private Utilitarios util;
	private TipoCuenta tipoCuenta;
	private Paquetes paquete;

	private List<TipoCuenta> listTipoCuenta;
	private List<TipoCuenta> listTipoCuentaFilter;
	private List<Paquetes> listPaquete;

	private String selectPaquete;

	// METODOS
	@PostConstruct
	private void init() {
		clear();
	}

	public void clear() {
		try {
			util = new Utilitarios();
			paquete = new Paquetes();
			tipoCuenta = new TipoCuenta();

			selectPaquete = "";

			listTipoCuenta = tipoCuentaDAO.listar();
			listPaquete = paquetDAO.listar();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean verificarVacios() {
		try {
			boolean valor = false;

			if (tipoCuenta.getNombreTipo() != null && paquete.getIdPaquete() != null)
				valor = true;
			else
				util.mostrarMensaje("INFO.", "Existen datos vacíos, por favor, revisar!.", "W");

			return valor;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void guardar() {
		try {

			if (verificarVacios()) {
				if (tipoCuenta.getIdTipo() != null) {
					if (tipoCuenta.getIdTipo() != 0) {
						util.mostrarMensaje("INFO.",
								"Está intentando guardar un Tipo existente, presione NUEVO y reingrese los datos!.",
								"W");
					}
				} else {
					tipoCuenta.setPaquete(paquete);
					tipoCuentaDAO.insertar(tipoCuenta);

					util.mostrarMensaje("INFO.", "TIPO CUENTA guardado exitosamente!.", "I");
					clear();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			util.mostrarMensaje("ERROR.", "ERROR AL GUARDAR!.", "E");
		}
	}

	public void actualizar() {
		try {
			if (verificarVacios()) {
				if (tipoCuenta.getIdTipo() != null) {
					tipoCuentaDAO.actualizar(tipoCuenta.getIdTipo(), tipoCuenta);
					util.mostrarMensaje("INFO.", "TIPO CUENTA actualizada exitosamente!.", "I");
					clear();
				} else
					util.mostrarMensaje("INFO.", "No existe el tipo de cuenta, imposible actualizar!.", "f");
			}
		} catch (Exception e) {
			e.printStackTrace();
			util.mostrarMensaje("ERROR.", "ERROR AL ACTUALIZAR!.", "E");
		}
	}

	public void eliminar() {
		try {
			if (tipoCuenta.getIdTipo() != null) {
				tipoCuentaDAO.eliminar(tipoCuenta.getIdTipo());
				util.mostrarMensaje("INFO.", "TIPO CUENTA eliminada exitosamente!.", "I");
				clear();
			} else
				util.mostrarMensaje("INFO.", "No existe el tipo de cuenta, imposible eliminar!.", "f");

		} catch (Exception e) {
			e.printStackTrace();
			util.mostrarMensaje("ERROR.", "ERROR AL ELIMINAR!.", "E");
		}
	}

	// Setters y Getters
	public String getSelectPaquete() {
		return selectPaquete;
	}

	public void setSelectPaquete(String selectPaquete) {
		this.selectPaquete = selectPaquete;
	}

	public List<TipoCuenta> getListTipoCuentaFilter() {
		return listTipoCuentaFilter;
	}

	public void setListTipoCuentaFilter(List<TipoCuenta> listTipoCuentaFilter) {
		this.listTipoCuentaFilter = listTipoCuentaFilter;
	}

	public TipoCuenta getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(TipoCuenta tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public Paquetes getPaquete() {
		return paquete;
	}

	public void setPaquete(Paquetes paquete) {
		this.paquete = paquete;
	}

	public List<TipoCuenta> getListTipoCuenta() {
		return listTipoCuenta;
	}

	public void setListTipoCuenta(List<TipoCuenta> listTipoCuenta) {
		this.listTipoCuenta = listTipoCuenta;
	}

	public List<Paquetes> getListPaquete() {
		return listPaquete;
	}

	public void setListPaquete(List<Paquetes> listPaquete) {
		this.listPaquete = listPaquete;
	}

}
