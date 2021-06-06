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
import org.primefaces.model.charts.axes.cartesian.CartesianScaleLabel;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.hbar.HorizontalBarChartDataSet;
import org.primefaces.model.charts.hbar.HorizontalBarChartModel;
import org.primefaces.model.charts.optionconfig.title.Title;

import com.cempresarial.entities.DTO.AgenciaPorEvaluacionDTO;
import com.cempresarial.recursos.SesionController;
import com.cempresarial.recursos.Utilitarios;
import com.cempresarial.rest.client.service.AgenciaService;
import com.cempresarial.rest.client.service.ClienteService;
import com.cempresarial.rest.client.service.EmpleadoService;
import com.cempresarial.rest.client.service.EmpresaService;
import com.cempresarial.rest.client.service.EvaluacionService;
import com.cempresarial.rest.client.service.PerfilService;
import com.cempresarial.rest.client.service.PermisoAgenciaService;
import com.cempresarial.rest.client.service.SectorService;
import com.cempresariales.servicio.commons.admin.model.entity.Perfil;
import com.cempresariales.servicio.commons.admin.model.entity.PermisoAgencia;
import com.cempresariales.servicio.commons.admin.model.entity.Usuario;
import com.cempresariales.servicio.commons.model.entity.Agencia;
import com.cempresariales.servicio.commons.model.entity.Cliente;
import com.cempresariales.servicio.commons.model.entity.Empleado;
import com.cempresariales.servicio.commons.model.entity.Empresa;
import com.cempresariales.servicio.commons.model.entity.Evaluacion;
import com.cempresariales.servicio.commons.model.entity.Sector;

/**
 *
 * @author DIGETBI 05
 */
@ManagedBean
@SessionScoped
public class Generales extends SesionController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Generales() {
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
	private ClienteService clienteService;
	@Inject
	private EmpresaService empresaService;
	@Inject
	private SectorService sectorService;

	private Usuario user;

	private List<AgenciaPorEvaluacionDTO> list;
	private List<Agencia> listAgencia;
	private List<Evaluacion> listEvaluacion;

	private List<Cliente> listCliente;
	private List<Empresa> listEmpresa;
	private List<Sector> listSector;

	private Long idCliente;
	private Long idEmpresa;
	private Long idSector;
	private Long idPerfil;

	private List<Long> idAgencias;

	private HorizontalBarChartModel hbarModel;
	private BarChartModel barModel1;

	@PostConstruct
	public void init() {

		user = getCurrentUser();
		cargarData();
		cargarCliente();
		createHorizontalBarModel();
		createBarModel1();

	}

	public void cargarCliente() {
		try {
			//listCliente = new ArrayList<>();
			//listSector = new ArrayList<>();

			// listCliente = clienteService.listar();
			// listSector = sectorService.listar();

			// idCliente = new Long(0);
			// idSector = new Long(0);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void buscarEmpresaPorCliente() {
		try {
			listEmpresa = empresaService.findByClienteIdCliente(clienteService.buscarPorId(idCliente));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void llenarAgencias() {
		try {
			list = new ArrayList<>();
			listEvaluacion = evaService.findEvaByAgencias(idAgencias);

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

	public void graficarPorEmpresa() {
		try {

			idAgencias = new ArrayList<>();
			listAgencia = new ArrayList<>();

			listAgencia = agenciaService.findByEmpresaIdEmpresa(empresaService.buscarPorId(idEmpresa));
			for (Agencia ag : listAgencia) {
				idAgencias.add(ag.getIdAgencia());

			}

			llenarAgencias();

			createHorizontalBarModel();
			createBarModel1();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void cargarData() {

		try {

			List<Perfil> resultadoBusqueda = perfilI.obtenerPerfilesUsuario(user.getNombreUsuario());

			idPerfil = resultadoBusqueda.get(0).getIdPerfil();

			idAgencias = new ArrayList<>();
			listAgencia = new ArrayList<>();

			listCliente = new ArrayList<>();
			listEmpresa = new ArrayList<>();

			Utilitarios util = new Utilitarios();
			if (idPerfil != 1) {

				List<PermisoAgencia> listPE = permisoAgenciaI.findPermisosAgeciaByPerfil(idPerfil);
				util.mostrarMensaje("PERFIL DE INRGESO", "Bienvenido USUARIO PERFIL : " + idPerfil, "W");

				System.out.println("Bienvenido USUARIO PERFIL : " + idPerfil);

				for (PermisoAgencia pa : listPE) {
					idAgencias.add(pa.getIdAgencia());
					listAgencia.add(agenciaService.buscarPorId(pa.getIdAgencia()));
				}

				Empresa emp = empresaService.buscarPorId(user.getIdEmpresa());
				listCliente.add(emp.getClienteIdCliente());
				listEmpresa.add(emp);

				System.out.println("CLIENTES : " + emp.getClienteIdCliente().getIdCliente());
				System.out.println("EMPRESA : " + emp.getIdEmpresa());

				idEmpresa = emp.getIdEmpresa();
				idCliente = emp.getClienteIdCliente().getIdCliente();

			} else {
				util.mostrarMensaje("PERFIL DE INRGESO", "Bienvenido ADMIN", "W");
				System.out.println("Bienvenido USUARIO PERFIL ADMIN : " + idPerfil);
				listAgencia = agenciaService.listar();
				for (Agencia ag : listAgencia) {
					idAgencias.add(ag.getIdAgencia());
				}
				listEmpresa = new ArrayList<>();
				idEmpresa = new Long(0);

				listCliente = clienteService.listar();

				idCliente = new Long(0);
			}

			llenarAgencias();
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

		for (AgenciaPorEvaluacionDTO re : list) {

			values.add(re.getTotalBloques());

			barDataSet.setData(values);

			labels.add(re.getAgencia().getNombreAgencia());

			data.setLabels(labels);
		}

		data.addChartDataSet(barDataSet);

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

	/***** SETRES Y GETTERS ********/

	public HorizontalBarChartModel getHbarModel() {
		return hbarModel;
	}

	public List<AgenciaPorEvaluacionDTO> getList() {
		return list;
	}

	public void setList(List<AgenciaPorEvaluacionDTO> list) {
		this.list = list;
	}

	public void setHbarModel(HorizontalBarChartModel hbarModel) {
		this.hbarModel = hbarModel;
	}

	public BarChartModel getBarModel1() {
		return barModel1;
	}

	public void setBarModel1(BarChartModel barModel1) {
		this.barModel1 = barModel1;
	}

	public List<Cliente> getListCliente() {
		return listCliente;
	}

	public void setListCliente(List<Cliente> listCliente) {
		this.listCliente = listCliente;
	}

	public List<Sector> getListSector() {
		return listSector;
	}

	public void setListSector(List<Sector> listSector) {
		this.listSector = listSector;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public Long getIdSector() {
		return idSector;
	}

	public void setIdSector(Long idSector) {
		this.idSector = idSector;
	}

	public List<Empresa> getListEmpresa() {
		return listEmpresa;
	}

	public void setListEmpresa(List<Empresa> listEmpresa) {
		this.listEmpresa = listEmpresa;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

}
