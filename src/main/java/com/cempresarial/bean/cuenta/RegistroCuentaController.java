package com.cempresarial.bean.cuenta;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.cempresarial.recursos.SesionController;
import com.cempresarial.recursos.Utilitarios;
import com.cempresarial.rest.client.service.CuentaService;
import com.cempresarial.rest.client.service.TipoCuentaService;
import com.cempresariales.servicio.commons.admin.model.entity.Cuenta;
import com.cempresariales.servicio.commons.admin.model.entity.TipoCuenta;

@ManagedBean(name = "registroCuentaController")
@SessionScoped
public class RegistroCuentaController extends SesionController implements Serializable {

	private static final long serialVersionUID = -3841566840680152460L;

	public RegistroCuentaController() {
		super();
	}

	@Inject
	private CuentaService cuentaDAO;
	@Inject
	private TipoCuentaService tipoCuentaDAO;

	// VARIABLES
	private Utilitarios util;
	private Cuenta cuenta;

	private List<Cuenta> listaCuenta;
	private List<Cuenta> listaCuentaFilter;
	private List<TipoCuenta> listaTipoCuenta;
	private long idTipoCuenta;

	// METODOS
	@PostConstruct
	private void init() {
		clear();
	}

	public void clear() {
		try {
			util = new Utilitarios();
			cuenta = new Cuenta();
			idTipoCuenta = new Long(0);

			listaCuenta = cuentaDAO.listar();
			listaTipoCuenta = tipoCuentaDAO.listar();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean verificarVacios() {
		try {
			boolean valor = false;

			if (cuenta.getDireccionCuenta() != null && idTipoCuenta != 0 && cuenta.getNombreCuenta() != null
					&& cuenta.getRucCuenta() != null && cuenta.getTelefonoCuenta() != null
					&& cuenta.getEmailCuenta() != null)
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
				if (cuenta.getIdCuenta() != null) {
					if (cuenta.getIdCuenta() != 0) {
						util.mostrarMensaje("INFO.",
								"Está intentando guardar una CUENTA existente, presione NUEVO y reingrese los datos!.",
								"W");
					}
				} else {
					cuenta.setTipoCuenta((TipoCuenta) tipoCuentaDAO.buscarPorId(idTipoCuenta));
					cuentaDAO.insertar(cuenta);

					util.mostrarMensaje("INFO.", "CUENTA guardado exitosamente!.", "I");
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
				if (cuenta.getIdCuenta() != null) {
					cuenta.setTipoCuenta((TipoCuenta) tipoCuentaDAO.buscarPorId(idTipoCuenta));
					cuentaDAO.actualizar(cuenta.getIdCuenta(), cuenta);
					util.mostrarMensaje("INFO.", "CUENTA actualizada exitosamente!.", "I");
					clear();
				} else
					util.mostrarMensaje("INFO.", "No existe la cuenta, imposible actualizar!.", "f");
			}
		} catch (Exception e) {
			e.printStackTrace();
			util.mostrarMensaje("ERROR.", "ERROR AL ACTUALIZAR!.", "E");
		}
	}

	public void eliminar() {
		try {
			if (cuenta.getIdCuenta() != null) {
				cuentaDAO.eliminar(cuenta.getIdCuenta());
				util.mostrarMensaje("INFO.", "CUENTA eliminada exitosamente!.", "I");
				clear();
			} else
				util.mostrarMensaje("INFO.", "No existe la cuenta, imposible eliminar!.", "f");

		} catch (Exception e) {
			e.printStackTrace();
			util.mostrarMensaje("ERROR.", "ERROR AL ELIMINAR!.", "E");
		}
	}

	// Setters y Getters
	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public List<Cuenta> getListaCuenta() {
		return listaCuenta;
	}

	public void setListaCuenta(List<Cuenta> listaCuenta) {
		this.listaCuenta = listaCuenta;
	}

	public List<Cuenta> getListaCuentaFilter() {
		return listaCuentaFilter;
	}

	public void setListaCuentaFilter(List<Cuenta> listaCuentaFilter) {
		this.listaCuentaFilter = listaCuentaFilter;
	}

	public List<TipoCuenta> getListaTipoCuenta() {
		return listaTipoCuenta;
	}

	public void setListaTipoCuenta(List<TipoCuenta> listaTipoCuenta) {
		this.listaTipoCuenta = listaTipoCuenta;
	}

	public long getIdTipoCuenta() {
		return idTipoCuenta;
	}

	public void setIdTipoCuenta(long idTipoCuenta) {
		this.idTipoCuenta = idTipoCuenta;
	}

}
