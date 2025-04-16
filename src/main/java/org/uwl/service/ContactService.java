package org.uwl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uwl.entity.Contact;
import org.uwl.repository.ContactRepository;

@Service
public class ContactService {

  @Autowired private ContactRepository contactRepository;

  @Transactional
  public Contact save(Contact contact) {
    return contactRepository.save(contact);
  }
}
