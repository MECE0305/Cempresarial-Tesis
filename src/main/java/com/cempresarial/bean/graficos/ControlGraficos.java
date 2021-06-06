/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cempresarial.bean.graficos;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.cempresarial.recursos.SesionController;
import com.cempresarial.recursos.Utilitarios;
import com.cempresariales.servicio.commons.admin.model.entity.Usuario;

/**
 *
 * @author DIGETBI 05
 */
@ManagedBean
@SessionScoped
public class ControlGraficos extends SesionController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ControlGraficos() {
		super();
	}

	private Usuario user;
	private String grafico = "";
	private Utilitarios utils;

	@PostConstruct
	public void init() {

		user = getCurrentUser();
		utils = new Utilitarios();

	}

	public void cambiarTemplates(int tipo) {
		try {

			System.out.println("ENTRA ACAMBIAR GRAFICO: " + tipo);

			if (tipo == 1) {
				grafico = "generales.xhtml";

			}
			if (tipo == 2) {
				grafico = "aspectoarea.xhtml";
			}

			if (tipo == 3) {
				grafico = "indicadoresporagencia.xhtml";
			}

			if (tipo == 4) {
				grafico = "indicadoresrol.xhtml";
			}

			if (tipo == 5) {
				grafico = "indicadoresporciudad.xhtml";
			}

			if (tipo == 6) {
				grafico = "indicadoresporarearol.xhtml";
			}

			if (tipo == 7) {
				grafico = "protocolos.xhtml";
			}

			utils.ejecutarPaneles("statusDialog", "h");

		} catch (Exception e) {
			e.printStackTrace();
		//	utils.ejecutarPaneles("statusDialog", "h");
		}
	}

	public String getGrafico() {
		return grafico;
	}

	public void setGrafico(String grafico) {
		this.grafico = grafico;
	}

}
