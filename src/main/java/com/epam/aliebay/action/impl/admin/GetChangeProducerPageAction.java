package com.epam.aliebay.action.impl.admin;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.PostgreSqlDaoFactory;
import com.epam.aliebay.dao.Interface.ProducerDao;
import com.epam.aliebay.entity.Producer;
import com.epam.aliebay.exception.ProducerNotFoundException;
import com.epam.aliebay.util.AccessValidator;
import com.epam.aliebay.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.JspNameConstants.ADD_CHANGE_PRODUCER_JSP;
import static com.epam.aliebay.constant.JspNameConstants.ERROR_JSP;
import static com.epam.aliebay.constant.OtherConstants.*;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.ID_PARAMETER;

public class GetChangeProducerPageAction implements Action {
    private final ProducerDao producerDao = PostgreSqlDaoFactory.getInstance().getProducerDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE) == null) {
            RoutingUtils.sendError(HttpServletResponse.SC_UNAUTHORIZED, ERROR_401_TITLE, ERROR_401_MESSAGE, req, resp);
        }
        else if (AccessValidator.isAccessPermitted(req)) {
            int id = Integer.parseInt(req.getParameter(ID_PARAMETER));
            Producer producer = producerDao.getProducerById(id).orElseThrow(
                    () -> new ProducerNotFoundException("Cannot find producer with id = " + id));
            req.getSession().setAttribute(EDITED_PRODUCER_ATTRIBUTE, producer);
            req.setAttribute(ACTION_ATTRIBUTE, CHANGE_PRODUCER_FORM_ACTION);
            RoutingUtils.forwardToPage(ADD_CHANGE_PRODUCER_JSP, req, resp);
        } else {
            RoutingUtils.sendError(HttpServletResponse.SC_FORBIDDEN, ERROR_403_TITLE, ERROR_403_MESSAGE, req, resp);
        }
    }
}
