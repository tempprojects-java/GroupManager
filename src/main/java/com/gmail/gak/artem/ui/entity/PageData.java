package com.gmail.gak.artem.ui.entity;

public class PageData {
    private final String link;
    private final String icon;
    private final String title;

    public PageData(String link, String icon, String title) {
        this.link = link;
        this.icon = icon;
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public String getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }
}
