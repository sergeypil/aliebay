package com.epam.aliebay.validation.field;

import java.time.YearMonth;
import java.util.Set;

public class ExpirationDateValidator extends AbstractValidator implements ConstraintValidator {

    public ExpirationDateValidator(String value, String attributeOfError, Set<String> attributes) {
        super(value, attributeOfError, attributes);
    }

    @Override
    public void validate() {
        if (value == null || value.isEmpty()) {
            return;
        }
        YearMonth yearMonth = YearMonth.parse(value);

        if (yearMonth.isBefore(YearMonth.now())) {
            attributes.add(attributeOfError);
        }
    }
}
