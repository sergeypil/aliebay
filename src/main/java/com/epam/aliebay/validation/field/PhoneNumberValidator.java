package com.epam.aliebay.validation.field;

import java.util.Set;

public class PhoneNumberValidator extends  AbstractValidator implements ConstraintValidator {
    private static final String PHONE_NUMBER_REGEX = "^[0-9\\-]{1,}[0-9\\-]{3,15}$";

    public PhoneNumberValidator(String value, String attributeOfError, Set<String> attributes) {
        super(value, attributeOfError, attributes);
    }

    @Override
    public void validate() {
        if (value == null || value.isEmpty()) {
            return;
        }
        if (!value.matches(PHONE_NUMBER_REGEX)) {
            attributes.add(attributeOfError);
        }
    }
}
