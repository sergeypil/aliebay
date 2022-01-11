package com.epam.aliebay.validation.form;

import com.epam.aliebay.dto.CategoryDto;
import com.epam.aliebay.util.ValidationUtils;
import com.epam.aliebay.validation.field.ConstraintValidator;
import com.epam.aliebay.validation.field.ImageValidator;
import com.epam.aliebay.validation.field.LessThanSymbolsValidator;
import com.epam.aliebay.validation.field.NotNullOrEmptyValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

import static com.epam.aliebay.constant.AttributeConstants.WRONG_IMAGE_ATTRIBUTE;
import static com.epam.aliebay.constant.AttributeConstants.WRONG_NAME_ATTRIBUTE;

public class CategoryConstraintValidatorsStorage extends AbstractConstraintValidatorsStorage {
    private static final int MAX_LENGTH_CATEGORY_NAME = 30;

    public Set<ConstraintValidator> getConstraintValidators() {
        return constraintValidators;
    }


    public static CategoryConstraintValidatorsStorage createDefault(CategoryDto categoryDto, Map<Integer, Set<String>> langToWrongName,
                                    HttpServletRequest req) {
        CategoryConstraintValidatorsStorage storage = new CategoryConstraintValidatorsStorage();
        categoryDto.getLangIdToCategoryName().forEach((langId, name) -> {
            storage.constraintValidators.add(new NotNullOrEmptyValidator(name, WRONG_NAME_ATTRIBUTE, langToWrongName.get(langId)));
            storage.constraintValidators.add(new LessThanSymbolsValidator(name, WRONG_NAME_ATTRIBUTE, langToWrongName.get(langId),
                    MAX_LENGTH_CATEGORY_NAME));
        });
        if (ValidationUtils.isRequestContainsMultipartContent(req) && ValidationUtils.isImageLoaded(categoryDto.getImagePart())) {
            storage.constraintValidators.add(new ImageValidator(categoryDto.getImagePart(), WRONG_IMAGE_ATTRIBUTE, langToWrongName.get(null)));
        }
        return storage;
    }
}
