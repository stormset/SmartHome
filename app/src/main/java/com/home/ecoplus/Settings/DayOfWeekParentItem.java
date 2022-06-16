package com.home.ecoplus.Settings;

import com.home.ecoplus.RecycleViewAdapter.DayOfWeekItem;

import java.util.List;

public class DayOfWeekParentItem extends SettingsItem {

    private List<DayOfWeekItem> list;

    public DayOfWeekParentItem(List<DayOfWeekItem> list) {
        this.list = list;
    }

    public List<DayOfWeekItem> getList() {
        return list;
    }

    public void setList(List<DayOfWeekItem> list) {
        this.list = list;
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
        return TYPE_DAY_OF_WEEK_ITEM;
    }
}
