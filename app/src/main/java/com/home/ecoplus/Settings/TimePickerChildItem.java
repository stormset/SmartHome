package com.home.ecoplus.Settings;


import androidx.annotation.Nullable;

public class TimePickerChildItem extends ChildItem {

	@Nullable
	private Integer hour;
	@Nullable
	private Integer minute;

	public TimePickerChildItem(@Nullable Integer hour, @Nullable Integer minute) {
		this.hour = hour;
		this.minute = minute;
	}

	public TimePickerChildItem() {
		hour = null;
		minute = null;
	}

	@Nullable
	public Integer getHour() {
		return hour;
	}

	@Nullable
	public Integer getMinute() {
		return minute;
	}

	public void setHour(@Nullable Integer hour) {
		this.hour = hour;
	}

	public void setMinute(@Nullable Integer minute) {
		this.minute = minute;
	}

	@Override
	public int getType() {
		return TYPE_TIME_PICKER_CHILD;
	}
}