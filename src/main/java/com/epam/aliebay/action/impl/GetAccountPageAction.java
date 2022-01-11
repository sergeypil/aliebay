package com.epam.aliebay.action.impl;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.DaoFactory;
import com.epam.aliebay.dao.Interface.OrderDao;
import com.epam.aliebay.dao.Interface.OrderStatusDao;
import com.epam.aliebay.entity.Order;
import com.epam.aliebay.entity.OrderStatus;
import com.epam.aliebay.entity.User;
import com.epam.aliebay.dto.RegisterDto;
import com.epam.aliebay.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.JspNameConstants.ACCOUNT_JSP;
import static com.epam.aliebay.constant.OtherConstants.ID_ORDER_STATUS_CANCELLED;
import static com.epam.aliebay.constant.OtherConstants.ID_ORDER_STATUS_IN_PROCESS;

public class GetAccountPageAction implements Action {
    private final OrderDao orderDao = DaoFactory.getDaoFactory().getOrderDao();
    private final OrderStatusDao orderStatusDao = DaoFactory.getDaoFactory().getOrderStatusDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute(CURRENT_USER_ATTRIBUTE);
        RegisterDto registerDto = mapUserToRegisterDto(user);
        req.setAttribute(REGISTER_DTO_ATTRIBUTE, registerDto);

        List<Order> currentUserOrders = orderDao.getOrdersByIdUser(user.getId(), (String) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE));
        req.getSession().setAttribute(CURRENT_USER_ORDERS_ATTRIBUTE, currentUserOrders);

        List<OrderStatus> orderStatuses = orderStatusDao.getAllOrderStatuses((String) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE));
        List<OrderStatus> orderStatusesForCustomer = orderStatuses.stream()
                .filter(os -> os.getId() == ID_ORDER_STATUS_IN_PROCESS || os.getId() == ID_ORDER_STATUS_CANCELLED)
                .collect(Collectors.toList());
        req.getSession().setAttribute(ORDER_STATUSES_FOR_CUSTOMER_ATTRIBUTE, orderStatusesForCustomer);
        RoutingUtils.forwardToPage(ACCOUNT_JSP, req, resp);
    }

    private RegisterDto mapUserToRegisterDto(User user) {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername(user.getUsername());
        registerDto.setFirstName(user.getFirstName());
        registerDto.setLastName(user.getLastName());
        registerDto.setBirthDate(String.valueOf(user.getBirthDate()));
        registerDto.setEmail(user.getEmail());
        registerDto.setPhoneNumber(user.getPhoneNumber());
        return registerDto;
    }
}
