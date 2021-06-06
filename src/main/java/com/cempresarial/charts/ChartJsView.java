/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cempresarial.charts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.ItemSelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScaleLabel;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.axes.radial.RadialScales;
import org.primefaces.model.charts.axes.radial.linear.RadialLinearAngleLines;
import org.primefaces.model.charts.axes.radial.linear.RadialLinearPointLabels;
import org.primefaces.model.charts.axes.radial.linear.RadialLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;
import org.primefaces.model.charts.hbar.HorizontalBarChartDataSet;
import org.primefaces.model.charts.hbar.HorizontalBarChartModel;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.elements.Elements;
import org.primefaces.model.charts.optionconfig.elements.ElementsLine;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.radar.RadarChartDataSet;
import org.primefaces.model.charts.radar.RadarChartModel;
import org.primefaces.model.charts.radar.RadarChartOptions;

import com.cempresarial.entities.DTO.AgenciaPorEvaluacionDTO;
import com.cempresarial.entities.DTO.ContenidoRespuestaDTO;
import com.cempresarial.entities.DTO.RespuestaDTO;
import com.cempresarial.entities.DTO.RespuestaPorCategoriaDTO;
import com.cempresarial.entities.DTO.RolPorEvaluacionDTO;
import com.cempresarial.recursos.SesionController;
import com.cempresarial.rest.client.service.AgenciaService;
import com.cempresarial.rest.client.service.AreaService;
import com.cempresarial.rest.client.service.CatalogoPreguntaService;
import com.cempresarial.rest.client.service.CategoriaService;
import com.cempresarial.rest.client.service.ChecklistCatalogoPreguntaService;
import com.cempresarial.rest.client.service.ChecklistEvaluacionService;
import com.cempresarial.rest.client.service.ChecklistService;
import com.cempresarial.rest.client.service.CiudadService;
import com.cempresarial.rest.client.service.ClienteService;
import com.cempresarial.rest.client.service.EmpleadoService;
import com.cempresarial.rest.client.service.EmpresaService;
import com.cempresarial.rest.client.service.EvaluacionService;
import com.cempresarial.rest.client.service.PerfilService;
import com.cempresarial.rest.client.service.PermisoAgenciaService;
import com.cempresarial.rest.client.service.PreguntaService;
import com.cempresarial.rest.client.service.ProvinciaService;
import com.cempresarial.rest.client.service.RangoService;
import com.cempresarial.rest.client.service.RegionService;
import com.cempresarial.rest.client.service.RespuestaService;
import com.cempresarial.rest.client.service.RolService;
import com.cempresarial.rest.client.service.SectorService;
import com.cempresarial.rest.client.service.ZonaEstructuralService;
import com.cempresarial.rest.client.service.ZonaService;
import com.cempresariales.servicio.commons.admin.model.entity.Perfil;
import com.cempresariales.servicio.commons.admin.model.entity.PermisoAgencia;
import com.cempresariales.servicio.commons.admin.model.entity.Usuario;
import com.cempresariales.servicio.commons.model.entity.Agencia;
import com.cempresariales.servicio.commons.model.entity.Area;
import com.cempresariales.servicio.commons.model.entity.CatalogoPregunta;
import com.cempresariales.servicio.commons.model.entity.Categoria;
import com.cempresariales.servicio.commons.model.entity.Checklist;
import com.cempresariales.servicio.commons.model.entity.ChecklistHasEvaluacion;
import com.cempresariales.servicio.commons.model.entity.Ciudad;
import com.cempresariales.servicio.commons.model.entity.Cliente;
import com.cempresariales.servicio.commons.model.entity.Empleado;
import com.cempresariales.servicio.commons.model.entity.Empresa;
import com.cempresariales.servicio.commons.model.entity.Evaluacion;
import com.cempresariales.servicio.commons.model.entity.Pregunta;
import com.cempresariales.servicio.commons.model.entity.Provincia;
import com.cempresariales.servicio.commons.model.entity.RangoDesempenio;
import com.cempresariales.servicio.commons.model.entity.Region;
import com.cempresariales.servicio.commons.model.entity.Respuesta;
import com.cempresariales.servicio.commons.model.entity.Rol;
import com.cempresariales.servicio.commons.model.entity.Sector;
import com.cempresariales.servicio.commons.model.entity.Zona;
import com.cempresariales.servicio.commons.model.entity.ZonaEstructural;

