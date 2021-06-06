/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cempresarial.bean.graficos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.cempresarial.entities.DTO.ContenidoRespuestaDTO;
import com.cempresarial.entities.DTO.RespuestaDTO;
import com.cempresarial.entities.DTO.RespuestaPorCategoriaDTO;
import com.cempresarial.entities.DTO.RolPorEvaluacionDTO;
import com.cempresarial.recursos.SesionController;
import com.cempresarial.rest.client.service.AgenciaService;
import com.cempresarial.rest.client.service.CatalogoPreguntaService;
import com.cempresarial.rest.client.service.CategoriaService;
import com.cempresarial.rest.client.service.ChecklistEvaluacionService;
import com.cempresarial.rest.client.service.ChecklistService;
import com.cempresarial.rest.client.service.EmpleadoService;
import com.cempresarial.rest.client.service.EvaluacionService;
import com.cempresarial.rest.client.service.PerfilService;
import com.cempresarial.rest.client.service.PermisoAgenciaService;
import com.cempresarial.rest.client.service.RespuestaService;
import com.cempresarial.rest.client.service.RolService;
import com.cempresariales.servicio.commons.admin.model.entity.Perfil;
import com.cempresariales.servicio.commons.admin.model.entity.PermisoAgencia;
import com.cempresariales.servicio.commons.admin.model.entity.Usuario;
import com.cempresariales.servicio.commons.model.entity.Agencia;
import com.cempresariales.servicio.commons.model.entity.Area;
import com.cempresariales.servicio.commons.model.entity.CatalogoPregunta;
import com.cempresariales.servicio.commons.model.entity.Categoria;
import com.cempresariales.servicio.commons.model.entity.Checklist;
import com.cempresariales.servicio.commons.model.entity.ChecklistHasEvaluacion;
import com.cempresariales.servicio.commons.model.entity.Empleado;
import com.cempresariales.servicio.commons.model.entity.Evaluacion;
import com.cempresariales.servicio.commons.model.entity.Respuesta;
import com.cempresariales.servicio.commons.model.entity.Rol;

/**
 *
 * @author DIGETBI 05
 */
