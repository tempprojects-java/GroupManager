package com.gmail.gak.artem.ui.dataprovider;

import com.gmail.gak.artem.backend.entity.Group;
import com.gmail.gak.artem.backend.service.GroupService;
import com.gmail.gak.artem.ui.utils.ConstContainer;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.QuerySortOrderBuilder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.vaadin.artur.spring.dataprovider.FilterablePageableDataProvider;

import java.util.List;

@SpringComponent
public class GroupDataProvider extends FilterablePageableDataProvider<Group, String> {

    private GroupService groupService;
    private List<QuerySortOrder> defaultSortOrders;

    public GroupDataProvider(GroupService groupService) {
        this.groupService = groupService;
        setSortOrders(ConstContainer.DEFAULT_SORT_DIRECTION, ConstContainer.GROUP_SORT_FIELDS);
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
    protected Page<Group> fetchFromBackEnd(Query<Group, String> query, Pageable pageable) {
        return groupService.find(pageable);
    }

    @Override
    protected List<QuerySortOrder> getDefaultSortOrders() {
        return defaultSortOrders;
    }

    @Override
    protected int sizeInBackEnd(Query<Group, String> query) {
        return (int) groupService.count();
    }
}
