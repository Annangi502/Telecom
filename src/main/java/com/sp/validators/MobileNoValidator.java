package com.sp.validators;

import java.util.regex.Pattern;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;


public class MobileNoValidator implements IValidator<String> {

	private final String MOBILE_PATTERN = "^([0-9]{10})$";

	private final Pattern pattern;

	public MobileNoValidator() {
		pattern = Pattern.compile(MOBILE_PATTERN);
	}

	@Override
	public void validate(IValidatable<String> validatable) {

		final String value = validatable.getValue();

		// validate Mobile Number
		if (pattern.matcher(value).matches() == false) {
			error(validatable, "mobileno");
		}

	}

	private void error(IValidatable<String> validatable, String errorKey) {
		ValidationError error = new ValidationError();
		error.addMessageKey(getClass().getSimpleName() + "." + errorKey);
		validatable.error(error);
	}

}