package org.uwl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.uwl.entity.Contact;
import org.uwl.service.ContactService;

@RestController
@RequestMapping("/contact")
public class ContactController {

  @Autowired private ContactService contactService;

  @PostMapping("/save")
  public ResponseEntity<Contact> save(@RequestBody Contact contact) {
    return ResponseEntity.ok(contactService.save(contact));
  }
}
