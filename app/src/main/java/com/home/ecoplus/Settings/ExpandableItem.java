package com.home.ecoplus.Settings;

import java.util.List;

public class ExpandableItem extends SettingsItem {

    private String mName;
    private List<ChildItem> mChildItems;
    private boolean showSelectedName;
    private String selectedName = "";
    List<String> selectedNameList;

    public ExpandableItem(String name, List<ChildItem> childItems) {
        mName = name;
        mChildItems = childItems;
        this.showSelectedName = false;
    }


    public ExpandableItem(String name,  List<ChildItem> childItems, List<String> selectedNames) {
        mName = name;
        mChildItems = childItems;
        this.showSelectedName = true;
        selectedNameList = selectedNames;
    }

    public String getName() {
        return mName;
    }

    public boolean shouldShowSelectedName() {
        return showSelectedName;
    }

    public void setShowSelectedName(boolean showSelectedName) {
        this.showSelectedName = showSelectedName;
    }

    public String getSelectedName() {
        return selectedName;
    }

    public void setSelectedName(String selectedName) {
        this.selectedName = selectedName;
    }

    public List<String> getSelectedNameList() {
        return selectedNameList;
    }

    @Override
    public List<ChildItem> getChildList() {
        return mChildItems;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    public ChildItem getChildItem(int position) {
        return mChildItems.get(position);
    }

    @Override
    public int getType() {
        return TYPE_EXPANDABLE_ITEM;
    }
}
