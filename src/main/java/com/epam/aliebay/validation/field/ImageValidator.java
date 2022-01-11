package com.epam.aliebay.validation.field;

import javax.servlet.http.Part;
import java.util.Set;

public class ImageValidator implements ConstraintValidator {
    private Part imagePart;
    private String attributeOfError;
    private Set<String> attributes;


    private static final int MIN_IMAGE_SIZE = 0;
    private static final int MAX_IMAGE_SIZE = 1024 * 1024 * 5;
    private static final String JPEG_CONTENT_TYPE = "image/jpeg";
    private static final String JPG_CONTENT_TYPE = "image/jpg";
    private static final String PNG_CONTENT_TYPE = "image/png";

    public ImageValidator(Part imagePart, String attributeOfError, Set<String> attributes) {
        this.imagePart = imagePart;
        this.attributeOfError = attributeOfError;
        this.attributes = attributes;
    }

    @Override
    public void validate() {
        if(!(imagePart.getSize() > MIN_IMAGE_SIZE && imagePart.getSize() < MAX_IMAGE_SIZE &&
                (imagePart.getContentType().equals(JPEG_CONTENT_TYPE) ||
                        imagePart.getContentType().equals(JPG_CONTENT_TYPE) ||
                        imagePart.getContentType().equals(PNG_CONTENT_TYPE)))) {
            attributes.add(attributeOfError);
        }
    }
}
