package com.gmail.gak.artem.ui.component;

import com.gmail.gak.artem.backend.entity.Contact;
import com.gmail.gak.artem.ui.exception.FormStackValidationException;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class ContactFormsList extends VerticalLayout {
    private VerticalLayout layout;

    public ContactFormsList() {
        setSpacing(false);
        setPadding(false);

        layout = new VerticalLayout();
        layout.getStyle().set("border-top", "1px dashed #ccc");
        layout.getStyle().set("margin-top", "var(--lumo-space-m)");
        layout.setSpacing(false);
        layout.setPadding(false);
        layout.setVisible(false);

        add(layout);
    }

    public List<Contact> getContacts() throws FormStackValidationException {
        List<Contact> contacts = new ArrayList<>();
        List<ContactFast> forms = new ArrayList<>();
        boolean isValid = true;

        layout.getChildren().forEach(component -> forms.add((ContactFast) component));
        for (ContactFast form : forms) {
            try {
                Contact contact = new Contact();
                form.write(contact);
                contacts.add(contact);
            } catch (ValidationException ex) {
                isValid = false;
            }
        }

        if (!isValid) {
            throw new FormStackValidationException();
        }

        return contacts;
    }

    public void addContact() {
        if (!layout.isVisible()) {
            layout.setVisible(true);
        }

        ContactFast contactLayout = new ContactFast();
        layout.add(contactLayout);

        contactLayout.addCloseListener(event -> {
            layout.remove(contactLayout);
            if (layout.getChildren().count() == 0) {
                layout.setVisible(false);
            }
        });
    }

    public void clear() {
        layout.removeAll();
    }
}