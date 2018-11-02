package com.elitbet.service.impl;

import com.elitbet.model.WagerStatus;
import com.elitbet.repository.WagerStatusRepository;
import com.elitbet.service.WagerStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WagerStatusServiceImpl implements WagerStatusService {
    @Autowired
    WagerStatusRepository wagerStatusRepository;
    @Override
    public WagerStatus findByDescription(String description) {
        return wagerStatusRepository.findByDescriptionEquals(description);
    }
}