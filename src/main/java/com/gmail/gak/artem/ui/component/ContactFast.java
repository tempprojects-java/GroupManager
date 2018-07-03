package com.gmail.gak.artem.ui.component;

import com.gmail.gak.artem.backend.entity.Contact;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;


public class ContactFast extends FormLayout {
    private int index;
    private TextField name;
    private TextField surname;
    private TextField phone;
    private TextField email;

    private Button deleteButton;

    private BeanValidationBinder<Contact> binder = new BeanValidationBinder<>(Contact.class);

    public ContactFast() {
        name = new TextField("Name");
        surname = new TextField("Surname");
        phone = new TextField("Phone");
        email = new TextField("E-mail");

        binder.forField(name).asRequired("This value can't be empty").bind(Contact::getName, Contact::setName);
        binder.bind(surname, "surname");
        binder.bind(email, "email");
        binder.bind(phone, "phone");

        deleteButton = new Button(VaadinIcon.CLOSE.create());

        VerticalLayout actionLayout = new VerticalLayout(deleteButton);
        actionLayout.setSpacing(false);
        actionLayout.setPadding(false);
        actionLayout.getStyle().set("padding-top", "var(--lumo-space-m)");

        add(name, surname, phone, email, actionLayout);
        getStyle().set("border-bottom", "1px dashed #ccc");
        getStyle().set("padding-bottom", "var(--lumo-space-m)");
    }

    public Registration addCloseListener(ComponentEventListener<ClickEvent<Button>> listener) {
        return deleteButton.addClickListener(listener);
    }

    public void write(Contact contact) throws ValidationException {
        binder.writeBean(contact);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
