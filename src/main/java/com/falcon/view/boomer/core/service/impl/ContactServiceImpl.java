package com.falcon.view.boomer.core.service.impl;

import com.falcon.view.boomer.core.domain.dto.ContactMessageDTO;
import com.falcon.view.boomer.core.domain.entity.ContactMessageEntity;
import com.falcon.view.boomer.core.mapper.ContactMessageMapper;
import com.falcon.view.boomer.core.repository.ContactRepository;
import com.falcon.view.boomer.core.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;
    private final ContactMessageMapper contactMessageMapper;

    @Override
    public void add(ContactMessageDTO contact) {
        ContactMessageEntity contactMessageEntity = contactMessageMapper.toEntity(contact);
        contactRepository.save(contactMessageEntity);
    }
}
