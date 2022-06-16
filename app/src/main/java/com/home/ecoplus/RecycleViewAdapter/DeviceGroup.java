package com.home.ecoplus.RecycleViewAdapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.home.ecoplus.Devices.DeviceItem;

import java.util.ArrayList;
import java.util.List;


public class DeviceGroup extends ListItem {

	@NonNull
	private List<String> deviceIDs;
	@NonNull
	private Integer roomID;
	@NonNull
	private String groupName;
	@NonNull
	private List<String> deviceNames;
	@NonNull
	private List<DeviceItem> deviceItems;
	@Nullable
	private Integer groupIconID;
	@Nullable
	private Integer viewType = null;

	public DeviceGroup(@NonNull List<String> deviceIDs, @NonNull Integer roomID, @NonNull String groupName, @NonNull List<String> deviceNames, @Nullable Integer groupIconID, @NonNull List<DeviceItem> deviceItems) {
		this.deviceIDs = deviceIDs;
		this.roomID = roomID;
		this.groupName = groupName;
		this.deviceNames = deviceNames;
		this.deviceItems = deviceItems;
		this.groupIconID = groupIconID;
	}

	@NonNull
	public List<String> getDeviceIDs() {
		return deviceIDs;
	}

	@NonNull
	public Integer getRoomID() {
		return roomID;
	}

	@NonNull
	public String getGroupName() {
		return groupName;
	}

	@NonNull
	public List<String> getDeviceNames() {
		return deviceNames;
	}

	@Nullable
	public Integer getGroupIconID(){
		return groupIconID;
	}

	@NonNull
	public List<DeviceItem> getDeviceItems() {
		return deviceItems;
	}

	@NonNull
	public String getDeviceType() {
		return deviceIDs.get(0).split("-")[0];
	}

	@NonNull
	public Boolean isActivated() {
		for (DeviceItem deviceItem : deviceItems){
			if (deviceItem.isActivated()) return true;
		}
		return false;
	}

	public void setRoomID(@NonNull Integer roomID) {
		this.roomID = roomID;
	}

	public void setGroupName(@NonNull String groupName) {
		this.groupName = groupName;
	}

	public void setDeviceName(@NonNull String deviceName, int position) {
		deviceNames.set(position, deviceName);
	}

	@NonNull
	public Integer getRespondingDeviceCount(){
		int respondingDeviceCount = 0;
		for (DeviceItem deviceItem : deviceItems){
			if (deviceItem != null)respondingDeviceCount++;
		}
		return respondingDeviceCount;
	}


	@Override
	public int getType() {
		if (viewType == null) {
			return TYPE_DEVICE_GROUP;
		}else {
			return viewType;
		}
	}

}