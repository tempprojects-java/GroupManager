package com.gmail.gak.artem.ui.views.contact;

import com.gmail.gak.artem.backend.entity.Contact;
import com.gmail.gak.artem.backend.entity.Group;
import com.gmail.gak.artem.ui.component.SearchBox;
import com.gmail.gak.artem.ui.component.ToolbarLayout;
import com.gmail.gak.artem.ui.utils.ConstContainer;
import com.gmail.gak.artem.ui.views.HasNotification;
import com.gmail.gak.artem.ui.views.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridMultiSelectionModel;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.*;

import static com.gmail.gak.artem.ui.utils.ConstContainer.NOTIFICATION_DURATION;

@Route(value = ConstContainer.PAGE_CONTACT, layout = MainView.class)
@RouteAlias(value = ConstContainer.PAGE_ROOT, layout = MainView.class)
public class ContactView extends VerticalLayout implements HasUrlParameter<Long>, BeforeEnterObserver, HasNotification {
    private Grid<Contact> grid;
    private SearchBox searchBox;
    private ComboBox<Group> comboBox;
    private Button addBtn;
    private Button deleteBtn;

    private final ContactPresenter presenter;
    private final ContactDialog dialog;

    public ContactView(ContactPresenter presenter, ContactDialog dialog) {
        this.presenter = presenter;
        this.dialog = dialog;

        addToolbar();
        addGrid();
        presenter.init(this);
    }

    private void addToolbar() {
        comboBox = new ComboBox<>();
        comboBox.setItemLabelGenerator(Group::getName);
        comboBox.setPlaceholder("All");

        searchBox = new SearchBox();
        searchBox.setPlaceholder("Find Name");

        addBtn = new Button("Create", event -> presenter.create());
        addBtn.getElement().setAttribute("theme", "primary");

        deleteBtn = new Button("Delete", event -> presenter.delete());
        deleteBtn.setEnabled(false);
        deleteBtn.getElement().setAttribute("theme", "error");

        ToolbarLayout toolbarLayout = new ToolbarLayout();
        toolbarLayout.add(ToolbarLayout.SIDE.LEFT, addBtn, deleteBtn);
        toolbarLayout.add(ToolbarLayout.SIDE.RIGHT, comboBox, searchBox);

        add(toolbarLayout);
    }

    private void addGrid() {
        grid = new Grid<>();

        GridMultiSelectionModel<Contact> selectionModel = (GridMultiSelectionModel<Contact>) grid.setSelectionMode(Grid.SelectionMode.MULTI);
        selectionModel.addMultiSelectionListener(multiSelectionEvent -> presenter.select(multiSelectionEvent));
//        selectionModel.setSelectAllCheckboxVisibility(GridMultiSelectionModel.SelectAllCheckboxVisibility.VISIBLE);

        grid.addColumn(Contact::getId)
                .setHeader("ID")
                .setSortable(true).setSortProperty("id")
                .setFlexGrow(0).setWidth("80px");
        grid.addColumn(Contact::getName)
                .setHeader("Name")
                .setSortable(true).setSortProperty("name");
        grid.addColumn(Contact::getSurname)
                .setHeader("Surname")
                .setSortable(true).setSortProperty("surname");
        grid.addColumn(Contact::getEmail)
                .setHeader("E-mail")
                .setSortable(true).setSortProperty("email");
        grid.addColumn(Contact::getPhone).
                setHeader("Phone")
                .setSortable(true).setSortProperty("phone");
        grid.addColumn(contact -> {
            if(contact.getGroup() == null) {
                return "- no group -";
            }
            return contact.getGroup().getName();

        }).setHeader("Group")
                .setSortable(true).setSortProperty("group");
        grid.addColumn(new ComponentRenderer<>(contact -> {
            Button button = new Button(new Icon(VaadinIcon.EDIT));
            button.addClickListener(event -> presenter.edit(contact.getId()));
            return button;
        })).setHeader("")
                .setFlexGrow(0).setWidth("100px");

        add(grid);
    }

    public Grid<Contact> getGrid() {
        return grid;
    }

    public SearchBox getSearchBox() {
        return searchBox;
    }

    public ContactDialog getDialog() {
        return dialog;
    }

    public ComboBox<Group> getComboBox() {
        return comboBox;
    }

    public Button getAddButton() {
        return addBtn;
    }

    public Button getDeleteButton() {
        return deleteBtn;
    }

    @Override
    public void showNotification(String text) {
        Notification.show(text, NOTIFICATION_DURATION, Notification.Position.BOTTOM_STRETCH);
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Long parameter) {
        if(parameter != null) {
            presenter.onNavigation(parameter);
            dialog.open();
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {

    }
}
