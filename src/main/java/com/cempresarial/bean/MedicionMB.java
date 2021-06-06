/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cempresarial.bean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import com.cempresarial.entities.DTO.ContenidoRespuestaDTO;
import com.cempresarial.entities.DTO.RespuestaDTO;
import com.cempresarial.recursos.SesionController;
import com.cempresarial.recursos.Utilitarios;
import com.cempresarial.rest.client.service.AgenciaService;
import com.cempresarial.rest.client.service.CatalogoPreguntaService;
import com.cempresarial.rest.client.service.CategoriaService;
import com.cempresarial.rest.client.service.ChecklistCatalogoPreguntaService;
import com.cempresarial.rest.client.service.ChecklistEvaluacionService;
import com.cempresarial.rest.client.service.ChecklistService;
import com.cempresarial.rest.client.service.ClienteService;
import com.cempresarial.rest.client.service.EmpleadoService;
import com.cempresarial.rest.client.service.EmpresaService;
import com.cempresarial.rest.client.service.EncabezadoService;
import com.cempresarial.rest.client.service.EstadoEvaluacionService;
import com.cempresarial.rest.client.service.EvaluacionEncabezadoService;
import com.cempresarial.rest.client.service.EvaluacionService;
import com.cempresarial.rest.client.service.PesoService;
import com.cempresarial.rest.client.service.PreguntaService;
import com.cempresarial.rest.client.service.RespuestaService;
import com.cempresarial.rest.client.service.RolService;
import com.cempresariales.servicio.commons.model.entity.Agencia;
import com.cempresariales.servicio.commons.model.entity.CatalogoPregunta;
import com.cempresariales.servicio.commons.model.entity.Categoria;
import com.cempresariales.servicio.commons.model.entity.Checklist;
import com.cempresariales.servicio.commons.model.entity.ChecklistHasCatalogoPregunta;
import com.cempresariales.servicio.commons.model.entity.ChecklistHasEvaluacion;
import com.cempresariales.servicio.commons.model.entity.ChecklistHasEvaluacionPK;
import com.cempresariales.servicio.commons.model.entity.Cliente;
import com.cempresariales.servicio.commons.model.entity.Empleado;
import com.cempresariales.servicio.commons.model.entity.Empresa;
import com.cempresariales.servicio.commons.model.entity.Encabezado;
import com.cempresariales.servicio.commons.model.entity.EstadoEvaluacion;
import com.cempresariales.servicio.commons.model.entity.Evaluacion;
import com.cempresariales.servicio.commons.model.entity.EvaluacionHasEncabezado;
import com.cempresariales.servicio.commons.model.entity.EvaluacionHasEncabezadoPK;
import com.cempresariales.servicio.commons.model.entity.Respuesta;
import com.cempresariales.servicio.commons.model.entity.Rol;

/**
 *
 * @author DIGETBI 05
 */
@SessionScoped
@ManagedBean
public class MedicionMB extends SesionController implements Serializable {

	/**
	 * VARIABLES DE SESION
	 **/
	private static final long serialVersionUID = 6771930005130933302L;
	private static final Logger log = Logger.getLogger(MedicionMB.class.getName());

	/**
	 * Servicio
	 **/
	@Inject
	private EvaluacionService evaluacionI;
	@Inject
	private ClienteService clienteI;
	@Inject
	private EmpresaService empresaI;
	@Inject
	private AgenciaService agenciaI;
	@Inject
	private RolService rolI;
	@Inject
	private EmpleadoService empleadoI;
	@Inject
	private ChecklistService plantillaI;
	@Inject
	private EncabezadoService encabezadoService;
	@Inject
	private EstadoEvaluacionService estadoService;
	@Inject
	private CatalogoPreguntaService cpI;
	@Inject
	private ChecklistCatalogoPreguntaService ccpI;

	@Inject
	private PreguntaService preguntaI;
	@Inject
	private RespuestaService respuestaI;
	@Inject
	private PesoService pesoI;
	@Inject
	private CategoriaService categoriaI;
	@Inject
	private EvaluacionEncabezadoService eeI;
	@Inject
	private ChecklistEvaluacionService clevaI;

