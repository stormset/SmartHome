package com.home.ecoplus.RecycleViewAdapter;

import androidx.annotation.NonNull;

public class SceneItem extends ListItem {

	@NonNull
	private Integer sceneID;
	private String sceneName;
	private Integer iconID;
	private Boolean activated;

	public SceneItem(@NonNull Integer sceneID, @NonNull String sceneName, @NonNull Integer iconID, @NonNull Boolean activated) {
		this.sceneID = sceneID;
		this.sceneName = sceneName;
		this.iconID = iconID;
		this.activated = activated;
	}

	public void setActivatedState(boolean activated) {
		this.activated = activated;
	}

	@NonNull
	public Integer getSceneID() {
		return sceneID;
	}

	@NonNull
	public String getSceneName() {
		return sceneName;
	}

	@NonNull
	public Integer getIconID() {
		return iconID;
	}

	@NonNull
	public Boolean getActivatedState() {
		return activated;
	}


	@Override
	public int getType() {
		return TYPE_SCENE_ITEM;
	}

}