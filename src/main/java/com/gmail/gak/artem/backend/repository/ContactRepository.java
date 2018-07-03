package com.gmail.gak.artem.backend.repository;

import com.gmail.gak.artem.backend.entity.Contact;
import com.gmail.gak.artem.backend.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    Page<Contact> findByGroup(Group group, Pageable pageable);

    Page<Contact> findByNameLikeIgnoreCase(String nameFilter, Pageable pageable);

    Page<Contact> findByNameLikeIgnoreCaseAndGroup(String nameFilter, Group group, Pageable pageable);

    Page<Contact> findBy(Pageable pageable);

    long countByGroup(Group group);

    long countByNameLikeIgnoreCase(String nameFilter);

    long countByNameLikeIgnoreCaseAndGroup(String nameFilter, Group group);
}
