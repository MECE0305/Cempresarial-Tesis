package com.cempresarial.bean.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.cempresarial.recursos.SesionController;
import com.cempresarial.recursos.Utilitarios;
import com.cempresarial.rest.client.service.AgenciaService;
import com.cempresarial.rest.client.service.ClienteService;
import com.cempresarial.rest.client.service.EmpresaService;
import com.cempresarial.rest.client.service.PerfilService;
import com.cempresarial.rest.client.service.PermisoAgenciaService;
import com.cempresariales.servicio.commons.admin.model.entity.Perfil;
import com.cempresariales.servicio.commons.admin.model.entity.PermisoAgencia;
import com.cempresariales.servicio.commons.model.entity.Agencia;
import com.cempresariales.servicio.commons.model.entity.Cliente;
import com.cempresariales.servicio.commons.model.entity.Empresa;

@ManagedBean(name = "permisoAgenciaController")
@SessionScoped
public class PermisoAgenciaController extends SesionController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PermisoAgenciaController() {
		super();
	}

	@Inject
	private PerfilService perfilDAO;
	@Inject
	private ClienteService clienteI;
	@Inject
	private EmpresaService empresaI;
	@Inject
	private AgenciaService agenciaI;
	@Inject
	private PermisoAgenciaService permisoAgenciaI;

	// VARIABLES
	private Utilitarios util;

	private List<Perfil> listPerfil;
	private List<Perfil> listPerfilSelect;

	private List<Cliente> listCliente;
	private List<Empresa> listEmpresa;
	private List<Agencia> listAgencia;
	private List<Agencia> listAgenciaSelect;

	private List<PermisoAgencia> listPermisoAgencia;

	private Long idCliente;
	private Long idEmpresa;

	// METODOS
	@PostConstruct
	private void init() {
		clear();

	}

	public void clear() {
		try {
			util = new Utilitarios();
			listPerfil = perfilDAO.listar();
			listAgencia = new ArrayList<>();
			listCliente = clienteI.listar();

			listPermisoAgencia = permisoAgenciaI.listar();
			cargarChecks();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String nombreAgencia(Long id) {
		return agenciaI.buscarPorId(id).getNombreAgencia();
	}
	
	public String nombreEmpresa(Long id) {
		return agenciaI.buscarPorId(id).getEmpresaIdEmpresa().getNombreEmpresa();
	}

	public void eliminarPermiso(PermisoAgencia item) {
		permisoAgenciaI.eliminar(item.getIdPermisoAgencia());
		listPermisoAgencia = permisoAgenciaI.listar();

	}

	public void switchActiva(PermisoAgencia entidad) {

		permisoAgenciaI.actualizar(entidad.getIdAgencia(), entidad);
		if (entidad.getActivoPermisoAgencia())
			util.mostrarMensaje("Permiso ", "Activada Exitosamente", "I");
		else
			util.mostrarMensaje("Permiso ", "Desactivada Exitosamente", "w");

		actualizarListaPermisoAgencia();
	}

	public void cargarChecks() {
		try {

			listAgenciaSelect = new ArrayList<>();
			listPerfilSelect = new ArrayList<>();
			List<PermisoAgencia> list = permisoAgenciaI.listar();
			for (PermisoAgencia pa : list) {
				Perfil p = perfilDAO.buscarPorId(pa.getPerfil().getIdPerfil());
				Agencia a = agenciaI.buscarPorId(pa.getIdAgencia());

				listAgenciaSelect.add(a);
				listPerfilSelect.add(p);

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void guardarPermisos() {
		try {
			for (Perfil p : listPerfilSelect) {
				for (Agencia a : listAgenciaSelect) {
					PermisoAgencia pa = new PermisoAgencia();
					pa.setActivoPermisoAgencia(true);
					pa.setIdAgencia(a.getIdAgencia());
					pa.setPerfil(p);

					permisoAgenciaI.insertar(pa);

					util.mostrarMensaje("Permiso Agencia", "Ingreso exitoso", "I");

					listPermisoAgencia = permisoAgenciaI.listar();

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			util.mostrarMensaje("Permiso Agencia", "Ocurrió un Error", "E");
			e.printStackTrace();
		}
	}

	public void actualizarListaPerfil() {
		listPerfil = perfilDAO.listar();
	}

	public void actualizarListaPermisoAgencia() {
		listPermisoAgencia = permisoAgenciaI.listar();
	}

	public void findByClienteIdCliente() {
		Cliente cliente = clienteI.buscarPorId(idCliente);
		listEmpresa = empresaI.findByClienteIdCliente(cliente);
	}

	public void findByEmpresaIdEmpresa() {
		Empresa empresa = empresaI.buscarPorId(idEmpresa);
		listAgencia = agenciaI.findByEmpresaIdEmpresa(empresa);
	}

	// SETTERS Y GETTERS
	public Utilitarios getUtil() {
		return util;
	}

	public void setUtil(Utilitarios util) {
		this.util = util;
	}

	public List<Perfil> getListPerfil() {
		return listPerfil;
	}

	public void setListPerfil(List<Perfil> listPerfil) {
		this.listPerfil = listPerfil;
	}

	public List<Perfil> getListPerfilSelect() {
		return listPerfilSelect;
	}

	public void setListPerfilSelect(List<Perfil> listPerfilSelect) {
		this.listPerfilSelect = listPerfilSelect;
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

	public List<Agencia> getListAgenciaSelect() {
		return listAgenciaSelect;
	}

	public void setListAgenciaSelect(List<Agencia> listAgenciaSelect) {
		this.listAgenciaSelect = listAgenciaSelect;
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

	public List<PermisoAgencia> getListPermisoAgencia() {
		return listPermisoAgencia;
	}

	public void setListPermisoAgencia(List<PermisoAgencia> listPermisoAgencia) {
		this.listPermisoAgencia = listPermisoAgencia;
	}

}