	/*
	 * @Inject private ChecklistPesoPreguntaService cppI;
	 */
	/**
	 * VARIABLES
	 **/
	private List<Cliente> listCliente;
	private List<Empresa> listEmpresa;
	private List<Agencia> listAgencia;
	private List<Empleado> listEmpleado;
	private List<Rol> listRol;
	private List<Checklist> listPlantillas;
	private List<Checklist> listCheckList;

	private List<EstadoEvaluacion> listEstadoevaluacion;

	private List<Encabezado> listaencabezado;
	private List<Encabezado> listaencabezadoSelect;

	private List<EvaluacionHasEncabezado> listaEvaluacionencabezado;

	private List<CatalogoPregunta> listaCatalogoPregunta;
	private List<Categoria> listCategorias;

	private List<Respuesta> listaRespuesta;
	private List<Categoria> listCategoriasRespuesta;
	private List<RespuestaDTO> listaResp;
	private Long idCliente;
	private Long idEmpresa;
	private Long idAgencia;
	private Long idEmpleado;
	private Long idRol;
	private Long idEstadoEvaluacion;

	private Checklist checklist;
	private Evaluacion evaluacion;

	private UploadedFile fileVideo;
	private StreamedContent streamVideo;
	// cambiar entre linux o mac o windows
	private String pathimages = "";
	private String mensaje = "";

	private Utilitarios utils;
	private Date fechaActual;

	@PostConstruct
	private void init() {
		try {
			this.clear();
		} catch (Exception e) {
			log.info("Error en init. " + e.getMessage() + "**" + e.fillInStackTrace() + "***" + e.getCause() + "***"
					+ e.getLocalizedMessage());
		}
	}

	/**
	 * METODOS
	 **/
	public void clear() {
		try {
			checklist = new Checklist();
			evaluacion = new Evaluacion();
			listCliente = clienteI.listar();
			listEmpresa = new ArrayList<>();
			listAgencia = new ArrayList<>();
			listRol = rolI.listar();
			listEmpleado = new ArrayList<>();
			listPlantillas = new ArrayList<>();
			listaEvaluacionencabezado = new ArrayList<>();
			listaRespuesta = new ArrayList<>();
			listaencabezado = encabezadoService.listar();
			listEstadoevaluacion = estadoService.listar();
			listaCatalogoPregunta = cpI.listar();
			listCategoriasRespuesta = new ArrayList<>();
			listaResp = new ArrayList<>();
			listCheckList = new ArrayList<>();
			idCliente = new Long("0");
			idEmpresa = new Long("0");
			idAgencia = new Long("0");
			idEmpleado = new Long("0");
			idEstadoEvaluacion = new Long("0");
			mensaje = "";

			utils = new Utilitarios();

			fechaActual = new Date();

		} catch (Exception e) {
			log.info("Error en clear. " + e);
		}
	}

	public void limpiarListaFormulario() {
		listaResp = new ArrayList<>();
	}

	public void reiniciarCalculos(RespuestaDTO item) {
		int index = listaResp.indexOf(item);
		for (int y = 0; y < item.getListaContenidoRespuesta().size(); y++) {
			ContenidoRespuestaDTO cdto = listaResp.get(index).getListaContenidoRespuesta().get(y);

			cdto.getRespuesta().setValorCalculadoRespuesta(cdto.getRespuesta().getValorRealRespuesta());
			listaResp.get(index).getListaContenidoRespuesta().set(y, cdto);

		}
	}

	public void calcularTiempos() {

		try {
			evaluacion.setContactoEvaluacion(
					getDifferenceBetwenDates(evaluacion.getAtencionEvaluacion(), evaluacion.getHoraFinEvaluacion()));
			evaluacion.setEsperaEvaluacion(
					getDifferenceBetwenDates(evaluacion.getHoraInicioEvaluacion(), evaluacion.getAtencionEvaluacion()));

			SimpleDateFormat sdfResult = new SimpleDateFormat("HH:mm:ss", Locale.US);
			SimpleDateFormat sdfResultMinutos = new SimpleDateFormat("m", Locale.US);

			System.out.println(sdfResult.format(evaluacion.getContactoEvaluacion())); // 01:57:00
			System.out.println(sdfResultMinutos.format(evaluacion.getContactoEvaluacion()) + " Minutos"); // 57 Minutos

			System.out.println("Fecha contacto: " + evaluacion.getContactoEvaluacion());
			System.out.println("Fecha espera: " + evaluacion.getEsperaEvaluacion());

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			utils.mostrarMensaje("Fechas", "Revisar horas (inicio, fin, atención)", "W");
		}

	}

