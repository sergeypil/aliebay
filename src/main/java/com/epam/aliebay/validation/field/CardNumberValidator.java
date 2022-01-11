package com.epam.aliebay.validation.field;

import java.util.Set;

public class CardNumberValidator extends AbstractValidator implements ConstraintValidator {
    private static final String CARD_NUMBER_REGEX = "^\\d{16}$";

    public CardNumberValidator(String value, String attributeOfError, Set<String> attributes) {
        super(value, attributeOfError, attributes);
    }

    @Override
    public void validate() {
        if (value == null || value.isEmpty()) {
            return;
        }
        if (!value.matches(CARD_NUMBER_REGEX)) {
            attributes.add(attributeOfError);
        }
    }
}
