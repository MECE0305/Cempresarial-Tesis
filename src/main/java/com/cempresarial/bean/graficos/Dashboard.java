/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cempresarial.bean.graficos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.ItemSelectEvent;
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
import org.primefaces.model.charts.optionconfig.elements.Elements;
import org.primefaces.model.charts.optionconfig.elements.ElementsLine;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.radar.RadarChartDataSet;
import org.primefaces.model.charts.radar.RadarChartModel;
import org.primefaces.model.charts.radar.RadarChartOptions;

import com.cempresarial.entities.DTO.AgenciaPorEvaluacionDTO;
import com.cempresarial.entities.DTO.RespuestaPorCategoriaDTO;
import com.cempresarial.entities.DTO.RolPorEvaluacionDTO;
import com.cempresarial.recursos.SesionController;
import com.cempresarial.recursos.Utilitarios;
import com.cempresarial.rest.client.service.AgenciaService;
import com.cempresarial.rest.client.service.CatalogoPreguntaService;
import com.cempresarial.rest.client.service.CategoriaService;
import com.cempresarial.rest.client.service.ChecklistEvaluacionService;
import com.cempresarial.rest.client.service.ChecklistService;
import com.cempresarial.rest.client.service.ClienteService;
import com.cempresarial.rest.client.service.EmpleadoService;
import com.cempresarial.rest.client.service.EmpresaService;
import com.cempresarial.rest.client.service.EvaluacionService;
import com.cempresarial.rest.client.service.PerfilService;
import com.cempresarial.rest.client.service.PermisoAgenciaService;
import com.cempresarial.rest.client.service.RangoService;
import com.cempresarial.rest.client.service.RespuestaService;
import com.cempresarial.rest.client.service.RolService;
import com.cempresarial.rest.client.service.SectorService;
import com.cempresariales.servicio.commons.admin.model.entity.Perfil;
import com.cempresariales.servicio.commons.admin.model.entity.PermisoAgencia;
import com.cempresariales.servicio.commons.admin.model.entity.Usuario;
import com.cempresariales.servicio.commons.model.entity.Agencia;
import com.cempresariales.servicio.commons.model.entity.Categoria;
import com.cempresariales.servicio.commons.model.entity.Checklist;
import com.cempresariales.servicio.commons.model.entity.ChecklistHasEvaluacion;
import com.cempresariales.servicio.commons.model.entity.Cliente;
import com.cempresariales.servicio.commons.model.entity.Empleado;
import com.cempresariales.servicio.commons.model.entity.Empresa;
import com.cempresariales.servicio.commons.model.entity.Evaluacion;
import com.cempresariales.servicio.commons.model.entity.RangoDesempenio;
import com.cempresariales.servicio.commons.model.entity.Rol;
import com.cempresariales.servicio.commons.model.entity.Sector;

/**
 *
 * @author DIGETBI 05
 */
