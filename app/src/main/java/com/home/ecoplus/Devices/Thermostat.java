package com.home.ecoplus.Devices;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class Thermostat extends DeviceItem {

	@Nullable
	private List<SmartRadiatorValve> smartRadiatorValves;
	@NonNull
	private HeatingSystemType heatingSystemType;
	@NonNull
	private HeatingModes heatingMode;
	@NonNull
	private Float currentTemperature;
	private Float targetTemperature;


	public Thermostat(@Nullable List<SmartRadiatorValve> smartRadiatorValves, @NonNull HeatingSystemType heatingSystemType, @NonNull HeatingModes heatingMode, @NonNull Float currentTemperature, @Nullable Float targetTemperature) {
		this.smartRadiatorValves = smartRadiatorValves;
		this.heatingSystemType = heatingSystemType;
		this.heatingMode = heatingMode;
		this.currentTemperature = currentTemperature;
		this.targetTemperature = targetTemperature;
	}

	@Nullable
	public List<SmartRadiatorValve> getSmartRadiatorValves(){
		return smartRadiatorValves;
	}

	@NonNull
	public HeatingSystemType getHeatingSystemType(){
		return heatingSystemType;
	}

	@NonNull
	public HeatingModes getHeatingMode() {
		return heatingMode;
	}

	@NonNull
	public Float getCurrentTemperature() {
		return currentTemperature;
	}

	@NonNull
	public Float getTargetTemperature() {
		return targetTemperature;
	}

	public void addSmartValve(SmartRadiatorValve smartRadiatorValve){
		if (smartRadiatorValves != null) {
			smartRadiatorValves.add(smartRadiatorValve);
		}else {
			smartRadiatorValves = new ArrayList<>();
			smartRadiatorValves.add(smartRadiatorValve);
		}
	}

	public void setHeatingSystemType(@NonNull HeatingSystemType heatingSystemType) {
		this.heatingSystemType = heatingSystemType;
	}

	public void setHeatingMode(@NonNull HeatingModes heatingMode) {
		this.heatingMode = heatingMode;
	}

	public void setCurrentTemperature(@NonNull Float currentTemperature) {
		this.currentTemperature = currentTemperature;
	}

	public void setTargetTemperature(Float targetTemperature) {
		this.targetTemperature = targetTemperature;
	}

	@Override
	@NonNull
	public Boolean isActivated() {
		return heatingMode != HeatingModes.OFF;
	}
}