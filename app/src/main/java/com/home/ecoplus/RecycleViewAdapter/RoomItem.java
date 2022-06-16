package com.home.ecoplus.RecycleViewAdapter;


import androidx.annotation.NonNull;

public class RoomItem extends ListItem {

	@NonNull
	private Integer roomID;
	private String roomName;

	public RoomItem(@NonNull Integer roomID, @NonNull String roomName) {
		this.roomID = roomID;
		this.roomName = roomName;
	}

	@NonNull
	public Integer getRoomID() {
		return roomID;
	}

	@NonNull
	public String getRoomName() {
		return roomName;
	}


	@Override
	public int getType() {
		return TYPE_ROOM;
	}

}