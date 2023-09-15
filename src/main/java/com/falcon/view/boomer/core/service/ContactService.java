package com.falcon.view.boomer.core.service;

import com.falcon.view.boomer.core.domain.dto.ContactMessageDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ContactService {
    void add(ContactMessageDTO contact) throws JsonProcessingException;
}
