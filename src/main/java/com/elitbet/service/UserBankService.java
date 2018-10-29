package com.elitbet.service;

import com.elitbet.model.User;
import com.elitbet.model.UserBank;

public interface UserBankService {
    UserBank createUserBank(User user);
    UserBank findById(Long userBankId);
    UserBank findByUserId(Long userId);
    UserBank updateBankValue(UserBank userBank, double valueUpdate);
}
