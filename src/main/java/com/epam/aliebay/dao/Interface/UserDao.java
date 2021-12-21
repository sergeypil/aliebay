package com.epam.aliebay.dao.Interface;

import com.epam.aliebay.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> getUserById(int id, String language);

    List<User> getAllUsers(String language);

    void saveUser(User user);

    void updateUser(User user, int id);

    Optional<User> getUserByUsername(String username, String language);

    Optional<User> getUserByEmail(String email, String language);

}