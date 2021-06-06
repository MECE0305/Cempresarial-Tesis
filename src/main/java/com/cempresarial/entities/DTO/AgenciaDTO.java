package com.cempresarial.entities.DTO;

import java.io.Serializable;

import com.cempresariales.servicio.commons.model.entity.Agencia;

public class AgenciaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Agencia agencia;
	private boolean check;

	

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

}
