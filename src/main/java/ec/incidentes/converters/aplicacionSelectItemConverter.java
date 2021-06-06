package ec.incidentes.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import org.omnifaces.converter.SelectItemsConverter;

import com.cempresariales.servicio.commons.admin.model.entity.Aplicacion;

@FacesConverter(forClass = Aplicacion.class)
public class aplicacionSelectItemConverter extends SelectItemsConverter {

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String id = (value instanceof Aplicacion) ? ((Aplicacion) value).getNombre() : null;
		return (id != null) ? String.valueOf(id) : null;
	}
}
