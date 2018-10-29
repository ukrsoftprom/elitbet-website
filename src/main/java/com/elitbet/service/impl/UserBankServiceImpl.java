package com.elitbet.service.impl;

import com.elitbet.model.User;
import com.elitbet.model.UserBank;
import com.elitbet.repository.UserBankRepository;
import com.elitbet.repository.UserRepository;
import com.elitbet.service.FindById;
import com.elitbet.service.UserBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserBankServiceImpl extends FindById<UserBank,UserBankRepository> implements UserBankService {
    @Autowired
    UserBankRepository userBankRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public UserBank createUserBank(User user) {
        UserBank userBank = new UserBank();
        userBank.setUser(user);
        userBank.setBankValue(0.0);
        return userBankRepository.save(userBank);
    }

    @Override
    public UserBank findByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            return user.get().getUserBank();
        }

        throw new IllegalArgumentException("Given user with userId=" + userId + " does not exists.");
    }

    @Override
    public UserBank updateBankValue(UserBank userBank, double valueUpdate) {
        double valueOld = userBank.getBankValue();
        double valueNew = valueOld + valueUpdate;
        userBank.setBankValue(valueNew);
        return userBankRepository.save(userBank);
    }
}