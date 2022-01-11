package com.epam.aliebay.validation.field;

import java.util.Set;

public class CardHolderValidator extends AbstractValidator implements ConstraintValidator {
    private static  final String CARD_HOLDER_NAME_REGEX = "^[A-Za-z-]+\\s+[A-Za-z-]+$";

    public CardHolderValidator(String value, String attributeOfError, Set<String> attributes) {
        super(value, attributeOfError, attributes);
    }

    @Override
    public void validate() {
        if (value == null || value.isEmpty()) {
            return;
        }
        if (!value.matches(CARD_HOLDER_NAME_REGEX)) {
            attributes.add(attributeOfError);
        }
    }
}
