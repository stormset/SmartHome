package com.home.ecoplus.Settings;

import com.home.ecoplus.RecycleViewAdapter.DayOfWeekItem;
import com.home.ecoplus.RecycleViewAdapter.UserInstanceItem;
import com.home.ecoplus.UserView;

import java.util.List;

public class UserViewParentItem extends SettingsItem {

    private List<UserInstanceItem> list;

    public UserViewParentItem(List<UserInstanceItem> list) {
        this.list = list;
    }

    public List<UserInstanceItem> getList() {
        return list;
    }

    public void setList(List<UserInstanceItem> list) {
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
        return TYPE_USER_VIEW_ITEM;
    }
}
