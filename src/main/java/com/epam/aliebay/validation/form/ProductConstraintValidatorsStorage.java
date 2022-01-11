package com.epam.aliebay.validation.form;

import com.epam.aliebay.dto.ProductDto;
import com.epam.aliebay.util.ValidationUtils;
import com.epam.aliebay.validation.field.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Set;

import static com.epam.aliebay.constant.AttributeConstants.*;

public class ProductConstraintValidatorsStorage extends AbstractConstraintValidatorsStorage {
    private static final int MAX_LENGTH_PRODUCT_NAME = 45;
    private static final int MAX_LENGTH_DESCRIPTION = 1500;

    public Set<ConstraintValidator> getConstraintValidators() {
        return constraintValidators;
    }

    public static ProductConstraintValidatorsStorage createDefault(ProductDto productDto, Set<String> attributes, HttpServletRequest req) {
        ProductConstraintValidatorsStorage storage = new ProductConstraintValidatorsStorage();
        Collections.addAll(storage.constraintValidators,
                new NotNullOrEmptyValidator(productDto.getName(), WRONG_NAME_ATTRIBUTE, attributes),
                new LessThanSymbolsValidator(productDto.getName(), WRONG_NAME_ATTRIBUTE, attributes, MAX_LENGTH_PRODUCT_NAME),
                new NotNullOrEmptyValidator(productDto.getDescription(), WRONG_DESCRIPTION_ATTRIBUTE, attributes),
                new LessThanSymbolsValidator(productDto.getDescription(), WRONG_DESCRIPTION_ATTRIBUTE, attributes, MAX_LENGTH_DESCRIPTION),
                new NotNullOrEmptyValidator(productDto.getPrice(), WRONG_PRICE_ATTRIBUTE, attributes),
                new PriceValidator(productDto.getPrice(), WRONG_PRICE_ATTRIBUTE, attributes),
                new NotNullOrEmptyValidator(productDto.getCount(), WRONG_COUNT_ATTRIBUTE, attributes),
                new CountValidator(productDto.getCount(), WRONG_COUNT_ATTRIBUTE, attributes));
        if (ValidationUtils.isRequestContainsMultipartContent(req) && ValidationUtils.isImageLoaded(productDto.getImagePart())) {
            storage.constraintValidators.add(new ImageValidator(productDto.getImagePart(), WRONG_IMAGE_ATTRIBUTE, attributes));
        }
        return storage;
    }
}
