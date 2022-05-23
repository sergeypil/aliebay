package com.epam.aliebay.validation.field;

import java.util.Objects;
import java.util.Set;

public class AbstractValidator {
    protected String value;
    protected String attributeOfError;
    protected Set<String> attributes;

    public AbstractValidator(String value, String attributeOfError, Set<String> attributes) {
        this.value = value;
        this.attributeOfError = attributeOfError;
        this.attributes = attributes;
    }
}
