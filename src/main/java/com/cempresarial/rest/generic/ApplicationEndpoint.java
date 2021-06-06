/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cempresarial.rest.generic;

import com.cempresariales.servicio.commons.model.entity.Agencia;
import com.cempresariales.servicio.commons.model.entity.Cliente;
import com.cempresariales.servicio.commons.model.entity.Empresa;
import com.cempresariales.servicio.commons.model.entity.Rol;

/**
 *
 * @author DIGETBI 05
 */
public class ApplicationEndpoint {

	public static String listar() {
		return "/listar";
	}

	public static String insertar() {	

		return "/crear";
	}

	public static String actualizar(Long id) {
		return "/editar/" + id;
	}
	
	public static String restablecer(Long id) {
		return "/restablecer/" + id;
	}

	public static String actualizarPK(Long idTabla1, Long idTabla2) {
		return "/editar/" + idTabla1 + "/" + idTabla2;
	}

	public static String buscarPorPK(Long idTabla1, Long idTabla2) {
		return "/ver/" + idTabla1 + "/" + idTabla2;
	}

	public static String buscarPorId(Long id) {
		return "/ver/" + id;
	}

	public static String buscarPorCatalogoPreguntaPK(Long idCategoria, Long idPregunta, Long idPeso) {
		return "/ver/" + idCategoria + "/" + idPregunta + "/" + idPeso;
	}

	public static String eliminar(Long id) {
		return "/eliminar/" + id;
	}

	public static String eliminarPorPK(Long idTabla1, Long idTabla2) {
		return "/eliminar/" + idTabla1 + "/" + idTabla2;
	}

	public static String eliminarCheckCatalogoPregunta(Long idChecklist, Long idCategoria, Long idPregunta,
			Long idPeso) {
		return "/eliminar/" + idChecklist + "/" + idCategoria + "/" + idPregunta + "/" + idPeso;
	}

	// listar categorias por checklist
	public static String countPreguntasByChecklist(Long id) {
		return "/countPreguntasByChecklist/" + id;
	}

	// listar preguntas por checklist
	public static String countCategoriaByChecklist(Long id) {
		return "/countCategoriaByChecklist/" + id;
	}

	// listar preguntas por checklist
	public static String findByRolIdRol(Rol id) {
		return "/findByRolIdRol";
	}

	// listar empresas por cliente
	public static String findByClienteIdCliente(Cliente id) {
		return "/findByClienteIdCliente";
	}

	// listar agencia por empresa
	public static String findByEmpresaIdEmpresa(Empresa id) {
		return "/findByEmpresaIdEmpresa";
	}

	// listar empeado por agencia
	public static String findByAgenciaIdAgencia(Agencia id) {
		return "/findByAgenciaIdAgencia";
	}

	// listar categorias por checklist
	public static String findByIdChecklist(Long id) {
		return "/findByIdChecklist/" + id;
	}

	// listar categorias por checklist
	public static String findRolHasEmpleadoByEmpleado(Long id) {
		return "/findByEmpleado/" + id;
	}

	// listar preguntas por categoria desde catalogopreguntas
	public static String findPreguntasByCategoria(Long id) {
		return "/findPreguntasByCategoria/" + id;
	}

	// listar rol empleado por rol
	public static String findByRol(Long id) {
		return "/findByRol/" + id;
	}

	// listar emcabezado evaluacion por encabezado
	public static String findByEncabezado(Long id) {
		return "/findByEncabezado/" + id;
	}

	// listar encabezado evaluacion por evaluacion
	public static String findByEvaluacion(Long id) {
		return "/findByEvaluacion/" + id;
	}

	// listar categorias por checklist
	public static String listPreguntasByChecklist(Long id) {
		return "/listPreguntasByChecklist/" + id;
	}

	// listar preguntas por checklist
	public static String listCategoriaByChecklist(Long id) {
		return "/listCategoriaByChecklist/" + id;
	}

	// listar preguntas por checklist
	public static String findByChecklistID(Long id) {
		return "/findByChecklistID/" + id;
	}

	// listar preguntas por checklist
	public static String findByCategoria(Long id) {
		return "/findByCategoria/" + id;
	}

	// listar catalogo pregunta por idcatalogopregunta
	public static String findByIdCatalogoPregunta(Long id) {
		return "/findByIdCatalogoPregunta/" + id;
	}

