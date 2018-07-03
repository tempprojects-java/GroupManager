package com.gmail.gak.artem.backend.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Groups")
public class Group extends AbstractEntity {
    private String name;

    @OneToMany(mappedBy="group", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<Contact> contacts = new ArrayList<>();

    public Group() {
    }

    @PreRemove
    private void preRemove() {
        contacts.forEach(contact -> contact.setGroup(null));
    }

    public Group(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}
