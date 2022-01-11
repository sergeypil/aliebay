package com.epam.aliebay.action.impl.ajax;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.DaoFactory;
import com.epam.aliebay.dao.Interface.UserDao;
import com.epam.aliebay.dao.Interface.UserStatusDao;
import com.epam.aliebay.entity.User;
import com.epam.aliebay.entity.UserStatus;
import com.epam.aliebay.exception.UserNotFoundException;
import com.epam.aliebay.exception.UserStatusNotFoundException;
import com.epam.aliebay.util.RoutingUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.aliebay.constant.AttributeConstants.CURRENT_LANGUAGE_ATTRIBUTE;
import static com.epam.aliebay.constant.AttributeConstants.CURRENT_USER_ATTRIBUTE;
import static com.epam.aliebay.constant.OtherConstants.ADMIN_ROLE;
import static com.epam.aliebay.constant.OtherConstants.USER_STATUS_JSON;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.ID_USER_PARAMETER;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.ID_USER_STATUS_PARAMETER;

public class ChangeUserStatusAction implements Action {
    private static final Logger LOGGER = Logger.getLogger(ChangeUserStatusAction.class);
    private final UserDao userDao = DaoFactory.getDaoFactory().getUserDao();
    private final UserStatusDao userStatusDao = DaoFactory.getDaoFactory().getUserStatusDao();
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE) != null &&
                ((User)req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE)).getRole().equalsIgnoreCase(ADMIN_ROLE)) {
            String idUser = req.getParameter(ID_USER_PARAMETER);
            String idUserStatus = req.getParameter(ID_USER_STATUS_PARAMETER);
            User user = userDao.getUserById(Integer.parseInt(idUser), (String) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE))
                    .orElseThrow(() -> new UserNotFoundException("Cannot find user with id = " + idUser));

            UserStatus userStatus = userStatusDao.getUserStatusById(Integer.parseInt(idUserStatus), (String) req.getSession()
                    .getAttribute(CURRENT_LANGUAGE_ATTRIBUTE)).orElseThrow(
                    () -> new UserStatusNotFoundException("Cannot find user status with id = " + idUserStatus));
            user.setStatus(userStatus);
            userDao.updateUser(user, user.getId());
            LOGGER.info("User " + ((User) req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE)).getUsername()
                    + " changed user status user with id " + idUser + " to user status id " + idUserStatus);
            sendResponse(user.getStatus().getName(), req, resp);
        }
        else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
    private void sendResponse(String orderStatus, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject data = new JSONObject();
        data.put(USER_STATUS_JSON, orderStatus);
        RoutingUtils.sendJSON(data, req, resp);
    }
}
