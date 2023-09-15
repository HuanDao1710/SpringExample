package com.falcon.view.boomer.core.controller;

import com.falcon.view.boomer.core.domain.dto.ContactMessageDTO;
import com.falcon.view.boomer.core.service.ContactService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ContactController {
    private final ContactService contactService;

    @PostMapping("/contactMessage")
    public ResponseEntity<Object> insertContact(@RequestBody @Valid ContactMessageDTO contact) throws
            JsonProcessingException {
        contactService.add(contact);
        return ResponseEntity.ok().build();
    }
}
