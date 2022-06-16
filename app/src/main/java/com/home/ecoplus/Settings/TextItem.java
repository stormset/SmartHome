package com.home.ecoplus.Settings;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TextItem extends SettingsItem {

	@NonNull
	private String text;
	private Integer textColor;
	private boolean showArrow;

	public TextItem(@NonNull String text, @Nullable Integer textColorResource, boolean showArrow) {
		this.text = text;
		this.textColor = textColorResource;
		this.showArrow = showArrow;
	}

	public TextItem(@NonNull String text, @Nullable Integer textColorResource) {
		this.text = text;
		this.textColor = textColorResource;
		this.showArrow = false;
	}

	public TextItem(@NonNull String text, boolean showArrow) {
		this.text = text;
		textColor = null;
		this.showArrow = showArrow;
	}

	public TextItem(@NonNull String text) {
		this.text = text;
		textColor = null;
		this.showArrow = false;
	}

	@NonNull
	public String getText() {
		return text;
	}

	public Integer getTextColor() {
		return textColor;
	}

	public boolean shouldShowArrow() {
		return showArrow;
	}

	public void setText(@NonNull String text) {
		this.text = text;
	}

	public void setTextColor(Integer textColor) {
		this.textColor = textColor;
	}

	public void setShowArrow(boolean showArrow) {
		this.showArrow = showArrow;
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
		return TYPE_TEXT;
	}
}