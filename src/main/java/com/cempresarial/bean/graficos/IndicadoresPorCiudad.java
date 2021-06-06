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

import com.cempresarial.entities.DTO.AgenciaPorEvaluacionDTO;
import com.cempresarial.entities.DTO.CiudadPorEvaluacionDTO;
import com.cempresarial.recursos.SesionController;
import com.cempresarial.rest.client.service.AgenciaService;
import com.cempresarial.rest.client.service.ChecklistEvaluacionService;
import com.cempresarial.rest.client.service.ChecklistService;
import com.cempresarial.rest.client.service.CiudadService;
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
import com.cempresariales.servicio.commons.model.entity.Ciudad;
import com.cempresariales.servicio.commons.model.entity.Empleado;
import com.cempresariales.servicio.commons.model.entity.Evaluacion;

/**
 *
 * @author DIGETBI 05
 */
@ManagedBean
@SessionScoped
public class IndicadoresPorCiudad extends SesionController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IndicadoresPorCiudad() {
		super();
	}

	@Inject
	private CiudadService ciudadService;

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

	private BarChartModel barModel2;

	private Usuario user;

	private List<Evaluacion> listEvaluacion;
	private List<ChecklistHasEvaluacion> listChecklistEvaluacion;
	private List<Agencia> listAgencia;

	private List<AgenciaPorEvaluacionDTO> list;
	private List<CiudadPorEvaluacionDTO> listCiudadEvaluacion;

	private List<Long> idAgencias;

	@PostConstruct
	public void init() {

		user = getCurrentUser();

		cargarEvaluacionsConPermisoAgencias();
		cargarDatosEvaluacionPorAgencia();
		componerCiudadEvaluacion();

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

	public void componerCiudadEvaluacion() {
		try {
			List<Ciudad> listCiudad = new ArrayList<>();
			System.out.println("AGENCIAS PERMITIDAS ?: " + idAgencias);
			listCiudad = ciudadService.findCiudadesByAgencias(idAgencias);
			listCiudadEvaluacion = new ArrayList<>();

			System.out.println("LISTA CIUDADES: " + listCiudad.size());
			for (Ciudad c : listCiudad) {
				System.out.println("CIUDADES A MOSTRAR LISTADO PERMISTIDO: " + c.getNombreCiudad());

				CiudadPorEvaluacionDTO ciudadDTO = new CiudadPorEvaluacionDTO();
				ciudadDTO.setCiudad(c);
				float totalBloques = 0;
				float totalPreguntas = 0;
				for (AgenciaPorEvaluacionDTO dto : list) {

					System.out.println("CIUDADES A MOSTRAR: " + dto.getAgencia().getCiudadIdCiudad().getNombreCiudad());
					if (c.getIdCiudad() == dto.getAgencia().getCiudadIdCiudad().getIdCiudad()) {
						totalBloques = totalBloques + dto.getTotalBloques();
						totalPreguntas = totalPreguntas + dto.getTotalPreguntas();
					}
				}

				ciudadDTO.setTotalBloques(totalBloques);
				ciudadDTO.setTotalPreguntas(totalPreguntas);

				listCiudadEvaluacion.add(ciudadDTO);
			}

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
		for (CiudadPorEvaluacionDTO obj : listCiudadEvaluacion) {

			values.add(obj.getTotalBloques());
			labels.add(obj.getCiudad().getNombreCiudad());

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
