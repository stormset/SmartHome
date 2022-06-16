package com.home.ecoplus.Settings;

import java.util.List;

public class HeaderItem extends SettingsItem {

    private String name;

    public HeaderItem(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<ChildItem> getChildList() {
        return null;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    @Override
    public int getType() {
        return TYPE_HEADER;
    }
}
