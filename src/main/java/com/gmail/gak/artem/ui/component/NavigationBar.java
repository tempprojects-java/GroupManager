package com.gmail.gak.artem.ui.component;

import com.gmail.gak.artem.ui.entity.PageData;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;

import java.util.ArrayList;
import java.util.List;

public class NavigationBar extends VerticalLayout implements AfterNavigationObserver {

    private List<String> hrefs = new ArrayList<>();
    private String defaultHref;
    private String currentHref;
    private Tabs tabs = new Tabs();

    public void init(List<PageData> pages, String defaultHref) {
        this.defaultHref = defaultHref;

        for (PageData page : pages) {
            Icon icon = new Icon("vaadin", page.getIcon());
            icon.setSize("3em");
            Tab tab = new Tab(icon, new Span(page.getTitle()));
            tab.getElement().setAttribute("theme", "icon-on-top");
            hrefs.add(page.getLink());
            tabs.add(tab);
        }

        tabs.addSelectedChangeListener(e -> navigate());
        add(tabs);
    }

    private void navigate() {
        int idx = tabs.getSelectedIndex();
        if (idx >= 0 && idx < hrefs.size()) {
            String href = hrefs.get(idx);

            if (!href.equals(currentHref)) {
                UI.getCurrent().navigate(href);
            }
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        String href = event.getLocation().getFirstSegment().isEmpty() ? defaultHref
                : event.getLocation().getFirstSegment();
        currentHref = href;
        tabs.setSelectedIndex(hrefs.indexOf(href));
    }
}
