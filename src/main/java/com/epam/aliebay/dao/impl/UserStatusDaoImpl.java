package com.epam.aliebay.dao.impl;

import com.epam.aliebay.dao.*;
import com.epam.aliebay.dao.Interface.UserStatusDao;
import com.epam.aliebay.entity.UserStatus;

import java.util.List;
import java.util.Optional;

public class UserStatusDaoImpl extends AbstractBaseDao implements UserStatusDao {
    private static final ResultSetHandler<UserStatus> userStatusResultSetHandler =
            ResultSetHandlerFactory.getSingleResultSetHandler(ResultSetHandlerFactory.USER_STATUS_RESULT_SET_HANDLER);
    private static final ResultSetHandler<List<UserStatus>> userStatusesResultSetHandler =
            ResultSetHandlerFactory.getListResultSetHandler(ResultSetHandlerFactory.USER_STATUS_RESULT_SET_HANDLER);

    private static final String SELECT_USER_STATUS_BY_ID_QUERY = "SELECT us.id as id_user_status, usi.name, explanation FROM user_status us " +
            "LEFT JOIN user_status_detail usi ON us.id=usi.id " +
            "LEFT JOIN language l ON usi.id_language=l.id WHERE us.id = ? " +
            "AND l.id in (SELECT l.id FROM language l WHERE l.code=?)";
    private static final String SELECT_ALL_USER_STATUSES_QUERY = "SELECT us.id as id_user_status, usi.name, explanation FROM user_status us " +
            "LEFT JOIN user_status_detail usi ON us.id=usi.id " +
            "LEFT JOIN language l ON usi.id_language=l.id WHERE " +
            "l.id in (SELECT l.id FROM language l WHERE l.code = ?)";

    @Override
    public Optional<UserStatus> getUserStatusById(int id, String language) {
        return Optional.ofNullable(JdbcTemplate.select(SELECT_USER_STATUS_BY_ID_QUERY, userStatusResultSetHandler, id, language));
    }

    @Override
    public List<UserStatus> getAllUserStatuses(String language) {
        return JdbcTemplate.select(SELECT_ALL_USER_STATUSES_QUERY, userStatusesResultSetHandler, language);
    }

}
