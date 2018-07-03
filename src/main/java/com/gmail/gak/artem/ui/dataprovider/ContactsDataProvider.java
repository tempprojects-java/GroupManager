package com.gmail.gak.artem.ui.dataprovider;

import com.gmail.gak.artem.backend.entity.Contact;
import com.gmail.gak.artem.backend.service.ContactService;
import com.gmail.gak.artem.ui.entity.ContactFilter;
import com.gmail.gak.artem.ui.utils.ConstContainer;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.QuerySortOrderBuilder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.vaadin.artur.spring.dataprovider.FilterablePageableDataProvider;

import java.util.List;
import java.util.function.Consumer;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ContactsDataProvider extends FilterablePageableDataProvider<Contact, ContactFilter> {
    private final ContactService contactService;

    private List<QuerySortOrder> defaultSortOrders;
    private Consumer<Page<Contact>> pageObserver;

    public ContactsDataProvider(ContactService contactService) {
        this.contactService = contactService;
        setSortOrders(ConstContainer.DEFAULT_SORT_DIRECTION, ConstContainer.CONTACT_SORT_FIELDS);
    }

    private void setSortOrders(Sort.Direction direction, String[] properties) {
        QuerySortOrderBuilder builder = new QuerySortOrderBuilder();
        for (String property : properties) {
            if (direction.isAscending()) {
                builder.thenAsc(property);
            } else {
                builder.thenDesc(property);
            }
        }
        defaultSortOrders = builder.build();
    }

    @Override
    protected Page<Contact> fetchFromBackEnd(Query<Contact, ContactFilter> query, Pageable pageable) {
        ContactFilter filter = query.getFilter().orElse(null);
        if(filter == null) {
            return contactService.find(pageable);
        }

        Page<Contact> page = contactService.findAnyMatching(filter.getName(), filter.getGroup(), pageable);
        if (pageObserver != null) {
            pageObserver.accept(page);
        }
        return page;
    }

    @Override
    protected int sizeInBackEnd(Query<Contact, ContactFilter> query) {
        ContactFilter filter = query.getFilter().orElse(null);

        if(filter == null){
            return (int) contactService.count();
        }

        return (int) contactService.countAnyMatching(filter.getName(), filter.getGroup());
    }

    @Override
    protected List<QuerySortOrder> getDefaultSortOrders() {
        return defaultSortOrders;
    }
}
