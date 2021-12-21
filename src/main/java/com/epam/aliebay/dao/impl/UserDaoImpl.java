package com.epam.aliebay.dao.impl;

import com.epam.aliebay.dao.*;
import com.epam.aliebay.dao.Interface.UserDao;
import com.epam.aliebay.entity.User;

import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends AbstractBaseDao implements UserDao {
    private static final ResultSetHandler<User> userResultSetHandler =
            ResultSetHandlerFactory.getSingleResultSetHandler(ResultSetHandlerFactory.USER_RESULT_SET_HANDLER);
    private static final ResultSetHandler<List<User>> usersResultSetHandler =
            ResultSetHandlerFactory.getListResultSetHandler(ResultSetHandlerFactory.USER_RESULT_SET_HANDLER);

    private static final String SELECT_ALL_USERS_QUERY = "SELECT u.id, u.username, u.password, u.email, u.role, " +
            "u.first_name, u.last_name, u.phone_number, u.birth_date, id_user_status, " +
            "usi.name, us.explanation FROM user_account u INNER JOIN user_status us ON u.id_user_status=us.id " +
            "LEFT JOIN user_status_detail usi ON us.id=usi.id " +
            "LEFT JOIN language l ON usi.id_language=l.id WHERE " +
            "l.id in (SELECT l.id FROM language l WHERE l.code=?) ORDER BY u.id DESC";
    private static final String SELECT_USER_BY_ID_QUERY = "SELECT u.id, u.username, u.password, u.email, u.role, " +
            "u.first_name, u.last_name, u.phone_number, u.birth_date, id_user_status, " +
            "usi.name, us.explanation FROM user_account u INNER JOIN user_status us ON u.id_user_status=us.id " +
            "LEFT JOIN user_status_detail usi ON us.id=usi.id " +
            "LEFT JOIN language l ON usi.id_language=l.id WHERE u.id=? AND " +
            "l.id in (SELECT l.id FROM language l WHERE l.code=?)";
    private static final String INSERT_USER_QUERY = "INSERT INTO user_account " +
            "(username, password, email, role, first_name, last_name, phone_number, birth_date, id_user_status) " +
            "VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_USER_QUERY = "UPDATE user_account SET username = ?, " +
            "password = ?, email = ?, role=?, first_name=?, last_name=?, phone_number=?, birth_date=?," +
            "id_user_status=? WHERE id = ?";
    private static final String SELECT_USER_BY_USERNAME_QUERY = "SELECT u.id, u.username, u.password, u.email, u.role, " +
            "u.first_name, u.last_name, u.phone_number, u.birth_date, id_user_status, " +
            "usi.name, us.explanation FROM user_account u INNER JOIN user_status us ON u.id_user_status=us.id " +
            "LEFT JOIN user_status_detail usi ON us.id=usi.id " +
            "LEFT JOIN language l ON usi.id_language=l.id WHERE u.username=? AND " +
            "l.id in (SELECT l.id FROM language l WHERE l.code=?)";
    private static final String SELECT_USER_BY_EMAIL_QUERY = "SELECT u.id, u.username, u.password, u.email, u.role, " +
            "u.first_name, u.last_name, u.phone_number, u.birth_date, id_user_status, " +
            "usi.name, us.explanation FROM user_account u INNER JOIN user_status us ON u.id_user_status=us.id " +
            "LEFT JOIN user_status_detail usi ON us.id=usi.id " +
            "LEFT JOIN language l ON usi.id_language=l.id WHERE u.email=? AND " +
            "l.id in (SELECT l.id FROM language l WHERE l.code=?)";


    public Optional<User> getUserById(int id, String language) {
        return Optional.ofNullable(JdbcTemplate.select(SELECT_USER_BY_ID_QUERY, userResultSetHandler, id, language));
    }

    public Optional<User> getUserByUsername(String username, String language) {
        return Optional.ofNullable(JdbcTemplate.select(SELECT_USER_BY_USERNAME_QUERY, userResultSetHandler, username, language));
    }

    public Optional<User> getUserByEmail(String email, String language) {
        return Optional.ofNullable(JdbcTemplate.select(SELECT_USER_BY_EMAIL_QUERY, userResultSetHandler, email, language));
    }

    @Override
    public List<User> getAllUsers(String language) {
        return JdbcTemplate.select(SELECT_ALL_USERS_QUERY, usersResultSetHandler, language);
    }

    @Override
    public void saveUser(User user) {
        JdbcTemplate.executeUpdate(INSERT_USER_QUERY, user.getUsername(), user.getPassword(), user.getEmail(),
                user.getRole(), user.getFirstName(), user.getLastName(), user.getPhoneNumber(), user.getBirthDate(),
                user.getStatus().getId());
    }

    @Override
    public void updateUser(User user, int id) {
        JdbcTemplate.executeUpdate(UPDATE_USER_QUERY,  user.getUsername(), user.getPassword(), user.getEmail(),
                user.getRole(), user.getFirstName(), user.getLastName(), user.getPhoneNumber(), user.getBirthDate(),
                user.getStatus().getId(), id);
    }
}
