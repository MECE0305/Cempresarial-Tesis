/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cempresarial.recursos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSeparator;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import com.cempresarial.rest.client.service.AplicacionService;
import com.cempresarial.rest.client.service.AutorizacionService;
import com.cempresarial.rest.client.service.MenuService;
import com.cempresarial.rest.client.service.PerfilService;
import com.cempresariales.servicio.commons.admin.model.entity.Aplicacion;
import com.cempresariales.servicio.commons.admin.model.entity.Autorizacion;
import com.cempresariales.servicio.commons.admin.model.entity.Menu;
import com.cempresariales.servicio.commons.admin.model.entity.Perfil;
import com.cempresariales.servicio.commons.admin.model.entity.Usuario;

/**
 *
 * @author DESARROLLO
 */
@ManagedBean(name = "sesionUsuario")
@SessionScoped
public class SessionUsuario extends SesionController implements Serializable {

	private static final long serialVersionUID = 1L;

	// SERVICIOS

	@Inject
	private MenuService menuI;
	@Inject
	private PerfilService perfilI;
	@Inject
	private AplicacionService aplicacionDAO;
	@Inject
	private AutorizacionService permisosDAO;

	// VARIABLES
	private Usuario usuario;
	private List<Perfil> perfiles;
	private List<Menu> listMenuPadre;

	private String urlAplicacion;
	private String perfilSeleccionado;
	private MenuModel model;

	// POSTCONSTRUCT
	@PostConstruct
	public void init() {
		try {

			usuario = cargarUsuario();
			cargarPerfiles();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// METODOS
	public Usuario cargarUsuario() {
		if (usuario == null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
			HttpSession session = request.getSession();
			// Assertion a = (Assertion)
			// session.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
			// String nombreUsuario = a.getPrincipal().getName();
			// usuario = ss.obtenerUsuario(nombreUsuario);
			usuario = (Usuario) session.getAttribute("currentUser");
		}
		return usuario;
	}

	private void cargarMenusPadrePorRol(String rol) {
		try {
			FacesContext fc = FacesContext.getCurrentInstance();
			urlAplicacion = fc.getExternalContext().getInitParameter("urlAplicacion");

			System.out.println("APPLICACION: " + urlAplicacion);

			Aplicacion aplicacion = aplicacionDAO.buscarPorId(new Long(1));

			List<Menu> menus = menuI.obtenerMenusPorRolAplicativo(rol, aplicacion.getIdAplicacion());

			System.out.println("MENUS: " + menus.size());

			listMenuPadre = new ArrayList<>();
			menus.stream().filter((mp) -> (mp.getTipo().equals("menu"))).forEachOrdered((mp) -> {
				listMenuPadre.add(mp);
			});

			System.out.println("MENUS: " + listMenuPadre.size());
			cargarSubmenus();
			cargarMenus();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String cargarPermisos(Menu subitem) {
		try {
			List<Autorizacion> list = permisosDAO.findByMenu(subitem);
			System.out.println("AUTS: " + list.size());

			for (Autorizacion a : list) {
				if (a.getPerfil().getNombre().equals(perfilSeleccionado)) {
					System.out.println("PERFIL MENU Y AUTORIZACION: " + perfilSeleccionado);
					System.out.println("PERFIL MENU Y AUTORIZACION: " + a.getPerfil().getNombre());
					System.out.println("PERFIL MENU Y AUTORIZACION: " + a.getPermisos());
				}
			}

			return subitem.getUrl();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "#";
		}
	}

	public void cargarSubmenus() {
		try {
			for (int x = 0; x < listMenuPadre.size(); x++) {
				List<Menu> listMenuHijos = menuI.obtenerMenusHijo(perfilSeleccionado, listMenuPadre.get(x).getIdMenu(),
						"opcion", "opcion");

				System.out.println("HABER MENUS ASOMEN !!!!!!!" + listMenuPadre.get(x).getIdMenu());

				if (listMenuHijos != null)
					listMenuPadre.get(x).setMenuList(listMenuHijos);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cargarMenus() {
		try {
			model = new DefaultMenuModel();
			DefaultSeparator separator = new DefaultSeparator();
			separator.setStyleClass("fa fa-ellipsis-v menu-separator");
			DefaultSubMenu menuPadre;
			DefaultMenuItem menuHijo;
			for (Menu menu : listMenuPadre) {
				if (menu.getMenuPadre() == null) {
					menuPadre = DefaultSubMenu.builder().label(menu.getNombre()).build();
					List<Menu> menusHijos = menuI.findByMenuPadre(menu.getIdMenu());

					for (Menu children : menusHijos) {
						menuHijo = DefaultMenuItem.builder().value(children.getNombre())
								.url(urlAplicacion + children.getUrl()).icon(children.getIcon()).update("mensaje")
								.ajax(false).iconPos(children.getIconPosition()).build();
						menuPadre.getElements().add(menuHijo);
					}
					menuPadre.getElements().add(new DefaultSeparator());
					model.getElements().add(menuPadre);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cargarPerfiles() {
		try {
			String nombreUsuario = cargarUsuario().getNombreUsuario();
			List<Perfil> resultadoBusqueda = perfilI.obtenerPerfilesUsuario(nombreUsuario);
			perfiles = resultadoBusqueda;
			perfilSeleccionado = resultadoBusqueda.get(0).getNombre();
			cargarMenusPadrePorRol(perfilSeleccionado);
		} catch (Exception e) {
			e.printStackTrace();
			perfilSeleccionado = "SIN PERFIL";
		}
	}

	// SETTERS Y GETTERS

	public MenuModel getModel() {
		return model;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}

	public List<Menu> getListMenuPadre() {
		return listMenuPadre;
	}

	public void setListMenuPadre(List<Menu> listMenuPadre) {
		this.listMenuPadre = listMenuPadre;
	}

	public String getPerfilSeleccionado() {
		return perfilSeleccionado;
	}

	public void setPerfilSeleccionado(String perfilSeleccionado) {
		this.perfilSeleccionado = perfilSeleccionado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Perfil> getPerfiles() {
		return perfiles;
	}

	public void setPerfiles(List<Perfil> perfiles) {
		this.perfiles = perfiles;
	}

}
