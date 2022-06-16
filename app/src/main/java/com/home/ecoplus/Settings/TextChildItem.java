package com.home.ecoplus.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TextChildItem extends ChildItem {

	@NonNull
	private String text;
	@Nullable
	private Boolean isSelected;

	public TextChildItem(@NonNull String text, @Nullable Boolean isSelected) {
		this.text = text;
		this.isSelected = isSelected;
	}

	public TextChildItem(@NonNull String text) {
		this.text = text;
		this.isSelected = false;
	}

	@NonNull
	public String getText() {
		return text;
	}

	public boolean isSelected() {
		if (isSelected != null){
			return isSelected;
		}else {
			return false;
		}
	}

	public void setSelected(@Nullable Boolean selected) {
		isSelected = selected;
	}

	public void setText(@NonNull String text) {
		this.text = text;
	}

	@Override
	public int getType() {
		return TYPE_TEXT_CHILD;
	}
}