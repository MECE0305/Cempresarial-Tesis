package com.cempresarial.entities.DTO;

import java.io.Serializable;

import com.cempresariales.servicio.commons.model.entity.Ciudad;

public class CiudadPorEvaluacionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Ciudad ciudad;
	private float totalBloques;
	private float totalPreguntas;

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

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

}
