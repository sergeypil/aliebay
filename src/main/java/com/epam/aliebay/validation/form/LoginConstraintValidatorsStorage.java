package com.epam.aliebay.validation.form;

import com.epam.aliebay.validation.field.ConstraintValidator;
import com.epam.aliebay.validation.field.LessThanSymbolsValidator;
import com.epam.aliebay.validation.field.NotNullOrEmptyValidator;

import java.util.Collections;
import java.util.Set;

import static com.epam.aliebay.constant.AttributeConstants.*;

public class LoginConstraintValidatorsStorage extends AbstractConstraintValidatorsStorage {
    private static final int MAX_LENGTH_USERNAME_OR_EMAIL_LENGTH = 45;
    private static final int MAX_LENGTH_PASSWORD = 45;

    public Set<ConstraintValidator> getConstraintValidators() {
        return constraintValidators;
    }

    public static LoginConstraintValidatorsStorage createDefault(String usernameOrEmail, String password, Set<String> attributes) {
        LoginConstraintValidatorsStorage storage = new LoginConstraintValidatorsStorage();
        Collections.addAll(storage.constraintValidators,
                new NotNullOrEmptyValidator(usernameOrEmail, WRONG_LOGIN_ATTRIBUTE, attributes),
                new LessThanSymbolsValidator(usernameOrEmail, WRONG_LOGIN_ATTRIBUTE, attributes, MAX_LENGTH_USERNAME_OR_EMAIL_LENGTH),
                new NotNullOrEmptyValidator(password, WRONG_PASSWORD_ATTRIBUTE, attributes),
                new LessThanSymbolsValidator(password, WRONG_PASSWORD_ATTRIBUTE, attributes, MAX_LENGTH_PASSWORD));
        return storage;
    }
}
