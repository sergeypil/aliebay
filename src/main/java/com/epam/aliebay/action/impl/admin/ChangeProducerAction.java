package com.epam.aliebay.action.impl.admin;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.PostgreSqlDaoFactory;
import com.epam.aliebay.dao.Interface.ProducerDao;
import com.epam.aliebay.entity.Producer;
import com.epam.aliebay.entity.User;
import com.epam.aliebay.util.AccessValidator;
import com.epam.aliebay.util.ActionUtils;
import com.epam.aliebay.util.RoutingUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.aliebay.constant.ActionConstants.GET_ADMIN_PRODUCERS_PAGE_ACTION;
import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.JspNameConstants.ADD_CHANGE_PRODUCER_JSP;
import static com.epam.aliebay.constant.JspNameConstants.ERROR_JSP;
import static com.epam.aliebay.constant.OtherConstants.*;

public class ChangeProducerAction implements Action {
    private static final Logger LOGGER = Logger.getLogger(ChangeProducerAction.class);
    private final ProducerDao producerDao = PostgreSqlDaoFactory.getInstance().getProducerDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE) == null) {
            RoutingUtils.sendError(HttpServletResponse.SC_UNAUTHORIZED, ERROR_401_TITLE, ERROR_401_MESSAGE, req, resp);
        }
        else if (AccessValidator.isAccessPermitted(req)) {
            Producer editedProducer = (Producer) req.getSession().getAttribute(EDITED_PRODUCER_ATTRIBUTE);
            boolean areAllParametersValid = ActionUtils.validateRequestFromProducerForm(req, editedProducer);
            if (!areAllParametersValid) {
                req.getSession().setAttribute(EDITED_PRODUCER_ATTRIBUTE, editedProducer);
                req.setAttribute(ACTION_ATTRIBUTE, CHANGE_PRODUCER_FORM_ACTION);
                RoutingUtils.forwardToPage(ADD_CHANGE_PRODUCER_JSP, req, resp);
            } else {
                req.getSession().removeAttribute(EDITED_PRODUCER_ATTRIBUTE);
                producerDao.updateProducer(editedProducer, editedProducer.getId());
                LOGGER.info("User " + ((User) req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE)).getUsername()
                        + " changed producer " + editedProducer.getName() + " in database");
                resp.sendRedirect(req.getAttribute(HOST_NAME_ATTRIBUTE) + GET_ADMIN_PRODUCERS_PAGE_ACTION);
            }
        } else {
            RoutingUtils.sendError(HttpServletResponse.SC_FORBIDDEN, ERROR_403_TITLE, ERROR_403_MESSAGE, req, resp);
        }
    }
}
