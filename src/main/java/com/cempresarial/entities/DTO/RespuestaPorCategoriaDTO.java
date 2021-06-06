package com.cempresarial.entities.DTO;

import java.io.Serializable;

import com.cempresariales.servicio.commons.model.entity.Categoria;

public class RespuestaPorCategoriaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Categoria categoria;
	private double totalBloques;
	private double totalPreguntas;

	public double getTotalBloques() {
		return totalBloques;
	}

	public void setTotalBloques(double totalBloques) {
		this.totalBloques = totalBloques;
	}

	public double getTotalPreguntas() {
		return totalPreguntas;
	}

	public void setTotalPreguntas(double totalPreguntas) {
		this.totalPreguntas = totalPreguntas;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}
