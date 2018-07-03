package com.gmail.gak.artem.backend.entity;

import javax.persistence.*;

@Entity
@Table(name="Contacts")
public class Contact extends AbstractEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="group_id", nullable = true)
    private Group group;

    private String name;
    private String surname;
    private String phone;
    private String email;

    public Contact() {
    }

    public Contact(Group group, String name, String surname, String phone, String email) {
        super();
        this.group = group;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
