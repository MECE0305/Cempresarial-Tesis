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
import org.primefaces.model.charts.axes.cartesian.CartesianScaleLabel;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;

import com.cempresarial.entities.DTO.AreaPorEvaluacionDTO;
import com.cempresarial.entities.DTO.RolPorEvaluacionDTO;
import com.cempresarial.recursos.SesionController;
import com.cempresarial.rest.client.service.AgenciaService;
import com.cempresarial.rest.client.service.AreaService;
import com.cempresarial.rest.client.service.ChecklistEvaluacionService;
import com.cempresarial.rest.client.service.ChecklistService;
import com.cempresarial.rest.client.service.EmpleadoService;
import com.cempresarial.rest.client.service.EvaluacionService;
import com.cempresarial.rest.client.service.PerfilService;
import com.cempresarial.rest.client.service.PermisoAgenciaService;
import com.cempresarial.rest.client.service.RolService;
import com.cempresariales.servicio.commons.admin.model.entity.Perfil;
import com.cempresariales.servicio.commons.admin.model.entity.PermisoAgencia;
import com.cempresariales.servicio.commons.admin.model.entity.Usuario;
import com.cempresariales.servicio.commons.model.entity.Agencia;
import com.cempresariales.servicio.commons.model.entity.Area;
import com.cempresariales.servicio.commons.model.entity.Checklist;
import com.cempresariales.servicio.commons.model.entity.ChecklistHasEvaluacion;
import com.cempresariales.servicio.commons.model.entity.Empleado;
import com.cempresariales.servicio.commons.model.entity.Evaluacion;
import com.cempresariales.servicio.commons.model.entity.Rol;

/**
 *
 * @author DIGETBI 05
 */
