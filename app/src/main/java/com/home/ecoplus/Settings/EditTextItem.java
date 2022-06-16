package com.home.ecoplus.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.List;

public class EditTextItem extends SettingsItem {

	@Nullable
	private String text;
	@NonNull
	private String placeholder;


	public EditTextItem(@NonNull String placeholder) {
		this.text = null;
		this.placeholder = placeholder;
	}

	public EditTextItem(@Nullable String text, @NonNull String placeholder) {
		this.text = text;
		this.placeholder = placeholder;
	}

	@Nullable
	public String getText() {
		return text;
	}

	@NonNull
	public String getPlaceholder() {
		return placeholder;
	}

	public void setText(@Nullable String text) {
		this.text = text;
	}

	public void setPlaceholder(@NonNull String placeholder) {
		this.placeholder = placeholder;
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
		return TYPE_EDIT_TEXT;
	}
}