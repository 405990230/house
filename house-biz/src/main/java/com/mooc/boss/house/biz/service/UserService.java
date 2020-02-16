package com.mooc.boss.house.biz.service;

import com.mooc.boss.house.common.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    boolean addAccount(User account);

    boolean enable(String key);

    User auth(String username, String password);

    List<User> getUserByQuery(User user);

    void updateUser(User updateUser, String email);
}