@ManagedBean
@SessionScoped
public class IndicadoresPorAreaRol extends SesionController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IndicadoresPorAreaRol() {
		super();
	}

	@Inject
	private EmpleadoService empleadoService;
	@Inject
	private EvaluacionService evaService;
	@Inject
	private PermisoAgenciaService permisoAgenciaI;
	@Inject
	private PerfilService perfilI;
	@Inject
	private AgenciaService agenciaService;
	@Inject
	private ChecklistEvaluacionService cleService;
	@Inject
	private ChecklistService checklistService;
	@Inject
	private RolService rolService;
	@Inject
	private AreaService areaService;

	private BarChartModel barModel1;

	private Usuario user;

	private List<RolPorEvaluacionDTO> listRolPorEvaluacion;
	private List<AreaPorEvaluacionDTO> listAreaPorEvaluacion;
	private List<Evaluacion> listEvaluacion;
	private List<Empleado> listEmpleado;
	private List<ChecklistHasEvaluacion> listChecklistEvaluacion;
	private List<Agencia> listAgencia;
	private List<Area> listArea;
	private List<Rol> listRol;

	private List<Long> idAgencias;
	private List<Long> idRoles;

	@PostConstruct
	public void init() {

		user = getCurrentUser();

		cargarEvaluacionsConPermisoAgencias();
		cargarEmpleados();

		cargarEvaluacionesXRol();
		cargarAreas();

		createBarModel1();

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

			listChecklistEvaluacion = new ArrayList<>();
			List<Long> idEvaluaciones = new ArrayList<>();
			for (Evaluacion eva : listEvaluacion) {
				idEvaluaciones.add(eva.getIdEvaluacion());
			}

			for (ChecklistHasEvaluacion cle : cleService.findCheckListEvaluacionByEvaluaciones(idEvaluaciones)) {
				System.out.println(cle.getChecklist());
				Checklist cl = checklistService
						.buscarPorId(cle.getChecklistHasEvaluacionPK().getChecklistIdChecklist());
				Evaluacion ev = evaService.buscarPorId(cle.getChecklistHasEvaluacionPK().getEvaluacionIdEvaluacion());
				ChecklistHasEvaluacion clee = new ChecklistHasEvaluacion();
				clee.setChecklist(cl);
				clee.setEvaluacion(ev);
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

			idRoles = new ArrayList<>();
			for (Rol rol : listRol) {
				idRoles.add(rol.getIdRol());
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void cargarEvaluacionesXRol() {
		try {

			listRolPorEvaluacion = new ArrayList<>();
			for (Rol rol : listRol) {
				RolPorEvaluacionDTO re = new RolPorEvaluacionDTO();
				float totalBloques = 0;
				float totalPreguntas = 0;
				List<Long> idRol = new ArrayList<>();
				idRol.add(rol.getIdRol());

				List<Evaluacion> list = cleService.findEvaluacionByIdsRol(idRol);

				for (Evaluacion eva : list) {
					totalBloques = totalBloques + Float.parseFloat(eva.getPromedioSeccion());
					totalPreguntas = totalPreguntas + Float.parseFloat(eva.getPromedioPregunta());

					System.out.println("TOTAL BLOQUES NORMAL: " + eva.getPromedioSeccion());
					System.out.println("TOTAL BLOQUES TRANSFORMADO: " + Float.parseFloat(eva.getPromedioSeccion()));
					System.out.println("TOTAL BLOQUES: " + totalBloques);
				}

				re.setRol(rol);
				re.setTotalBloques(totalBloques);
				re.setTotalPreguntas(totalPreguntas);

				listRolPorEvaluacion.add(re);

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void cargarAreas() {
		try {
			listArea = new ArrayList<>();
			listArea = areaService.findAreasByRoles(idRoles);

			System.out.println("AREAS LISTADO: " + listArea.size());

			listAreaPorEvaluacion = new ArrayList<>();

			for (Area a : listArea) {

				AreaPorEvaluacionDTO areaDTO = new AreaPorEvaluacionDTO();
				areaDTO.setArea(a);
				float totalBloques = 0;
				float totalPreguntas = 0;
				for (RolPorEvaluacionDTO dto : listRolPorEvaluacion) {

					if (a.getIdArea() == dto.getRol().getAreaIdArea().getIdArea()) {
						totalBloques = totalBloques + dto.getTotalBloques();
						totalPreguntas = totalPreguntas + dto.getTotalPreguntas();
					}
				}

				areaDTO.setTotalBloques(totalBloques);
				areaDTO.setTotalPreguntas(totalPreguntas);

				listAreaPorEvaluacion.add(areaDTO);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void createBarModel1() {

		barModel1 = new BarChartModel();
		ChartData data = new ChartData();

		BarChartDataSet barDataSet = new BarChartDataSet();
		barDataSet.setLabel("Indicadores");
		barDataSet.setBackgroundColor("rgba(255, 99, 132, 0.2)");
		barDataSet.setBorderColor("rgb(255, 99, 132)");
		barDataSet.setBorderWidth(1);

		List<Number> values = new ArrayList<>();
		List<String> labels = new ArrayList<>();

		for (AreaPorEvaluacionDTO re : listAreaPorEvaluacion) {

			values.add(re.getTotalBloques());

			barDataSet.setData(values);

			labels.add(re.getArea().getNombreArea());

			data.setLabels(labels);
		}

		/*
		 * BarChartDataSet barDataSet2 = new BarChartDataSet();
		 * barDataSet2.setLabel("My Second Dataset");
		 * barDataSet2.setBackgroundColor("rgba(255, 159, 64, 0.2)");
		 * barDataSet2.setBorderColor("rgb(255, 159, 64)");
		 * barDataSet2.setBorderWidth(1); List<Number> values2 = new ArrayList<>();
		 * values2.add(85); values2.add(69); values2.add(85); values2.add(69);
		 * values2.add(85); values2.add(69); values2.add(85); values2.add(69);
		 * 
		 * barDataSet2.setData(values2);
		 */

		data.addChartDataSet(barDataSet);
		// data.addChartDataSet(barDataSet2);

		barModel1.setData(data);

		// Options
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearAxes = new CartesianLinearAxes();
		CartesianLinearTicks ticks = new CartesianLinearTicks();
		// ticks.setBeginAtZero(true);

		CartesianScaleLabel l = new CartesianScaleLabel();
		l.setFontSize(3);
		l.setFontColor("rgb(255, 99, 132)");

		linearAxes.setScaleLabel(l);

		linearAxes.setTicks(ticks);
		cScales.addYAxesData(linearAxes);
		options.setScales(cScales);

		Title title = new Title();
		title.setDisplay(true);
		title.setText("");
		options.setTitle(title);

		barModel1.setOptions(options);
	}

	/******* SETTER y GETTERS ********/
	public BarChartModel getBarModel1() {
		return barModel1;
	}

	public void setBarModel1(BarChartModel barModel1) {
		this.barModel1 = barModel1;
	}
}
