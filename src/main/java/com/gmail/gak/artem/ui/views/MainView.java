package com.gmail.gak.artem.ui.views;

import com.gmail.gak.artem.ui.component.NavigationBar;
import com.gmail.gak.artem.ui.entity.PageData;
import com.gmail.gak.artem.ui.utils.ConstContainer;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Viewport(ConstContainer.VIEWPORT)
public class MainView extends VerticalLayout implements RouterLayout, BeforeEnterObserver {

    private NavigationBar navigationBar;


    @Autowired
    public MainView() {
        navigationBar = new NavigationBar();
        List<PageData> pages = new ArrayList<>();

        pages.add(new PageData(ConstContainer.PAGE_CONTACT, ConstContainer.ICON_CONTACT, ConstContainer.TITLE_CONTACT));
        pages.add(new PageData(ConstContainer.PAGE_GROUP, ConstContainer.ICON_GROUP, ConstContainer.TITLE_GROUP));

        navigationBar.init(pages, ConstContainer.PAGE_CONTACT);

        add(navigationBar);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {

    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        if (content != null) {
            getElement().appendChild(content.getElement());
        }
    }
}
