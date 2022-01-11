package com.epam.aliebay.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

public class ValidationUtils {

    public static boolean isImageLoaded(Part filePart) {
        return filePart.getSize() > 0;
    }

    public static boolean isRequestContainsMultipartContent(HttpServletRequest req) {
        return req.getContentType() != null && req.getContentType().toLowerCase().contains("multipart/form-data");
    }
}
