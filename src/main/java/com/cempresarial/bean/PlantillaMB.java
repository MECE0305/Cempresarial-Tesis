/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cempresarial.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.cempresarial.entities.DTO.CheckListDTO;
import com.cempresarial.recursos.Utilitarios;
import com.cempresarial.rest.client.service.AgenciaService;
import com.cempresarial.rest.client.service.CatalogoPreguntaService;
import com.cempresarial.rest.client.service.CategoriaService;
import com.cempresarial.rest.client.service.ChecklistCatalogoPreguntaService;
import com.cempresarial.rest.client.service.ChecklistService;
import com.cempresarial.rest.client.service.ClienteService;
import com.cempresarial.rest.client.service.EmpleadoService;
import com.cempresarial.rest.client.service.EmpresaService;
import com.cempresarial.rest.client.service.PesoService;
import com.cempresarial.rest.client.service.PreguntaService;
import com.cempresarial.rest.client.service.RolService;
import com.cempresariales.servicio.commons.model.entity.Agencia;
import com.cempresariales.servicio.commons.model.entity.CatalogoPregunta;
import com.cempresariales.servicio.commons.model.entity.CatalogoPreguntaPK;
import com.cempresariales.servicio.commons.model.entity.Categoria;
import com.cempresariales.servicio.commons.model.entity.Checklist;
import com.cempresariales.servicio.commons.model.entity.ChecklistHasCatalogoPregunta;
import com.cempresariales.servicio.commons.model.entity.ChecklistHasCatalogoPreguntaPK;
import com.cempresariales.servicio.commons.model.entity.Cliente;
import com.cempresariales.servicio.commons.model.entity.Empleado;
import com.cempresariales.servicio.commons.model.entity.Empresa;
import com.cempresariales.servicio.commons.model.entity.Peso;
import com.cempresariales.servicio.commons.model.entity.Pregunta;
import com.cempresariales.servicio.commons.model.entity.Rol;

/**
 *
 * @author DIGETBI 05
 */
@SessionScoped
@ManagedBean
public class PlantillaMB implements Serializable {

	/**
	 * VARIABLES DE SESION
	 *
	 */
	private static final long serialVersionUID = 6771930005130933302L;
	private static final Logger log = Logger.getLogger(PlantillaMB.class.getName());

	/**
	 * Servicio
	 *
	 */
	@Inject
	private CategoriaService categoriaI;
	@Inject
	private PreguntaService preguntaI;
	@Inject
	private PesoService pesoI;
	@Inject
	private CatalogoPreguntaService cpI;
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
	private ChecklistCatalogoPreguntaService clcpI;

	/**
	 * VARIABLES
	 *
	 */
	private List<Categoria> listCategorias;
	private List<Pregunta> listaPregunta;
	private List<Pregunta> listaPreguntaSeleccionada;
	private List<Peso> listaPeso;
	private List<CatalogoPregunta> listaCatalogoPregunta;
	private List<CheckListDTO> listaCatalogoPreguntaSelect;

	private CatalogoPregunta catalogoPregunta;
	private Categoria categoria;
	private Pregunta pregunta;
	private Peso peso;
	private Checklist checklist;

	private Long idCategoria;
	private Long idPregunta;
	private Long idPeso;

	private List<Cliente> listCliente;
	private List<Empresa> listEmpresa;
	private List<Agencia> listAgencia;
	private List<Empleado> listEmpleado;
	private List<Rol> listRol;
	private List<Checklist> listPlantillas;

	private List<ChecklistHasCatalogoPregunta> listChecklist;

	private int idCliente;
	private int idEmpresa;
	private int idAgencia;
	private int idEmpleado;
	private Long idRol;

	private Utilitarios utils;

	@PostConstruct
	public void init() {
		try {
			this.clear();
		} catch (Exception e) {
			log.info("Error en init. " + e);
		}
	}

