package com.epam.aliebay.validation.form;

import com.epam.aliebay.dto.ChangePasswordDto;
import com.epam.aliebay.validation.field.*;

import java.util.Collections;
import java.util.Set;

import static com.epam.aliebay.constant.AttributeConstants.*;

public class ChangePasswordConstraintValidatorsStorage extends AbstractConstraintValidatorsStorage {
    private static final int MAX_LENGTH_PASSWORD = 45;

    public Set<ConstraintValidator> getConstraintValidators() {
        return constraintValidators;
    }

    public static ChangePasswordConstraintValidatorsStorage createDefault(ChangePasswordDto changePasswordDto, Set<String> attributes) {
        ChangePasswordConstraintValidatorsStorage storage = new ChangePasswordConstraintValidatorsStorage();
        Collections.addAll(storage.constraintValidators,
                new NotNullOrEmptyValidator(changePasswordDto.getNewPassword(), WRONG_PASSWORD_ATTRIBUTE, attributes),
                new LessThanSymbolsValidator(changePasswordDto.getNewPassword(), WRONG_PASSWORD_ATTRIBUTE, attributes, MAX_LENGTH_PASSWORD),
                new PasswordValidator(changePasswordDto.getNewPassword(), WRONG_PASSWORD_ATTRIBUTE, attributes),
                new NotNullOrEmptyValidator(changePasswordDto.getConfirmedPassword(), WRONG_CONFIRMED_PASSWORD_ATTRIBUTE, attributes),
                new LessThanSymbolsValidator(changePasswordDto.getNewPassword(), WRONG_CONFIRMED_PASSWORD_ATTRIBUTE, attributes, MAX_LENGTH_PASSWORD),
                new ConfirmedPasswordValidator(changePasswordDto.getConfirmedPassword(),changePasswordDto.getConfirmedPassword(),
                        WRONG_CONFIRMED_PASSWORD_ATTRIBUTE, attributes));
        return storage;
    }
}
