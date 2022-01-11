package com.epam.aliebay.validation.field;

import java.util.Set;

public class ConfirmedPasswordValidator extends AbstractValidator implements ConstraintValidator {
    private String password;

    public ConfirmedPasswordValidator(String password, String value, String attributeOfError, Set<String> attributes) {
        super(value, attributeOfError, attributes);
        this.password = password;
    }

    @Override
    public void validate() {
        if (value == null || value.isEmpty()) {
            return;
        }
        if(!password.equals(value)) {
            attributes.add(attributeOfError);
        }
    }
}
