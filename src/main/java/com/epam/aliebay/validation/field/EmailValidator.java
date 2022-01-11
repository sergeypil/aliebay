package com.epam.aliebay.validation.field;

import java.util.Set;

public class EmailValidator extends AbstractValidator implements ConstraintValidator {

    private static final String EMAIL_REGEX = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";

    public EmailValidator(String value, String attributeOfError, Set<String> attributes) {
        super(value, attributeOfError, attributes);
    }

    @Override
    public void validate() {
        if (value == null || value.isEmpty()) {
            return;
        }
        if (!value.matches(EMAIL_REGEX)) {
                attributes.add(attributeOfError);
        }
    }
}
