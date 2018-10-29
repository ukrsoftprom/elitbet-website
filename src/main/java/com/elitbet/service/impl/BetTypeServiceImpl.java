package com.elitbet.service.impl;

import com.elitbet.model.BetType;
import com.elitbet.repository.BetTypeRepository;
import com.elitbet.service.BetTypeService;
import com.elitbet.service.FindById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BetTypeServiceImpl extends FindById<BetType,BetTypeRepository> implements BetTypeService {
    @Autowired
    BetTypeRepository betTypeRepository;

    @Override
    public BetType findByDescription(String description) {
        return betTypeRepository.findAllByDescriptionEquals(description);
    }
}
