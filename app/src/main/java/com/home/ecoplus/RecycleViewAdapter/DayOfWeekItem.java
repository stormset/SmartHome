package com.home.ecoplus.RecycleViewAdapter;


import androidx.annotation.Nullable;

public class DayOfWeekItem{

    @Nullable
    private Boolean isClicked;

    public DayOfWeekItem(@Nullable Boolean isClicked) {
        this.isClicked = isClicked;
    }

    public DayOfWeekItem() {
        this.isClicked = false;
    }

    public boolean getClicked() {
        if (isClicked != null){
            return isClicked;
        }
        else {
            return false;
        }
    }

    public void setClicked(@Nullable Boolean clicked) {
        isClicked = clicked;
    }
}