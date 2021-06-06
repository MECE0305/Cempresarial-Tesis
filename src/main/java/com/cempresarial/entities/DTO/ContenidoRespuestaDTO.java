package com.cempresarial.entities.DTO;

import com.cempresariales.servicio.commons.model.entity.CatalogoPregunta;
import com.cempresariales.servicio.commons.model.entity.Checklist;
import com.cempresariales.servicio.commons.model.entity.Respuesta;

public class ContenidoRespuestaDTO {

	private Respuesta respuesta;
	private CatalogoPregunta catalogoPregunta;
	private Checklist checklist;

	public Respuesta getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(Respuesta respuesta) {
		this.respuesta = respuesta;
	}

	public CatalogoPregunta getCatalogoPregunta() {
		return catalogoPregunta;
	}

	public void setCatalogoPregunta(CatalogoPregunta catalogoPregunta) {
		this.catalogoPregunta = catalogoPregunta;
	}

	public Checklist getChecklist() {
		return checklist;
	}

	public void setChecklist(Checklist checklist) {
		this.checklist = checklist;
	}

}
