package com.home.ecoplus.RecycleViewAdapter;


import androidx.annotation.NonNull;

public class SummaryItem extends ListItem {

	@NonNull
	private Integer homeID;
	private String homeName;

	public SummaryItem(@NonNull Integer homeID, @NonNull String homeName) {
		this.homeID = homeID;
		this.homeName = homeName;
	}

	@NonNull
	public Integer getHomeID() {
		return homeID;
	}

	@NonNull
	public String getHomeName() {
		return homeName;
	}


	@Override
	public int getType() {
		return TYPE_SUMMARY;
	}

}