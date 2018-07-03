package com.gmail.gak.artem.ui.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ToolbarLayout extends HorizontalLayout {
    HorizontalLayout leftSide;
    HorizontalLayout rightSide;

    public enum SIDE {
        LEFT, RIGHT
    }

    public ToolbarLayout() {
        setSpacing(false);
        setPadding(false);
        setWidth("100%");
        leftSide = new HorizontalLayout();
        rightSide = new HorizontalLayout();
        rightSide.setAlignItems(FlexComponent.Alignment.BASELINE);

        add(init(leftSide),init(rightSide, true));
    }

    private Component init(HorizontalLayout component) {
        return init(component, false);
    }

    private Component init(HorizontalLayout component, boolean rightAlign) {
        Div wrapper = new Div(component);

        VerticalLayout container = new VerticalLayout(wrapper);
        container.setSpacing(false);
        container.setPadding(false);
        container.setWidth("50%");
        if(rightAlign) {
            container.setAlignItems(Alignment.END);
        }

        return container;
    }

    public void add(SIDE side, Component... components) {
        if(SIDE.LEFT.equals(side)) {
            leftSide.add(components);
        }

        if(SIDE.RIGHT.equals(side)) {
            rightSide.add(components);
        }
    }
}
