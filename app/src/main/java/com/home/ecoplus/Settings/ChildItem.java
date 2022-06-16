package com.home.ecoplus.Settings;

public abstract class ChildItem {
    public static final int TYPE_TEXT_CHILD = 40;
    public static final int TYPE_TWO_STATE_CHILD = 41;
    public static final int TYPE_TIME_PICKER_CHILD = 42;
    abstract public int getType();
}
