package com.elitbet.repository;

import com.elitbet.model.UserBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBankRepository extends JpaRepository<UserBank, Long> {
}
