/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cempresarial.bean.graficos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;

import com.cempresarial.entities.DTO.RespuestaPorCategoriaDTO;
import com.cempresarial.recursos.SesionController;
import com.cempresarial.rest.client.service.AgenciaService;
import com.cempresarial.rest.client.service.CategoriaService;
import com.cempresarial.rest.client.service.ChecklistEvaluacionService;
import com.cempresarial.rest.client.service.ChecklistService;
import com.cempresarial.rest.client.service.EmpleadoService;
import com.cempresarial.rest.client.service.EvaluacionService;
import com.cempresarial.rest.client.service.PerfilService;
import com.cempresarial.rest.client.service.PermisoAgenciaService;
import com.cempresarial.rest.client.service.PreguntaService;
import com.cempresarial.rest.client.service.RespuestaService;
import com.cempresarial.rest.client.service.RolService;
import com.cempresariales.servicio.commons.admin.model.entity.Perfil;
import com.cempresariales.servicio.commons.admin.model.entity.PermisoAgencia;
import com.cempresariales.servicio.commons.admin.model.entity.Usuario;
import com.cempresariales.servicio.commons.model.entity.Agencia;
import com.cempresariales.servicio.commons.model.entity.Area;
import com.cempresariales.servicio.commons.model.entity.Categoria;
import com.cempresariales.servicio.commons.model.entity.Checklist;
import com.cempresariales.servicio.commons.model.entity.ChecklistHasEvaluacion;
import com.cempresariales.servicio.commons.model.entity.Empleado;
import com.cempresariales.servicio.commons.model.entity.Evaluacion;
import com.cempresariales.servicio.commons.model.entity.Pregunta;
import com.cempresariales.servicio.commons.model.entity.Respuesta;
import com.cempresariales.servicio.commons.model.entity.Rol;

/**
 *
 * @author DIGETBI 05
 */
@ManagedBean
@SessionScoped
public class AspectoArea extends SesionController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AspectoArea() {
		super();
	}

	@Inject
	private ChecklistService checklistService;
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
	private RolService rolService;
	@Inject
	private CategoriaService categoriaService;
	@Inject
	private RespuestaService respuestaService;
	@Inject
	private PreguntaService preguntaService;

	private Usuario user;

	private BarChartModel barModel2;

	private List<Checklist> listCheckList;
	private List<Empleado> listEmpleado;
	private List<Agencia> listAgencia;
	private List<Evaluacion> listEvaluacion;
	private List<ChecklistHasEvaluacion> listChecklistEvaluacion;
	private List<Area> listArea;
	private List<Rol> listRol;
	private List<Categoria> listCategoria;
	private List<Pregunta> listPregunta;

	private List<RespuestaPorCategoriaDTO> listRespuestCategoria;

	private List<Long> idRoles;
	private List<Long> idAgencias;

	@PostConstruct
	public void init() {

		user = getCurrentUser();
		cargarData();

		createBarModel2();

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

			
			  listEvaluacion = evaService.findEvaByAgencias(idAgencias);
			  
			  listChecklistEvaluacion = new ArrayList<>(); List<Long> idEvaluaciones = new
			  ArrayList<>(); for (Evaluacion eva : listEvaluacion) {
			  idEvaluaciones.add(eva.getIdEvaluacion()); }
			  
			  for (ChecklistHasEvaluacion cle :
			  cleService.findCheckListEvaluacionByEvaluaciones(idEvaluaciones)) {
			  System.out.println(cle.getChecklist()); Checklist cl = checklistService
			  .buscarPorId(cle.getChecklistHasEvaluacionPK().getChecklistIdChecklist());
			  Evaluacion ev = evaService.buscarPorId(cle.getChecklistHasEvaluacionPK().
			  getEvaluacionIdEvaluacion()); ChecklistHasEvaluacion clee = new
			  ChecklistHasEvaluacion(); clee.setChecklist(cl); clee.setEvaluacion(ev);
			  listChecklistEvaluacion.add(clee);
			  
			  }
			 

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void cargarEmpleados() {
		try {
			listEmpleado = new ArrayList<>();
			listEmpleado = empleadoService.findEmpleadoByAgencias(idAgencias);
			List<Long> idEmpleados = new ArrayList<>();
			for (Empleado emp : listEmpleado) {
				idEmpleados.add(emp.getIdEmpleado());
			}
			listRol = new ArrayList<>();
			listRol = rolService.findRolByEmpleados(idEmpleados);
			listArea = new ArrayList<>();
			idRoles = new ArrayList<>();
			for (Rol rol : listRol) {
				listArea.add(rol.getAreaIdArea());
				idRoles.add(rol.getIdRol());
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void cargarChecklistByRol() {
		try {
			listCheckList = new ArrayList<>();

			listCheckList = checklistService.findCheckListByRoles(idRoles);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void cargarData() {

		try {

			cargarEvaluacionsConPermisoAgencias();
			cargarEmpleados();
			cargarChecklistByRol();
			List<Long> list1 = new ArrayList<>();
			List<Long> list2 = new ArrayList<>();

			for (Checklist cl : listCheckList) {
				list1.add(cl.getIdChecklist());
			}

			listCategoria = categoriaService.findCategoriasByChecklist(list1);
			listRespuestCategoria = new ArrayList<>();
			for (Categoria obj : listCategoria) {
				list2.add(obj.getIdCategoria());
				RespuestaPorCategoriaDTO rcdto = new RespuestaPorCategoriaDTO();
				rcdto.setCategoria(obj);

				double totalBloques = 0;
				double totalPreguntas = 0;

				List<Respuesta> list = respuestaService.findRespuestaByCategoria(obj.getIdCategoria());

				for (Respuesta resp : list) {
					totalBloques = totalBloques + resp.getValorCalculadoRespuesta();
					totalPreguntas = totalPreguntas + resp.getValorRealRespuesta();
				}

				rcdto.setTotalBloques(totalBloques);
				rcdto.setTotalPreguntas(totalPreguntas);

				listRespuestCategoria.add(rcdto);

			}

//			listPregunta = preguntaService.findPreguntasByCategorias(list2);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void createBarModel2() {

		barModel2 = new BarChartModel();
		ChartData data = new ChartData();

		BarChartDataSet barDataSet = new BarChartDataSet();
		barDataSet.setLabel("Indicadores");
		barDataSet.setBackgroundColor("rgba(255, 99, 132, 0.2)");
		barDataSet.setBorderColor("rgb(255, 99, 132)");
		barDataSet.setBorderWidth(1);

		List<Number> values = new ArrayList<>();
		List<String> labels = new ArrayList<>();
		for (RespuestaPorCategoriaDTO obj : listRespuestCategoria) {

			values.add(obj.getTotalBloques());
			labels.add(obj.getCategoria().getNombreCategoria());

		}

		barDataSet.setData(values);

		data.addChartDataSet(barDataSet);

		data.setLabels(labels);
		barModel2.setData(data);

		// Options
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearAxes = new CartesianLinearAxes();
		CartesianLinearTicks ticks = new CartesianLinearTicks();
		ticks.setBeginAtZero(true);
		linearAxes.setTicks(ticks);
		cScales.addYAxesData(linearAxes);
		options.setScales(cScales);

		Title title = new Title();
		title.setDisplay(true);
		title.setText("");
		options.setTitle(title);

		barModel2.setOptions(options);
	}

	/******* SETTER y GETTERS ********/

	public BarChartModel getBarModel2() {
		return barModel2;
	}

	public void setBarModel2(BarChartModel barModel2) {
		this.barModel2 = barModel2;
	}

}
