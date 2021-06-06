package com.cempresarial.entities.DTO;

public class CheckListDTO {

	private Long idCatalogoPregunta;
	private Long idCategoria;
	private Long idPregunta;
	private Long idPeso;
	private String nombreCategoria;
	private int orden;
	
	
	
	

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public Long getIdCatalogoPregunta() {
		return idCatalogoPregunta;
	}

	public void setIdCatalogoPregunta(Long idCatalogoPregunta) {
		this.idCatalogoPregunta = idCatalogoPregunta;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public Long getIdPregunta() {
		return idPregunta;
	}

	public void setIdPregunta(Long idPregunta) {
		this.idPregunta = idPregunta;
	}

	public Long getIdPeso() {
		return idPeso;
	}

	public void setIdPeso(Long idPeso) {
		this.idPeso = idPeso;
	}

	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}

}
