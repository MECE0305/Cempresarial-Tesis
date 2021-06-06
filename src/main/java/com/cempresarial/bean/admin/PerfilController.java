package com.cempresarial.bean.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;

import com.cempresarial.recursos.SesionController;
import com.cempresarial.recursos.Utilitarios;
import com.cempresarial.rest.client.service.AgenciaService;
import com.cempresarial.rest.client.service.AutorizacionService;
import com.cempresarial.rest.client.service.ClienteService;
import com.cempresarial.rest.client.service.EmpresaService;
import com.cempresarial.rest.client.service.PerfilHasUsuarioService;
import com.cempresarial.rest.client.service.PerfilService;
import com.cempresarial.rest.client.service.PermisoAgenciaService;
import com.cempresarial.rest.client.service.UsuarioService;
import com.cempresariales.servicio.commons.admin.model.entity.Autorizacion;
import com.cempresariales.servicio.commons.admin.model.entity.Perfil;
import com.cempresariales.servicio.commons.admin.model.entity.PerfilHasUsuario;
import com.cempresariales.servicio.commons.admin.model.entity.PerfilHasUsuarioPK;
import com.cempresariales.servicio.commons.admin.model.entity.PermisoAgencia;
import com.cempresariales.servicio.commons.admin.model.entity.Usuario;
import com.cempresariales.servicio.commons.model.entity.Agencia;
import com.cempresariales.servicio.commons.model.entity.Cliente;
import com.cempresariales.servicio.commons.model.entity.Empresa;

