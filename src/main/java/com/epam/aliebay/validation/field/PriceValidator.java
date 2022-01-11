package com.epam.aliebay.validation.field;

import java.math.BigDecimal;
import java.util.Set;

public class PriceValidator extends AbstractValidator implements ConstraintValidator {
    private static final String PRICE_REGEX = "^[0-9]{0,6}[,.]?[0-9]{0,2}$";
    private static final String MIN_PRICE = "0.01";
    private static final String MAX_PRICE = "999999.99";

    public PriceValidator(String value, String attributeOfError, Set<String> attributes) {
        super(value, attributeOfError, attributes);
    }

    @Override
    public void validate() {
        if (value == null || value.isEmpty()) {
            return;
        }
        if (!value.matches(PRICE_REGEX)) {
            attributes.add(attributeOfError);
        } else {
            value = value.replace(",", ".");
            if (new BigDecimal(value).compareTo(new BigDecimal(MIN_PRICE)) < 0 ||
                    new BigDecimal(value).compareTo(new BigDecimal(MAX_PRICE)) > 0) {
                attributes.add(attributeOfError);
            }
        }
    }
}
