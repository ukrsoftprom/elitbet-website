package com.elitbet.service.impl;

import com.elitbet.model.Bet;
import com.elitbet.model.User;
import com.elitbet.repository.UserRepository;
import com.elitbet.service.BetService;
import com.elitbet.service.FindById;
import com.elitbet.service.UserBankService;
import com.elitbet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends FindById<User,UserRepository> implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserBankService userBankService;

    @Override
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public User createUser(User user){
        // TODO: 18.10.2018 Додати реєстрацію з поштою
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user =  userRepository.save(user);
        userBankService.createUserBank(user);
        return user;
    }

    @Override
    public User updatePassword(Long userId, String password) {
        User user = findById(userId);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