@ManagedBean
@SessionScoped
public class Dashboard extends SesionController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Dashboard() {
		super();
	}

	@Inject
	private RangoService rangoService;
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
	@Inject
	private RolService rolService;
	@Inject
	private ChecklistEvaluacionService cleService;
	@Inject
	private ChecklistService checklistService;
	@Inject
	private CategoriaService categoriaService;
	@Inject
	private RespuestaService respuestaService;
	@Inject
	private CatalogoPreguntaService cpService;

	private Usuario user;
	private Empresa empresa;
	private RangoDesempenio rangoA;
	private RangoDesempenio rangoB;
	private RangoDesempenio rangoC;
	private RangoDesempenio rangoD;

	private List<AgenciaPorEvaluacionDTO> list;
	private List<Agencia> listAgencia;
	private List<Evaluacion> listEvaluacion;
	private List<RangoDesempenio> listRango;
	private List<Empleado> listEmpleado;
	private List<Rol> listRol;
	private List<Cliente> listCliente;
	private List<Empresa> listEmpresa;
	private List<Sector> listSector;
	private List<Checklist> listCheckList;
	private List<Categoria> listCategoria;
	private List<RespuestaPorCategoriaDTO> rcDtoList;
	List<Number> dataVal = new ArrayList<>();

	private List<RolPorEvaluacionDTO> listRolPorEvaluacion;
	private List<ChecklistHasEvaluacion> cleList = new ArrayList<>();

	private Long idCliente;
	private Long idEmpresa;
	private Long idSector;
	private Long idPerfil;
	private Long idAgencia;
	private Long idRol;
	private Long idEmpleado;

	private List<Long> idAgencias;
	private List<Long> idRoles;
	private List<Long> idCheckList;

	private HorizontalBarChartModel hbarModel;
	private BarChartModel barModel1;
	private RadarChartModel radarModel;

	@PostConstruct
	public void init() {

		user = getCurrentUser();
		idAgencias = new ArrayList<>();
		listRol = new ArrayList<>();
		listEvaluacion = new ArrayList<>();
		listAgencia = new ArrayList<>();
		idEmpresa = new Long("0");
		idCliente = new Long("0");
		System.out.println("********* "+listEvaluacion.size());
		//cargarData();
		empresa = new Empresa();
		empresa = empresaService.buscarPorId(idEmpresa);
		listRango = rangoService.findByEmpresa(user.getIdEmpresa());
		listRolPorEvaluacion = new ArrayList<>();


		/*cargarEmpleados();

		ubicarDesempenioEvaluacion();

		*/
		  createBarModel1(); 
		  createHorizontalBarModel(); 
		  createRadarModel();
		 

	}

	public void itemSelect(ItemSelectEvent event) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected",
				"Item Index: " + event.getItemIndex() + ", DataSet Index:" + event.getDataSetIndex());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/*
	 * public void cargarChecklistByRol() { try {
	 * 
	 * idCheckList = new ArrayList<>();
	 * 
	 * for (ChecklistHasEvaluacion cl : cleList) {
	 * idCheckList.add(cl.getChecklistHasEvaluacionPK().getChecklistIdChecklist());
	 * }
	 * 
	 * listCategoria = categoriaService.findCategoriasByChecklist(idCheckList);
	 * 
	 * System.out.println("CATEGORIAS:" + listCategoria.size());
	 * 
	 * } catch (Exception e) { // TODO: handle exception e.printStackTrace(); } }
	 */

	public void llenarAgencias() {
		try {
			
			list = new ArrayList<>();
			listEvaluacion = evaService.findEvaByAgencias(idAgencias);

			List<Long> idEvaluaciones = new ArrayList<>();
			for (Evaluacion eva : listEvaluacion) {
				idEvaluaciones.add(eva.getIdEvaluacion());
			}

			cleList = new ArrayList<>();
			cleList = cleService.findCheckListEvaluacionByEvaluaciones(idEvaluaciones);

			idCheckList = new ArrayList<>();
			listCategoria = new ArrayList<>();
			for (ChecklistHasEvaluacion obj : cleList) {
				idCheckList.add(obj.getChecklistHasEvaluacionPK().getChecklistIdChecklist());
			}

			listCategoria = categoriaService.findCategoriasByChecklist(idCheckList);

			rcDtoList = new ArrayList<>();

			double totalB = 0.00;

			int i = 0;
			for (Categoria cat : listCategoria) {

				RespuestaPorCategoriaDTO obj = new RespuestaPorCategoriaDTO();
				totalB = 0.00;

				for (Evaluacion re : listEvaluacion) {

					List<Evaluacion> list = evaService.findByEvaluacionCategoria(re.getIdEvaluacion(),
							cat.getIdCategoria());

					i++;

					if (list.size() > 0) {
						totalB = totalB + Double.parseDouble(re.getPromedioSeccion());
					}
				}

				obj.setCategoria(cat);
				obj.setTotalBloques(totalB);

				rcDtoList.add(obj);

			}
			
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

	public void ubicarDesempenioEvaluacion() {
		try {

			rangoA = new RangoDesempenio();
			rangoB = new RangoDesempenio();
			rangoC = new RangoDesempenio();
			rangoD = new RangoDesempenio();

			rangoA.setNombreRango("EXCELENTE");
			rangoA.setMinimoRango(0.00);
			rangoB.setNombreRango("ACEPTABLE");
			rangoB.setMinimoRango(0.00);
			rangoC.setNombreRango("NO ACEPTABLE");
			rangoC.setMinimoRango(0.00);
			rangoD.setNombreRango("ALERTA");
			rangoD.setMinimoRango(0.00);

			for (RangoDesempenio rango : listRango) {
				for (Evaluacion eva : listEvaluacion) {
					Double valor = Double.parseDouble(eva.getPromedioTotal());
					switch (rango.getNombreRango().toUpperCase()) {
					case "EXCELENTE":
						rangoA.setColorRango(rango.getColorRango());
						if (valor <= rango.getMaximoRango() && valor >= rango.getMinimoRango()) {
							rangoA.setMinimoRango(rangoA.getMinimoRango() + 1);
						}
						break;

					case "ACEPTABLE":
						rangoB.setColorRango(rango.getColorRango());
						if (valor <= rango.getMaximoRango() && valor >= rango.getMinimoRango()) {
							rangoB.setMinimoRango(rangoB.getMinimoRango() + 1);
						}
						break;
					case "NO ACEPTABLE":
						rangoC.setColorRango(rango.getColorRango());

						if (valor <= rango.getMaximoRango() && valor >= rango.getMinimoRango()) {
							rangoC.setMinimoRango(rangoC.getMinimoRango() + 1);
						}
						break;
					case "ALERTA":
						rangoD.setColorRango(rango.getColorRango());

						if (valor <= rango.getMaximoRango() && valor >= rango.getMinimoRango()) {
							rangoD.setMinimoRango(rangoD.getMinimoRango() + 1);
						}
						break;
					default:
						break;
					}

				}
			}

			rangoA.setDescripcionRango(Double.toString(rangoA.getMinimoRango()));
			rangoB.setDescripcionRango(Double.toString(rangoB.getMinimoRango()));
			rangoC.setDescripcionRango(Double.toString(rangoC.getMinimoRango()));
			rangoD.setDescripcionRango(Double.toString(rangoD.getMinimoRango()));

			BigDecimal auxDecimal = new BigDecimal(rangoA.getMinimoRango().toString());
			rangoA.setDescripcionRango(auxDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toString());

			auxDecimal = new BigDecimal(rangoB.getMinimoRango().toString());
			rangoB.setDescripcionRango(auxDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toString());

			auxDecimal = new BigDecimal(rangoC.getMinimoRango().toString());
			rangoC.setDescripcionRango(auxDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toString());

			auxDecimal = new BigDecimal(rangoD.getMinimoRango().toString());
			rangoD.setDescripcionRango(auxDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cargarData() {

		try {
			
			List<Perfil> resultadoBusqueda = perfilI.obtenerPerfilesUsuario(user.getNombreUsuario());

			idPerfil = resultadoBusqueda.get(0).getIdPerfil();
			Perfil perfil = new Perfil();
			perfil = perfilI.buscarPorId(idPerfil);
			Utilitarios util = new Utilitarios();

			idAgencias = new ArrayList<>();
			listAgencia = new ArrayList<>();

			listCliente = new ArrayList<>();
			listEmpresa = new ArrayList<>();

			List<PermisoAgencia> listPE = permisoAgenciaI.findPermisosAgeciaByPerfil(idPerfil);
			util.mostrarMensaje("PERFIL DE INRGESO", "Bienvenido USUARIO PERFIL : " + idPerfil, "W");

			for (PermisoAgencia pa : listPE) {
				idAgencias.add(pa.getIdAgencia());
				listAgencia.add(agenciaService.buscarPorId(pa.getIdAgencia()));
			}
			

			Empresa emp = empresaService.buscarPorId(user.getIdEmpresa());
			listCliente.add(emp.getClienteIdCliente());
			listEmpresa.add(emp);

			idEmpresa = emp.getIdEmpresa();
			idCliente = emp.getClienteIdCliente().getIdCliente();

			llenarAgencias();
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

			// listArea = new ArrayList<>();
			idRoles = new ArrayList<>();
			for (Rol rol : listRol) {
				// listArea.add(rol.getAreaIdArea());
				idRoles.add(rol.getIdRol());
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void createBarModel1() {

		try {
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

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void createHorizontalBarModel() {

		try {
			List<AgenciaPorEvaluacionDTO> list = new ArrayList<>();

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

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// hbarModel.setOptions(options);
	}

	public void createRadarModel() {
		// cargarChecklistByRol();

		try {
			radarModel = new RadarChartModel();
			ChartData data = new ChartData();

			RadarChartDataSet radarDataSet = new RadarChartDataSet();
			radarDataSet.setLabel("Segmentos");
			radarDataSet.setFill(true);
			radarDataSet.setBackgroundColor("rgba(255, 99, 132, 0.2)");
			radarDataSet.setBorderColor("rgb(255, 99, 132)");
			radarDataSet.setPointBackgroundColor("rgb(255, 99, 132)");
			radarDataSet.setPointBorderColor("#fff");
			radarDataSet.setPointHoverBackgroundColor("#fff");
			radarDataSet.setPointHoverBorderColor("rgb(255, 99, 132)");
			
			for (RespuestaPorCategoriaDTO obj : rcDtoList) {
				dataVal.add(obj.getTotalBloques());
			}

			radarDataSet.setData(dataVal);

			/*
			 * RadarChartDataSet radarDataSet2 = new RadarChartDataSet();
			 * radarDataSet2.setLabel("AÑO 2019"); radarDataSet2.setFill(true);
			 * radarDataSet2.setBackgroundColor("rgba(54, 162, 235, 0.2)");
			 * radarDataSet2.setBorderColor("rgb(54, 162, 235)");
			 * radarDataSet2.setPointBackgroundColor("rgb(54, 162, 235)");
			 * radarDataSet2.setPointBorderColor("#fff");
			 * radarDataSet2.setPointHoverBackgroundColor("#fff");
			 * radarDataSet2.setPointHoverBorderColor("rgb(54, 162, 235)"); List<Number>
			 * dataVal2 = new ArrayList<>(); dataVal2.add(28); dataVal2.add(48);
			 * dataVal2.add(40); dataVal2.add(19); dataVal2.add(96); dataVal2.add(27); //
			 * dataVal2.add(100); radarDataSet2.setData(dataVal2);
			 * data.addChartDataSet(radarDataSet2);
			 */

			data.addChartDataSet(radarDataSet);

			List<String> labels = new ArrayList<>();
			for (RespuestaPorCategoriaDTO obj : rcDtoList)
				labels.add(obj.getCategoria().getNombreCategoria());
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
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
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

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<RangoDesempenio> getListRango() {
		return listRango;
	}

	public void setListRango(List<RangoDesempenio> listRango) {
		this.listRango = listRango;
	}

	public List<Agencia> getListAgencia() {
		return listAgencia;
	}

	public void setListAgencia(List<Agencia> listAgencia) {
		this.listAgencia = listAgencia;
	}

	public List<Evaluacion> getListEvaluacion() {
		return listEvaluacion;
	}

	public void setListEvaluacion(List<Evaluacion> listEvaluacion) {
		this.listEvaluacion = listEvaluacion;
	}

	public List<Empleado> getListEmpleado() {
		return listEmpleado;
	}

	public void setListEmpleado(List<Empleado> listEmpleado) {
		this.listEmpleado = listEmpleado;
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

	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	public Long getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public RangoDesempenio getRangoA() {
		return rangoA;
	}

	public void setRangoA(RangoDesempenio rangoA) {
		this.rangoA = rangoA;
	}

	public RangoDesempenio getRangoB() {
		return rangoB;
	}

	public void setRangoB(RangoDesempenio rangoB) {
		this.rangoB = rangoB;
	}

	public RangoDesempenio getRangoC() {
		return rangoC;
	}

	public void setRangoC(RangoDesempenio rangoC) {
		this.rangoC = rangoC;
	}

	public RangoDesempenio getRangoD() {
		return rangoD;
	}

	public void setRangoD(RangoDesempenio rangoD) {
		this.rangoD = rangoD;
	}

	public RadarChartModel getRadarModel() {
		return radarModel;
	}

	public void setRadarModel(RadarChartModel radarModel) {
		this.radarModel = radarModel;
	}

	public List<Categoria> getListCategoria() {
		return listCategoria;
	}

	public void setListCategoria(List<Categoria> listCategoria) {
		this.listCategoria = listCategoria;
	}

}
