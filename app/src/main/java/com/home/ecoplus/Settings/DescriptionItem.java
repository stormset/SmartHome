package com.home.ecoplus.Settings;

import java.util.List;

public class DescriptionItem extends SettingsItem {

    private String text;

    public DescriptionItem(String text) {
        this.text = text;
    }


    public String getText() {
        return text;
    }

    public void setText(String name) {
        this.text = name;
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
        return TYPE_DESCRIPTION;
    }
}
