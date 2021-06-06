package com.cempresarial.entities.DTO;

import java.io.Serializable;

import com.cempresariales.servicio.commons.model.entity.Area;

public class AreaPorEvaluacionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Area area;
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
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	

	

	
}
