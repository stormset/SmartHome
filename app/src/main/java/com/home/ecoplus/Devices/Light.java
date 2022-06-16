package com.home.ecoplus.Devices;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class Light extends DeviceItem {

	@Nullable
	private Integer brightness;
	@NonNull
	private Boolean activated;

	/**
	 * @param brightness should be null if brightness control is not allowed
	 * */

	public Light(@Nullable Integer brightness, @NonNull Boolean activated) {
		this.brightness = brightness;
		this.activated = activated;
		if (brightness != null){
			if (!activated)this.brightness = 0;
			if (brightness == 0)this.activated = false;
		}
	}


	@Nullable
	public Integer getBrightness() {
		return brightness;
	}

	public void setBrightness(@NonNull Integer brightness){
		this.brightness = brightness;
		if(brightness == 0)activated = false;
	}

	public void setActivated(@NonNull Boolean activated){
		this.activated = activated;
		if (brightness != null){
			if (activated){
				brightness = 100;
			}
			else{
				brightness = 0;
			}
		}
	}

	@Override
	@NonNull
	public Boolean isActivated() {
		return activated;
	}

}