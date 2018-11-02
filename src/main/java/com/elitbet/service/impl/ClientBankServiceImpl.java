package com.elitbet.service.impl;

import com.elitbet.model.Client;
import com.elitbet.model.ClientBank;
import com.elitbet.repository.ClientBankRepository;
import com.elitbet.service.FindById;
import com.elitbet.service.ClientBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientBankServiceImpl extends FindById<ClientBank,ClientBankRepository> implements ClientBankService {
    @Autowired
    ClientBankRepository clientBankRepository;

    @Override
    public void createClientBank(Client client) {
        ClientBank clientBank = new ClientBank();
        clientBank.setClient(client);
        clientBank.setBankValue(100.0);
        clientBankRepository.save(clientBank);
    }

    @Override
    public void updateBankValue(ClientBank clientBank, double valueUpdate) {
        double valueOld = clientBank.getBankValue();
        double valueNew = valueOld + valueUpdate;
        clientBank.setBankValue(valueNew);
        clientBankRepository.save(clientBank);
    }
}