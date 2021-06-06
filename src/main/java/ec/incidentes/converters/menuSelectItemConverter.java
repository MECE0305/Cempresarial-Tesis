package ec.incidentes.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import org.omnifaces.converter.SelectItemsConverter;

import com.cempresariales.servicio.commons.admin.model.entity.Menu;

@FacesConverter(forClass = Menu.class)
public class menuSelectItemConverter extends SelectItemsConverter {

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String id = (value instanceof Menu) ? ((Menu) value).getNombre() : null;
		return (id != null) ? String.valueOf(id) : null;
	}
}
