package com.elitbet.service.impl;

import com.elitbet.model.Client;
import com.elitbet.repository.ClientRepository;
import com.elitbet.service.FindById;
import com.elitbet.service.ClientBankService;
import com.elitbet.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl extends FindById<Client,ClientRepository> implements ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientBankService clientBankService;

    @Override
    public Client findByName(String username){
        return clientRepository.findByName(username);
    }

    @Override
    public void createClient(Client client){
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client =  clientRepository.save(client);
        clientBankService.createClientBank(client);
    }

    @Override
    public Client updatePassword(Long userId, String password) {
        Client client = findById(userId);
        client.setPassword(passwordEncoder.encode(password));
        return clientRepository.save(client);
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

}