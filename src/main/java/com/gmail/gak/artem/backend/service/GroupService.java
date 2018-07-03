package com.gmail.gak.artem.backend.service;

import com.gmail.gak.artem.backend.entity.Group;
import com.gmail.gak.artem.backend.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GroupService implements CrudService<Group> {
    private final GroupRepository groupRepository;
    private final ContactService contactService;

    @Autowired
    public GroupService(GroupRepository groupRepository, ContactService contactService) {
        this.groupRepository= groupRepository;
        this.contactService = contactService;
    }

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Transactional
    public Page<Group> find(Pageable pageable) {
        return groupRepository.findBy(pageable);
    }

    @Override
    public JpaRepository getRepository() {
        return groupRepository;
    }

    @Override
    @Transactional
    public Group createNew() {
        return new Group();
    }

    public Group findById(long id) {
        return groupRepository.findById(id).orElse(null);
    }
}
