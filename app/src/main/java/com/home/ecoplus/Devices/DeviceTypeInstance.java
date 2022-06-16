package com.home.ecoplus.Devices;

import android.graphics.Point;
import android.view.View;

public class DeviceTypeInstance {
    private final Point sizes;
    private final Point parentCoordinates;
    private final View parent;
    private final View controller;
    private final int deviceAdapterID;

    public DeviceTypeInstance(View parent, View controller, int deviceAdapterID, int width, int height){
        this.parent = parent;
        parentCoordinates = new Point((int)parent.getX(), (int) parent.getY());
        this.controller = controller;
        this.deviceAdapterID = deviceAdapterID;
        sizes = new Point();
        sizes.x = width;
        sizes.y = height;
    }

    public View getParent(){
        return parent;
    }

    public Point getParentCoordinates(){
        return parentCoordinates;
    }

    public View getController(){
        return controller;
    }

    public int getDeviceAdapterID() {
        return deviceAdapterID;
    }

    public int getWidth(){
        return sizes.x;
    }

    public int getHeight(){
        return sizes.y;
    }
}
