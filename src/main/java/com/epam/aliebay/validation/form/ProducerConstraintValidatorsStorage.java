package com.epam.aliebay.validation.form;

import com.epam.aliebay.validation.field.*;

import java.util.Set;

import static com.epam.aliebay.constant.AttributeConstants.*;

public class ProducerConstraintValidatorsStorage extends AbstractConstraintValidatorsStorage {
    private static final int MAX_LENGTH_PRODUCER_NAME = 30;

    public Set<ConstraintValidator> getConstraintValidators() {
        return constraintValidators;
    }

    public static ProducerConstraintValidatorsStorage createDefault(String producerName, Set<String> attributes) {
        ProducerConstraintValidatorsStorage storage = new ProducerConstraintValidatorsStorage();
        storage.constraintValidators.add(new NotNullOrEmptyValidator(producerName, WRONG_NAME_ATTRIBUTE, attributes));
        storage.constraintValidators.add(new LessThanSymbolsValidator(producerName, WRONG_NAME_ATTRIBUTE, attributes,
                MAX_LENGTH_PRODUCER_NAME));
        return storage;
    }
}
