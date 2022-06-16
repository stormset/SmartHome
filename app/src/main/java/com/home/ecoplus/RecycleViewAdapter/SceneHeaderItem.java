package com.home.ecoplus.RecycleViewAdapter;


import androidx.annotation.NonNull;

public class SceneHeaderItem extends ListItem {

	@NonNull
	private String sceneHeader;

	public SceneHeaderItem(@NonNull String sceneHeader) {
		this.sceneHeader = sceneHeader;
	}

	@NonNull
	public String getsceneHeaderName() {
		return sceneHeader;
	}


	@Override
	public int getType() {
		return TYPE_SCENE_HEADER;
	}

}