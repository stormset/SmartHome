package com.home.ecoplus.Devices;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SmartRadiatorValve extends DeviceItem {

	private HeatingModes heatingMode;
	@NonNull
	private Float currentTemperature;
	private Float targetTemperature;


	public SmartRadiatorValve(@Nullable HeatingModes heatingMode, @NonNull Float currentTemperature, @Nullable Float targetTemperature) {
		this.heatingMode = heatingMode;
		this.currentTemperature = currentTemperature;
		this.targetTemperature = targetTemperature;
	}

	@Nullable
	public HeatingModes getHeatingMode() {
		return heatingMode;
	}

	@NonNull
	public Float getCurrentTemperature() {
		return currentTemperature;
	}

	@Nullable
	public Float getTargetTemperature() {
		return targetTemperature;
	}

	public void setHeatingMode(HeatingModes heatingMode) {
		this.heatingMode = heatingMode;
		if (heatingMode == HeatingModes.OFF)targetTemperature = null;
	}

	public void setCurrentTemperature(@NonNull Float currentTemperature) {
		this.currentTemperature = currentTemperature;
	}

	public void setTargetTemperature(@NonNull Float targetTemperature) {
		this.targetTemperature = targetTemperature;
	}


	@Override
	@NonNull
	public Boolean isActivated() {
		return heatingMode != HeatingModes.OFF;
	}

}