	/**
	 * METODOS
	 *
	 */
	public void clear() {
		try {
			listCategorias = categoriaI.listar();
			listaPregunta = preguntaI.listar();
			listaPeso = pesoI.listar();
			listaCatalogoPregunta = cpI.listar();

			listCliente = clienteI.listar();
			listEmpresa = new ArrayList<>();
			listAgencia = new ArrayList<>();
			listRol = rolI.listar();
			listEmpleado = new ArrayList<>();
			listaPreguntaSeleccionada = new ArrayList<>();

			listaCatalogoPregunta = cpI.listar();

			listaCatalogoPreguntaSelect = new ArrayList<>();
			listChecklist = clcpI.listar();

			actualizarCheckLists();
			actualizarPlantillas();
			checklist = new Checklist();

			catalogoPregunta = new CatalogoPregunta();
			categoria = new Categoria();
			pregunta = new Pregunta();
			peso = new Peso();

			idCategoria = new Long(0);
			idPregunta = new Long(0);
			idPeso = new Long(0);

			utils = new Utilitarios();

		} catch (Exception e) {
			log.info("Error en init. " + e);
		}
	}

	public void actualizarPlantillas() {
		listPlantillas = plantillaI.listar();
	}

	public void pasarCheckList(Long entidad) {
		checklist = plantillaI.buscarPorId(entidad);
	}

	public void pasarDatosChecklist(Checklist entidad) {
		checklist = entidad;
		idRol = checklist.getRolIdRol().getIdRol();

		listaCatalogoPreguntaSelect = new ArrayList<>();

		List<Categoria> listC = plantillaI.listCategoriaByChecklist(entidad.getIdChecklist());

		List<ChecklistHasCatalogoPregunta> list = clcpI.findByChecklistID(entidad.getIdChecklist());

		for (Categoria cat : listC) {
			for (ChecklistHasCatalogoPregunta obj : list) {
				if (cat.getIdCategoria() == obj.getChecklistHasCatalogoPreguntaPK()
						.getCatalogoPreguntaCategoriaIdCategoria()) {
					CheckListDTO cl = new CheckListDTO();

					cl.setIdCategoria(cat.getIdCategoria());
					cl.setIdPregunta(obj.getChecklistHasCatalogoPreguntaPK().getCatalogoPreguntaPreguntaIdPregunta());
					cl.setIdPeso(obj.getChecklistHasCatalogoPreguntaPK().getCatalogoPreguntaPesoIdPeso());
					cl.setIdCategoria(cat.getIdCategoria());
					cl.setNombreCategoria(cat.getNombreCategoria());
					cl.setOrden(obj.getOrdenBloque());
					listaCatalogoPreguntaSelect.add(cl);
					break;
				}
			}
		}

	}

	public List<ChecklistHasCatalogoPregunta> detalleCheckList(Checklist entidad) {

		List<ChecklistHasCatalogoPregunta> listC = clcpI.findByChecklistID(entidad.getIdChecklist());
		for (int x = 0; x < listC.size(); x++) {
			Long idCategoria = listC.get(x).getChecklistHasCatalogoPreguntaPK()
					.getCatalogoPreguntaCategoriaIdCategoria();
			Long idPeso = listC.get(x).getChecklistHasCatalogoPreguntaPK().getCatalogoPreguntaPesoIdPeso();
			Long idPregunta = listC.get(x).getChecklistHasCatalogoPreguntaPK().getCatalogoPreguntaPreguntaIdPregunta();
			CatalogoPregunta cp = cpI.buscarPorId(idCategoria, idPregunta, idPeso);

			listC.get(x).setCatalogoPregunta(cp);

		}

		return listC;
	}

	public void verificarCheckList() {
		idRol = checklist.getRolIdRol().getIdRol();
		utils.mostrarMensaje("CHECKLIST",
				"Se ha seleccionado el Checklist " + checklist.getNombreChecklist() + " para USO", "W");
	}

