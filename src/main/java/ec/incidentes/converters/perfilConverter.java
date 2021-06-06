/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incidentes.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import com.cempresariales.servicio.commons.admin.model.entity.Perfil;

/**
 *
 * @author DESARROLLO
 */
@FacesConverter(value = "perfilConverter")
public class perfilConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext fc, UIComponent comp, String value) {
		DualListModel<Perfil> model = (DualListModel<Perfil>) ((PickList) comp).getValue();
		for (Perfil employee : model.getSource()) {
			if (employee.getNombre().equals(value)) {
				return employee;
			}
		}
		for (Perfil employee : model.getTarget()) {
			if (employee.getNombre().equals(value)) {
				return employee;
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent comp, Object value) {
		return ((Perfil) value).getNombre();
	}
}
