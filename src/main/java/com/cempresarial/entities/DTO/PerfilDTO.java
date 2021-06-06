package com.cempresarial.entities.DTO;

import java.io.Serializable;

import com.cempresariales.servicio.commons.admin.model.entity.Perfil;

public class PerfilDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Perfil perfil;
	private boolean check;

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

}