	public void actualizarCheckLists() {
		listChecklist = clcpI.listar();

		for (int x = 0; x < listChecklist.size(); x++) {
			ChecklistHasCatalogoPregunta ob = new ChecklistHasCatalogoPregunta();
			ob = listChecklist.get(x);
			for (CatalogoPregunta obj : listaCatalogoPregunta) {
				if (obj.getCatalogoPreguntaPK().getCategoriaIdCategoria() == ob.getChecklistHasCatalogoPreguntaPK()
						.getCatalogoPreguntaCategoriaIdCategoria()
						&& obj.getCatalogoPreguntaPK().getPesoIdPeso() == ob.getChecklistHasCatalogoPreguntaPK()
								.getCatalogoPreguntaPesoIdPeso()
						&& obj.getCatalogoPreguntaPK().getPreguntaIdPregunta() == ob.getChecklistHasCatalogoPreguntaPK()
								.getCatalogoPreguntaPreguntaIdPregunta()) {
					listChecklist.get(x).setCatalogoPregunta(obj);
				}
			}

		}

	}

	public void guardarChecklist() {

		Checklist cl = new Checklist();
		if (checklist.getIdChecklist() == null) {

			checklist.setRolIdRol(rolI.buscarPorId(idRol));
			cl = plantillaI.insertar(checklist);
		} else {
			cl = checklist;
		}

		List<CatalogoPregunta> list = new ArrayList<>();
		for (CheckListDTO obj : listaCatalogoPreguntaSelect) {
			list = new ArrayList<>();
			list.addAll(preguntasByCategoria(obj.getIdCategoria()));
			for (CatalogoPregunta cp : list) {

				ChecklistHasCatalogoPregunta ccp = new ChecklistHasCatalogoPregunta();
				ChecklistHasCatalogoPreguntaPK pk = new ChecklistHasCatalogoPreguntaPK();

				pk.setCatalogoPreguntaCategoriaIdCategoria(cp.getCategoria().getIdCategoria());
				pk.setCatalogoPreguntaPesoIdPeso(cp.getPeso().getIdPeso());
				pk.setCatalogoPreguntaPreguntaIdPregunta(cp.getPregunta().getIdPregunta());
				pk.setChecklistIdChecklist(cl.getIdChecklist());
				ccp.setActivo(true);
				ccp.setChecklistHasCatalogoPreguntaPK(pk);
				ccp.setOrdenBloque(obj.getOrden());

				clcpI.insertar(ccp);

			}
		}

		utils.mostrarMensaje("CheckList", "Añadido exitosamente", "I");

		this.clear();
	}

	public void agregarChecklist(Categoria obj) {
		if(preguntasByCategoria(obj.getIdCategoria()).size() >0)
		{
		CheckListDTO cl = new CheckListDTO();
		cl.setIdCategoria(obj.getIdCategoria());
		cl.setNombreCategoria(obj.getNombreCategoria());
		listaCatalogoPreguntaSelect.add(cl);
		utils.mostrarMensaje("Categoría", "Agregada Exitosamente", "I");
		}else {
			utils.mostrarMensaje("Inconveniente", "Re requiere Categorias con Preguntas para el Registro", "W");
		}
		
	}

	public void quitarChecklist(CheckListDTO obj) {
		listaCatalogoPreguntaSelect.remove(obj);
		for (CheckListDTO dto : listaCatalogoPreguntaSelect) {
			if (dto.getIdCategoria() == obj.getIdCategoria()) {
				clcpI.eliminar(checklist.getIdChecklist(), obj.getIdCategoria(), obj.getIdPregunta(), obj.getIdPeso());
			}
		}

		utils.mostrarMensaje("Eliminación", "existosa", "F");

	}

