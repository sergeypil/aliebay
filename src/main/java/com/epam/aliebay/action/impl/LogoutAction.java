package com.epam.aliebay.action.impl;

import com.epam.aliebay.action.Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.aliebay.constant.ActionConstants.GET_HOME_PAGE_ACTION;
import static com.epam.aliebay.constant.AttributeConstants.CURRENT_USER_ATTRIBUTE;
import static com.epam.aliebay.constant.AttributeConstants.HOST_NAME_ATTRIBUTE;

public class LogoutAction implements Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        session.removeAttribute(CURRENT_USER_ATTRIBUTE);
        resp.sendRedirect(req.getAttribute(HOST_NAME_ATTRIBUTE) + GET_HOME_PAGE_ACTION);
    }
}
