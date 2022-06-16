package com.home.ecoplus.Settings;

import androidx.annotation.NonNull;

public class MoreStateChildItem extends ChildItem {

	@NonNull
	private Boolean state;
	@NonNull
	private String name;
	@NonNull
	private String enabledStateName;
	@NonNull
	private String disabledStateName;

	public MoreStateChildItem(@NonNull Boolean state, @NonNull String name, @NonNull String enabledStateName, @NonNull String disabledStateName) {
		this.state = state;
		this.name = name;
		this.enabledStateName = enabledStateName;
		this.disabledStateName = disabledStateName;
	}

	@NonNull
	public Boolean getState() {
		return state;
	}

	@NonNull
	public String getName() {
		return name;
	}

	@NonNull
	public String getEnabledStateName() {
		return enabledStateName;
	}

	@NonNull
	public String getDisabledStateName() {
		return disabledStateName;
	}

	public void setState(@NonNull Boolean state) {
		this.state = state;
	}

	public void setName(@NonNull String name) {
		this.name = name;
	}

	public void setEnabledStateName(@NonNull String enabledStateName) {
		this.enabledStateName = enabledStateName;
	}

	public void setDisabledStateName(@NonNull String disabledStateName) {
		this.disabledStateName = disabledStateName;
	}

	@Override
	public int getType() {
		return TYPE_TWO_STATE_CHILD;
	}
}