	public void guardar() {
		try {

			CatalogoPreguntaPK pk = new CatalogoPreguntaPK();
			pk.setCategoriaIdCategoria(categoria.getIdCategoria());
			pk.setPesoIdPeso(peso.getIdPeso());
			pk.setPreguntaIdPregunta(pregunta.getIdPregunta());

			catalogoPregunta.setActivoCatalogoPregunta(true);
			catalogoPregunta.setCatalogoPreguntaPK(pk);

			cpI.insertar(catalogoPregunta);

			utils.mostrarMensaje("Registro", "Exitoso", "I");

			this.clear();

		} catch (Exception e) {
			e.printStackTrace();
			utils.mostrarMensaje("Checklist", "Problema al registraar", "E");
		}
	}

	public void actualizarListaCatalogoPregunta() {
		listaCatalogoPregunta = cpI.listar();
	}

	public void actualizarListaCategoria() {
		listCategorias = categoriaI.listar();

	}

	public void actualizarListaPregunta() {
		listaPregunta = preguntaI.listar();
	}

	public void actualizarListaPeso() {
		listaPeso = pesoI.listar();
	}

	public void onRowSelectCategoria(SelectEvent<Object> event) {
		categoria = (Categoria) event.getObject();
		utils.mostrarMensaje("Selección ", categoria.getClass().getName() + " - " + categoria.getNombreCategoria(),
				"I");
	}

	public void onRowUnselectCategoria(UnselectEvent<Object> event) {
		categoria = (Categoria) event.getObject();
		utils.mostrarMensaje("Deselección ", categoria.getClass().getName() + " - " + categoria.getNombreCategoria(),
				"W");
	}

	public void onRowSelectPregunta(SelectEvent<Object> event) {
		pregunta = (Pregunta) event.getObject();
		utils.mostrarMensaje("Selección ", pregunta.getClass().getName() + " - " + pregunta.getNombrePregunta(), "I");
	}

	public void onRowUnselectPregunta(UnselectEvent<Object> event) {
		pregunta = (Pregunta) event.getObject();
		utils.mostrarMensaje("Deselección ", pregunta.getClass().getName() + " - " + pregunta.getNombrePregunta(), "W");
	}

	public void onRowSelectPeso(SelectEvent<Object> event) {
		peso = (Peso) event.getObject();
		utils.mostrarMensaje("Selección ", peso.getClass().getName() + " : " + peso.getPeso() + " / "
				+ peso.getPorcentaje() + "/" + peso.getValor(), "I");
	}

	public void onRowUnselectPeso(UnselectEvent<Object> event) {
		peso = (Peso) event.getObject();
		utils.mostrarMensaje("Selección ", peso.getClass().getName() + " : " + peso.getPeso() + " / "
				+ peso.getPorcentaje() + "/" + peso.getValor(), "W");
	}

	public void findByClienteIdCliente() {
		Cliente cliente = clienteI.buscarPorId(Long.parseLong(Integer.toString(idCliente)));
		listEmpresa = empresaI.findByClienteIdCliente(cliente);
	}

	public void findByEmpresaIdEmpresa() {
		Empresa empresa = empresaI.buscarPorId(Long.parseLong(Integer.toString(idEmpresa)));
		listAgencia = agenciaI.findByEmpresaIdEmpresa(empresa);

	}

	public List<CatalogoPregunta> preguntasByCategoria(Long id) {
		List<CatalogoPregunta> list = new ArrayList<>();

		list.addAll(cpI.findCatalogoPreguntaByIdCategoria(id));

		/*
		 * for (CatalogoPregunta obj : listaCatalogoPregunta) if
		 * (obj.getCatalogoPreguntaPK().getCategoriaIdCategoria() == id) list.add(obj);
		 */
		return list;
	}

