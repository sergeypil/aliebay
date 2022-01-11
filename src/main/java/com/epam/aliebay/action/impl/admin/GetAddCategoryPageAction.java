package com.epam.aliebay.action.impl.admin;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.util.ActionUtils;
import com.epam.aliebay.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.JspNameConstants.ADD_CHANGE_CATEGORY_JSP;
import static com.epam.aliebay.constant.OtherConstants.*;

public class GetAddCategoryPageAction implements Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ActionUtils.getDataForAddCategoryForm(req);
        req.setAttribute(ACTION_ATTRIBUTE, ADD_CATEGORY_FORM_ACTION);
        RoutingUtils.forwardToPage(ADD_CHANGE_CATEGORY_JSP, req, resp);
    }
}
