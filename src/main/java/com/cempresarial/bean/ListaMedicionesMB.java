/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cempresarial.bean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;

import com.cempresarial.rest.client.service.*;
import com.cempresariales.servicio.commons.model.entity.*;
import org.apache.commons.io.FileUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;
import org.primefaces.model.file.UploadedFile;

import com.cempresarial.entities.DTO.BuscadorDTO;
import com.cempresarial.entities.DTO.ContenidoRespuestaDTO;
import com.cempresarial.entities.DTO.EvaluacionDTO;
import com.cempresarial.entities.DTO.RespuestaDTO;
import com.cempresarial.recursos.SesionController;
import com.cempresarial.recursos.Utilitarios;
import com.cempresariales.servicio.commons.admin.model.entity.Perfil;
import com.cempresariales.servicio.commons.admin.model.entity.PermisoAgencia;
import com.cempresariales.servicio.commons.admin.model.entity.Usuario;

/**
 * @author DIGETBI 05
 */
@SessionScoped
@ManagedBean
public class ListaMedicionesMB extends SesionController implements Serializable {

    /**
     * VARIABLES DE SESION
     **/
    private static final long serialVersionUID = 6771930005130933302L;
    private static final Logger log = Logger.getLogger(ListaMedicionesMB.class.getName());

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
    private EmpleadoService empleadoI;
    @Inject
    private ChecklistService checkListI;
    @Inject
    private ChecklistEvaluacionService cleI;
    @Inject
    private CategoriaService categoriaI;
    @Inject
    private RespuestaService respuestaI;
    @Inject
    private ChecklistCatalogoPreguntaService ccpI;
    @Inject
    private CatalogoPreguntaService cpI;
    @Inject
    private RolService rolI;
    @Inject
    private EncabezadoService encabezadoService;
    @Inject
    private EstadoEvaluacionService estadoService;
    @Inject
    private EvaluacionEncabezadoService eeI;
    @Inject
    private RangoService rangoI;
    @Inject
    private UsuarioService usuarioI;

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
    @Inject
    private PermisoAgenciaService permisoAgenciaI;

    @Inject
    private PerfilService perfilI;

    @Inject
    private EmpleadoHasRolService empHasRolI;

    /**
     * VARIABLES
     **/

    private List<Evaluacion> listaEvaluacion;
    private List<EvaluacionDTO> listaEvaluacionDTO;
    private List<Categoria> listaCategoria;
    private List<Checklist> listaCheckList;
    private List<ChecklistHasEvaluacion> list;
    private List<Respuesta> listaRespuesta;

    private List<RespuestaDTO> listaResp;

    private List<String> listP;

    private EvaluacionDTO evaluacionDTO;
    private Utilitarios utils;

    private int numeroSICumple;
    private int numeroNOCumple;
    private int numeroNOAplica;

    private DonutChartModel donutModel;
    private List<DonutChartModel> listaDonutModel;
    private float puntuacion;

    private StreamedContent fotoCliente;
    private StreamedContent fotoEmpresa;
    private StreamedContent fotoEmpleado;

    private Long idCliente;
    private Long idEmpresa;
    private Long idAgencia;
    private Long idEmpleado;
    private Long idRol;
    private Long idArea;
    private Long idEstadoEvaluacion;

    private List<Cliente> listCliente;
    private List<Empresa> listEmpresa;
    private List<Agencia> listAgencia;
    private List<Empleado> listEmpleado;
    private List<Rol> listRol;
    private List<Area> listArea;
    private List<Checklist> listPlantillas;

    private List<Categoria> listCategoriasRespuesta;
    private List<Categoria> listCategorias;
    private List<Checklist> listCheckList;
    private List<CatalogoPregunta> listaCatalogoPregunta;
    private List<Encabezado> listaencabezado;
    private List<Encabezado> listaencabezadoSelect;
    private List<EvaluacionHasEncabezado> listaEvaluacionencabezado;
    private List<EstadoEvaluacion> listEstadoevaluacion;

    private Evaluacion evaluacion;

    private UploadedFile fileVideo;
    private StreamedContent streamVideo;

    private RangoDesempenio rango;

    private Long perfilSeleccionado;
    /**
     * VARIABLES PARA FILTROS**
     */
    private BuscadorDTO buscadorDTO;
    private List<Categoria> listCategoriaFiltro;
    private List<Usuario> listUsuarios;
    private List<Evaluacion> listaEvaluacionFiltro;

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
    private Long idSector;

    private int indexTabFiltros;

    private Usuario usuario;

    private List<EvaluacionDTO> listaEvaluacionDTOSelect;


    public List<EvaluacionDTO> getListaEvaluacionDTOSelect() {
        return listaEvaluacionDTOSelect;
    }

    public void setListaEvaluacionDTOSelect(List<EvaluacionDTO> listaEvaluacionDTOSelect) {
        this.listaEvaluacionDTOSelect = listaEvaluacionDTOSelect;
    }

