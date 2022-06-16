package com.home.ecoplus.Settings;

import com.bignerdranch.expandablerecyclerview.model.Parent;

public abstract class SettingsItem implements Parent<ChildItem> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_TEXT = 1;
    public static final int TYPE_EDIT_TEXT = 2;
    public static final int TYPE_TWO_STATE_ITEM = 3;
    public static final int TYPE_SWITCH_ITEM = 4;
    public static final int TYPE_DAY_OF_WEEK_ITEM = 5;
    public static final int TYPE_USER_VIEW_ITEM = 6;
    public static final int TYPE_EXPANDABLE_SELECTABLE_ITEM = 7;
    public static final int TYPE_EXPANDABLE_ITEM = 8;
    public static final int TYPE_DESCRIPTION = 9;

    abstract public int getType();
}