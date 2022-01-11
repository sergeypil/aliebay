package com.epam.aliebay.validation.field;

import java.time.LocalDate;
import java.util.Set;

public class BirthDateValidator extends AbstractValidator implements ConstraintValidator {

    public BirthDateValidator(String value, String attributeOfError, Set<String> attributes) {
        super(value, attributeOfError, attributes);
    }

    @Override
    public void validate() {
        if (value == null || value.isEmpty()) {
            return;
        }
        LocalDate localDate = LocalDate.parse(value);
        if (localDate.isAfter(LocalDate.now())) {
            attributes.add(attributeOfError);
        }
    }
}
