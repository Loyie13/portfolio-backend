package com.Portfolio.service;

import com.Portfolio.model.ContactMessage;
import com.Portfolio.repository.ContactMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    private final ContactMessageRepository contactMessageRepository;

    @Autowired
    public ContactService(ContactMessageRepository contactMessageRepository) {
        this.contactMessageRepository = contactMessageRepository;
    }

    public ContactMessage saveMessage(ContactMessage contactMessage) {
        return contactMessageRepository.save(contactMessage);
    }
}