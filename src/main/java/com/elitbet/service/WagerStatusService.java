package com.elitbet.service;

import com.elitbet.model.WagerStatus;

public interface WagerStatusService {
    WagerStatus findByDescription(String description);
}
