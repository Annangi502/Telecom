package com.sp.validators;

import java.util.regex.Pattern;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;


public class NumberValidator implements IValidator<String> {

	private final String NUMBER_PATTERN = "^\\d+$";

	private final Pattern pattern;

	public NumberValidator() {
		pattern = Pattern.compile(NUMBER_PATTERN);
	}

	@Override
	public void validate(IValidatable<String> validatable) {

		final String value = validatable.getValue();
		char c=value.charAt(0);
		
		// validate Alphanumaric String
		if (pattern.matcher(value).matches() == false) {

			error(validatable, "number");
		}

	}

	private void error(IValidatable<String> validatable, String errorKey) {
		ValidationError error = new ValidationError();
		error.addMessageKey(getClass().getSimpleName() + "." + errorKey);
		validatable.error(error);
	}

}