@ManagedBean
@SessionScoped
public class Protocolos extends SesionController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Protocolos() {
		super();
	}

	@Inject
	private EmpleadoService empleadoService;
	@Inject
	private PermisoAgenciaService permisoAgenciaI;
	@Inject
	private PerfilService perfilI;
	@Inject
	private AgenciaService agenciaService;
	@Inject
	private EvaluacionService evaService;
	@Inject
	private ChecklistEvaluacionService cleService;
	@Inject
	private ChecklistService checklistService;
	@Inject
	private RolService rolService;
	@Inject
	private CategoriaService categoriaService;
	@Inject
	private RespuestaService respuestaService;
	@Inject
	private CatalogoPreguntaService cpService;

	private Usuario user;

	private List<RolPorEvaluacionDTO> listRolPorEvaluacion;
	private List<Evaluacion> listEvaluacion;
	private List<Empleado> listEmpleado;
	private List<ChecklistHasEvaluacion> listChecklistEvaluacion;
	private List<Agencia> listAgencia;
	private List<Area> listArea;
	private List<Rol> listRol;
	private List<Checklist> listCheckList;
	private List<Categoria> listCategoria;
	private List<RespuestaPorCategoriaDTO> listRespuestCategoria;

	private List<Long> idAgencias;
	private List<Long> idRoles;
	private List<Long> idEmpleados;

	private List<RespuestaDTO> listaResp;

	private Long idPerfil;
	private Long idEmpleado;
	private Long idSucursal;
	private Long idFormulario;

	private String fecha;
	private String nombreFormulario;

	@PostConstruct
	public void init() {

		user = getCurrentUser();

		cargarEvaluacionsConPermisoAgencias();

		// cargarEmpleados();
		// cargarChecklistByRol();
		// cargarRespuestas();

		Date fecha1 = new Date();
		fecha = fecha1.toLocaleString();

		// formarRespuestaExtendida();

	}

	public void cargarEvaluacionsConPermisoAgencias() {
		try {
			List<Perfil> resultadoBusqueda = perfilI.obtenerPerfilesUsuario(user.getNombreUsuario());
			List<PermisoAgencia> list = permisoAgenciaI
					.findPermisosAgeciaByPerfil(resultadoBusqueda.get(0).getIdPerfil());

			idAgencias = new ArrayList<>();
			listAgencia = new ArrayList<>();
			for (PermisoAgencia pa : list) {
				idAgencias.add(pa.getIdAgencia());
				listAgencia.add(agenciaService.buscarPorId(pa.getIdAgencia()));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void cargarEmpleadosConIdAgencia() {
		try {
			idAgencias = new ArrayList<>();
			idAgencias.add(idSucursal);
			cargarEmpleados();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void cargarEmpleados() {
		try {
			listEmpleado = new ArrayList<>();
			listEmpleado = empleadoService.findEmpleadoByAgencias(idAgencias);
			idEmpleados = new ArrayList<>();
			for (Empleado emp : listEmpleado) {
				idEmpleados.add(emp.getIdEmpleado());
			}

			cargarRoles();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void cagarRolesConIdEmpleado() {
		try {
			idEmpleados = new ArrayList<>();
			idEmpleados.add(idEmpleado);

			cargarRoles();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void cargarRoles() {
		try {
			listRol = new ArrayList<>();
			listRol = rolService.findRolByEmpleados(idEmpleados);
			listArea = new ArrayList<>();
			idRoles = new ArrayList<>();
			for (Rol rol : listRol) {
				idRoles.add(rol.getIdRol());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void cargarChecklistPorIdRol() {
		try {

			idRoles = new ArrayList<>();
			idRoles.add(idPerfil);

			cargarChecklistByRol();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void cargarChecklistByRol() {
		try {
			listCheckList = checklistService.findCheckListByRoles(idRoles);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void cargarRespuestas() {
		try {

			List<Long> list1 = new ArrayList<>();

			// for (Checklist cl : listCheckList) {
			// list1.add(cl.getIdChecklist());
			// }

			list1.add(idFormulario);
			nombreFormulario = checklistService.buscarPorId(idFormulario).getNombreChecklist();
			listCategoria = categoriaService.findCategoriasByChecklist(list1);
			formarRespuestaExtendida();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void formarRespuestaExtendida() {

		Long id = new Long(0);
		/*
		 * if (!listCheckList.isEmpty()) id = listCheckList.get(0).getIdChecklist();
		 */
		id = idFormulario;

		listaResp = new ArrayList<>();

		List<ContenidoRespuestaDTO> listContenido = new ArrayList<>();
		for (Categoria cat : listCategoria) {
			Categoria categoria = null;
			listContenido = new ArrayList<>();

			List<Respuesta> listR = new ArrayList<>();
			listR = respuestaService.findRespuestaByCategoria(cat.getIdCategoria());

			for (Respuesta re : listR) {

				System.out.println("CATALOGO PREGUNTA: " + re.getIdCatalogoPregunta());

				CatalogoPregunta cp = cpService.findByIdCatalogoPregunta(re.getIdCatalogoPregunta());

				if (cp.getCatalogoPreguntaPK().getCategoriaIdCategoria() == cat.getIdCategoria()) {

					System.out.println("CATEGORIAS IGUALES");

					categoria = new Categoria();
					categoria = cat;

					ContenidoRespuestaDTO crdto = new ContenidoRespuestaDTO();
					crdto.setCatalogoPregunta(cp);
					crdto.setChecklist(checklistService.buscarPorId(id));
					crdto.setRespuesta(re);

					listContenido.add(crdto);
				}
			}

			if (categoria != null) {
				RespuestaDTO rdto = new RespuestaDTO();
				rdto.setCategoria(categoria);
				rdto.setListaContenidoRespuesta(listContenido);
				listaResp.add(rdto);
			}

		}

	}

	/***** SETRES Y GETTERS ********/
	public List<RespuestaDTO> getListaResp() {
		return listaResp;
	}

	public void setListaResp(List<RespuestaDTO> listaResp) {
		this.listaResp = listaResp;
	}

	public List<Empleado> getListEmpleado() {
		return listEmpleado;
	}

	public void setListEmpleado(List<Empleado> listEmpleado) {
		this.listEmpleado = listEmpleado;
	}

	public List<Agencia> getListAgencia() {
		return listAgencia;
	}

	public void setListAgencia(List<Agencia> listAgencia) {
		this.listAgencia = listAgencia;
	}

	public List<Area> getListArea() {
		return listArea;
	}

	public void setListArea(List<Area> listArea) {
		this.listArea = listArea;
	}

	public List<Rol> getListRol() {
		return listRol;
	}

	public void setListRol(List<Rol> listRol) {
		this.listRol = listRol;
	}

	public List<Checklist> getListCheckList() {
		return listCheckList;
	}

	public void setListCheckList(List<Checklist> listCheckList) {
		this.listCheckList = listCheckList;
	}

	public Long getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}

	public Long getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public Long getIdSucursal() {
		return idSucursal;
	}

	public void setIdSucursal(Long idSucursal) {
		this.idSucursal = idSucursal;
	}

	public Long getIdFormulario() {
		return idFormulario;
	}

	public void setIdFormulario(Long idFormulario) {
		this.idFormulario = idFormulario;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getNombreFormulario() {
		return nombreFormulario;
	}

	public void setNombreFormulario(String nombreFormulario) {
		this.nombreFormulario = nombreFormulario;
	}

}
