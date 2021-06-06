package com.cempresarial.recursos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.cempresariales.servicio.commons.model.entity.Cliente;

public class Utilitarios implements Serializable {

	private static final long serialVersionUID = 5627070176361650393L;

	private FacesMessage msg = null;

	/** Navega entre paginas **/
	public String navegaPagina(String pagina) {
		try {
			return pagina + "?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			return "#";
		}
	}

	/** Abre y cierra los paneles modal de paginas **/
	public void ejecutarPaneles(String panel, String orden) {
		try {

			if (orden.toUpperCase().equals("S"))
				PrimeFaces.current().executeScript("PF('" + panel + "').show()");
			else
				PrimeFaces.current().executeScript("PF('" + panel + "').hide()");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Muestra los mensajes growl de las paginas dependiendi de las iniciales de
	 * severetyInfo
	 **/
	public void mostrarMensaje(String titulo, String mensaje, String tipo) {
		try {
			msg = new FacesMessage(mensaje);
			if (tipo.toUpperCase().equals("I"))
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, mensaje));
			if (tipo.toUpperCase().equals("W"))
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, titulo, mensaje));
			if (tipo.toUpperCase().equals("E"))
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, mensaje));
			if (tipo.toUpperCase().equals("F"))
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL, titulo, mensaje));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public StreamedContent obtenerImagen(byte[] plano) {

		try {
			InputStream is = new ByteArrayInputStream(plano);
			//StreamedContent image = new DefaultStreamedContent(is);
			StreamedContent image = DefaultStreamedContent.builder()
                    .stream(() -> new ByteArrayInputStream(plano)).build();
			return image;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/** Path Imagenes **/
	public String pathImagenes() {
		String path="";
		try {
			
			if (isWindows())
				path = "C:\\Users\\PC\\Desktop\\imágenes\\";
			else if (isUnix())
				path = "usr/cempresarial/";
			else
				path = "usr/cempresarial/";
			return path;
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	private static String OS = System.getProperty("os.name").toLowerCase();
	private static String OSArch = System.getProperty("os.arch").toLowerCase();
	private static String OSVersion = System.getProperty("os.version").toLowerCase();

	public static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}

	public static boolean isMac() {
		return (OS.indexOf("mac") >= 0);
	}

	public static boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
	}
	
	
	public StreamedContent getImage(String pathImagenFoto) {
		
		try {
			
				DefaultStreamedContent image = null;
				File file = new File(pathImagenFoto);
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

	public StreamedContent getFotoInicial(String logo) {
		try {

			DefaultStreamedContent image = null;

			String path = "";
			path = pathImagenes();

			File file = new File(path + logo);
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

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}



}