@ManagedBean(name = "perfilController")
@SessionScoped
public class PerfilController extends SesionController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PerfilController() {
		super();
	}

	@Inject
	private UsuarioService usuarioDAO;
	@Inject
	private PerfilService perfilDAO;
	@Inject
	private PerfilHasUsuarioService perfiluserDAO;
	@Inject
	private AutorizacionService autorizacionDAO;
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
	private Usuario usuario;
	private Perfil perfil;
	private PerfilHasUsuario perfilUsuario;

	private List<Usuario> listUsuario;
	private List<Usuario> listUsuarioSelect;

	private List<Perfil> listPerfil;
	private List<Perfil> listPerfilSelect;
	private List<PerfilHasUsuario> listPerfilUsuario;
	private List<PerfilHasUsuario> listPerfilUsuarioSelect;

	private DualListModel<Perfil> perfiles;

	private boolean guardarRender;
	private boolean guardarUPRender;
	public String mensajePerfiles = "";

	private List<Cliente> listCliente;
	private List<Empresa> listEmpresa;
	private List<Agencia> listAgencia;
	private List<Agencia> listAgenciaSelect;

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
			usuario = new Usuario();
			perfil = new Perfil();
			listPerfil = perfilDAO.listar();

			listUsuario = usuarioDAO.listar();
			actualizarListaPerfilUsuario();
			inicalizarModelPerfiles();
			llenarExtremos();

			listAgencia = new ArrayList<>();
			listCliente = clienteI.listar();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizarLista() {
		listPerfil = perfilDAO.listar();
	}
	
	public void actualizarListaPerfilUsuario() {
		listPerfilUsuario = perfiluserDAO.listar();
		llenarExtremos();
	}

	public void findByClienteIdCliente() {
		Cliente cliente = clienteI.buscarPorId(idCliente);
		listEmpresa = empresaI.findByClienteIdCliente(cliente);
	}

	public void findByEmpresaIdEmpresa() {
		Empresa empresa = empresaI.buscarPorId(idEmpresa);
		listAgencia = agenciaI.findByEmpresaIdEmpresa(empresa);
	}

	public void llenarExtremos() {
		try {
			for (int x = 0; x < listPerfilUsuario.size(); x++) {
				Usuario u = usuarioDAO
						.buscarPorId(listPerfilUsuario.get(x).getPerfilHasUsuarioPK().getUsuarioIdUsuario());
				Perfil p = perfilDAO.buscarPorId(listPerfilUsuario.get(x).getPerfilHasUsuarioPK().getPerfilIdPerfil());

				listPerfilUsuario.get(x).setPerfil(p);
				listPerfilUsuario.get(x).setUsuario(u);

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void onTransfer(TransferEvent event) {
		StringBuilder builder = new StringBuilder();
		for (Object item : event.getItems()) {
			builder.append(((Perfil) item).getNombre()).append("<br />");
		}

		FacesMessage msg = new FacesMessage();
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		msg.setSummary("Items Transferred");
		msg.setDetail(builder.toString());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onSelect(SelectEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().toString()));
	}

	public void onUnselect(UnselectEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected", event.getObject().toString()));
	}

	public void onReorder() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "List Reordered", null));
	}

	public boolean verificarAntesGuradarUsuarioPerfil() {
		try {
			if (usuario.getIdUsuario() == 0 || perfiles.getTarget().size() == 0) {
				util.mostrarMensaje("Perfil-Usuario",
						"Usuario o Perfiles no seleccionados. Por favor, revizar antes de asignar", "w");
				guardarUPRender = false;

			} else {
				util.mostrarMensaje("Perfil-Usuario", "Desea guardar la ASIGNACION?", "i");
				guardarUPRender = true;
			}
			return guardarUPRender;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void crearUsuarioPerfil() {
		int valor = 0;
		try {

			int valorItem = perfiluserDAO.listar().size();

			if (verificarPaquete(7, valorItem)) {
				if (guardarUPRender) {

					List<Perfil> listPerfilesUsuario = perfilDAO.obtenerPerfilesUsuario(usuario.getNombreUsuario());
					List<Perfil> listPerfilGuardar = new ArrayList<>();

					if (listPerfilesUsuario.size() > 0) {
						for (Perfil pu : listPerfilesUsuario) {
							for (Perfil pg : perfiles.getTarget()) {
								if (pu.getIdPerfil() != pg.getIdPerfil()) {
									listPerfilGuardar.add(pg);
								}
							}
						}
					} else {
						listPerfilGuardar = perfiles.getTarget();
					}

					for (Perfil p : listPerfilGuardar) {
						PerfilHasUsuario pu = new PerfilHasUsuario();
						PerfilHasUsuarioPK pupk = new PerfilHasUsuarioPK();
						pupk.setUsuarioIdUsuario(usuario.getIdUsuario());
						pupk.setPerfilIdPerfil(p.getIdPerfil());
						pu.setPerfil(p);
						pu.setUsuario(usuario);
						pu.setPerfilHasUsuarioPK(pupk);
						pu.setActivoPerfilHasUsuario(true);
						perfiluserDAO.insertar(pu);
						valor++;

					}

					if (valor > 0) {
						util.mostrarMensaje("Perfil-Usuario", "Se ha ingresaro las Asignaciones correctamente", "I");
					} else {
						util.mostrarMensaje("Perfil-Usuario", "Asignaciones no realizadas", "e");
					}
				}
				actualizarListaPerfilUsuario();			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void inicalizarModelPerfiles() {
		try {
			List<Perfil> perfilesSource = perfilDAO.listar();
			List<Perfil> perfilesTarget = new ArrayList<>();

			perfiles = new DualListModel<Perfil>();
			perfiles.setSource(perfilesSource);
			perfiles.setTarget(perfilesTarget);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void eliminarPerfil(Perfil per) {
		try {

			List<Autorizacion> list = autorizacionDAO.findByPerfil(per);
			if (list.isEmpty()) {
				List<PerfilHasUsuario> lista = perfiluserDAO.findPerfilUsuarioByPerfil(per);
				if (!lista.isEmpty()) {
					util.mostrarMensaje("PERFIL", "No se puede eliminar, Perfil em USO", "w");
				} else {
					perfilDAO.eliminar(per.getIdPerfil());
					util.mostrarMensaje("PERFIL", "Perfil eliminado exitosamente", "i");
				}
			} else {
				util.mostrarMensaje("PERFIL", "No se puede eliminar, Perfil em USO", "w");
			}
			listPerfil = perfilDAO.listar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void eliminarPerfilUsuario(PerfilHasUsuario peruser) {
		try {
			perfiluserDAO.eliminar(peruser.getPerfilHasUsuarioPK().getPerfilIdPerfil(),
					peruser.getPerfilHasUsuarioPK().getUsuarioIdUsuario());
			actualizarListaPerfilUsuario();
			util.mostrarMensaje("PERFIL", "Perfil eliminado exitosamente", "i");
		} catch (Exception e) {
			e.printStackTrace();
			util.mostrarMensaje("PERFIL", "No se puede eliminar, Consulte con su Administrador", "w");
		}
	}

	public void actualizarPerfil() {
		try {
			if (verificarDatosVacios()) {
				if (perfil.getIdPerfil() != 0) {
					perfilDAO.actualizar(perfil.getIdPerfil(), perfil);
					listPerfil = perfilDAO.listar();
					util.mostrarMensaje("PERFIL", "El perfil ha sido actualizado correctamente.!", "i");
				} else {
					util.mostrarMensaje("PERFIL", "No se puede actualizar el perfil. Id no identificado o no existe!",
							"i");
				}
			} else {
				mensajePerfiles = "Existen Datos VACÍOS, por favor, verificar antes de guardar";
				util.mostrarMensaje("PERFIL", mensajePerfiles, "W");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void activaDesactivaPerfil(Perfil per) {
		try {

			if (per.getActivo()) {
				per.setActivo(false);
				util.mostrarMensaje("PERFIL", "Perfil DESACTIVADO correctamente.!", "i");
			} else {
				per.setActivo(true);
				util.mostrarMensaje("PERFIL", "Perfil ACTIVADO correctamente.!", "i");
			}
			perfilDAO.actualizar(per.getIdPerfil(), per);
			listPerfil = perfilDAO.listar();
			inicalizarModelPerfiles();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void crearPerfil() {
		try {

			int valorItem = perfilDAO.listar().size();

			if (verificarPaquete(3, valorItem)) {
				if (guardarRender) {
					Perfil perfilInsertado = new Perfil();
					perfilInsertado = perfilDAO.insertar(perfil);
					util.mostrarMensaje("PERFIL", "Perfil creado exitosamente", "I");
					if (!listAgenciaSelect.isEmpty()) {
						for (Agencia a : listAgenciaSelect) {
							PermisoAgencia pa = new PermisoAgencia();
							pa.setActivoPermisoAgencia(true);
							pa.setPerfil(perfilInsertado);
							pa.setIdAgencia(a.getIdAgencia());
							permisoAgenciaI.insertar(pa);
							util.mostrarMensaje("Permiso a Agencia", a.getNombreAgencia(), "E");
						}
					}
					clear();
				} else {
					mensajePerfiles = "No se ha podido guardar el PERFIL";
					util.mostrarMensaje("PERFIL", mensajePerfiles, "E");
					guardarRender = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean verificarAntesGuardar() {
		try {

			if (verificarDatosVacios()) {
				/*
				 * 
				 * Perfil per = perfilDAO.findPerfilByNombre(perfil.getNombre()); if (per !=
				 * null) { if (!verificarSimilares(perfil)) { mensajePerfiles =
				 * "Se identificaron, similitudes con otros Perfiles, por favor, revisar antes de guardar"
				 * ; util.mostrarMensaje("PERFIL", mensajePerfiles, "W"); guardarRender = false;
				 * } else { mensajePerfiles = "Desea guardar el PERFIL?";
				 * util.mostrarMensaje("PERFIL", mensajePerfiles, "I"); guardarRender = true; }
				 * } else {
				 */
				mensajePerfiles = "Desea guardar el PERFIL?";
				util.mostrarMensaje("PERFIL", mensajePerfiles, "I");
				guardarRender = true;
				// }

			} else {
				mensajePerfiles = "Existen Datos VACÍOS, por favor, verificar antes de guardar";
				util.mostrarMensaje("PERFIL", mensajePerfiles, "W");
				guardarRender = false;
			}

			return guardarRender;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean verificarSimilares(Perfil per) {
		try {
			return !(perfil.getNombre().trim().toUpperCase().equals(per.getNombre().trim().toUpperCase())
					|| perfil.getDescripcion().trim().toUpperCase().equals(per.getDescripcion().trim().toUpperCase()));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean verificarDatosVacios() {
		try {
			return !(perfil.getNombre().equals("") || perfil.getDescripcion().equals(""));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// SETTERS Y GETTERS
	public Utilitarios getUtil() {
		return util;
	}

	public void setUtil(Utilitarios util) {
		this.util = util;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public List<Perfil> getListPerfil() {
		return listPerfil;
	}

	public void setListPerfil(List<Perfil> listPerfil) {
		this.listPerfil = listPerfil;
	}

	public boolean isGuardarRender() {
		return guardarRender;
	}

	public void setGuardarRender(boolean guardarRender) {
		this.guardarRender = guardarRender;
	}

	public String getMensajePerfiles() {
		return mensajePerfiles;
	}

	public void setMensajePerfiles(String mensajePerfiles) {
		this.mensajePerfiles = mensajePerfiles;
	}

	public List<Perfil> getListPerfilSelect() {
		return listPerfilSelect;
	}

	public void setListPerfilSelect(List<Perfil> listPerfilSelect) {
		this.listPerfilSelect = listPerfilSelect;
	}

	public List<Usuario> getListUsuario() {
		return listUsuario;
	}

	public void setListUsuario(List<Usuario> listUsuario) {
		this.listUsuario = listUsuario;
	}

	public DualListModel<Perfil> getPerfiles() {
		return perfiles;
	}

	public void setPerfiles(DualListModel<Perfil> perfiles) {
		this.perfiles = perfiles;
	}

	public PerfilHasUsuario getPerfilUsuario() {
		return perfilUsuario;
	}

	public void setPerfilUsuario(PerfilHasUsuario perfilUsuario) {
		this.perfilUsuario = perfilUsuario;
	}

	public List<PerfilHasUsuario> getListPerfilUsuario() {
		return listPerfilUsuario;
	}

	public void setListPerfilUsuario(List<PerfilHasUsuario> listPerfilUsuario) {
		this.listPerfilUsuario = listPerfilUsuario;
	}

	public List<PerfilHasUsuario> getListPerfilUsuarioSelect() {
		return listPerfilUsuarioSelect;
	}

	public void setListPerfilUsuarioSelect(List<PerfilHasUsuario> listPerfilUsuarioSelect) {
		this.listPerfilUsuarioSelect = listPerfilUsuarioSelect;
	}

	public boolean isGuardarUPRender() {
		return guardarUPRender;
	}

	public void setGuardarUPRender(boolean guardarUPRender) {
		this.guardarUPRender = guardarUPRender;
	}

	public List<Usuario> getListUsuarioSelect() {
		return listUsuarioSelect;
	}

	public void setListUsuarioSelect(List<Usuario> listUsuarioSelect) {
		this.listUsuarioSelect = listUsuarioSelect;
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

	public List<Agencia> getListAgenciaSelect() {
		return listAgenciaSelect;
	}

	public void setListAgenciaSelect(List<Agencia> listAgenciaSelect) {
		this.listAgenciaSelect = listAgenciaSelect;
	}

}
