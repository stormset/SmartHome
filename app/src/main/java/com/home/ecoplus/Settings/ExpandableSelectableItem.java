package com.home.ecoplus.Settings;

import androidx.annotation.Nullable;

import com.home.ecoplus.Utility;

import java.util.List;

public class ExpandableSelectableItem extends SettingsItem {

    private String mName;
    @Nullable
    private Boolean isSelected;
    private List<ChildItem> mChildItems;
    private boolean showSelectedName = false;
    private String selectedName = "";

    public ExpandableSelectableItem(String name, @Nullable Boolean isSelected, List<ChildItem> childItems) {
        mName = name;
        this.isSelected = isSelected;
        mChildItems = childItems;
        this.showSelectedName = false;
    }

    public ExpandableSelectableItem(String name, List<ChildItem> childItems) {
        mName = name;
        isSelected = false;
        this.isSelected = null;
        mChildItems = childItems;
        this.showSelectedName = false;
    }

    public ExpandableSelectableItem(String name, @Nullable Boolean isSelected, List<ChildItem> childItems, boolean showSelectedName) {
        mName = name;
        this.isSelected = isSelected;
        mChildItems = childItems;
        this.showSelectedName = showSelectedName;
        if (showSelectedName){
            trySetSelectedName();
        }
    }

    public ExpandableSelectableItem(String name, List<ChildItem> childItems, boolean showSelectedName) {
        mName = name;
        isSelected = false;
        this.isSelected = null;
        mChildItems = childItems;
        this.showSelectedName = showSelectedName;
        if (showSelectedName){
            trySetSelectedName();
        }
    }

    public String getName() {
        return mName;
    }

    public boolean shouldShowSelected() {
        return showSelectedName;
    }

    public boolean isSelected() {
        if (isSelected != null){
            return isSelected;
        }else {
            return false;
        }
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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

    public void trySetSelectedName(){
        if (mChildItems.get(0) instanceof TimePickerChildItem){
            TimePickerChildItem timePickerChildItem = (TimePickerChildItem)mChildItems.get(0);
            if (timePickerChildItem.getHour() != null && timePickerChildItem.getMinute() != null)
            setSelectedName(Utility.getDisplayTime(timePickerChildItem.getHour(), timePickerChildItem.getMinute()));
        }
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
        return TYPE_EXPANDABLE_SELECTABLE_ITEM;
    }
}
