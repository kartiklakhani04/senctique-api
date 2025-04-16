package org.uwl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uwl.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {}
