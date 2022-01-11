package com.epam.aliebay.validation.field;

import java.util.Set;

public class CountValidator extends AbstractValidator implements ConstraintValidator {
    private static final String INTEGER_REGEX = "^[0-9]{1,9}$";
    public CountValidator(String value, String attributeOfError, Set<String> attributes) {
        super(value, attributeOfError, attributes);
    }

    @Override
    public void validate() {
        if (value == null || value.isEmpty()) {
            return;
        }
        if (!value.matches(INTEGER_REGEX)) {
            attributes.add(attributeOfError);
        }
    }
}
