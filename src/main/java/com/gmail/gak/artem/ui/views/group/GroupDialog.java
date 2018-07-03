package com.gmail.gak.artem.ui.views.group;

import com.gmail.gak.artem.backend.entity.Contact;
import com.gmail.gak.artem.backend.entity.Group;
import com.gmail.gak.artem.ui.component.ContactFormsList;
import com.gmail.gak.artem.ui.exception.FormStackValidationException;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
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

import java.util.List;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GroupDialog extends Dialog {

    private ContactFormsList formList;

    private TextField name;

    private Button saveBtn;
    private Button closeBtn;
    private Button addBtn;

    private BeanValidationBinder<Group> binder = new BeanValidationBinder<>(Group.class);

    public GroupDialog() {
        setCloseOnEsc(true);
        setCloseOnOutsideClick(true);

        formList = new ContactFormsList();

        name = new TextField("Name");

        binder.forField(name).asRequired("This value can't be empty").bind(Group::getName, Group::setName);

        saveBtn = new Button("Save");
        saveBtn.getElement().setAttribute("theme", "primary");

        closeBtn = new Button("Close", event -> close());

        addBtn = new Button("Add Contact", event -> formList.addContact());
        addBtn.getStyle().set("margin-top", "var(--lumo-space-m)");
        addBtn.getElement().setAttribute("theme","success primary");

        HorizontalLayout buttonsLayout = new HorizontalLayout(saveBtn, closeBtn);
        buttonsLayout.setAlignItems(FlexComponent.Alignment.END);
        buttonsLayout.getStyle().set("paddingTop", "var(--lumo-space-m)");

        FormLayout formLayout = new FormLayout();
        formLayout.add(name);

        add(formLayout, addBtn, formList, buttonsLayout);
    }

    public List<Contact> getContact() throws FormStackValidationException {
        return formList.getContacts();
    }

    public void read(Group group) {
        binder.readBean(group);
    }

    public void write(Group group) throws ValidationException {
        binder.writeBean(group);
    }

    public Registration addSaveListener(ComponentEventListener<ClickEvent<Button>> listener) {
        return saveBtn.addClickListener(listener);
    }

    public void clear() {
        formList.clear();
    }
}
