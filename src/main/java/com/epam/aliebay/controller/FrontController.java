package com.epam.aliebay.controller;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.action.ActionFactory;
import com.epam.aliebay.exception.ResourceNotFoundException;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

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
        } catch (ResourceNotFoundException e) {
            String fullURL = req.getRequestURL() + (req.getQueryString() != null ? "?" + req.getQueryString() : "");
            logger.error("Cannot execute request with URL " + fullURL + ", resource not found ", e);
            throw e;
        }
        catch (Throwable e) {
            String fullURL = req.getRequestURL() + (req.getQueryString() != null ? "?" + req.getQueryString() : "");
            logger.error("Cannot execute request with URL " + fullURL , e);
            throw e;
        }
    }
}
