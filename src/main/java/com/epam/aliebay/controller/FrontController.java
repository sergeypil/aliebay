package com.epam.aliebay.controller;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.action.ActionFactory;
import com.epam.aliebay.constant.AttributeConstants;
import com.epam.aliebay.dao.connectionpool.ConnectionPool;
import com.epam.aliebay.exception.OrderNotFoundException;
import com.epam.aliebay.util.RoutingUtils;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.RoundingMode;

import static com.epam.aliebay.constant.AttributeConstants.ERROR_MESSAGE_ATTRIBUTE;
import static com.epam.aliebay.constant.AttributeConstants.ERROR_TITLE_ATTRIBUTE;
import static com.epam.aliebay.constant.JspNameConstants.ERROR_JSP;

public class FrontController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(FrontController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String pathInfo = req.getServletPath() + req.getPathInfo();
            Action action = ActionFactory.getInstance().getCommand(pathInfo);
            action.execute(req, resp);
        } catch (Exception e) {
            logger.error("Cannot execute request", e);
            throw e;
        }
    }
}