    @PostConstruct
    public void init() {
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
            evaluacionDTO = new EvaluacionDTO();
            utils = new Utilitarios();
            listaResp = new ArrayList<>();
            evaluacion = new Evaluacion();
            listP = new ArrayList<>();
            // listaEvaluacion = evaluacionI.listar();
            // list = cleI.listar();
            listCategoriaFiltro = categoriaI.listar();
            rango = new RangoDesempenio();

            listaRegion = regionI.listar();
            listaZona = zonaI.listar();
            listaProvincia = provinciaI.listar();
            listaCiudad = ciudadI.listar();
            listaZonaE = zonaEI.listar();

            listCliente = clienteI.listar();

            //listaEvaluacionDTO = new ArrayList<>();

            quitarFiltros();

            cargarusuario();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String rolPorEmpleado(Empleado emp) {
        try {
            List<RolHasEmpleado> list = new ArrayList<>();
            list = empHasRolI.findRolHasEmpleadoByEmpleado(emp);
            if (list.size() > 0) {
                Rol r = new Rol();
				r = rolI.buscarPorId(list.get(0).getRolHasEmpleadoPK().getRolIdRol());
				return r.getNombreRol();
            } else {
                return "NOT FOUND";
            }


        } catch (Exception e) {
            e.printStackTrace();
            return "NOT FOUND";
        }
    }

    private void cargarusuario() {
        try {
            usuario = getCurrentUser();
            List<Perfil> resultadoBusqueda = perfilI.obtenerPerfilesUsuario(usuario.getNombreUsuario());
            List<PermisoAgencia> list = permisoAgenciaI
                    .findPermisosAgeciaByPerfil(resultadoBusqueda.get(0).getIdPerfil());

            perfilSeleccionado = resultadoBusqueda.get(0).getIdPerfil();

            List<Long> idAgencias = new ArrayList<>();
            for (PermisoAgencia pa : list) {
                idAgencias.add(pa.getIdAgencia());
            }

            listaEvaluacion = evaluacionI.findEvaByAgencias(idAgencias);
            System.out.println("AGENCIAS PARA IN: " + idAgencias);

            System.out.println("TAMAÑO DE BUSQUEDA POR IN: " + listaEvaluacion);

            // listaEvaluacion = evaluacionI.listar();

            // construirDTOMedicion();

            /*
             * List<Evaluacion> listEva = new ArrayList<>(); for (EvaluacionDTO eva :
             * listaEvaluacionDTO) { for (PermisoAgencia pa : list) { if (pa.getIdAgencia()
             * == eva.getAgencia().getIdAgencia()) { listEva.add(eva.getEvaluacion()); } } }
             */

            construirDTOMedicion();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    // tab change para botones con filtros
    public void cleanOntabChange() {
        indexTabFiltros = 0;
    }

    public void onTabChange(TabChangeEvent event) {
        String idTab = event.getTab().getId();

        System.out.println("tab id = " + event.getTab().getId());

        switch (idTab) {
            case "tab0":
                indexTabFiltros = 0;
                listAgencia = new ArrayList<>();
                break;
            case "tab1":
                indexTabFiltros = 1;
                listAgencia = agenciaI.listar();
                System.out.println("SE CARGA LISTAGENCIA TOTAL");
                break;
            case "tab2":
                indexTabFiltros = 2;
                break;

            default:
                break;
        }

    }

    public void aplicarFiltro() {

        try {

            List<Evaluacion> listaFindParams = evaluacionI.findByParams(buscadorDTO);

            listaEvaluacion = listaFindParams;

            construirDTOMedicion();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void quitarFiltrosAvanzados() {
        try {

            if (indexTabFiltros == 0) {
                idCliente = null;
                idEmpresa = null;
                idAgencia = null;
                idEmpleado = null;
            }

            if (indexTabFiltros == 1) {
                idAgencia = null;
                idRol = null;
                idArea = null;

            }
            if (indexTabFiltros == 2) {
                idRegion = null;
                idZona = null;
                idProvincia = null;
                idCiudad = null;
                idZonaE = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void aplicarFiltrosAvanzados() {
        try {

            switch (indexTabFiltros) {
                case 0:
                    aplicarFiltrosClientes();
                    break;
                case 1:
                    aplicarFiltrosRoles();
                    break;
                case 2:
                    aplicarFiltrosSegmentacion();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void aplicarFiltrosRoles() {
        try {

            idCliente = (long) 0;

            idEmpresa = (long) 0;

            idSector = (long) 0;

            if (idAgencia == null) {
                idAgencia = (long) 0;
            }
            if (idEmpleado == null) {
                idEmpleado = (long) 0;
            }
            if (idRol == null) {
                idRol = (long) 0;
            }
            if (idArea == null) {
                idArea = (long) 0;
            }

            List<Evaluacion> listaFindParams = evaluacionI.findByFiltroTabClienteAndRol(idCliente, idEmpresa, idSector,
                    idAgencia, idEmpleado, idRol, idArea);

            listaEvaluacion = listaFindParams;

            construirDTOMedicion();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void aplicarFiltrosClientes() {
        try {

            if (idCliente == null) {
                idCliente = (long) 0;
            }
            if (idEmpresa == null) {
                idEmpresa = (long) 0;
            }
            if (idSector == null) {
                idSector = (long) 0;
            }
            if (idAgencia == null) {
                idAgencia = (long) 0;
            }
            if (idEmpleado == null) {
                idEmpleado = (long) 0;
            }

            idRol = (long) 0;

            idArea = (long) 0;

            List<Evaluacion> listaFindParams = evaluacionI.findByFiltroTabClienteAndRol(idCliente, idEmpresa, idSector,
                    idAgencia, idEmpleado, idRol, idArea);

            listaEvaluacion = listaFindParams;

            construirDTOMedicion();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void aplicarFiltrosSegmentacion() {
        try {

            if (idCiudad == null) {
                idCiudad = (long) 0;
            }
            if (idProvincia == null) {
                idProvincia = (long) 0;

            }
            if (idZonaE == null) {
                idZonaE = (long) 0;

            }
            if (idRegion == null) {
                idRegion = (long) 0;

            }
            if (idZona == null) {
                idZona = (long) 0;

            }

            List<Evaluacion> listaFindParams = evaluacionI.findBySegmentacion(idRegion, idZona, idProvincia, idCiudad,
                    idZonaE);

            listaEvaluacion = listaFindParams;

            construirDTOMedicion();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void quitarFiltros() {
        try {
            buscadorDTO = new BuscadorDTO();
            listCategoriaFiltro = categoriaI.listar();
            listUsuarios = usuarioI.listar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void upload(FileUploadEvent event1) {
        try {
            fileVideo = event1.getFile();
            streamVideo = new DefaultStreamedContent(event1.getFile().getInputStream());

            evaluacion.setVideoEvaluacion(saveFile(evaluacion));
            actualizarEvaluacion();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String saveFile(Evaluacion evaluacion) {

        String pathimages = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        String path = pathimages;

        path += "/resources/videos/" + evaluacion.getIdEvaluacion() + "/video-" + evaluacion.getIdEvaluacion() + ".mp4";
        File targetFile = new File(path);

        try {
            FileUtils.copyInputStreamToFile(fileVideo.getInputStream(), targetFile);
            return targetFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void agregarEncabezadoEvaluacion() {
        for (Encabezado en : listaencabezadoSelect) {
            EvaluacionHasEncabezado eha = new EvaluacionHasEncabezado();
            eha.setActivo(true);
            eha.setEncabezado(en);
            eha.setValorEncabezado("");
            listaEvaluacionencabezado.add(eha);

            EvaluacionHasEncabezadoPK pk = new EvaluacionHasEncabezadoPK();
            pk.setEncabezadoIdEncabezado(en.getIdEncabezado());
            pk.setEvaluacionIdEvaluacion(evaluacion.getIdEvaluacion());
            eha.setEvaluacionHasEncabezadoPK(pk);

            eeI.insertar(eha);
        }
    }

    public void actualizarEncabezadoevaluacion(EvaluacionHasEncabezado entidad) {
        eeI.actualizar(evaluacion.getIdEvaluacion(), entidad.getEncabezado().getIdEncabezado(), entidad);
        utils.mostrarMensaje("Auto Update", "Se ha actualizado el Encabezado", "W");
    }

    public void actualizarEvaluacion() {
        evaluacionI.actualizar(evaluacion.getIdEvaluacion(), evaluacion);
        utils.mostrarMensaje("Auto Update", "Se ha actualizado datos de Evaluación", "W");
    }

    public void actualizarEvaluacionXEmpleado() {
        evaluacion.setIdEmpleado(idEmpleado);
        evaluacionI.actualizar(evaluacion.getIdEvaluacion(), evaluacion);
        utils.mostrarMensaje("Auto Update", "Se ha actualizado el Empleado de la Evaluación", "W");
    }

    public void actualizarEvaluacionXEstado() {
        evaluacion.setEstadoEvaluacionIdEstado(estadoService.buscarPorId(idEstadoEvaluacion));
        evaluacionI.actualizar(evaluacion.getIdEvaluacion(), evaluacion);
        utils.mostrarMensaje("Auto Update", "Se ha actualizado el Estado de la Evaluación", "W");
    }

    public void actualizarRespuestas(ContenidoRespuestaDTO entidad) {
        respuestaI.actualizar(entidad.getRespuesta().getIdRespuesta(), entidad.getRespuesta());
        utils.mostrarMensaje("Auto Update",
                "Se ha actualizado la respuesta: " + entidad.getCatalogoPregunta().getPregunta().getNombrePregunta(),
                "W");
    }

    public void actualizarBloqueRespuestas(RespuestaDTO item, ContenidoRespuestaDTO subItem) {
        recalculosValores(item, subItem);
    }

    public void calcularTiempos() {

        evaluacion.setContactoEvaluacion(
                getDifferenceBetwenDates(evaluacion.getAtencionEvaluacion(), evaluacion.getHoraFinEvaluacion()));
        evaluacion.setEsperaEvaluacion(
                getDifferenceBetwenDates(evaluacion.getHoraInicioEvaluacion(), evaluacion.getAtencionEvaluacion()));

        actualizarEvaluacion();

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

    public void findRolByArea() {
        listRol = rolI.findRolByArea(idArea);
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
        listPlantillas = checkListI.findByRolIdRol(rol);
    }

    public void findByAgenciaIdAgencia() {
        Agencia agencia = agenciaI.buscarPorId(idAgencia);
        listEmpleado = empleadoI.findByAgenciaIdAgencia(agencia);
    }

    public int countPreguntasByChecklist(Long idChecklist) {
        return checkListI.countPreguntasByChecklist(idChecklist);
    }

    public int countCategoriaPreguntasByChecklist(Long idChecklist) {
        return checkListI.countCategoriaByChecklist(idChecklist);
    }

    public void categoriasByCheckList(Long id) {
        listCategorias = checkListI.listCategoriaByChecklist(id);
    }

    public void actualizarListaEncabezados() {
        listaencabezado = encabezadoService.listar();
    }

    public List<CatalogoPregunta> preguntasByCategoria(Long id) {
        List<CatalogoPregunta> list = new ArrayList<>();
        for (CatalogoPregunta obj : listaCatalogoPregunta)
            if (obj.getCatalogoPreguntaPK().getCategoriaIdCategoria() == id)
                list.add(obj);

        return list;
    }

    public void agregarCheckListRespuesta(Long id) {

        List<ChecklistHasEvaluacion> listCheckListEva = new ArrayList<>();
        listCheckListEva = cleI.findByCheckListEvaluacion(id, evaluacion.getIdEvaluacion());

        log.info("TAMAÑO DE LISTA X CKECKLIST Y EVALUACION: " + listCheckListEva.size());

        if (listCheckListEva.size() == 0) {

            boolean match = false;
            if (listCheckList.size() == 0)
                listCheckList.add(checkListI.buscarPorId(id));
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
            listCategoriasRespuesta = checkListI.listCategoriaByChecklist(id);
            for (Categoria cat : listCategoriasRespuesta) {
                List<ChecklistHasCatalogoPregunta> list = ccpI.findByCategoria(cat.getIdCategoria());
                listContenido = new ArrayList<>();
                for (ChecklistHasCatalogoPregunta cp : list) {
                    Respuesta re = new Respuesta();

                    CatalogoPregunta catp = new CatalogoPregunta();
                    catp = cpI.buscarPorId(cat.getIdCategoria(),
                            cp.getChecklistHasCatalogoPreguntaPK().getCatalogoPreguntaPreguntaIdPregunta(),
                            cp.getChecklistHasCatalogoPreguntaPK().getCatalogoPreguntaPesoIdPeso());

                    re.setIdCatalogoPregunta(catp.getIdCatalogoPregunta());

                    re.setCumpleRespuesta(false);
                    re.setNoProcede(true);
                    re.setObservacionRespuesta("");

                    re.setPesoRespuesta(Integer.parseInt(catp.getPeso().getPeso()));
                    re.setPorcentajeRespuesta(Integer.parseInt(catp.getPeso().getPorcentaje()));
                    re.setValorRealRespuesta(Double.parseDouble(catp.getPeso().getValor()));
                    re.setValorCalculadoRespuesta(Double.parseDouble(catp.getPeso().getValor()));

                    ContenidoRespuestaDTO crdto = new ContenidoRespuestaDTO();
                    crdto.setCatalogoPregunta(catp);
                    crdto.setChecklist(checkListI.buscarPorId(id));
                    crdto.setRespuesta(re);

                    listContenido.add(crdto);
                }
                RespuestaDTO rdto = new RespuestaDTO();
                rdto.setCategoria(cat);
                rdto.setListaContenidoRespuesta(listContenido);
                listaResp.add(rdto);

            }

            ingresarCheckListRespuesta();

            utils.mostrarMensaje("FORMULARIO: Agregado Correctamente", "", "I");

        } else {
            utils.mostrarMensaje("ERROR: El Formulario YA se encuentra agregado !", "", "W");
        }

    }

    public void ingresarCheckListRespuesta() {
        // ingresar chechkist has evaluacion
        for (Checklist cl : listCheckList) {
            ChecklistHasEvaluacion clhe = new ChecklistHasEvaluacion();
            ChecklistHasEvaluacionPK pk = new ChecklistHasEvaluacionPK();
            pk.setChecklistIdChecklist(cl.getIdChecklist());
            pk.setEvaluacionIdEvaluacion(evaluacion.getIdEvaluacion());

            clhe.setActivo(true);
            // clhe.setChecklist(cl);
            // clhe.setEvaluacion(eva);
            clhe.setChecklistHasEvaluacionPK(pk);
            cleI.insertar(clhe);
        }

        // ingresa respuestas
        Respuesta respuesta = new Respuesta();
        for (RespuestaDTO dto : listaResp) {
            for (ContenidoRespuestaDTO cdto : dto.getListaContenidoRespuesta()) {
                respuesta = new Respuesta();
                respuesta.setIdCatalogoPregunta(cdto.getCatalogoPregunta().getIdCatalogoPregunta());
                respuesta.setChecklistHasEvaluacion(
                        new ChecklistHasEvaluacion(cdto.getChecklist().getIdChecklist(), evaluacion.getIdEvaluacion()));
                respuesta.setCumpleRespuesta(cdto.getRespuesta().getCumpleRespuesta());
                respuesta.setNoProcede(cdto.getRespuesta().getNoProcede());
                respuesta.setObservacionRespuesta(cdto.getRespuesta().getObservacionRespuesta());

                respuesta.setPesoRespuesta(cdto.getRespuesta().getPesoRespuesta());
                respuesta.setPorcentajeRespuesta(cdto.getRespuesta().getPorcentajeRespuesta());
                respuesta.setValorRealRespuesta(cdto.getRespuesta().getValorRealRespuesta());
                respuesta.setValorCalculadoRespuesta(cdto.getRespuesta().getValorCalculadoRespuesta());

                respuestaI.insertar(respuesta);

            }
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

    public void resetearCalculos(RespuestaDTO item) {
        int index = listaResp.indexOf(item);
        for (int x = 0; x < item.getListaContenidoRespuesta().size(); x++) {
            ContenidoRespuestaDTO cdto = listaResp.get(index).getListaContenidoRespuesta().get(x);
            cdto.getRespuesta().setValorCalculadoRespuesta(cdto.getRespuesta().getValorRealRespuesta());
            // actualiza registro
            respuestaI.actualizar(cdto.getRespuesta().getIdRespuesta(), cdto.getRespuesta());
            listaResp.get(index).getListaContenidoRespuesta().set(x, cdto);
        }
    }

    public void reCalcular(RespuestaDTO item) {

        int cantidadNO = 0;
        int cantidadSI = 0;
        double valorDistribuye = 0;

        for (ContenidoRespuestaDTO obj : item.getListaContenidoRespuesta()) {
            if (obj.getRespuesta().getNoProcede()) {
                cantidadSI++;
            } else {
                cantidadNO++;
                valorDistribuye = valorDistribuye + obj.getRespuesta().getValorRealRespuesta();
            }
        }

        if (cantidadSI > 0)
            valorDistribuye = valorDistribuye / cantidadSI;

        int index = listaResp.indexOf(item);
        for (int x = 0; x < item.getListaContenidoRespuesta().size(); x++) {
            ContenidoRespuestaDTO cdto = listaResp.get(index).getListaContenidoRespuesta().get(x);
            if (cdto.getRespuesta().getNoProcede()) {
                cdto.getRespuesta()
                        .setValorCalculadoRespuesta(cdto.getRespuesta().getValorRealRespuesta() + valorDistribuye);
                // actualiza registro
                respuestaI.actualizar(cdto.getRespuesta().getIdRespuesta(), cdto.getRespuesta());
                listaResp.get(index).getListaContenidoRespuesta().set(x, cdto);
            }

        }

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

                        // actualiza registro
                        respuestaI.actualizar(cdto.getRespuesta().getIdRespuesta(), cdto.getRespuesta());
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

                        // actualiza registro
                        respuestaI.actualizar(cdto.getRespuesta().getIdRespuesta(), cdto.getRespuesta());
                    }
                    listaResp.get(index).getListaContenidoRespuesta().set(y, cdto);

                }

                System.out.println("VALOR 2 POR DEFECTO" + valor);

                int indice2 = listaResp.get(index).getListaContenidoRespuesta().indexOf(subItem);
                ContenidoRespuestaDTO cdto = listaResp.get(index).getListaContenidoRespuesta().get(indice2);
                cdto.getRespuesta().setValorCalculadoRespuesta(valor);
                listaResp.get(index).getListaContenidoRespuesta().set(indice2, cdto);

                // actualiza registro

                respuestaI.actualizar(cdto.getRespuesta().getIdRespuesta(), cdto.getRespuesta());

            }

            respuestaI.actualizar(subItem.getRespuesta().getIdRespuesta(), subItem.getRespuesta());
        } catch (

                Exception e) {
            e.printStackTrace();
        }
    }

    public void construirDTOMedicion() {
        listaEvaluacionDTO = new ArrayList<>();
        for (Evaluacion eva : listaEvaluacion) {
            EvaluacionDTO evadto = new EvaluacionDTO();

            evadto.setEmpleado(empleadoI.buscarPorId(eva.getIdEmpleado()));
            evadto.setEvaluacion(eva);
            listaEvaluacionDTO.add(evadto);
        }

    }

    public void cargarRoles() {
        try {

            List<Rol> listRol = new ArrayList<>();
            listaCheckList = new ArrayList<>();

            List<Long> idEvaluaciones = new ArrayList<>();
            for (Evaluacion eva : listaEvaluacion) {
                idEvaluaciones.add(eva.getIdEvaluacion());
            }

            list = cleI.findCheckListEvaluacionByEvaluaciones(idEvaluaciones);


            System.out.println("DATOS:" + evaluacionDTO.getEvaluacion()
                    .getCreaEvaluacion());
            System.out.println("DATOS:" + evaluacionDTO.getEvaluacion()
                    .getIdEvaluacion());


            for (ChecklistHasEvaluacion ce : list) {
                if (ce.getChecklistHasEvaluacionPK().getEvaluacionIdEvaluacion() == evaluacionDTO.getEvaluacion()
                        .getIdEvaluacion()) {
                    Checklist cl = checkListI.buscarPorId(ce.getChecklistHasEvaluacionPK().getChecklistIdChecklist());
                    listaCheckList.add(cl);
                    listRol.add(cl.getRolIdRol());
                }
            }

            listP = new ArrayList<>();
            if (!listRol.isEmpty()) {
                for (int x = 0; x < listRol.size(); x++) {
                    listP.add(listRol.get(x).getNombreRol());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarCategorias() {
        listaCategoria = new ArrayList<>();
        listaRespuesta = new ArrayList<>();
        List<Respuesta> list = respuestaI.listar();
        for (Checklist cl : listaCheckList) {
            listaCategoria.addAll(checkListI.listCategoriaByChecklist(cl.getIdChecklist()));

            listaRespuesta.addAll(respuestaI.findByIdEvaluacionIdChecklist(
                    evaluacionDTO.getEvaluacion().getIdEvaluacion(), cl.getIdChecklist()));

            System.out.println("VALORES EN FOR: " + evaluacionDTO.getEvaluacion().getIdEvaluacion() + "********"
                    + cl.getIdChecklist());
            System.out.println("VALORES EN FOR LISTA RESPUESTA: " + listaRespuesta.size());

            /*
             * for (Respuesta r : list) { if
             * (r.getChecklistHasEvaluacion().getChecklistHasEvaluacionPK().
             * getChecklistIdChecklist() == cl .getIdChecklist() &&
             * r.getChecklistHasEvaluacion().getChecklistHasEvaluacionPK()
             * .getEvaluacionIdEvaluacion() ==
             * evaluacionDTO.getEvaluacion().getIdEvaluacion()) listaRespuesta.add(r); }
             */

        }

        for (Checklist cl : listaCheckList) {
            formarRespuestaExtendida(cl.getIdChecklist());
        }

        numeroNOAplica = 0;
        numeroNOCumple = 0;
        numeroSICumple = 0;
        puntuacion = 0;
        for (Respuesta re : listaRespuesta) {
            if (re.getCumpleRespuesta()) {
                numeroSICumple++;
                String valor = Double.toString(re.getValorCalculadoRespuesta());
                puntuacion = puntuacion + Float.parseFloat(valor);
            } else {
                numeroNOCumple++;
            }

            if (!re.getNoProcede())
                numeroNOAplica++;
        }

        BigDecimal formatNumber = new BigDecimal(puntuacion);
        formatNumber = formatNumber.setScale(2, RoundingMode.HALF_UP);
        puntuacion = formatNumber.floatValue();

        buscarRangoDesempenio(puntuacion);
    }

    public void buscarRangoDesempenio(float valor) {

        rango = rangoI.findByRangoAndEmpresa(valor,
                evaluacionDTO.getEmpleado().getAgenciaIdAgencia().getEmpresaIdEmpresa().getIdEmpresa());

        if (rango == null)
            rango = new RangoDesempenio();

    }

    public DonutChartModel crearDonuts(RespuestaDTO dto) {

        int totales = 0;
        int valorSI = 0;
        int valorNO = 0;
        int valorNOAPLICA = 0;
        float valor = 0;

        totales = dto.getListaContenidoRespuesta().size();
        valor = 0;
        valorSI = 0;
        valorNO = 0;
        valorNOAPLICA = 0;
        for (ContenidoRespuestaDTO cdto : dto.getListaContenidoRespuesta()) {
            if (cdto.getRespuesta().getCumpleRespuesta())
                valorSI++;
            else
                valorNO++;

            if (!cdto.getRespuesta().getNoProcede())
                valorNOAPLICA++;

            valor = (valorSI * 100) / totales;

            BigDecimal formatNumber = new BigDecimal(valor);
            formatNumber = formatNumber.setScale(2, RoundingMode.HALF_UP);

            valor = formatNumber.floatValue();

        }

        return createDonutModel(valor, valor + "%");

    }

    public DonutChartModel createDonutModel(float valor, String nombre) {
        donutModel = new DonutChartModel();
        ChartData data = new ChartData();

        DonutChartDataSet dataSet = new DonutChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(valor);
        values.add(100 - valor);
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        if (valor > 0 && valor <= 30)
            bgColors.add("rgb(230, 0, 0)");
        if (valor > 30 && valor <= 60)
            bgColors.add("rgb(255, 204, 102)");
        if (valor > 60)
            bgColors.add("rgb(51, 204, 51)");

        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add(nombre);
        labels.add("");
        data.setLabels(labels);

        donutModel.setData(data);

        return donutModel;
    }

    public void formarRespuestaExtendida(Long id) {

        listaResp = new ArrayList<>();

        List<ContenidoRespuestaDTO> listContenido = new ArrayList<>();
        for (Categoria cat : listaCategoria) {
            listContenido = new ArrayList<>();
            for (Respuesta re : listaRespuesta) {

                System.out.println("CATALOGO PREGUNTA XQ ENTRA AKII PEEEEE: " + re.getIdCatalogoPregunta());

                CatalogoPregunta cp = cpI.findByIdCatalogoPregunta(re.getIdCatalogoPregunta());

                if (cp.getCatalogoPreguntaPK().getCategoriaIdCategoria() == cat.getIdCategoria()) {

                    System.out.println("CATEGORIAS IGUALES");

                    ContenidoRespuestaDTO crdto = new ContenidoRespuestaDTO();
                    crdto.setCatalogoPregunta(cp);
                    crdto.setChecklist(checkListI.buscarPorId(id));
                    crdto.setRespuesta(re);

                    listContenido.add(crdto);
                }
            }
            RespuestaDTO rdto = new RespuestaDTO();

            rdto.setCategoria(cat);

            rdto.setListaContenidoRespuesta(listContenido);
            listaResp.add(rdto);

        }

    }

    public void eliminarMedicion(EvaluacionDTO entidad) {
        evaluacionI.eliminar(entidad.getEvaluacion().getIdEvaluacion());
        utils.mostrarMensaje("Eliminación", "exitosa", "F");
    }

    String pathCliente = "";
    String pathEmpresa = "";
    String pathEmpleado = "";

    public String verDetalleMedicion(EvaluacionDTO entidad) {
        evaluacionDTO = entidad;
        cargarRoles();
        cargarCategorias();

        /*
         * fotoCliente = utils.getImage(entidad.getEmpleado().getAgenciaIdAgencia().
         * getEmpresaIdEmpresa() .getClienteIdCliente().getFotoCliente()); fotoEmpresa =
         * utils.getImage(entidad.getEmpleado().getAgenciaIdAgencia().
         * getEmpresaIdEmpresa().getImagenEmpresa()); fotoEmpleado =
         * utils.getImage(entidad.getEmpleado().getFotoEmpleado());
         */

        pathCliente = entidad.getEmpleado().getAgenciaIdAgencia().getEmpresaIdEmpresa().getClienteIdCliente()
                .getFotoCliente();
        pathEmpresa = entidad.getEmpleado().getAgenciaIdAgencia().getEmpresaIdEmpresa().getImagenEmpresa();
        pathEmpleado = entidad.getEmpleado().getFotoEmpleado();

        return utils.navegaPagina("medicion");
    }

    public StreamedContent getImageCliente() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
                return new DefaultStreamedContent();
            } else {
                // Long idCliente =
                // Long.parseLong(context.getExternalContext().getRequestParameterMap().get("clienteId"));
                // Cliente obj = clienteService.buscarPorId(idCliente);

                return utils.getImage(pathCliente);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public StreamedContent getImageEmpresa() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
                return new DefaultStreamedContent();
            } else {
                // Long idCliente =
                // Long.parseLong(context.getExternalContext().getRequestParameterMap().get("clienteId"));
                // Cliente obj = clienteService.buscarPorId(idCliente);

                return utils.getImage(pathEmpresa);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public StreamedContent getImageEmpleado() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
                return new DefaultStreamedContent();
            } else {
                // Long idCliente =
                // Long.parseLong(context.getExternalContext().getRequestParameterMap().get("clienteId"));
                // Cliente obj = clienteService.buscarPorId(idCliente);

                return utils.getImage(pathEmpleado);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String verActualizarMedicion(EvaluacionDTO entidad) {
        evaluacionDTO = entidad;
        evaluacion = entidad.getEvaluacion();

        idCliente = evaluacionDTO.getEmpleado().getAgenciaIdAgencia().getEmpresaIdEmpresa().getClienteIdCliente()
                .getIdCliente();
        idEmpresa = evaluacionDTO.getEmpleado().getAgenciaIdAgencia().getEmpresaIdEmpresa().getIdEmpresa();
        idAgencia = evaluacionDTO.getEmpleado().getAgenciaIdAgencia().getIdAgencia();
        idEmpleado = evaluacionDTO.getEmpleado().getIdEmpleado();
        idEstadoEvaluacion = evaluacion.getEstadoEvaluacionIdEstado().getIdEstado();

        listCliente = clienteI.listar();
        findByClienteIdCliente();
        findByAgenciaIdAgencia();
        findByEmpresaIdEmpresa();
        listEstadoevaluacion = estadoService.listar();
        listaencabezado = encabezadoService.listar();
        listRol = rolI.listar();
        listaCatalogoPregunta = cpI.listar();

        listaEvaluacionencabezado = new ArrayList<>();
        listCheckList = new ArrayList<>();

        listaEvaluacionencabezado = eeI.findByEvaluacion(evaluacion.getIdEvaluacion());
        for (int x = 0; x < listaEvaluacionencabezado.size(); x++) {
            Long id = listaEvaluacionencabezado.get(x).getEvaluacionHasEncabezadoPK().getEncabezadoIdEncabezado();
            Encabezado encabezado = encabezadoService.buscarPorId(id);
            listaEvaluacionencabezado.get(x).setEncabezado(encabezado);
        }

        cargarRoles();
        cargarCategorias();

        return utils.navegaPagina("edicionMedicion");
    }

    public void elimminarEncabezadoEvaluacion(EvaluacionHasEncabezado entidad) {
        eeI.eliminar(evaluacion.getIdEvaluacion(), entidad.getEncabezado().getIdEncabezado());
        listaEvaluacionencabezado.remove(entidad);
    }

    public String goBackListaMediciones() {
        return utils.navegaPagina("listamediciones");
    }

    public void actualizarLista() {

        if (perfilSeleccionado == 1) {
            listaEvaluacion = evaluacionI.listar();
        } else {
            cargarusuario();
        }
        construirDTOMedicion();

    }

    public StreamedContent obtenerFoto(String path) {
        try {

            DefaultStreamedContent image = null;
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream ous = null;
            InputStream ios = null;

            byte[] buffer = new byte[4096];
            ous = new ByteArrayOutputStream();
            ios = new FileInputStream(file);
            int read = 0;
            while ((read = ios.read(buffer)) != -1) {
                ous.write(buffer, 0, read);
            }

            image = new DefaultStreamedContent(new ByteArrayInputStream(ous.toByteArray()), "image/png");
            return image;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * SETTERS Y GETTERS
     **/

    public float getPuntuacion() {
        return puntuacion;
    }

    public List<EstadoEvaluacion> getListEstadoevaluacion() {
        return listEstadoevaluacion;
    }

    public void setListEstadoevaluacion(List<EstadoEvaluacion> listEstadoevaluacion) {
        this.listEstadoevaluacion = listEstadoevaluacion;
    }

    public List<EvaluacionHasEncabezado> getListaEvaluacionencabezado() {
        return listaEvaluacionencabezado;
    }

    public void setListaEvaluacionencabezado(List<EvaluacionHasEncabezado> listaEvaluacionencabezado) {
        this.listaEvaluacionencabezado = listaEvaluacionencabezado;
    }

    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }

    public List<CatalogoPregunta> getListaCatalogoPregunta() {
        return listaCatalogoPregunta;
    }

    public void setListaCatalogoPregunta(List<CatalogoPregunta> listaCatalogoPregunta) {
        this.listaCatalogoPregunta = listaCatalogoPregunta;
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

    public List<Categoria> getListCategoriasRespuesta() {
        return listCategoriasRespuesta;
    }

    public void setListCategoriasRespuesta(List<Categoria> listCategoriasRespuesta) {
        this.listCategoriasRespuesta = listCategoriasRespuesta;
    }

    public List<Categoria> getListCategorias() {
        return listCategorias;
    }

    public void setListCategorias(List<Categoria> listCategorias) {
        this.listCategorias = listCategorias;
    }

    public List<Checklist> getListCheckList() {
        return listCheckList;
    }

    public void setListCheckList(List<Checklist> listCheckList) {
        this.listCheckList = listCheckList;
    }

    public List<Cliente> getListCliente() {
        return listCliente;
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

    public void setPuntuacion(float puntuacion) {
        this.puntuacion = puntuacion;
    }

    public List<DonutChartModel> getListaDonutModel() {
        return listaDonutModel;
    }

    public void setListaDonutModel(List<DonutChartModel> listaDonutModel) {
        this.listaDonutModel = listaDonutModel;
    }

    public int getNumeroSICumple() {
        return numeroSICumple;
    }

    public void setNumeroSICumple(int numeroSICumple) {
        this.numeroSICumple = numeroSICumple;
    }

    public int getNumeroNOCumple() {
        return numeroNOCumple;
    }

    public void setNumeroNOCumple(int numeroNOCumple) {
        this.numeroNOCumple = numeroNOCumple;
    }

    public int getNumeroNOAplica() {
        return numeroNOAplica;
    }

    public void setNumeroNOAplica(int numeroNOAplica) {
        this.numeroNOAplica = numeroNOAplica;
    }

    public List<Categoria> getListaCategoria() {
        return listaCategoria;
    }

    public void setListaCategoria(List<Categoria> listaCategoria) {
        this.listaCategoria = listaCategoria;
    }

    public List<Checklist> getListaCheckList() {
        return listaCheckList;
    }

    public void setListaCheckList(List<Checklist> listaCheckList) {
        this.listaCheckList = listaCheckList;
    }

    public List<Respuesta> getListaRespuesta() {
        return listaRespuesta;
    }

    public void setListaRespuesta(List<Respuesta> listaRespuesta) {
        this.listaRespuesta = listaRespuesta;
    }

    public List<RespuestaDTO> getListaResp() {
        return listaResp;
    }

    public void setListaResp(List<RespuestaDTO> listaResp) {
        this.listaResp = listaResp;
    }

    public List<Evaluacion> getListaEvaluacion() {
        return listaEvaluacion;
    }

    public List<String> getListP() {
        return listP;
    }

    public void setListP(List<String> listP) {
        this.listP = listP;
    }

    public List<ChecklistHasEvaluacion> getList() {
        return list;
    }

    public void setList(List<ChecklistHasEvaluacion> list) {
        this.list = list;
    }

    public StreamedContent getFotoCliente() {
        return fotoCliente;
    }

    public void setFotoCliente(StreamedContent fotoCliente) {
        this.fotoCliente = fotoCliente;
    }

    public StreamedContent getFotoEmpresa() {
        return fotoEmpresa;
    }

    public void setFotoEmpresa(StreamedContent fotoEmpresa) {
        this.fotoEmpresa = fotoEmpresa;
    }

    public StreamedContent getFotoEmpleado() {
        return fotoEmpleado;
    }

    public void setFotoEmpleado(StreamedContent fotoEmpleado) {
        this.fotoEmpleado = fotoEmpleado;
    }

    public EvaluacionDTO getEvaluacionDTO() {
        return evaluacionDTO;
    }

    public void setEvaluacionDTO(EvaluacionDTO evaluacionDTO) {
        this.evaluacionDTO = evaluacionDTO;
    }

    public List<EvaluacionDTO> getListaEvaluacionDTO() {
        return listaEvaluacionDTO;
    }

    public void setListaEvaluacionDTO(List<EvaluacionDTO> listaEvaluacionDTO) {
        this.listaEvaluacionDTO = listaEvaluacionDTO;
    }

    public void setListaEvaluacion(List<Evaluacion> listaEvaluacion) {
        this.listaEvaluacion = listaEvaluacion;
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

    public Long getIdEstadoEvaluacion() {
        return idEstadoEvaluacion;
    }

    public void setIdEstadoEvaluacion(Long idEstadoEvaluacion) {
        this.idEstadoEvaluacion = idEstadoEvaluacion;
    }

    public RangoDesempenio getRango() {
        return rango;
    }

    public void setRango(RangoDesempenio rango) {
        this.rango = rango;
    }

    public List<Categoria> getListCategoriaFiltro() {
        return listCategoriaFiltro;
    }

    public void setListCategoriaFiltro(List<Categoria> listCategoriaFiltro) {
        this.listCategoriaFiltro = listCategoriaFiltro;
    }

    public List<Usuario> getListUsuarios() {
        return listUsuarios;
    }

    public void setListUsuarios(List<Usuario> listUsuarios) {
        this.listUsuarios = listUsuarios;
    }

    public BuscadorDTO getBuscadorDTO() {
        return buscadorDTO;
    }

    public void setBuscadorDTO(BuscadorDTO buscadorDTO) {
        this.buscadorDTO = buscadorDTO;
    }

    public List<Evaluacion> getListaEvaluacionFiltro() {
        return listaEvaluacionFiltro;
    }

    public void setListaEvaluacionFiltro(List<Evaluacion> listaEvaluacionFiltro) {
        this.listaEvaluacionFiltro = listaEvaluacionFiltro;
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

    public int getIndexTabFiltros() {
        return indexTabFiltros;
    }

    public void setIndexTabFiltros(int indexTabFiltros) {
        this.indexTabFiltros = indexTabFiltros;
    }

    public Long getIdArea() {
        return idArea;
    }

    public void setIdArea(Long idArea) {
        this.idArea = idArea;
    }

    public List<Area> getListArea() {
        return listArea;
    }

    public void setListArea(List<Area> listArea) {
        this.listArea = listArea;
    }

    public Long getPerfilSeleccionado() {
        return perfilSeleccionado;
    }

    public void setPerfilSeleccionado(Long perfilSeleccionado) {
        this.perfilSeleccionado = perfilSeleccionado;
    }

}
