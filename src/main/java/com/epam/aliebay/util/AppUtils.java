package com.epam.aliebay.util;

import com.epam.aliebay.entity.Category;
import com.epam.aliebay.entity.Producer;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.epam.aliebay.constant.AttributeConstants.CATEGORIES_ON_RIGHT_PANEL_ATTRIBUTE;
import static com.epam.aliebay.constant.AttributeConstants.PRODUCERS_ON_RIGHT_PANEL_ATTRIBUTE;
import static com.epam.aliebay.constant.OtherConstants.COUNT_CATEGORIES_RIGHT_PANEL_ON_PRODUCT_PAGE;
import static com.epam.aliebay.constant.OtherConstants.COUNT_PRODUCERS_RIGHT_PANEL_ON_PRODUCT_PAGE;

public class AppUtils {
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
}
