package com.epam.aliebay.action.impl.admin;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.PostgreSqlDaoFactory;
import com.epam.aliebay.dao.Interface.ProducerDao;
import com.epam.aliebay.entity.Producer;
import com.epam.aliebay.util.AccessValidator;
import com.epam.aliebay.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.JspNameConstants.ADMIN_PRODUCERS_JSP;
import static com.epam.aliebay.constant.JspNameConstants.ERROR_JSP;
import static com.epam.aliebay.constant.OtherConstants.ERROR_403_MESSAGE;
import static com.epam.aliebay.constant.OtherConstants.ERROR_403_TITLE;

public class GetAllProducersAsAdminAction implements Action {
    private final ProducerDao producerDao = PostgreSqlDaoFactory.getInstance().getProducerDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (AccessValidator.isAccessPermitted(req)) {
            List<Producer> producers = producerDao.getAllProducers();
            req.setAttribute(ALL_PRODUCERS_ATTRIBUTE, producers);
            RoutingUtils.forwardToPage(ADMIN_PRODUCERS_JSP, req, resp);
        } else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            req.setAttribute(ERROR_TITLE_ATTRIBUTE, ERROR_403_TITLE);
            req.setAttribute(ERROR_MESSAGE_ATTRIBUTE, ERROR_403_MESSAGE);
            RoutingUtils.forwardToPage(ERROR_JSP, req, resp);
        }
    }
}
