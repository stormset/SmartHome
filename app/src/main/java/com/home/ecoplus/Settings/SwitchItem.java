package com.home.ecoplus.Settings;

import androidx.annotation.NonNull;

import java.util.List;

public class SwitchItem extends SettingsItem {

	@NonNull
	private Boolean state;
	@NonNull
	private String name;

	public SwitchItem(@NonNull Boolean state, @NonNull String name) {
		this.state = state;
		this.name = name;
	}

	@NonNull
	public Boolean getState() {
		return state;
	}

	@NonNull
	public String getName() {
		return name;
	}

	public void setState(@NonNull Boolean state) {
		this.state = state;
	}

	public void setName(@NonNull String name) {
		this.name = name;
	}

	@Override
	public List<ChildItem> getChildList() {
		return null;
	}

	@Override
	public boolean isInitiallyExpanded() {
		return false;
	}

	@Override
	public int getType() {
		return TYPE_SWITCH_ITEM;
	}
}