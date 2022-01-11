package com.epam.aliebay.validation.field;

import java.util.Set;

public class PasswordValidator extends AbstractValidator implements ConstraintValidator {
    private static final String PASSWORD_REGEX = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{5,45}$";

    public PasswordValidator(String value, String attributeOfError, Set<String> attributes) {
        super(value, attributeOfError, attributes);
    }

    @Override
    public void validate() {
        if (value == null || value.isEmpty()) {
            return;
        }
        if (!value.matches(PASSWORD_REGEX)) {
            attributes.add(attributeOfError);
        }
    }
}
