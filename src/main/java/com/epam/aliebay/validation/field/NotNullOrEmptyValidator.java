package com.epam.aliebay.validation.field;

import java.util.Set;

public class NotNullOrEmptyValidator extends AbstractValidator implements ConstraintValidator {

    public NotNullOrEmptyValidator(String value, String attributeOfError, Set<String> attributes) {
        super(value, attributeOfError, attributes);
    }

    @Override
    public void validate() {
        if (!isValid(value)) {
            attributes.add(attributeOfError);
        }
    }

    public static boolean isValid(String value) {
        return value != null && !value.isEmpty();
    }
}
