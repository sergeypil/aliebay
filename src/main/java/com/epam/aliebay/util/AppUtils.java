package com.epam.aliebay.util;

import com.epam.aliebay.entity.Category;
import com.epam.aliebay.entity.Producer;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Base64;
import java.util.List;

import static com.epam.aliebay.constant.AttributeConstants.CATEGORIES_ON_RIGHT_PANEL_ATTRIBUTE;
import static com.epam.aliebay.constant.AttributeConstants.PRODUCERS_ON_RIGHT_PANEL_ATTRIBUTE;
import static com.epam.aliebay.constant.OtherConstants.*;

public class AppUtils {
    private static final Logger logger = Logger.getLogger(AppUtils.class);
    private static final int BUFFER_LENGTH = 4 * 1024;

    public static void setProducersToAttributeAsidePanel(HttpServletRequest req, List<Producer> producers) {
        if (producers.size() < COUNT_PRODUCERS_RIGHT_PANEL_ON_PRODUCT_PAGE) {
            req.setAttribute(PRODUCERS_ON_RIGHT_PANEL_ATTRIBUTE, producers);
        } else {
            req.setAttribute(PRODUCERS_ON_RIGHT_PANEL_ATTRIBUTE, producers.subList(0, COUNT_PRODUCERS_RIGHT_PANEL_ON_PRODUCT_PAGE));
        }
    }

    public static void setCategoriesToAttributeRightAsidePanel(HttpServletRequest req, List<Category> categories) {
        if (categories.size() < COUNT_CATEGORIES_RIGHT_PANEL_ON_PRODUCT_PAGE) {
            req.setAttribute(CATEGORIES_ON_RIGHT_PANEL_ATTRIBUTE, categories);
        } else {
            req.setAttribute(CATEGORIES_ON_RIGHT_PANEL_ATTRIBUTE, categories.subList(0, COUNT_CATEGORIES_RIGHT_PANEL_ON_PRODUCT_PAGE));
        }
    }

    public static byte[] readImageBytes(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[BUFFER_LENGTH];
        int readLen;
        IOException exception = null;
        try {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                while ((readLen = inputStream.read(buffer, 0, BUFFER_LENGTH)) != -1)
                    outputStream.write(buffer, 0, readLen);

                return outputStream.toByteArray();
            }
        } catch (IOException e) {
            logger.error("Cannot read file", e);
            exception = e;
            throw e;
        } finally {
            if (exception == null) inputStream.close();
            else try {
                inputStream.close();
            } catch (IOException e) {
                exception.addSuppressed(e);
            }
        }
    }

    public static byte[] mapImageWithPrefixToBytes(String imageString) {
        String imageWithoutCodePrefix = imageString.substring(LENGTH_OF_PREFIX_TO_SHOW_IMAGE_ON_HTML_PAGE);
        return Base64.getDecoder().decode(imageWithoutCodePrefix);
    }

    public static String mapImageBytesToImageWithPrefix(byte[] imageBytes) {
        return PREFIX_TO_SHOW_IMAGE_ON_HTML_PAGE + Base64.getEncoder().encodeToString(imageBytes);
    }
}
