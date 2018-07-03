package com.gmail.gak.artem.ui.views.group;

import com.gmail.gak.artem.backend.entity.Contact;
import com.gmail.gak.artem.backend.entity.Group;
import com.gmail.gak.artem.backend.service.GroupService;
import com.gmail.gak.artem.ui.dataprovider.GroupDataProvider;
import com.gmail.gak.artem.ui.exception.FormStackValidationException;
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

import java.util.List;
import java.util.Set;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GroupPresenter {
    private boolean isNew;
    private final GroupDataProvider groupDataProvider;
    private final GroupService groupService;
    private GroupView view;
    private Group group;

    public GroupPresenter(GroupDataProvider groupDataProvider, GroupService groupService) {
        this.groupDataProvider = groupDataProvider;
        this.groupService = groupService;
    }

    public void init(GroupView view) {
        this.view = view;
        groupDataProvider.setSortOrder(new QuerySortOrder("id", SortDirection.DESCENDING));

        view.getGrid().setDataProvider(groupDataProvider);

        view.getDialog().addSaveListener(event -> save());
        view.getDialog().addOpenedChangeListener(event -> {
            if(!event.isOpened()) {
                closeGroup();
            }
        });
    }

    public void onNavigation(long id) {
        isNew = false;
        group = groupService.findById(id);
        view.getDialog().clear();
        view.getDialog().read(group);
        view.getDialog().open();
    }

    public void select(MultiSelectionEvent<Grid<Group>,Group> event) {
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
            view.getDialog().write(group);

            try {
                List<Contact> contacts = view.getDialog().getContact();
                for(Contact contact: contacts) {
                    contact.setGroup(group);
                }
                group.setContacts(contacts);
            } catch (FormStackValidationException ex) {
                view.showNotification("Please fill out all required fields marked in red.");
                return;
            }

            groupService.save(group);

            view.getDialog().close();

            if(isNew) {
                groupDataProvider.refreshAll();
                isNew = false;
            }
            else {
                groupDataProvider.refreshItem(group);
            }
            view.showNotification("Contact saved!");
        } catch (ValidationException ex) {
            view.showNotification(ex.getMessage());
        }
    }

    public void edit(Long id) {
        UI.getCurrent().navigate(ConstContainer.PAGE_GROUP + "/" + id);
    }

    public void create() {
        isNew = true;
        group = new Group();
        view.getDialog().clear();
        view.getDialog().read(group);
        view.getDialog().open();
    }

    public void delete() {
        Set<Group> groups = view.getGrid().getSelectedItems();
        for (Group group: groups) {
            groupService.delete(group.getId());
        }
        view.getGrid().deselectAll();
        groupDataProvider.refreshAll();
        view.showNotification("Group deleted!");
    }

    private void closeGroup() {
        UI.getCurrent().navigate(ConstContainer.PAGE_GROUP);
    }
}
