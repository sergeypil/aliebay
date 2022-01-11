package com.epam.aliebay.action.impl.admin;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.DaoFactory;
import com.epam.aliebay.dao.Interface.UserDao;
import com.epam.aliebay.dao.Interface.UserStatusDao;
import com.epam.aliebay.entity.User;
import com.epam.aliebay.entity.UserStatus;
import com.epam.aliebay.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.JspNameConstants.ADMIN_USERS_JSP;

public class GetAllUsersPageAction implements Action {
    private final UserDao userDao = DaoFactory.getDaoFactory().getUserDao();
    private final UserStatusDao userStatusDao = DaoFactory.getDaoFactory().getUserStatusDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<User> users = userDao.getAllUsers((String) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE));
        Map<String, List<User>> roleToListOfUsers = users.stream()
                .collect(Collectors.groupingBy(User::getRole));
        List<UserStatus> userStatuses = userStatusDao.getAllUserStatuses((String) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE));
        req.setAttribute(ROLE_TO_LIST_OF_USERS_ATTRIBUTE, roleToListOfUsers);
        req.setAttribute(USER_STATUSES_ATTRIBUTE, userStatuses);
        RoutingUtils.forwardToPage(ADMIN_USERS_JSP, req, resp);
    }
}
