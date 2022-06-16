package com.home.ecoplus.RecycleViewAdapter;

public abstract class ListItem {
    public static final int TYPE_SUMMARY = 0;
    public static final int TYPE_SCENE_HEADER = 1;
    public static final int TYPE_SCENE_GROUP = 2;
    public static final int TYPE_SCENE_ITEM = 3;
    public static final int TYPE_ROOM = 4;
	public static final int TYPE_DEVICE = 5;
    public static final int TYPE_DEVICE_GROUP = 6;

    public static final int GROUP_SMART_RADIATOR_VALVE = 5;

    abstract public int getType();

} 