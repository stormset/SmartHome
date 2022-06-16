package com.home.ecoplus.RecycleViewAdapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UserInstanceItem {

    @NonNull
    private Integer userID;
    @NonNull
    private String userName;
    @Nullable
    private String customName;

    private boolean remoteAccess;
    private boolean canEdit;

    public UserInstanceItem(@NonNull Integer userID, @NonNull String userName, @Nullable String customName, boolean remoteAccess, boolean canEdit) {
        this.userID = userID;
        this.userName = userName;
        this.customName = customName;
        this.remoteAccess = remoteAccess;
        this.canEdit = canEdit;
    }

    public UserInstanceItem(@NonNull Integer userID, @NonNull String userName, boolean remoteAccess, boolean canEdit) {
        this.userID = userID;
        this.userName = userName;
        this.customName = null;
        this.remoteAccess = remoteAccess;
        this.canEdit = canEdit;
    }

    public UserInstanceItem(@NonNull Integer userID, @NonNull String userName, @Nullable String customName) {
        this.userID = userID;
        this.userName = userName;
        this.customName = customName;
        remoteAccess = false;
        canEdit = false;
    }

    public UserInstanceItem(@NonNull Integer userID, @NonNull String userName) {
        this.userID = userID;
        this.userName = userName;
        this.customName = null;
        remoteAccess = false;
        canEdit = false;
    }

    @NonNull
    public Integer getUserID() {
        return userID;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    @NonNull
    public String getCustomName() {
        if (customName != null){
            return customName;
        }
        else {
            return userName;
        }
    }

    public boolean isRemoteAccess() {
        return remoteAccess;
    }

    public boolean canEdit() {
        return canEdit;
    }

    public void setUserID(@NonNull Integer userID) {
        this.userID = userID;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }

    public void setCustomName(@Nullable String customName) {
        this.customName = customName;
    }

    public void setRemoteAccess(boolean remoteAccess) {
        this.remoteAccess = remoteAccess;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }
}