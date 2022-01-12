package com.epam.aliebay.validation.form;

import com.epam.aliebay.dto.RegisterDto;
import com.epam.aliebay.validation.field.*;

import java.util.Collections;
import java.util.Set;

import static com.epam.aliebay.constant.AttributeConstants.*;

public class RegisterFormConstraintValidatorsStorage extends AbstractConstraintValidatorsStorage {
    private static final int MAX_LENGTH_FIRST_NAME = 45;
    private static final int MAX_LENGTH_LAST_NAME = 45;
    private static final int MAX_LENGTH_USERNAME_LENGTH = 45;
    private static final int MAX_LENGTH_EMAIL = 45;
    private static final int MAX_LENGTH_PASSWORD = 45;

    public Set<ConstraintValidator> getConstraintValidators() {
        return constraintValidators;
    }

    public static RegisterFormConstraintValidatorsStorage createDefaultForRegisterPage(RegisterDto registerDto, Set<String> attributes) {
        RegisterFormConstraintValidatorsStorage storage = new RegisterFormConstraintValidatorsStorage();
        fillOutConstraints(storage, registerDto, attributes);
        Collections.addAll(storage.constraintValidators,
                new NotNullOrEmptyValidator(registerDto.getPassword(), WRONG_PASSWORD_ATTRIBUTE, attributes),
                new LessThanSymbolsValidator(registerDto.getPassword(), WRONG_PASSWORD_ATTRIBUTE, attributes, MAX_LENGTH_PASSWORD),
                new PasswordValidator(registerDto.getPassword(), WRONG_PASSWORD_ATTRIBUTE, attributes),
                new NotNullOrEmptyValidator(registerDto.getConfirmedPassword(), WRONG_CONFIRMED_PASSWORD_ATTRIBUTE, attributes),
                new LessThanSymbolsValidator(registerDto.getPassword(), WRONG_CONFIRMED_PASSWORD_ATTRIBUTE, attributes, MAX_LENGTH_PASSWORD),
                new ConfirmedPasswordValidator(registerDto.getPassword(),registerDto.getConfirmedPassword(),
                        WRONG_CONFIRMED_PASSWORD_ATTRIBUTE, attributes));
        return storage;
    }

    public static RegisterFormConstraintValidatorsStorage createDefaultForUpdatePage(RegisterDto registerDto, Set<String> attributes) {
        RegisterFormConstraintValidatorsStorage storage = new RegisterFormConstraintValidatorsStorage();
        fillOutConstraints(storage, registerDto, attributes);
        return storage;
    }

    private static void fillOutConstraints(RegisterFormConstraintValidatorsStorage storage, RegisterDto registerDto, Set<String> attributes) {
        Collections.addAll(storage.constraintValidators,
                new NotNullOrEmptyValidator(registerDto.getFirstName(), WRONG_FIRST_NAME_ATTRIBUTE, attributes),
                new LessThanSymbolsValidator(registerDto.getFirstName(), WRONG_FIRST_NAME_ATTRIBUTE, attributes, MAX_LENGTH_FIRST_NAME),
                new NotNullOrEmptyValidator(registerDto.getLastName(), WRONG_LAST_NAME_ATTRIBUTE, attributes),
                new LessThanSymbolsValidator(registerDto.getLastName(), WRONG_LAST_NAME_ATTRIBUTE, attributes, MAX_LENGTH_LAST_NAME),
                new NotNullOrEmptyValidator(registerDto.getUsername(), WRONG_USERNAME_ATTRIBUTE, attributes),
                new LessThanSymbolsValidator(registerDto.getUsername(), WRONG_USERNAME_ATTRIBUTE, attributes, MAX_LENGTH_USERNAME_LENGTH),
                new UsernameValidator(registerDto.getUsername(), WRONG_USERNAME_ATTRIBUTE, attributes),
                new NotNullOrEmptyValidator(registerDto.getEmail(), WRONG_EMAIL_ATTRIBUTE, attributes),
                new LessThanSymbolsValidator(registerDto.getEmail(), WRONG_EMAIL_ATTRIBUTE, attributes, MAX_LENGTH_EMAIL),
                new EmailValidator(registerDto.getEmail(), WRONG_EMAIL_ATTRIBUTE, attributes),
                new NotNullOrEmptyValidator(registerDto.getPhoneNumber(), WRONG_PHONE_NUMBER_ATTRIBUTE, attributes),
                new PhoneNumberValidator(registerDto.getPhoneNumber(), WRONG_PHONE_NUMBER_ATTRIBUTE, attributes),
                new NotNullOrEmptyValidator(registerDto.getBirthDate(), WRONG_BIRTH_DATE_ATTRIBUTE, attributes),
                new BirthDateValidator(registerDto.getBirthDate(), WRONG_BIRTH_DATE_ATTRIBUTE, attributes));
    }
}
