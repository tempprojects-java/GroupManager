package com.gmail.gak.artem.backend.service;

import com.gmail.gak.artem.backend.entity.Contact;
import com.gmail.gak.artem.backend.entity.Group;
import com.gmail.gak.artem.backend.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ContactService implements CrudService<Contact> {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public Page<Contact> findAnyMatching(String name, Group group, Pageable pageable) {
        if(name == null && group == null) {
            return find(pageable);
        }

        if(name == null) {
            return contactRepository.findByGroup(group, pageable);
        }

        String nameFilter = "%" + name + "%";
        if(group == null) {
            return contactRepository.findByNameLikeIgnoreCase(nameFilter, pageable);
        }

        return contactRepository.findByNameLikeIgnoreCaseAndGroup(nameFilter, group, pageable);
    }

    public long countAnyMatching(String name, Group group) {
        if(name == null && group == null) {
            return contactRepository.count();
        }

        if(name == null) {
            return contactRepository.countByGroup(group);
        }

        String nameFilter = "%" + name + "%";
        if(group == null) {
            return contactRepository.countByNameLikeIgnoreCase(nameFilter);
        }

        return contactRepository.countByNameLikeIgnoreCaseAndGroup(nameFilter, group);
    }

    public Page<Contact> find(Pageable pageable) {
        return contactRepository.findBy(pageable);
    }

    public Contact findById(long id) {
        return contactRepository.findById(id).orElse(null);
    }

    @Override
    public JpaRepository<Contact, Long> getRepository() {
        return contactRepository;
    }

    @Override
    @Transactional
    public Contact createNew() {
        return new Contact();
    }
}
