package com.sp.validators;

import java.util.regex.Pattern;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

public class LandLineNumberValidator implements IValidator<String> {

	private final String LANDLINE_PATTERN = "^[0-9]{6,11}$";

	private final Pattern pattern;

	public LandLineNumberValidator() {
		pattern = Pattern.compile(LANDLINE_PATTERN);
	}

	@Override
	public void validate(IValidatable<String> validatable) {

		final String value = validatable.getValue();

		// validate Alphanumaric String
		if (pattern.matcher(value).matches() == false) {
			error(validatable, "landline");
		}

	}

	private void error(IValidatable<String> validatable, String errorKey) {
		ValidationError error = new ValidationError();
		error.addMessageKey(getClass().getSimpleName() + "." + errorKey);
		validatable.error(error);
	}

}