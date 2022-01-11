package com.epam.aliebay.validation.field;

import java.util.Set;

public class SecurityCodeValidator extends AbstractValidator implements ConstraintValidator {
    private static  final String SECURITY_CODE_REGEX = "^\\d{3,4}$";

    public SecurityCodeValidator(String value, String attributeOfError, Set<String> attributes) {
        super(value, attributeOfError, attributes);
    }

    @Override
    public void validate() {
        if (value == null || value.isEmpty()) {
            return;
        }
        if (!value.matches(SECURITY_CODE_REGEX)) {
            attributes.add(attributeOfError);
        }
    }
}
