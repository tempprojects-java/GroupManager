package com.gmail.gak.artem.ui.views.contact;

import com.gmail.gak.artem.backend.entity.Contact;
import com.gmail.gak.artem.backend.entity.Group;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ContactDialog extends Dialog {

    private TextField name;
    private TextField surname;
    private TextField email;
    private TextField phone;

    private ComboBox<Group> group;

    private Button saveBtn;
    private Button closeBtn;

    private BeanValidationBinder<Contact> binder = new BeanValidationBinder<>(Contact.class);

    public ContactDialog() {
        setCloseOnEsc(true);
        setCloseOnOutsideClick(true);

        name = new TextField("Name");
        surname = new TextField("Surname");
        email = new TextField("E-mail");
        phone = new TextField("Phone");

        group = new ComboBox<>("Group");
        group.setItemLabelGenerator(Group::getName);

        binder.forField(name).asRequired("This value can't be empty").bind(Contact::getName, Contact::setName);
        binder.bind(surname, "surname");
        binder.bind(email, "email");
        binder.bind(phone, "phone");
        binder.bind(group, "group");

        saveBtn = new Button("Save");
        saveBtn.getElement().setAttribute("theme", "primary");
        closeBtn = new Button("Close", event -> close());

        HorizontalLayout buttonsLayout = new HorizontalLayout(saveBtn, closeBtn);
        buttonsLayout.setAlignItems(FlexComponent.Alignment.END);
        buttonsLayout.getStyle().set("paddingTop", "var(--lumo-space-m)");

        FormLayout formLayout = new FormLayout();
        formLayout.add(name, surname, email, phone, group);

        add(formLayout, buttonsLayout);
    }

    public void read(Contact contact) {
        binder.readBean(contact);
    }

    public void write(Contact contact) throws ValidationException {
        binder.writeBean(contact);
    }

    public Registration addSaveListener(ComponentEventListener<ClickEvent<Button>> listener) {
        return saveBtn.addClickListener(listener);
    }

    public ComboBox<Group> getGroup() {
        return group;
    }
}
