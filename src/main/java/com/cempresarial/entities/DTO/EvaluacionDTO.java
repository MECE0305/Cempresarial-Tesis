package com.cempresarial.entities.DTO;

import java.util.ArrayList;
import java.util.List;

import com.cempresariales.servicio.commons.model.entity.Agencia;
import com.cempresariales.servicio.commons.model.entity.Empleado;
import com.cempresariales.servicio.commons.model.entity.Empresa;
import com.cempresariales.servicio.commons.model.entity.Evaluacion;
import com.cempresariales.servicio.commons.model.entity.Rol;

public class EvaluacionDTO {

	private Evaluacion evaluacion;
	private Empresa empresa;
	private Agencia agencia;
	private Empleado empleado;
	private List<Rol> listaRoles;
	
	
	public EvaluacionDTO()
	{
		
	}
	
	
	
	public List<Rol> getListaRoles() {
		return listaRoles;
	}
	public void setListaRoles(List<Rol> listaRoles) {
		this.listaRoles = listaRoles;
	}
	public Evaluacion getEvaluacion() {
		return evaluacion;
	}
	public void setEvaluacion(Evaluacion evaluacion) {
		this.evaluacion = evaluacion;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public Agencia getAgencia() {
		return agencia;
	}
	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}
	public Empleado getEmpleado() {
		return empleado;
	}
	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
	
	
	
	
	
}
