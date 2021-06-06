package ec.incidentes.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("ec.incidentes.validators.CedulaValidator")
public class CedulaValidator implements Validator {

	public static final int NUMERO_DE_PROVINCIAS = 24;
	public static final String CEDULA_PATTERN = "^[0-9]{10}$";

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		String cedula = (String) value;
		Pattern patron = Pattern.compile(CEDULA_PATTERN);
		Matcher encaja = patron.matcher(cedula);

		if (encaja.matches()) {
			int prov = Integer.parseInt(cedula.substring(0, 2));
			if (!((prov > 0) && (prov <= NUMERO_DE_PROVINCIAS))) {
				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "FORMATO", "CI incorrecto."));
			}
		} else {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "FORMATO", "CI incorrecto."));
		}

	}

}
