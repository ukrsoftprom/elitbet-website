package com.elitbet.service;

import com.elitbet.model.Client;
import com.elitbet.model.ClientBank;

public interface ClientBankService {
    ClientBank findById(Long clientBankId);
    void createClientBank(Client client);
    void updateBankValue(ClientBank clientBank, double valueUpdate);
}