	// elimina un registro de evaluacion has encabezado de la bdd
	public static String eliminarEvaluacionEncabezado(Long idEvaluacion, Long idEncabezado) {
		return "/eliminarEvaluacionEncabezado/" + idEvaluacion + "/" + idEncabezado;
	}

	// elimina un registro de evaluacion has encabezado de la bdd
	public static String findZonaEstructuralByCiudad(Long idCiudad) {
		return "/findZonaEstructuralByCiudad/" + idCiudad;
	}

	// elimina un registro de evaluacion has encabezado de la bdd
	public static String findZonaEstructuralCiudadByZonaEstructura(Long idZonaEstructural) {
		return "/findZonaEstructuralCiudadByZonaEstructura/" + idZonaEstructural;
	}

	// lista de catalogopregunta por un idcategoria ordenado acendentemente
	public static String findCatalogoPreguntaByIdCategoria(Long idCategoria) {
		return "/findByIdCategoria/" + idCategoria;
	}

	// lista de checklistcatalogopregunta x checklist y categori para armar las
	// repsuestas de las categorias
	public static String findByCategoriaChecklist(Long idCategoria, Long idChecklist) {
		return "/findByCategoriaChecklist/" + idCategoria + "/" + idChecklist;
	}

	// repsuestas de las categorias
	public static String findByCheckListEvaluacion(Long idChecklist, Long idEvaluacion) {
		return "/findByCheckListEvaluacion/" + idChecklist + "/" + idEvaluacion;
	}

	// repsuestas de las categorias
	public static String findByRangoAndEmpresa(float rango, Long idEmpresa) {
		return "/findByRangoAndEmpresa/" + rango + "/" + idEmpresa;
	}

	// buscar usuarios por nombre d eusuario unico
	public static String findByNombreUsuario() {
		return "/findByNombreUsuario";
	}

	// buscar usuario validando nombre d eusuario y clave
	public static String findByNombreUsuarioAndClave() {
		return "/findByNombreUsuarioAndClave";
	}

	// buscar menus xaplicacion
	public static String findByAplicacion() {
		return "/findByAplicacion";
	}

	// buscar menus xaplicacion
	public static String findByUrl(String url) {
		return "/findByUrl/" + url;
	}

	// buscar menus oadre por aplicacion
	public static String findMenuPadreByAplicacion(Long id) {
		return "/findMenuPadreByAplicacion/" + id;
	}

	// buscar menus por menu padre
	public static String findByMenuPadre(Long id) {
		return "/findByMenuPadre/" + id;
	}

	// buscar autorizacione spor menu
	public static String findByMenu() {
		return "/findByMenu";
	}

	// repsuestas de las categorias
	public static String findOpcionesByAplicacionAndMenuPadre(Long idAplicacion, Long idMenuPadre) {
		return "/findOpcionesByAplicacionAndMenuPadre/" + idAplicacion + "/" + idMenuPadre;
	}

	// repsuestas de las categorias
	public static String obtenerPerfilesUsuario(String nombreUsuario) {
		return "/obtenerPerfilesUsuario/" + nombreUsuario;
	}

	// buscar autorizacione spor perfil
	public static String findByPerfil() {
		return "/findByPerfil";
	}

	public static String findPermisosAgeciaByPerfil(Long id) {
		return "/findByPerfil/" + id;
	}

	// buscar perfil usuario por perfil
	public static String findPerfilUsuarioByPerfil() {
		return "/findPerfilUsuarioByPerfil";
	}

	// buscar perfil usuario por perfil
	public static String findPerfilByNombre(String nombre) {
		return "/findPerfilByNombre/" + nombre;
	}

	// buscar perfil usuario por perfil
	public static String findByParams() {
		return "/findByParams";
	}

	// metodo para obtener todas las evaluaciones por segmentacion
	public static String findBySegmentacion(Long idRegion, Long idZona, Long idProvincia, Long idCiudad,
			Long idZonaEstructural) {
		return "/findBySegmentacion/" + idRegion + "/" + idZona + "/" + idProvincia + "/" + idCiudad + "/"
				+ idZonaEstructural;
	}

