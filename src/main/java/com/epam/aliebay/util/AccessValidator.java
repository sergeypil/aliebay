package com.epam.aliebay.util;

import com.epam.aliebay.entity.User;

import javax.servlet.http.HttpServletRequest;

import static com.epam.aliebay.constant.AttributeConstants.CURRENT_USER_ATTRIBUTE;
import static com.epam.aliebay.constant.OtherConstants.ADMIN_ROLE;

public class AccessValidator {

    public static boolean isAccessPermitted(HttpServletRequest req) {
        if (req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE) != null) {
            User user = (User) req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE);
            return user.getRole().equals(ADMIN_ROLE);
        }
        else return false;
    }
}
