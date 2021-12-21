package com.epam.aliebay.dao.Interface;

import com.epam.aliebay.entity.UserStatus;

import java.util.List;
import java.util.Optional;

public interface UserStatusDao {

        Optional<UserStatus> getUserStatusById(int id, String language);

        List<UserStatus> getAllUserStatuses(String language);
    }
