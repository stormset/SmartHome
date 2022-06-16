package com.home.ecoplus.RecycleViewAdapter;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.home.ecoplus.Devices.DeviceItem;
import com.home.ecoplus.Devices.DeviceTypes;
import com.home.ecoplus.Devices.Light;

import java.util.List;


public class Device extends ListItem {

	@NonNull
	private String deviceID;
	@NonNull
	private Integer roomID;
	@NonNull
	private String deviceName;
	@Nullable
	private Integer iconID;
	@Nullable
	private DeviceItem deviceItem;
	@Nullable
	private Integer viewType = null;

	/**
	 * @param deviceItem null if the device is not responding
	 */

	public Device(@NonNull String deviceID, @NonNull Integer roomID, @NonNull String deviceName, @Nullable Integer iconID, @Nullable DeviceItem deviceItem) {
		this.deviceID = deviceID;
		this.roomID = roomID;
		this.deviceName = deviceName;
		this.iconID = iconID;
		this.deviceItem = deviceItem;
	}

	@NonNull
	public String getDeviceID() {
		return deviceID;
	}

	@NonNull
	public Integer getRoomID() {
		return roomID;
	}

	@NonNull
	public String getDeviceName() {
		return deviceName;
	}

	@Nullable
	public Integer getIconID(){
		return iconID;
	}

	@Nullable
	public DeviceItem getDeviceItem() {
		return deviceItem;
	}

	@NonNull
	public String getDeviceType() {
		return deviceID.split("-")[0];
	}

	@NonNull
	public Boolean isResponding(){
		return deviceItem != null;
	}

	@NonNull
	public Boolean isActivated(){
		if (deviceItem != null) {
			return deviceItem.isActivated();
		}else {
			return false;
		}
	}

	public void setDeviceID(@NonNull String deviceID) {
		this.deviceID = deviceID;
	}

	public void setRoomID(@NonNull Integer roomID) {
		this.roomID = roomID;
	}

	public void setDeviceName(@NonNull String deviceName) {
		this.deviceName = deviceName;
	}

	public void setIconID(@Nullable Integer iconID) {
		this.iconID = iconID;
	}

	public Boolean trySwitchState(){
		if (deviceItem != null) {
			switch (getDeviceType()) {
				case DeviceTypes.DEVICE_LIGHT:
					Light light = (Light) deviceItem;
					light.setActivated(!light.isActivated());
					return true;

				default:
					return false;
			}
		}
		else {
			return false;
		}
	}

	@Override
	public int getType() {
		if (viewType == null) {
			return TYPE_DEVICE;
		}else {
			return viewType;
		}
	}

}