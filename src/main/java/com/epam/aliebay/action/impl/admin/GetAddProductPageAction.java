package com.epam.aliebay.action.impl.admin;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.util.ActionUtils;
import com.epam.aliebay.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.JspNameConstants.ADD_CHANGE_PRODUCT_JSP;
import static com.epam.aliebay.constant.OtherConstants.*;

public class GetAddProductPageAction implements Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ActionUtils.getDataForProductForm(req);
        req.setAttribute(ACTION_ATTRIBUTE, ADD_PRODUCT_FORM_ACTION);
        RoutingUtils.forwardToPage(ADD_CHANGE_PRODUCT_JSP, req, resp);
    }
}
