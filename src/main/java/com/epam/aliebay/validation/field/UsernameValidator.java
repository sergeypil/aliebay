package com.epam.aliebay.validation.field;

import java.util.Set;

public class UsernameValidator extends AbstractValidator implements ConstraintValidator {
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9_.-]{1,45}$";

    public UsernameValidator(String value, String attributeOfError, Set<String> attributes) {
        super(value, attributeOfError, attributes);
    }

    @Override
    public void validate() {
        if (value == null || value.isEmpty()) {
            return;
        }
        if (!value.matches(USERNAME_REGEX)) {
            attributes.add(attributeOfError);
        }
    }
}
