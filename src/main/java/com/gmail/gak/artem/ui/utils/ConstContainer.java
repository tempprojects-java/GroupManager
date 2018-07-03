package com.gmail.gak.artem.ui.utils;

import org.springframework.data.domain.Sort;

public class ConstContainer {
    public static final String PAGE_ROOT = "";
    public static final String PAGE_CONTACT = "contact";
    public static final String PAGE_GROUP = "group";

    public static final String ICON_CONTACT = "user-card";
    public static final String ICON_GROUP = "group";

    public static final String TITLE_CONTACT = "Contacts";
    public static final String TITLE_GROUP = "Group";

    public static final String[] CONTACT_SORT_FIELDS = {"id", "name", "surname", "email", "phone", "group"};
    public static final String[] GROUP_SORT_FIELDS = {"name"};
    public static final Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.ASC;

    public static final String VIEWPORT = "width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes";

    public static final int NOTIFICATION_DURATION = 1000;
}
