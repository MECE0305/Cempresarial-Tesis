package com.cempresarial.entities.DTO;

import java.io.Serializable;

import com.cempresariales.servicio.commons.model.entity.Rol;

public class RolPorEvaluacionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Rol rol;
	private float totalBloques;
	private float totalPreguntas;
	public Rol getRol() {
		return rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	public float getTotalBloques() {
		return totalBloques;
	}
	public void setTotalBloques(float totalBloques) {
		this.totalBloques = totalBloques;
	}
	public float getTotalPreguntas() {
		return totalPreguntas;
	}
	public void setTotalPreguntas(float totalPreguntas) {
		this.totalPreguntas = totalPreguntas;
	}
	

	

	
}
