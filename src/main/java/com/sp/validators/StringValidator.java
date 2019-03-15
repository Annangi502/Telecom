package com.sp.validators;

import java.util.regex.Pattern;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;


public class StringValidator implements IValidator<String> {

	private final String STRING_PATTERN = "[a-zA-Z .']+$";

	private final Pattern pattern;

	public StringValidator() {
		pattern = Pattern.compile(STRING_PATTERN);
	}

	@Override
	public void validate(IValidatable<String> validatable) {

		final String value = validatable.getValue();

		// validate  String
		if (pattern.matcher(value).matches() == false) {
			error(validatable, "string");
		}

	}

	private void error(IValidatable<String> validatable, String errorKey) {
		ValidationError error = new ValidationError();
		error.addMessageKey(getClass().getSimpleName() + "." + errorKey);
		validatable.error(error);
	}

}
