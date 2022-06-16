package com.home.ecoplus.Settings;

import android.content.Context;
import androidx.annotation.NonNull;

import com.home.ecoplus.R;

import java.util.List;

public class MoreStateItem extends SettingsItem {

	@NonNull
	private Boolean state;
	@NonNull
	private String name;
	@NonNull
	private String enabledStateName;
	@NonNull
	private String disabledStateName;

	public MoreStateItem(@NonNull Boolean state, @NonNull String name, @NonNull String enabledStateName, @NonNull String disabledStateName) {
		this.state = state;
		this.name = name;
		this.enabledStateName = enabledStateName;
		this.disabledStateName = disabledStateName;
	}

	public MoreStateItem(@NonNull Boolean state, @NonNull String name, Context context) {
		this.state = state;
		this.name = name;
		this.enabledStateName = context.getResources().getString(R.string.enabled);
		this.disabledStateName = context.getResources().getString(R.string.disabled);
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
	public List<ChildItem> getChildList() {
		return null;
	}

	@Override
	public boolean isInitiallyExpanded() {
		return false;
	}

	@Override
	public int getType() {
		return TYPE_TWO_STATE_ITEM;
	}
}