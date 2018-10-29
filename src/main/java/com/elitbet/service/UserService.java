package com.elitbet.service;

import com.elitbet.model.User;

import java.util.List;

public interface UserService {
    User findById(Long userId);
    User findByUsername(String username);
    User createUser(User user);
    User updatePassword(Long userId, String password);
    List<User> findAll();
}
