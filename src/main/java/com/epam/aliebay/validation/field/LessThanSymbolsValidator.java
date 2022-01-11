package com.epam.aliebay.validation.field;

import java.util.Set;

public class LessThanSymbolsValidator extends AbstractValidator implements ConstraintValidator {
    private int maxLength;

    public LessThanSymbolsValidator(String value, String attributeOfError, Set<String> attributes, int maxLength) {
        super(value, attributeOfError, attributes);
        this.maxLength = maxLength;
    }

    @Override
    public void validate() {
        if (value == null || value.isEmpty()) {
            return;
        }
        if (value.length() > maxLength) {
            attributes.add(attributeOfError);
        }
    }
}
