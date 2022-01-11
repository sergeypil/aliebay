package com.epam.aliebay.validation.form;

import com.epam.aliebay.dto.CheckoutDto;
import com.epam.aliebay.validation.field.*;

import java.util.Collections;
import java.util.Set;

import static com.epam.aliebay.constant.AttributeConstants.*;

public class CheckoutConstraintValidatorsStorage extends AbstractConstraintValidatorsStorage {
    private static final int MAX_LENGTH_CARD_HOLDER = 100;
    private static final int MAX_LENGTH_ADDRESS = 300;

    public static CheckoutConstraintValidatorsStorage createDefault(CheckoutDto checkoutDto, Set<String> attributes) {
        CheckoutConstraintValidatorsStorage storage = new CheckoutConstraintValidatorsStorage();
        Collections.addAll(storage.constraintValidators,
                new NotNullOrEmptyValidator(checkoutDto.getCardNumber(), WRONG_CARD_NUMBER_ATTRIBUTE, attributes),
                new CardNumberValidator(checkoutDto.getCardNumber(), WRONG_CARD_NUMBER_ATTRIBUTE, attributes),
                new NotNullOrEmptyValidator(checkoutDto.getSecurityCode(), WRONG_SECURITY_CODE_ATTRIBUTE, attributes),
                new SecurityCodeValidator(checkoutDto.getSecurityCode(), WRONG_SECURITY_CODE_ATTRIBUTE, attributes),
                new NotNullOrEmptyValidator(checkoutDto.getCardHolder(), WRONG_CARD_HOLDER_ATTRIBUTE, attributes),
                new LessThanSymbolsValidator(checkoutDto.getCardHolder(), WRONG_CARD_HOLDER_ATTRIBUTE, attributes, MAX_LENGTH_CARD_HOLDER),
                new CardHolderValidator(checkoutDto.getCardHolder(), WRONG_CARD_HOLDER_ATTRIBUTE, attributes),
                new NotNullOrEmptyValidator(checkoutDto.getExpirationDate(), WRONG_DATE_ATTRIBUTE, attributes),
                new ExpirationDateValidator(checkoutDto.getExpirationDate(), WRONG_DATE_ATTRIBUTE, attributes),
                new NotNullOrEmptyValidator(checkoutDto.getAddress(), WRONG_ADDRESS_ATTRIBUTE, attributes),
                new LessThanSymbolsValidator(checkoutDto.getAddress(), WRONG_ADDRESS_ATTRIBUTE, attributes, MAX_LENGTH_ADDRESS));
        return storage;
    }
}
