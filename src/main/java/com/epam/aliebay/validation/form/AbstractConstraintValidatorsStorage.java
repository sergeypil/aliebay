package com.epam.aliebay.validation.form;

import com.epam.aliebay.validation.field.ConstraintValidator;

import java.util.HashSet;
import java.util.Set;

public class AbstractConstraintValidatorsStorage {
    protected final Set<ConstraintValidator> constraintValidators = new HashSet<>();

    public Set<ConstraintValidator> getConstraintValidators() {
        return constraintValidators;
    }
}
