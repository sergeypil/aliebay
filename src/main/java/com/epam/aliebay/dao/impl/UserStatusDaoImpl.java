package com.epam.aliebay.dao.impl;

import com.epam.aliebay.dao.*;
import com.epam.aliebay.dao.Interface.UserStatusDao;
import com.epam.aliebay.entity.UserStatus;

import java.util.List;
import java.util.Optional;

public class UserStatusDaoImpl extends AbstractBaseDao implements UserStatusDao {
    private static final ResultSetHandler<UserStatus> USER_STATUS_RESULT_SET_HANDLER =
            ResultSetHandlerFactory.getSingleResultSetHandler(ResultSetHandlerFactory.USER_STATUS_RESULT_SET_HANDLER);
    private static final ResultSetHandler<List<UserStatus>> USER_STATUSES_RESULT_SET_HANDLER =
            ResultSetHandlerFactory.getListResultSetHandler(ResultSetHandlerFactory.USER_STATUS_RESULT_SET_HANDLER);

    private static final String SELECT_USER_STATUS_BY_ID_QUERY = "SELECT us.id as user_status_id, usd.name, explanation " +
            "FROM user_status us LEFT JOIN user_status_detail usd ON us.id = usd.id " +
            "LEFT JOIN language l ON usd.language_id = l.id WHERE us.id = ? " +
            "AND l.id in (SELECT l.id FROM language l WHERE l.code = ?)";
    private static final String SELECT_ALL_USER_STATUSES_QUERY = "SELECT us.id as user_status_id, usd.name, explanation " +
            "FROM user_status us LEFT JOIN user_status_detail usd ON us.id=usd.id " +
            "LEFT JOIN language l ON usd.language_id = l.id WHERE " +
            "l.id in (SELECT l.id FROM language l WHERE l.code = ?)";

    @Override
    public Optional<UserStatus> getUserStatusById(int id, String language) {
        return Optional.ofNullable(JdbcTemplate.select(SELECT_USER_STATUS_BY_ID_QUERY, USER_STATUS_RESULT_SET_HANDLER, id, language));
    }

    @Override
    public List<UserStatus> getAllUserStatuses(String language) {
        return JdbcTemplate.select(SELECT_ALL_USER_STATUSES_QUERY, USER_STATUSES_RESULT_SET_HANDLER, language);
    }

}
