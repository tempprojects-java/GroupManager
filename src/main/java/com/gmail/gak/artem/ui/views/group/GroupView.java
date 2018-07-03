package com.gmail.gak.artem.ui.views.group;

import com.gmail.gak.artem.backend.entity.Group;
import com.gmail.gak.artem.ui.component.ToolbarLayout;
import com.gmail.gak.artem.ui.utils.ConstContainer;
import com.gmail.gak.artem.ui.views.HasNotification;
import com.gmail.gak.artem.ui.views.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridMultiSelectionModel;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.*;

import static com.gmail.gak.artem.ui.utils.ConstContainer.NOTIFICATION_DURATION;

@Route(value = ConstContainer.PAGE_GROUP, layout = MainView.class)
public class GroupView extends VerticalLayout implements HasUrlParameter<Long>, BeforeEnterObserver, HasNotification {
    private GroupPresenter presenter;
    private Button addBtn;
    private Button deleteBtn;
    private Grid<Group> grid;
    private final GroupDialog dialog;

    public GroupView(GroupPresenter presenter, GroupDialog dialog) {
        this.presenter = presenter;
        this.dialog = dialog;

        addToolbar();
        addGrid();
        presenter.init(this);
    }

    private void addGrid() {
        grid = new Grid<>();
        grid.setHeightByRows(true);

        GridMultiSelectionModel<Group> selectionModel = (GridMultiSelectionModel<Group>) grid.setSelectionMode(Grid.SelectionMode.MULTI);
        selectionModel.addMultiSelectionListener(multiSelectionEvent -> presenter.select(multiSelectionEvent));

        grid.addColumn(Group::getId)
                .setHeader("ID")
                .setSortable(true).setSortProperty("id")
                .setFlexGrow(0).setWidth("80px");
        grid.addColumn(Group::getName)
                .setHeader("Name")
                .setSortable(true).setSortProperty("name");
        grid.addColumn(new ComponentRenderer<>(group -> {
            Button button = new Button(new Icon(VaadinIcon.EDIT));
            button.addClickListener(event -> presenter.edit(group.getId()));
            return button;
        })).setHeader("")
                .setFlexGrow(0).setWidth("100px");

        add(grid);
    }

    private void addToolbar() {
        addBtn = new Button("Create", event -> presenter.create());
        addBtn.getElement().setAttribute("theme", "primary");

        deleteBtn = new Button("Delete", event -> presenter.delete());
        deleteBtn.setEnabled(false);
        deleteBtn.getElement().setAttribute("theme", "error");

        ToolbarLayout toolbarLayout = new ToolbarLayout();
        toolbarLayout.add(ToolbarLayout.SIDE.LEFT, addBtn, deleteBtn);

        add(toolbarLayout);
    }

    public GroupDialog getDialog() {
        return dialog;
    }
    
    public Button getAddButton() {
        return addBtn;
    }

    public Button getDeleteButton() {
        return deleteBtn;
    }

    public Grid<Group> getGrid() {
        return grid;
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
