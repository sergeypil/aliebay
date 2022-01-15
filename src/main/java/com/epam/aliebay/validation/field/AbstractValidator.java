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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractValidator)) return false;
        AbstractValidator that = (AbstractValidator) o;
        return Objects.equals(value, that.value) &&
                Objects.equals(attributeOfError, that.attributeOfError) &&
                Objects.equals(attributes, that.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, attributeOfError, attributes);
    }
}