	public static Date getDifferenceBetwenDates(Date dateInicio, Date dateFinal) {
		long milliseconds = dateFinal.getTime() - dateInicio.getTime();
		int seconds = (int) (milliseconds / 1000) % 60;
		int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
		int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.SECOND, seconds);
		c.set(Calendar.MINUTE, minutes);
		c.set(Calendar.HOUR_OF_DAY, hours);
		return c.getTime();
	}

	public String saveFile(Evaluacion evaluacion) {

		String path = pathimages;

		path += "/resources/videos/" + evaluacion.getIdEvaluacion() + "/video-" + evaluacion.getIdEvaluacion() + ".mp4";

		File targetFile = new File(path);

		try {
			FileUtils.copyInputStreamToFile(streamVideo.getStream(), targetFile);
			return targetFile.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	public void verificarPlantilla() {
		if (listCheckList.size() == 0) {
			mensaje = "No se ha agregado formulario Checklist, desea continuar?";

		} else {
			mensaje = "Desea continuar";
		}

	}

	public boolean verificarRequeridos() {
		if (idCliente == null || idEmpresa == null || idAgencia == null || idEmpleado == null
				|| idEstadoEvaluacion == null) {
			utils.mostrarMensaje("Medición", "Faltan Datos, por favor revisar", "W");
			return false;
		} else
			return true;
	}

	public void guardar() {

		try {
			if (verificarRequeridos()) {

				Double promedio_pregunta = 0.00;
				Double promedio_seccion = 0.00;
				Double promedio_total = 0.00;
				Double puntaje_evaluacion = 0.00;

				/*** Ingresa Evaluacion ***/
				// guarda estado evaluacion

				evaluacion.setEstadoEvaluacionIdEstado(estadoService.buscarPorId(idEstadoEvaluacion));
				// guarda path de video cargado
				evaluacion.setIdEmpleado(idEmpleado);

				evaluacion.setCreaEvaluacion(new Date());

				Evaluacion eva = evaluacionI.insertar(evaluacion);

				if (fileVideo != null) {
					eva.setVideoEvaluacion(this.saveFile(eva));
				}

				evaluacionI.actualizar(eva.getIdEvaluacion(), eva);

				// Ingresa Encabezado - Evaluacion
				if (listaEvaluacionencabezado.size() > 0) {
					for (EvaluacionHasEncabezado obj : listaEvaluacionencabezado) {
						EvaluacionHasEncabezadoPK pk = new EvaluacionHasEncabezadoPK();
						pk.setEncabezadoIdEncabezado(obj.getEncabezado().getIdEncabezado());
						pk.setEvaluacionIdEvaluacion(eva.getIdEvaluacion());
						obj.setEvaluacionHasEncabezadoPK(pk);

						eeI.insertar(obj);

					}
				}

				// ingresar chechkist has evaluacion
				for (Checklist cl : listCheckList) {
					ChecklistHasEvaluacion clhe = new ChecklistHasEvaluacion();
					ChecklistHasEvaluacionPK pk = new ChecklistHasEvaluacionPK();
					pk.setChecklistIdChecklist(cl.getIdChecklist());
					pk.setEvaluacionIdEvaluacion(eva.getIdEvaluacion());

					clhe.setActivo(true);
					// clhe.setChecklist(cl);
					// clhe.setEvaluacion(eva);
					clhe.setChecklistHasEvaluacionPK(pk);
					clevaI.insertar(clhe);
				}

				// ingresa respuestas
				Respuesta respuesta = new Respuesta();
				int numeroSecciones = 0;
				int numeroPreguntas = 0;
				for (RespuestaDTO dto : listaResp) {
					numeroSecciones++;
					for (ContenidoRespuestaDTO cdto : dto.getListaContenidoRespuesta()) {
						respuesta = new Respuesta();
						respuesta.setIdCatalogoPregunta(cdto.getCatalogoPregunta().getIdCatalogoPregunta());
						respuesta.setChecklistHasEvaluacion(new ChecklistHasEvaluacion(
								cdto.getChecklist().getIdChecklist(), eva.getIdEvaluacion()));
						respuesta.setCumpleRespuesta(cdto.getRespuesta().getCumpleRespuesta());
						respuesta.setNoProcede(cdto.getRespuesta().getNoProcede());
						respuesta.setObservacionRespuesta(cdto.getRespuesta().getObservacionRespuesta());

						respuesta.setPesoRespuesta(cdto.getRespuesta().getPesoRespuesta());
						respuesta.setPorcentajeRespuesta(cdto.getRespuesta().getPorcentajeRespuesta());
						respuesta.setValorRealRespuesta(cdto.getRespuesta().getValorRealRespuesta());
						respuesta.setValorCalculadoRespuesta(cdto.getRespuesta().getValorCalculadoRespuesta());

						promedio_pregunta = promedio_pregunta + cdto.getRespuesta().getValorCalculadoRespuesta();

						numeroPreguntas++;
						respuestaI.insertar(respuesta);

					}
				}

				eva = evaluacionI.buscarPorId(eva.getIdEvaluacion());
				eva.setPromedioPregunta(Double.toString((promedio_pregunta / numeroPreguntas)));
				eva.setPromedioSeccion(Double.toString((promedio_pregunta / numeroSecciones)));
				eva.setPromedioTotal(Double
						.toString(((promedio_pregunta / numeroPreguntas) + (promedio_pregunta / numeroSecciones)) / 2));

				// eva.setIdUsuarioCrea(getCurrentUser().getIdUsuario());

				evaluacionI.actualizar(eva.getIdEvaluacion(), eva);

				utils.mostrarMensaje("EVALUACIÓN ", "Registrada Correctamente", "I");
			}
		} catch (Exception e) {
			log.info("Error en guarda Medicion: " + e.getMessage());
			utils.mostrarMensaje("EVALUACIÓN ", "Ha ocurreo un problema en el registro", "E");
		}

	}

	public double calulcularTotalRespuestaBase(RespuestaDTO item) {
		double total = 0;

		for (ContenidoRespuestaDTO obj : item.getListaContenidoRespuesta()) {
			total = total + obj.getRespuesta().getValorRealRespuesta();
		}

		return total;
	}

	public double calulcularTotalRespuestaCalculado(RespuestaDTO item) {
		double total = 0;

		for (ContenidoRespuestaDTO obj : item.getListaContenidoRespuesta()) {
			if (obj.getRespuesta().getNoProcede())
				total = total + obj.getRespuesta().getValorCalculadoRespuesta();
		}

		return total;
	}

	public void recalculosValores(RespuestaDTO item, ContenidoRespuestaDTO subItem) {
		try {

			double valor = 0;
			int tamanioByCategoria = 0;
			double valorDistribuye = 0;
			int index = listaResp.indexOf(item);
			valor = subItem.getRespuesta().getValorCalculadoRespuesta();

			if (!subItem.getRespuesta().getNoProcede()) {

				System.out.println("NO PROCEDE GO DISTRIBUIR");

				for (int x = 0; x < item.getListaContenidoRespuesta().size(); x++) {
					System.out.println("VERIFICA SI PROCEDE: "
							+ item.getListaContenidoRespuesta().get(x).getRespuesta().getNoProcede());
					if (item.getListaContenidoRespuesta().get(x).getRespuesta().getNoProcede()) {

						tamanioByCategoria++;
					}
				}

				System.out.println("CUANTOS SI ? " + tamanioByCategoria);

				if (tamanioByCategoria != 0)
					valorDistribuye = valor / tamanioByCategoria;

				System.out.println("CAUNTO DISTRIBUYE ? " + valorDistribuye);

				for (int y = 0; y < listaResp.get(index).getListaContenidoRespuesta().size(); y++) {
					ContenidoRespuestaDTO cdto = listaResp.get(index).getListaContenidoRespuesta().get(y);

					if (!listaResp.get(index).getListaContenidoRespuesta().get(y).equals(subItem)
							&& cdto.getRespuesta().getNoProcede()) {
						System.out.println("GO DISTRIBUYE !!!!! ");

						cdto.getRespuesta().setValorCalculadoRespuesta(
								cdto.getRespuesta().getValorCalculadoRespuesta() + valorDistribuye);
					}
					listaResp.get(index).getListaContenidoRespuesta().set(y, cdto);

				}
			} else {
				System.out.println("NO DISTRIBUYE !!!!! ");

				for (int x = 0; x < item.getListaContenidoRespuesta().size(); x++) {
					System.out.println("VERIFICA SI PROCEDE: "
							+ item.getListaContenidoRespuesta().get(x).getRespuesta().getNoProcede());
					if (item.getListaContenidoRespuesta().get(x).getRespuesta().getNoProcede()) {

						tamanioByCategoria++;
					}
				}

				System.out.println("CUANTOS SI ? " + tamanioByCategoria);

				if (tamanioByCategoria != 0)
					valorDistribuye = valor / (tamanioByCategoria - 1);

				System.out.println("CAUNTO DISTRIBUYE ? " + valorDistribuye);

				for (int y = 0; y < listaResp.get(index).getListaContenidoRespuesta().size(); y++) {
					ContenidoRespuestaDTO cdto = listaResp.get(index).getListaContenidoRespuesta().get(y);

					if (!listaResp.get(index).getListaContenidoRespuesta().get(y).equals(subItem)
							&& cdto.getRespuesta().getNoProcede()) {
						System.out.println(
								"A QUITAR LO DISTRUBUIDO" + subItem.getRespuesta().getValorCalculadoRespuesta());
						cdto.getRespuesta().setValorCalculadoRespuesta(
								cdto.getRespuesta().getValorCalculadoRespuesta() - valorDistribuye);
					}
					listaResp.get(index).getListaContenidoRespuesta().set(y, cdto);

				}

				System.out.println("VALOR 2 POR DEFECTO" + valor);

				int indice2 = listaResp.get(index).getListaContenidoRespuesta().indexOf(subItem);
				ContenidoRespuestaDTO cdto = listaResp.get(index).getListaContenidoRespuesta().get(indice2);
				cdto.getRespuesta().setValorCalculadoRespuesta(valor);
				listaResp.get(index).getListaContenidoRespuesta().set(indice2, cdto);

			}
		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizarListaEncabezados() {
		listaencabezado = encabezadoService.listar();
	}

	public void findByClienteIdCliente() {
		Cliente cliente = clienteI.buscarPorId(idCliente);
		listEmpresa = empresaI.findByClienteIdCliente(cliente);
	}

	public void findByEmpresaIdEmpresa() {
		Empresa empresa = empresaI.buscarPorId(idEmpresa);
		listAgencia = agenciaI.findByEmpresaIdEmpresa(empresa);

	}

	public void findByRolIdRol() {
		Rol rol = rolI.buscarPorId(idRol);
		listPlantillas = plantillaI.findByRolIdRol(rol);
	}

	public void findByAgenciaIdAgencia() {
		Agencia agencia = agenciaI.buscarPorId(idAgencia);
		listEmpleado = empleadoI.findByAgenciaIdAgencia(agencia);
	}

	public int countPreguntasByChecklist(Long idChecklist) {
		return plantillaI.countPreguntasByChecklist(idChecklist);
	}

	public int countCategoriaPreguntasByChecklist(Long idChecklist) {
		return plantillaI.countCategoriaByChecklist(idChecklist);
	}

	public void upload(FileUploadEvent event1) {
		try {
			fileVideo = event1.getFile();
			streamVideo = new DefaultStreamedContent(event1.getFile().getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void agregarEncabezadoEvaluacion() {
		listaEvaluacionencabezado = new ArrayList<>();
		for (Encabezado en : listaencabezadoSelect) {
			EvaluacionHasEncabezado eha = new EvaluacionHasEncabezado();
			eha.setActivo(true);
			eha.setEncabezado(en);
			eha.setValorEncabezado("");
			listaEvaluacionencabezado.add(eha);
		}
	}

	public void verEncabezadoEvaluacion() {
		for (EvaluacionHasEncabezado en : listaEvaluacionencabezado) {
			System.out.println("DATOS INGRESADOS: " + en.getEncabezado().getNombreEncabezado());
			System.out.println("DATOS INGRESADOS: " + en.getValorEncabezado());
		}
	}

	public void categoriasByCheckList(Long id) {
		listCategorias = plantillaI.listCategoriaByChecklist(id);
	}

	public List<CatalogoPregunta> preguntasByCategoria(Long id) {
		List<CatalogoPregunta> list = new ArrayList<>();

		list.addAll(cpI.findCatalogoPreguntaByIdCategoria(id));

		return list;
	}

	public void agregarCheckListRespuesta(Long id) {

		try {

			Checklist chl = plantillaI.buscarPorId(id);
			boolean checkListVerifica = false;
			for (RespuestaDTO dtoR : listaResp) {
				if (dtoR.getListaContenidoRespuesta().contains(chl)) {
					checkListVerifica = true;
					break;

				}
			}

			if (!checkListVerifica) {
				boolean match = false;
				if (listCheckList.size() == 0)
					listCheckList.add(plantillaI.buscarPorId(id));
				else {
					for (Checklist cl : listCheckList)
						if (cl.getIdChecklist() != id) {
							match = true;
							break;
						}
				}

				if (!match && listaResp.size() == 0) {
					listaResp = new ArrayList<>();
				}
				List<ContenidoRespuestaDTO> listContenido = new ArrayList<>();
				listCategoriasRespuesta = plantillaI.listCategoriaByChecklist(id);

				System.out.println("CATEGORIAS X IDCHECKLIST: " + id);
				System.out.println("CATEGORIAS X IDCHECKLIST: " + listCategoriasRespuesta.size());

				for (Categoria cat : listCategoriasRespuesta) {
					List<ChecklistHasCatalogoPregunta> list = ccpI.findByCategoriaChecklist(cat.getIdCategoria(), id);
					listContenido = new ArrayList<>();
					for (ChecklistHasCatalogoPregunta cp : list) {
						Respuesta re = new Respuesta();

						CatalogoPregunta catp = new CatalogoPregunta();
						catp = cpI.buscarPorId(cat.getIdCategoria(),
								cp.getChecklistHasCatalogoPreguntaPK().getCatalogoPreguntaPreguntaIdPregunta(),
								cp.getChecklistHasCatalogoPreguntaPK().getCatalogoPreguntaPesoIdPeso());

						re.setIdCatalogoPregunta(catp.getIdCatalogoPregunta());

						re.setCumpleRespuesta(true);
						re.setNoProcede(true);
						re.setObservacionRespuesta("");

						re.setPesoRespuesta(Integer.parseInt(catp.getPeso().getPeso()));
						re.setPorcentajeRespuesta(Integer.parseInt(catp.getPeso().getPorcentaje()));
						re.setValorRealRespuesta(Double.parseDouble(catp.getPeso().getValor()));
						re.setValorCalculadoRespuesta(Double.parseDouble(catp.getPeso().getValor()));

						ContenidoRespuestaDTO crdto = new ContenidoRespuestaDTO();
						crdto.setCatalogoPregunta(catp);
						crdto.setChecklist(plantillaI.buscarPorId(id));
						crdto.setRespuesta(re);

						listContenido.add(crdto);
					}
					RespuestaDTO rdto = new RespuestaDTO();
					rdto.setCategoria(cat);
					rdto.setListaContenidoRespuesta(listContenido);
					listaResp.add(rdto);

				}

				utils.mostrarMensaje("FORMULARIO: Agregado Correctamente", "", "I");

			} else {
				utils.mostrarMensaje("ERROR: El Formulario YA se encuentra agregado !", "", "W");
			}

		} catch (Exception e) {
			e.printStackTrace();
			utils.mostrarMensaje("FORMULARIO", "Ha ocurrido un error al AGREGAR el FORMULARIO", "E");
		}

	}

	/**
	 * SETTERS Y GETTERS
	 **/

	public List<EstadoEvaluacion> getListEstadoevaluacion() {
		return listEstadoevaluacion;
	}

	public List<Respuesta> getListaRespuesta() {
		return listaRespuesta;
	}

	public void setListaRespuesta(List<Respuesta> listaRespuesta) {
		this.listaRespuesta = listaRespuesta;
	}

	public List<Categoria> getListCategoriasRespuesta() {
		return listCategoriasRespuesta;
	}

	public void setListCategoriasRespuesta(List<Categoria> listCategoriasRespuesta) {
		this.listCategoriasRespuesta = listCategoriasRespuesta;
	}

	public List<RespuestaDTO> getListaResp() {
		return listaResp;
	}

	public void setListaResp(List<RespuestaDTO> listaResp) {
		this.listaResp = listaResp;
	}

	public List<CatalogoPregunta> getListaCatalogoPregunta() {
		return listaCatalogoPregunta;
	}

	public void setListaCatalogoPregunta(List<CatalogoPregunta> listaCatalogoPregunta) {
		this.listaCatalogoPregunta = listaCatalogoPregunta;
	}

	public List<Categoria> getListCategorias() {
		return listCategorias;
	}

	public void setListCategorias(List<Categoria> listCategorias) {
		this.listCategorias = listCategorias;
	}

	public Long getIdEstadoEvaluacion() {
		return idEstadoEvaluacion;
	}

	public void setIdEstadoEvaluacion(Long idEstadoEvaluacion) {
		this.idEstadoEvaluacion = idEstadoEvaluacion;
	}

	public void setListEstadoevaluacion(List<EstadoEvaluacion> listEstadoevaluacion) {
		this.listEstadoevaluacion = listEstadoevaluacion;
	}

	public List<Cliente> getListCliente() {
		return listCliente;
	}

	public List<EvaluacionHasEncabezado> getListaEvaluacionencabezado() {
		return listaEvaluacionencabezado;
	}

	public void setListaEvaluacionencabezado(List<EvaluacionHasEncabezado> listaEvaluacionencabezado) {
		this.listaEvaluacionencabezado = listaEvaluacionencabezado;
	}

	public List<Encabezado> getListaencabezado() {
		return listaencabezado;
	}

	public void setListaencabezado(List<Encabezado> listaencabezado) {
		this.listaencabezado = listaencabezado;
	}

	public List<Encabezado> getListaencabezadoSelect() {
		return listaencabezadoSelect;
	}

	public void setListaencabezadoSelect(List<Encabezado> listaencabezadoSelect) {
		this.listaencabezadoSelect = listaencabezadoSelect;
	}

	public UploadedFile getFileVideo() {
		return fileVideo;
	}

	public void setFileVideo(UploadedFile fileVideo) {
		this.fileVideo = fileVideo;
	}

	public StreamedContent getStreamVideo() {
		return streamVideo;
	}

	public void setStreamVideo(StreamedContent streamVideo) {
		this.streamVideo = streamVideo;
	}

	public Evaluacion getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(Evaluacion evaluacion) {
		this.evaluacion = evaluacion;
	}

	public Checklist getChecklist() {
		return checklist;
	}

	public void setChecklist(Checklist checklist) {
		this.checklist = checklist;
	}

	public void setListCliente(List<Cliente> listCliente) {
		this.listCliente = listCliente;
	}

	public List<Empresa> getListEmpresa() {
		return listEmpresa;
	}

	public void setListEmpresa(List<Empresa> listEmpresa) {
		this.listEmpresa = listEmpresa;
	}

	public List<Agencia> getListAgencia() {
		return listAgencia;
	}

	public void setListAgencia(List<Agencia> listAgencia) {
		this.listAgencia = listAgencia;
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

	public List<Checklist> getListPlantillas() {
		return listPlantillas;
	}

	public void setListPlantillas(List<Checklist> listPlantillas) {
		this.listPlantillas = listPlantillas;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public Long getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(Long idAgencia) {
		this.idAgencia = idAgencia;
	}

	public Long getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
