package com.elitbet.repository;

import com.elitbet.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
    Client findByName(String name);
}