package com.elitbet.service;

import com.elitbet.model.Client;

import java.util.List;

public interface ClientService {
    Client findById(Long userId);
    Client findByName(String name);
    void createClient(Client client);
    Client updatePassword(Long userId, String password);
    List<Client> findAll();
}
