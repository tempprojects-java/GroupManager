package com.gmail.gak.artem.ui.component;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.dom.DebouncePhase;

public class SearchBox extends Composite<Div> {
    private TextField textField;
    private Button button;

    public SearchBox() {
        textField = new TextField();
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        ComponentUtil.addListener(textField, SearchValueChanged.class,
                e -> fireEvent(new ValueChanged(this, false)));

        button = new Button(new Icon(VaadinIcon.CLOSE), event -> clean());
        getContent().add(textField, button);
    }

    public String getValue() {
        return textField.getValue();
    }

    public void setValue(String value) {
        textField.setValue(value);
    }

    public void setPlaceholder(String placeholder) {
        textField.setPlaceholder(placeholder);
    }

    public void clean() {
        setValue("");
        textField.focus();
    }

    public void addValueChangeListener(ComponentEventListener<ValueChanged> listener) {
        this.addListener(ValueChanged.class, listener);
    }


    @DomEvent(value = "value-changed", debounce = @DebounceSettings(timeout = 300, phases = DebouncePhase.TRAILING))
    public static class SearchValueChanged extends ComponentEvent<TextField> {
        public SearchValueChanged(TextField source, boolean fromClient) {
            super(source, fromClient);
        }
    }

    public class ValueChanged extends ComponentEvent<SearchBox> {
        public ValueChanged(SearchBox source, boolean fromClient) {
            super(source, fromClient);
        }
    }
}
