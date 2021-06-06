package com.cempresarial.entities.DTO;

import java.io.Serializable;

import com.cempresariales.servicio.commons.model.entity.Agencia;

public class AgenciaPorEvaluacionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Agencia agencia;
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
	public Agencia getAgencia() {
		return agencia;
	}
	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}
	
	

	

	
}
