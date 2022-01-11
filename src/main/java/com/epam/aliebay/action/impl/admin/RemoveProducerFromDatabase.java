package com.epam.aliebay.action.impl.admin;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.DaoFactory;
import com.epam.aliebay.dao.Interface.ProducerDao;
import com.epam.aliebay.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.aliebay.constant.ActionConstants.GET_ADMIN_PRODUCERS_PAGE_ACTION;
import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.ID_PARAMETER;

public class RemoveProducerFromDatabase implements Action {
    private static final Logger LOGGER = Logger.getLogger(RemoveProductFromDatabase.class);
    private final ProducerDao producerDao = DaoFactory.getDaoFactory().getProducerDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter(ID_PARAMETER));
        producerDao.deleteProducerById(id);
        LOGGER.info("User " + ((User) req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE)).getUsername()
                + " deleted producer with id " + id + " from database");
        resp.sendRedirect(req.getAttribute(HOST_NAME_ATTRIBUTE) + GET_ADMIN_PRODUCERS_PAGE_ACTION);
    }
}
