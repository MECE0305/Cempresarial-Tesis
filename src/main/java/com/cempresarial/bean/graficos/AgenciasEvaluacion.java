/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cempresarial.bean.graficos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.hbar.HorizontalBarChartDataSet;
import org.primefaces.model.charts.hbar.HorizontalBarChartModel;
import org.primefaces.model.charts.optionconfig.title.Title;

import com.cempresarial.entities.DTO.AgenciaPorEvaluacionDTO;
import com.cempresarial.recursos.SesionController;
import com.cempresarial.rest.client.service.AgenciaService;
import com.cempresarial.rest.client.service.ChecklistEvaluacionService;
import com.cempresarial.rest.client.service.ChecklistService;
import com.cempresarial.rest.client.service.EmpleadoService;
import com.cempresarial.rest.client.service.EvaluacionService;
import com.cempresarial.rest.client.service.PerfilService;
import com.cempresarial.rest.client.service.PermisoAgenciaService;
import com.cempresariales.servicio.commons.admin.model.entity.Perfil;
import com.cempresariales.servicio.commons.admin.model.entity.PermisoAgencia;
import com.cempresariales.servicio.commons.admin.model.entity.Usuario;
import com.cempresariales.servicio.commons.model.entity.Agencia;
import com.cempresariales.servicio.commons.model.entity.Checklist;
import com.cempresariales.servicio.commons.model.entity.ChecklistHasEvaluacion;
import com.cempresariales.servicio.commons.model.entity.Empleado;
import com.cempresariales.servicio.commons.model.entity.Evaluacion;

/**
 *
 * @author DIGETBI 05
 */
@ManagedBean
@SessionScoped
public class AgenciasEvaluacion extends SesionController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AgenciasEvaluacion() {
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

	private HorizontalBarChartModel hbarModel;

	private Usuario user;

	private List<Evaluacion> listEvaluacion;
	private List<ChecklistHasEvaluacion> listChecklistEvaluacion;
	private List<Agencia> listAgencia;

	private List<AgenciaPorEvaluacionDTO> list;

	private List<Long> idAgencias;

	@PostConstruct
	public void init() {

		user = getCurrentUser();

		cargarEvaluacionsConPermisoAgencias();
		cargarDatosEvaluacionPorAgencia();

		createHorizontalBarModel();

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

	private void cargarDatosEvaluacionPorAgencia() {
		try {
			list = new ArrayList<>();

			System.out.println("TAMAÑO AGENCIAS PERMITIDAS" + listAgencia.size());

			for (Agencia a : listAgencia) {
				AgenciaPorEvaluacionDTO ae = new AgenciaPorEvaluacionDTO();
				ae.setAgencia(a);
				float totalBloques = 0;
				float totalPreguntas = 0;
				for (Evaluacion eva : listEvaluacion) {
					Empleado emp = empleadoService.buscarPorId(eva.getIdEmpleado());
					if (a.getIdAgencia() == emp.getAgenciaIdAgencia().getIdAgencia()) {
						totalBloques = totalBloques + Float.parseFloat(eva.getPromedioSeccion());
						totalPreguntas = totalPreguntas + Float.parseFloat(eva.getPromedioPregunta());

						System.out.println("TOTAL BLOQUES NORMAL: " + eva.getPromedioSeccion());
						System.out.println("TOTAL BLOQUES TRANSFORMADO: " + Float.parseFloat(eva.getPromedioSeccion()));
						System.out.println("TOTAL BLOQUES: " + totalBloques);
					}
				}

				ae.setTotalBloques(totalBloques);
				ae.setTotalPreguntas(totalPreguntas);

				list.add(ae);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void createHorizontalBarModel() {

		hbarModel = new HorizontalBarChartModel();
		ChartData data = new ChartData();

		HorizontalBarChartDataSet hbarDataSet = new HorizontalBarChartDataSet();
		hbarDataSet.setLabel("TOP AGENCIAS");

		System.out.println("TAMAÑO AGENCIAS POR EVALUACION" + list.size());

		List<Number> values = new ArrayList<>();
		List<String> labels = new ArrayList<>();
		List<String> bgColor = new ArrayList<>();
		List<String> borderColor = new ArrayList<>();
		list.sort(Comparator.comparing(AgenciaPorEvaluacionDTO::getTotalBloques).reversed());
		for (AgenciaPorEvaluacionDTO re : list) {

			values.add(re.getTotalBloques());

			hbarDataSet.setData(values);

			labels.add(re.getAgencia().getNombreAgencia());

			data.setLabels(labels);

			bgColor.add("rgba(255, 99, 132, 0.2)");
			hbarDataSet.setBackgroundColor(bgColor);

			borderColor.add("rgb(255, 99, 132)");
			hbarDataSet.setBorderColor(borderColor);
			hbarDataSet.setBorderWidth(1);

		}

		data.addChartDataSet(hbarDataSet);

		hbarModel.setData(data);

		// Options
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearAxes = new CartesianLinearAxes();
		CartesianLinearTicks ticks = new CartesianLinearTicks();
		ticks.setBeginAtZero(true);
		linearAxes.setTicks(ticks);
		cScales.addXAxesData(linearAxes);
		options.setScales(cScales);

		Title title = new Title();
		title.setDisplay(true);
		title.setText("Zonas");
		options.setTitle(title);

		// hbarModel.setOptions(options);
	}

	/******* SETTER y GETTERS ********/
	public HorizontalBarChartModel getHbarModel() {
		return hbarModel;
	}

	public void setHbarModel(HorizontalBarChartModel hbarModel) {
		this.hbarModel = hbarModel;
	}
}
