package com.epam.aliebay.validation.form;

import com.epam.aliebay.validation.field.ConstraintValidator;

public class FormValidator {
    public static void validate(AbstractConstraintValidatorsStorage constraintStorage) {
        constraintStorage.getConstraintValidators().forEach(ConstraintValidator::validate);
    }
}