	public void findByRolIdRol() {
		try {
			Rol rol = rolI.buscarPorId(idRol);
			listPlantillas = plantillaI.findByRolIdRol(rol);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void eliminarCatalogoPregunta(CatalogoPregunta entidad) {
		
		System.out.println("Si entróoooooooooooooo");
		try {
			cpI.eliminar(entidad.getIdCatalogoPregunta());
			utils.mostrarMensaje("Eliminación", "Realizada correctamente", "F");
			actualizarListaCatalogoPregunta();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	public void eliminarChecklist(Long id) {
		
		try {
		plantillaI.eliminar(id);
		utils.mostrarMensaje("Eliminación", "exitosa", "F");
		}catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
		}
	}

	public void onCellEdit(CellEditEvent event) {

		CatalogoPregunta entity = (CatalogoPregunta) ((DataTable) event.getComponent()).getRowData();

		cpI.actualizar(entity.getIdCatalogoPregunta(), entity);

		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Orden Cambiado",
					"Anterior: " + oldValue + ", Nuevo:" + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void findByAgenciaIdAgencia() {
		Agencia agencia = agenciaI.buscarPorId(Long.parseLong(Integer.toString(idAgencia)));
		listEmpleado = empleadoI.findByAgenciaIdAgencia(agencia);
	}

	/**
	 * SETTERS Y GETTERS
	 *
	 */

	public List<CheckListDTO> getListaCatalogoPreguntaSelect() {
		return listaCatalogoPreguntaSelect;
	}

	public void setListaCatalogoPreguntaSelect(List<CheckListDTO> listaCatalogoPreguntaSelect) {
		this.listaCatalogoPreguntaSelect = listaCatalogoPreguntaSelect;
	}

	public List<Categoria> getListCategorias() {
		return listCategorias;
	}

	public Checklist getChecklist() {
		return checklist;
	}

	public void setChecklist(Checklist checklist) {
		this.checklist = checklist;
	}

	public List<ChecklistHasCatalogoPregunta> getListChecklist() {
		return listChecklist;
	}

	public void setListChecklist(List<ChecklistHasCatalogoPregunta> listChecklist) {
		this.listChecklist = listChecklist;
	}

	public CatalogoPregunta getCatalogoPregunta() {
		return catalogoPregunta;
	}

	public void setCatalogoPregunta(CatalogoPregunta catalogoPregunta) {
		this.catalogoPregunta = catalogoPregunta;
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

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public int getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(int idAgencia) {
		this.idAgencia = idAgencia;
	}

	public int getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	public List<Pregunta> getListaPregunta() {
		return listaPregunta;
	}

	public void setListaPregunta(List<Pregunta> listaPregunta) {
		this.listaPregunta = listaPregunta;
	}

	public List<Peso> getListaPeso() {
		return listaPeso;
	}

	public void setListaPeso(List<Peso> listaPeso) {
		this.listaPeso = listaPeso;
	}

	public List<CatalogoPregunta> getListaCatalogoPregunta() {
		return listaCatalogoPregunta;
	}

	public void setListaCatalogoPregunta(List<CatalogoPregunta> listaCatalogoPregunta) {
		this.listaCatalogoPregunta = listaCatalogoPregunta;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Pregunta getPregunta() {
		return pregunta;
	}

	public void setPregunta(Pregunta pregunta) {
		this.pregunta = pregunta;
	}

	public Peso getPeso() {
		return peso;
	}

	public void setPeso(Peso peso) {
		this.peso = peso;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public Long getIdPregunta() {
		return idPregunta;
	}

	public void setIdPregunta(Long idPregunta) {
		this.idPregunta = idPregunta;
	}

	public Long getIdPeso() {
		return idPeso;
	}

	public void setIdPeso(Long idPeso) {
		this.idPeso = idPeso;
	}

	public void setListCategorias(List<Categoria> listCategorias) {
		this.listCategorias = listCategorias;
	}

	public List<Pregunta> getListaPreguntaSeleccionada() {
		return listaPreguntaSeleccionada;
	}

	public void setListaPreguntaSeleccionada(List<Pregunta> listaPreguntaSeleccionada) {
		this.listaPreguntaSeleccionada = listaPreguntaSeleccionada;
	}
	
}