/**
 *
 * @author DIGETBI 05
 */
@ManagedBean
@SessionScoped
public class ChartJsView extends SesionController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ChartJsView() {
		super();
	}

	@Inject
	private RangoService rangoService;
	@Inject
	private AgenciaService agenciaService;
	@Inject
	private ClienteService clienteI;
	@Inject
	private SectorService sectorI;
	@Inject
	private EmpresaService empresaService;
	@Inject
	private AreaService areaService;
	@Inject
	private RolService rolService;
	@Inject
	private EmpleadoService empleadoService;

	@Inject
	private CategoriaService categoriaService;
	@Inject
	private PreguntaService preguntaService;
	@Inject
	private RespuestaService respuestaService;
	@Inject
	private ChecklistService checklistService;
	@Inject
	private ChecklistCatalogoPreguntaService clcpService;
	@Inject
	private ChecklistEvaluacionService cleService;
	@Inject
	private CatalogoPreguntaService cpService;
	@Inject
	private EvaluacionService evaService;
	@Inject
	private PermisoAgenciaService permisoAgenciaI;
	@Inject
	private PerfilService perfilI;

	/*** SEGMENTACION ****/
	@Inject
	private RegionService regionI;
	@Inject
	private ZonaService zonaI;
	@Inject
	private ProvinciaService provinciaI;
	@Inject
	private CiudadService ciudadI;
	@Inject
	private ZonaEstructuralService zonaEI;

	private Usuario user;
	private List<RangoDesempenio> listRango;
	private List<Cliente> listCliente;
	private List<Sector> listSector;
	private List<Empresa> listEmpresa;
	private List<Agencia> listAgencia;
	private List<Agencia> listAgenciaSelect;
	private List<Area> listArea;
	private List<Rol> listRol;
	private List<Categoria> listCategoria;
	private List<Pregunta> listPregunta;
	private List<Respuesta> listRespuesta;
	private List<Checklist> listCheckList;
	private List<Evaluacion> listEvaluacion;
	private List<Empleado> listEmpleado;

	private List<ChecklistHasEvaluacion> listChecklistEvaluacion;

	private List<Long> idAgencias;
	private List<Long> idRoles;

	private Long idCliente;
	private Long idSector;
	private Long idEmpresa;
	private Long idAgencia;
	private Long idArea;
	private Long idRol;

	private Date filtroFecha;

	private DonutChartModel donutModel;
	private LineChartModel lineModel;
	private BarChartModel barModel2;
	private BarChartModel barModel1;

	private HorizontalBarChartModel hbarModel;

	private RadarChartModel radarModel;

	private RadarChartModel radarModel2;

	private String mes = "enero";
	private String[] selectedCities2;
	private List<String> cities;
	private List<String> selectedOptions;

	/*** SEGMENTACION ****/
	private List<Region> listaRegion;
	private List<Zona> listaZona;
	private List<Provincia> listaProvincia;
	private List<Ciudad> listaCiudad;
	private List<ZonaEstructural> listaZonaE;

	private Long idRegion;
	private Long idZona;
	private Long idProvincia;
	private Long idCiudad;
	private Long idZonaE;

	@PostConstruct
	public void init() {
		idCliente = new Long("0");
		idSector = new Long("0");
		idEmpresa = new Long("0");
		idAgencia = new Long("0");
		idArea = new Long("0");
		idRol = new Long("0");

		
		user = getCurrentUser();

		cargarCliente();
		cargarEvaluacionsConPermisoAgencias();
		cargarEmpleados();
		cargarChecklistByRol();

		/*
		 * listArea = areaService.listar(); listRol = rolService.listar();
		 * 
		 * listCategoria = categoriaService.listar(); listPregunta =
		 * preguntaService.listar(); listRespuesta = respuestaService.listar();
		 * listCheckList = checklistService.listar(); listEmpleado =
		 * empleadoService.listar();
		 */

		System.out.println("USUARIO: " + user.getIdEmpresa());

		filtroFecha = new Date();

		cargarSegmentacion();

		cargarRespuestas();

		cities = new ArrayList<String>();
		/*
		 * try { ClientesClient client = new ClientesClient("http://localhost:8001",
		 * ""); //Promedio cliente= client.getRipplePrice(); Cliente cliente=
		 * client.listaClientes().get(0);
		 * 
		 * cities.add(cliente.getNombre()+""); } catch (ServiceException ex) {
		 * cities.add(ex.toString());
		 * Logger.getLogger(ChartJsView.class.getName()).log(Level.SEVERE, null, ex); }
		 */

		cities.add("AG. ALBORADA - GUAYAQUIL");
		cities.add("AG. CEIBOS - GUAYAQUIL");
		cities.add("AG. MALL DEL SOL - GUAYAQUIL");
		cities.add("AG. 9 DE OCTUBRE - GUAYAQUIL");
		cities.add("AG. PDBCO EXPRESS LOS CHILLOS");
		cities.add("AG. PLAZA BATAN");
		cities.add("AG. BAHIA - GUAYAQUIL");
		cities.add("AG. LA GARZOTA - GUAYAQUIL");
		cities.add("AG. MERCADO CENTRAL - GUAYAQUIL");
		cities.add("AG. ORELLANA - GUAYAQUIL");
		cities.add("AG. PDBCO EXPRESS MEGA MAXI - GUAYAQUIL");
		cities.add("AG. SAN MARINO - GUAYAQUIL");
		cities.add("AG. URDESA - GUAYAQUIL");

		createRadarModel();
		createRadarModel2();
		createBarModel1();
		createBarModel2();
		createDonutModel();
		createLineModel();
		createHorizontalBarModel();

		formarRespuestaExtendida();

	}

	public void cargarCliente() {
		try {

			Empresa empresa = empresaService.buscarPorId(user.getIdEmpresa());
			listEmpresa = new ArrayList<>();
			listEmpresa.add(empresa);
			idEmpresa = user.getIdEmpresa();

			listRango = rangoService.findByEmpresa(user.getIdEmpresa());

			listCliente = new ArrayList<>();
			listCliente.add(clienteI.buscarPorId(empresa.getClienteIdCliente().getIdCliente()));
			idCliente = empresa.getClienteIdCliente().getIdCliente();

			listSector = new ArrayList<>();
			listSector.add(sectorI.buscarPorId(empresa.getSectorIdSector().getIdSector()));
			idSector = empresa.getSectorIdSector().getIdSector();

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
			listCategoria = new ArrayList<>();
			listPregunta = new ArrayList<>();
			listRespuesta = new ArrayList<>();
			listCheckList = new ArrayList<>();

			listCheckList = checklistService.findCheckListByRoles(idRoles);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void cargarSegmentacion() {
		try {

			listaRegion = new ArrayList<>();
			listaZona = new ArrayList<>();
			listaProvincia = new ArrayList<>();
			listaCiudad = new ArrayList<>();
			listaZonaE = new ArrayList<>();

			/*
			 * listaRegion = regionI.listar(); listaZona = zonaI.listar(); listaProvincia =
			 * provinciaI.listar(); listaCiudad = ciudadI.listar(); listaZonaE =
			 * zonaEI.listar();
			 */
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
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

	List<RespuestaPorCategoriaDTO> listRespuestCategoria;

	public void cargarRespuestas() {
		try {

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

					System.out.println("TOTAL BLOQUES: " + totalBloques);
					System.out.println("TOTAL BLOQUES: " + totalPreguntas);
				}

				rcdto.setTotalBloques(totalBloques);
				rcdto.setTotalPreguntas(totalPreguntas);

				listRespuestCategoria.add(rcdto);

			}

			listPregunta = preguntaService.findPreguntasByCategorias(list2);

			System.out.println("PREGUNTAS X CATEGORIAS:" + listPregunta.size());

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void cargarPreguntas() {
		try {

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void formarRespuestaExtendida() {

		Long id = new Long(0);
		if (!listCheckList.isEmpty())
			id = listCheckList.get(0).getIdChecklist();

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

	public void createRadarModel() {
		radarModel = new RadarChartModel();
		ChartData data = new ChartData();

		RadarChartDataSet radarDataSet = new RadarChartDataSet();
		radarDataSet.setLabel("AÑO 2018");
		radarDataSet.setFill(true);
		radarDataSet.setBackgroundColor("rgba(255, 99, 132, 0.2)");
		radarDataSet.setBorderColor("rgb(255, 99, 132)");
		radarDataSet.setPointBackgroundColor("rgb(255, 99, 132)");
		radarDataSet.setPointBorderColor("#fff");
		radarDataSet.setPointHoverBackgroundColor("#fff");
		radarDataSet.setPointHoverBorderColor("rgb(255, 99, 132)");
		List<Number> dataVal = new ArrayList<>();
		dataVal.add(65);
		dataVal.add(59);
		dataVal.add(90);
		dataVal.add(81);
		dataVal.add(56);
		dataVal.add(55);
		dataVal.add(40);
		radarDataSet.setData(dataVal);

		RadarChartDataSet radarDataSet2 = new RadarChartDataSet();
		radarDataSet2.setLabel("AÑO 2019");
		radarDataSet2.setFill(true);
		radarDataSet2.setBackgroundColor("rgba(54, 162, 235, 0.2)");
		radarDataSet2.setBorderColor("rgb(54, 162, 235)");
		radarDataSet2.setPointBackgroundColor("rgb(54, 162, 235)");
		radarDataSet2.setPointBorderColor("#fff");
		radarDataSet2.setPointHoverBackgroundColor("#fff");
		radarDataSet2.setPointHoverBorderColor("rgb(54, 162, 235)");
		List<Number> dataVal2 = new ArrayList<>();
		dataVal2.add(28);
		dataVal2.add(48);
		dataVal2.add(40);
		dataVal2.add(19);
		dataVal2.add(96);
		dataVal2.add(27);
		// dataVal2.add(100);
		radarDataSet2.setData(dataVal2);

		data.addChartDataSet(radarDataSet);
		data.addChartDataSet(radarDataSet2);

		List<String> labels = new ArrayList<>();
		labels.add("Bienvenida");
		labels.add("Telefonia");
		labels.add("Fantasma");
		labels.add("Lealtad");
		labels.add("Atencion");
		labels.add("Cordialidad");
		// labels.add("Desembolvimiento");
		data.setLabels(labels);

		/* Options */
		RadarChartOptions options = new RadarChartOptions();
		Elements elements = new Elements();
		ElementsLine elementsLine = new ElementsLine();
		elementsLine.setTension(0);
		elementsLine.setBorderWidth(3);
		elements.setLine(elementsLine);
		options.setElements(elements);

		radarModel.setOptions(options);
		radarModel.setData(data);
	}

	public void createRadarModel2() {
		radarModel2 = new RadarChartModel();
		ChartData data = new ChartData();

		RadarChartDataSet radarDataSet = new RadarChartDataSet();
		radarDataSet.setLabel("P.Practitioner");
		radarDataSet.setLineTension(0.1);
		radarDataSet.setBackgroundColor("rgba(102, 153, 204, 0.2)");
		radarDataSet.setBorderColor("rgba(102, 153, 204, 1)");
		radarDataSet.setPointBackgroundColor("rgba(102, 153, 204, 1)");
		radarDataSet.setPointBorderColor("#fff");
		radarDataSet.setPointHoverRadius(5);
		radarDataSet.setPointHoverBackgroundColor("#fff");
		radarDataSet.setPointHoverBorderColor("rgba(102, 153, 204, 1)");
		List<Number> dataVal = new ArrayList<>();
		dataVal.add(2);
		dataVal.add(3);
		dataVal.add(2);
		dataVal.add(1);
		dataVal.add(3);
		radarDataSet.setData(dataVal);

		RadarChartDataSet radarDataSet2 = new RadarChartDataSet();
		radarDataSet2.setLabel("P.Manager");
		radarDataSet2.setLineTension(0.1);
		radarDataSet2.setBackgroundColor("rgba(255, 204, 102, 0.2)");
		radarDataSet2.setBorderColor("rgba(255, 204, 102, 1)");
		radarDataSet2.setPointBackgroundColor("rgba(255, 204, 102, 1)");
		radarDataSet2.setPointBorderColor("#fff");
		radarDataSet2.setPointHoverRadius(5);
		radarDataSet2.setPointHoverBackgroundColor("#fff");
		radarDataSet2.setPointHoverBorderColor("rgba(255, 204, 102, 1)");
		List<Number> dataVal2 = new ArrayList<>();
		dataVal2.add(2);
		dataVal2.add(3);
		dataVal2.add(3);
		dataVal2.add(2);
		dataVal2.add(3);
		radarDataSet2.setData(dataVal2);

		data.addChartDataSet(radarDataSet);
		data.addChartDataSet(radarDataSet2);

		List<List<String>> labels = new ArrayList<>();
		labels.add(new ArrayList(Arrays.asList("Process", "Excellence")));
		labels.add(new ArrayList(Arrays.asList("Problem", "Solving")));
		labels.add(new ArrayList(Arrays.asList("Facilitation")));
		labels.add(new ArrayList(Arrays.asList("Project", "Mgmt")));
		labels.add(new ArrayList(Arrays.asList("Change", "Mgmt")));
		data.setLabels(labels);

		/* Options */
		RadarChartOptions options = new RadarChartOptions();
		RadialScales rScales = new RadialScales();

		RadialLinearAngleLines angelLines = new RadialLinearAngleLines();
		angelLines.setDisplay(true);
		angelLines.setLineWidth(0.5);
		angelLines.setColor("rgba(128, 128, 128, 0.2)");
		rScales.setAngelLines(angelLines);

		RadialLinearPointLabels pointLabels = new RadialLinearPointLabels();
		pointLabels.setFontSize(14);
		pointLabels.setFontStyle("300");
		pointLabels.setFontColor("rgba(204, 204, 204, 1)");
		pointLabels.setFontFamily("Lato, sans-serif");

		RadialLinearTicks ticks = new RadialLinearTicks();
		ticks.setBeginAtZero(true);
		ticks.setMaxTicksLimit(3);
		ticks.setMin(0);
		ticks.setMax(3);
		ticks.setDisplay(false);

		options.setScales(rScales);

		radarModel2.setOptions(options);
		radarModel2.setData(data);
		radarModel2.setExtender("skinRadarChart");
	}

	public String[] getSelectedCities2() {
		return selectedCities2;
	}

	public void setSelectedCities2(String[] selectedCities2) {
		this.selectedCities2 = selectedCities2;
	}

	public List<String> getCities() {
		return cities;
	}

	public void onItemUnselect(UnselectEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();

		FacesMessage msg = new FacesMessage();
		msg.setSummary("Item unselected: " + event.getObject().toString());
		msg.setSeverity(FacesMessage.SEVERITY_INFO);

		context.addMessage(null, msg);
	}

	public void createLineModel() {
		lineModel = new LineChartModel();
		ChartData data = new ChartData();

		LineChartDataSet dataSet = new LineChartDataSet();
		List<Object> values = new ArrayList<>();
		values.add(65);
		values.add(59);
		values.add(80);
		values.add(81);
		values.add(56);
		values.add(55);
		values.add(40);
		dataSet.setData(values);
		dataSet.setFill(false);
		dataSet.setLabel("My First Dataset");
		dataSet.setBorderColor("rgb(75, 192, 192)");
		dataSet.setLineTension(0.1);
		data.addChartDataSet(dataSet);

		List<String> labels = new ArrayList<>();
		labels.add("January");
		labels.add("February");
		labels.add("March");
		labels.add("April");
		labels.add("May");
		labels.add("June");
		labels.add("July");
		data.setLabels(labels);

		// Options
		LineChartOptions options = new LineChartOptions();
		Title title = new Title();
		title.setDisplay(true);
		title.setText("Line Chart");
		options.setTitle(title);

		lineModel.setOptions(options);
		lineModel.setData(data);
	}

	public void createDonutModel() {
		donutModel = new DonutChartModel();
		ChartData data = new ChartData();

		DonutChartDataSet dataSet = new DonutChartDataSet();
		List<Number> values = new ArrayList<>();
		values.add(300);
		values.add(50);
		values.add(100);
		dataSet.setData(values);

		List<String> bgColors = new ArrayList<>();
		bgColors.add("rgb(255, 99, 132)");
		bgColors.add("rgb(54, 162, 235)");
		bgColors.add("rgb(255, 205, 86)");
		dataSet.setBackgroundColor(bgColors);

		data.addChartDataSet(dataSet);
		List<String> labels = new ArrayList<>();
		labels.add("Red");
		labels.add("Blue");
		labels.add("Yellow");
		data.setLabels(labels);

		donutModel.setData(data);
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

	List<RolPorEvaluacionDTO> listRolPorEvaluacion = new ArrayList<>();

	public void cargarEvaluacionesXRol() {
		try {

			for (Rol rol : listRol) {
				RolPorEvaluacionDTO re = new RolPorEvaluacionDTO();
				float totalBloques = 0;
				float totalPreguntas = 0;
				idRoles = new ArrayList<>();
				idRoles.add(rol.getIdRol());

				List<Evaluacion> list = cleService.findEvaluacionByIdsRol(idRoles);

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

	public void createBarModel1() {

		cargarEvaluacionesXRol();
		barModel1 = new BarChartModel();
		ChartData data = new ChartData();

		BarChartDataSet barDataSet = new BarChartDataSet();
		barDataSet.setLabel("Indicadores");
		barDataSet.setBackgroundColor("rgba(255, 99, 132, 0.2)");
		barDataSet.setBorderColor("rgb(255, 99, 132)");
		barDataSet.setBorderWidth(1);

		List<Number> values = new ArrayList<>();
		List<String> labels = new ArrayList<>();

		System.out.println("TAMAÑO ROL X EVALUACION: " + listRolPorEvaluacion.size());

		for (RolPorEvaluacionDTO re : listRolPorEvaluacion) {

			values.add(re.getTotalBloques());

			barDataSet.setData(values);

			labels.add(re.getRol().getNombreRol());

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

	private void createHorizontalBarModel() {

		List<AgenciaPorEvaluacionDTO> list = new ArrayList<>();

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

	public void itemSelect(ItemSelectEvent event) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected",
				"Item Index: " + event.getItemIndex() + ", DataSet Index:" + event.getDataSetIndex());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/*** SEtter y Geters ***/

	public DonutChartModel getDonutModel() {
		return donutModel;
	}

	public Date getFiltroFecha() {
		return filtroFecha;
	}

	public void setFiltroFecha(Date filtroFecha) {
		this.filtroFecha = filtroFecha;
	}

	public List<RangoDesempenio> getListRango() {
		return listRango;
	}

	public void setListRango(List<RangoDesempenio> listRango) {
		this.listRango = listRango;
	}

	public void setDonutModel(DonutChartModel donutModel) {
		this.donutModel = donutModel;
	}

	public BarChartModel getBarModel2() {
		return barModel2;
	}

	public void setBarModel2(BarChartModel barModel2) {
		this.barModel2 = barModel2;
	}

	public HorizontalBarChartModel getHbarModel() {
		return hbarModel;
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

	public LineChartModel getLineModel() {
		return lineModel;
	}

	public void setLineModel(LineChartModel lineModel) {
		this.lineModel = lineModel;
	}

	public RadarChartModel getRadarModel() {
		return radarModel;
	}

	public void setRadarModel(RadarChartModel radarModel) {
		this.radarModel = radarModel;
	}

	public RadarChartModel getRadarModel2() {
		return radarModel2;
	}

	public void setRadarModel2(RadarChartModel radarModel2) {
		this.radarModel2 = radarModel2;
	}

	public List<Agencia> getListAgenciaSelect() {
		return listAgenciaSelect;
	}

	public void setListAgenciaSelect(List<Agencia> listAgenciaSelect) {
		this.listAgenciaSelect = listAgenciaSelect;
	}

	public List<Categoria> getListCategoria() {
		return listCategoria;
	}

	public void setListCategoria(List<Categoria> listCategoria) {
		this.listCategoria = listCategoria;
	}

	public List<Pregunta> getListPregunta() {
		return listPregunta;
	}

	public void setListPregunta(List<Pregunta> listPregunta) {
		this.listPregunta = listPregunta;
	}

	public List<Respuesta> getListRespuesta() {
		return listRespuesta;
	}

	public void setListRespuesta(List<Respuesta> listRespuesta) {
		this.listRespuesta = listRespuesta;
	}

	public List<Checklist> getListCheckList() {
		return listCheckList;
	}

	public void setListCheckList(List<Checklist> listCheckList) {
		this.listCheckList = listCheckList;
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

	public Long getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(Long idAgencia) {
		this.idAgencia = idAgencia;
	}

	public Long getIdArea() {
		return idArea;
	}

	public void setIdArea(Long idArea) {
		this.idArea = idArea;
	}

	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	public List<String> getSelectedOptions() {
		return selectedOptions;
	}

	public void setSelectedOptions(List<String> selectedOptions) {
		this.selectedOptions = selectedOptions;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
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

	public List<Empresa> getListEmpresa() {
		return listEmpresa;
	}

	public void setListEmpresa(List<Empresa> listEmpresa) {
		this.listEmpresa = listEmpresa;
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

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public List<Region> getListaRegion() {
		return listaRegion;
	}

	public void setListaRegion(List<Region> listaRegion) {
		this.listaRegion = listaRegion;
	}

	public List<Zona> getListaZona() {
		return listaZona;
	}

	public void setListaZona(List<Zona> listaZona) {
		this.listaZona = listaZona;
	}

	public List<Provincia> getListaProvincia() {
		return listaProvincia;
	}

	public void setListaProvincia(List<Provincia> listaProvincia) {
		this.listaProvincia = listaProvincia;
	}

	public List<Ciudad> getListaCiudad() {
		return listaCiudad;
	}

	public void setListaCiudad(List<Ciudad> listaCiudad) {
		this.listaCiudad = listaCiudad;
	}

	public List<ZonaEstructural> getListaZonaE() {
		return listaZonaE;
	}

	public void setListaZonaE(List<ZonaEstructural> listaZonaE) {
		this.listaZonaE = listaZonaE;
	}

	public Long getIdRegion() {
		return idRegion;
	}

	public void setIdRegion(Long idRegion) {
		this.idRegion = idRegion;
	}

	public Long getIdZona() {
		return idZona;
	}

	public void setIdZona(Long idZona) {
		this.idZona = idZona;
	}

	public Long getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(Long idProvincia) {
		this.idProvincia = idProvincia;
	}

	public Long getIdCiudad() {
		return idCiudad;
	}

	public void setIdCiudad(Long idCiudad) {
		this.idCiudad = idCiudad;
	}

	public Long getIdZonaE() {
		return idZonaE;
	}

	public void setIdZonaE(Long idZonaE) {
		this.idZonaE = idZonaE;
	}

	public List<ChecklistHasEvaluacion> getListChecklistEvaluacion() {
		return listChecklistEvaluacion;
	}

	public void setListChecklistEvaluacion(List<ChecklistHasEvaluacion> listChecklistEvaluacion) {
		this.listChecklistEvaluacion = listChecklistEvaluacion;
	}

	public List<Long> getIdAgencias() {
		return idAgencias;
	}

	public void setIdAgencias(List<Long> idAgencias) {
		this.idAgencias = idAgencias;
	}

	public List<Long> getIdRoles() {
		return idRoles;
	}

	public void setIdRoles(List<Long> idRoles) {
		this.idRoles = idRoles;
	}

	public List<Evaluacion> getListEvaluacion() {
		return listEvaluacion;
	}

	public void setListEvaluacion(List<Evaluacion> listEvaluacion) {
		this.listEvaluacion = listEvaluacion;
	}

	private List<RespuestaDTO> listaResp;

	public List<RespuestaDTO> getListaResp() {
		return listaResp;
	}

	public void setListaResp(List<RespuestaDTO> listaResp) {
		this.listaResp = listaResp;
	}

}
