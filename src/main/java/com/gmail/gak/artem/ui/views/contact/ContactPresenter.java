package com.gmail.gak.artem.ui.views.contact;

import com.gmail.gak.artem.backend.entity.Contact;
import com.gmail.gak.artem.backend.service.ContactService;
import com.gmail.gak.artem.backend.service.GroupService;
import com.gmail.gak.artem.ui.dataprovider.ContactsDataProvider;
import com.gmail.gak.artem.ui.dataprovider.GroupDataProvider;
import com.gmail.gak.artem.ui.entity.ContactFilter;
import com.gmail.gak.artem.ui.utils.ConstContainer;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.selection.MultiSelectionEvent;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.Set;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ContactPresenter {
    private ContactView view;
    private Contact contact;
    private boolean isNew;

    private final ContactsDataProvider dataProvider;
    private final GroupDataProvider groupDataProvider;
    private final ContactService contactService;
    private final GroupService groupService;

    private final ContactFilter filter;

    public ContactPresenter(ContactsDataProvider dataProvider, GroupDataProvider groupDataProvider, ContactService contactService, GroupService groupService, ContactFilter filter) {
        this.filter = filter;
        this.dataProvider = dataProvider;
        this.groupDataProvider = groupDataProvider;
        this.contactService = contactService;
        this.groupService = groupService;
    }

    public void init(ContactView view) {
        this.view = view;
        dataProvider.setSortOrder(new QuerySortOrder("id", SortDirection.DESCENDING));

        view.getGrid().setDataProvider(dataProvider);

        view.getDialog().addSaveListener(event -> save());
        view.getDialog().addOpenedChangeListener(event -> {
            if(!event.isOpened()) {
                closeContact();
            }
        });
        view.getDialog().getGroup().setDataProvider(groupDataProvider);

        view.getComboBox().setDataProvider(groupDataProvider);
        view.getComboBox().addValueChangeListener(event -> {
            filter.setGroup(event.getValue());
            filterChange();
        });

        view.getSearchBox().addValueChangeListener(event -> {
            filter.setName(view.getSearchBox().getValue());
            filterChange();
        });

        if(filter.getName() != null) {
            view.getSearchBox().setValue(filter.getName());
        }
        if(filter.getGroup() != null) {
            if(groupService.findById(filter.getGroup().getId()) == null) {
                filter.setGroup(null);
            }
            view.getComboBox().setValue(filter.getGroup());
        }

        filterChange();
    }

    public void onNavigation(long id) {
        isNew = false;
        contact = contactService.findById(id);
        view.getDialog().read(contact);
        view.getDialog().open();
    }

    public void filterChange() {
        view.getGrid().deselectAll();
        dataProvider.setFilter(filter);
    }

    public void select(MultiSelectionEvent<Grid<Contact>, Contact> event) {
        long size = event.getAllSelectedItems().size();
        if(size > 0) {
            view.getDeleteButton().setText("Delete (" + size + ")");
            view.getDeleteButton().setEnabled(true);

        } else {
            view.getDeleteButton().setEnabled(false);
            view.getDeleteButton().setText("Delete");
        }
    }

    public void save() {
        try {
            view.getDialog().write(contact);
            contactService.save(contact);

            if(isNew) {
                dataProvider.refreshAll();
                isNew = false;
            }
            else {
                dataProvider.refreshItem(contact);
            }
            view.showNotification("Contact saved!");
        } catch (ValidationException ex) {
            view.showNotification(ex.getMessage());
        }
    }

    public void edit(Long id) {
        UI.getCurrent().navigate(ConstContainer.PAGE_CONTACT + "/" + id);
    }

    public void create() {
        isNew = true;
        contact = new Contact();
        view.getDialog().read(contact);
        view.getDialog().open();
    }

    public void delete() {
        Set<Contact> contacts = view.getGrid().getSelectedItems();
        for (Contact contact: contacts) {
            contactService.delete(contact.getId());
        }
        view.getGrid().deselectAll();
        dataProvider.refreshAll();
        view.showNotification("Contact removed");
    }

    private void closeContact() {
        UI.getCurrent().navigate(ConstContainer.PAGE_CONTACT);
    }

    public boolean isNew() {
        return isNew;
    }
}