	// metodo para obtener todas las evaluaciones por filtro de cliente y rol
	public static String findByFiltroTabClienteAndRol(Long idCliente, Long idEmpresa, Long idSector, Long idAgencia,
			Long idEmpleado, Long idRol, Long idArea) {
		return "/findByFiltroTabClienteAndRol/" + idCliente + "/" + idEmpresa + "/" + idSector + "/" + idAgencia + "/"
				+ idEmpleado + "/" + idRol + "/" + idArea;
	}

	// buscar perfil usuario por perfil
	public static String findRolByArea(Long idArea) {
		return "/findRolByArea/" + idArea;
	}

	// buscar perfil usuario por perfil
	public static String obtenerMenusPorRolAplicativo(String rol, long idAplicativo) {
		return "/obtenerMenusPorRolAplicativo/" + rol + "/" + idAplicativo;
	}

	// buscar perfil usuario por perfil
	public static String obtenerMenusHijo(String rol, long idMenuPadre, String opcion, String opcion2) {
		return "/obtenerMenusHijo/" + rol + "/" + idMenuPadre + "/" + opcion + "/" + opcion2;
	}

	// buscar perfil usuario por perfil
	public static String findEvaByAgencias() {
		return "/findEvaByAgencias";
	}

	// buscar autorizaciones por url mennu para control d ebotones
	public static String findByUrlMenu() {
		return "/findByUrlMenu";
	}

	// buscar rangos de desemeño por rol
	public static String findByEmpresa(Long idEmpresa) {
		return "/findByEmpresa/" + idEmpresa;
	}

	// buscar rangos de desemeño por rol
	public static String findRespuestaByCategoria(Long idCategoria) {
		return "/findByCategoria/" + idCategoria;
	}

	// buscar rangos de desemeño por rol
	public static String findEmpleadoByAgencias() {
		return "/findEmpleadoByAgencias";
	}

	// buscar rangos de desemeño por rol
	public static String findRolByEmpleados() {
		return "/findRolByEmpleados";
	}

	// buscar rangos de desemeño por rol
	public static String findCheckListByRoles() {
		return "/findCheckListByRoles";
	}

	public static String findCheckListEvaluacionByCheckList(Long idChecklist) {
		return "/findCheckListEvaluacionByCheckList/" + idChecklist;
	}

	public static String findEvaluacionByCheckList(Long idChecklist) {
		return "/findEvaluacionByCheckList/" + idChecklist;
	}

	public static String findCheckListEvaluacionByEvaluacion(Long idEvaluacion) {
		return "/findCheckListEvaluacionByEvaluacion/" + idEvaluacion;
	}

	public static String findCheckListByEvaluacion(Long idEvaluacion) {
		return "/findCheckListByEvaluacion/" + idEvaluacion;
	}

	// buscar rangos de desemeño por rol
	public static String findCheckListEvaluacionByEvaluaciones() {
		return "/findCheckListEvaluacionByEvaluaciones";
	}

	// buscar rangos de desemeño por rol
	public static String findEvaluacionByIdsRol() {
		return "/findEvaluacionByIdsRol";
	}

	// buscar rangos de desemeño por rol
	public static String findEvaluacionByIdsChecklist() {
		return "/findEvaluacionByIdsChecklist";
	}

	// buscar rangos de desemeño por rol
	public static String findRespuestaByChecklistEvaluacion() {
		return "/findRespuestaByChecklistEvaluacion";
	}

	// buscar rangos de desemeño por rol
	public static String findCategoriasByChecklist() {
		return "/findCategoriasByChecklist";
	}

	// buscar rangos de desemeño por rol
	public static String findPreguntasByCategorias() {
		return "/findPreguntasByCategorias";
	}

	// buscar rangos de desemeño por rol
	public static String findCiudadesByAgencias() {
		return "/findCiudadesByAgencias";
	}

	// buscar rangos de desemeño por rol
	public static String findAreasByRoles() {
		return "/findAreasByRoles";
	}

	// buscar evaluaciones por evaluacion y categoria
	public static String findByEvaluacionCategoria(Long idEvaluacion, Long IdCategoria) {
		return "/findByEvaluacionCategoria/" + idEvaluacion + "/" + IdCategoria;
	}
	
	
	// buscar respuestas por evaluacion y categoria
		public static String findByIdEvaluacionIdChecklist(Long idEvaluacion, Long idChecklist) {
			return "/findByIdEvaluacionIdChecklist/" + idEvaluacion + "/" + idChecklist;
		}

}
