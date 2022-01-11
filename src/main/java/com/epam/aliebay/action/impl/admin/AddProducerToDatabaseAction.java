package com.epam.aliebay.action.impl.admin;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.DaoFactory;
import com.epam.aliebay.dao.Interface.ProducerDao;
import com.epam.aliebay.entity.Producer;
import com.epam.aliebay.entity.User;
import com.epam.aliebay.util.ActionUtils;
import com.epam.aliebay.util.RoutingUtils;
import com.epam.aliebay.validation.form.FormValidator;
import com.epam.aliebay.validation.form.ProducerConstraintValidatorsStorage;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.epam.aliebay.constant.ActionConstants.GET_ADMIN_PRODUCERS_PAGE_ACTION;
import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.JspNameConstants.ADD_CHANGE_PRODUCER_JSP;
import static com.epam.aliebay.constant.OtherConstants.*;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.PRODUCER_NAME_PARAMETER;


public class AddProducerToDatabaseAction implements Action {
    private static final Logger LOGGER = Logger.getLogger(AddProducerToDatabaseAction.class);
    private final ProducerDao producerDao = DaoFactory.getDaoFactory().getProducerDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String editedProducerName = req.getParameter(PRODUCER_NAME_PARAMETER);
        Set<String> validationErrorAttributes = getValidationErrors(editedProducerName);
        checkIfProducerNameExistInDb(editedProducerName, validationErrorAttributes);
        if (validationErrorAttributes.size() != 0) {
            ActionUtils.setAttributesWhenValidationErrorOnProducerForm(req, editedProducerName, validationErrorAttributes,
                    ADD_PRODUCER_FORM_ACTION);
            RoutingUtils.forwardToPage(ADD_CHANGE_PRODUCER_JSP, req, resp);
        } else {
            req.getSession().removeAttribute(EDITED_PRODUCER_NAME_ATTRIBUTE);
            producerDao.saveProducer(new Producer(editedProducerName));
            LOGGER.info("User " + ((User) req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE)).getUsername()
                    + " added producer " + editedProducerName + " in database");
            resp.sendRedirect(req.getAttribute(HOST_NAME_ATTRIBUTE) + GET_ADMIN_PRODUCERS_PAGE_ACTION);
        }
    }

    private Set<String> getValidationErrors(String editedProducerName) {
        Set<String> validationErrorAttributes = new HashSet<>();
        ProducerConstraintValidatorsStorage producerConstraintValidatorsStorage =
                ProducerConstraintValidatorsStorage.createDefault(editedProducerName, validationErrorAttributes);
        FormValidator.validate(producerConstraintValidatorsStorage);
        return validationErrorAttributes;
    }

    private void checkIfProducerNameExistInDb(String editedProducerName, Set<String> validationErrorAttributes) {
        Optional<Producer> optionalProducer = producerDao.getProducerByName(editedProducerName);
        if (optionalProducer.isPresent()) {
            validationErrorAttributes.add(ERROR_PRODUCER_NAME_EXIST_ATTRIBUTE);
        }
    }
}
