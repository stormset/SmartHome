package com.home.ecoplus;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

import com.home.ecoplus.RecycleViewAdapter.ListItem;
import com.home.ecoplus.RecycleViewAdapter.RoomItem;

import java.util.List;

public class Utility {
    private static final int ROUNDING_VALUE = 16;

    public static int calculateNoOfColumns(Context context, int itemWidthDp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / itemWidthDp);
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float convertPixelsToDp(float px, Context context){
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    @NonNull
    public static RoomItem getRoomById(List<ListItem> list, int roomID){
        for (int i = 0; i < list.size(); i++){
            ListItem listItem = list.get(i);
            if (listItem.getType() == ListItem.TYPE_ROOM){
                if (((RoomItem)listItem).getRoomID() == roomID){
                    return ((RoomItem)listItem);
                }
            }
        }
        return null;
    }

    public static int getDecimal(float temperature) {
        String temp = String.valueOf(com.temperatureselector.Utility.roundToHalf(temperature));
        return Integer.parseInt(temp.substring(temp.indexOf(".") + 1));
    }

    public static String getStringTemperature(float temperature){
        String temp = String.valueOf(com.temperatureselector.Utility.roundToHalf(temperature));
        return temp.substring(0, temp.indexOf("."));
    }

    private static int downScaleSize(float value, float scaleFactor) {
        return (int) Math.ceil(value / scaleFactor);
    }

    private static int roundSize(int value) {
        if (value % ROUNDING_VALUE == 0) {
            return value;
        }
        return value - (value % ROUNDING_VALUE) + ROUNDING_VALUE;
    }

    public static Point getScaledSize(float measuredWidth, float measuredHeight, float scaleFactor){
        Point point = new Point();
        int nonRoundedScaledWidth = downScaleSize(measuredWidth, scaleFactor);
        int nonRoundedScaledHeight = downScaleSize(measuredHeight, scaleFactor);
        point.x = roundSize(nonRoundedScaledWidth);
        point.y = roundSize(nonRoundedScaledHeight);
        return point;
    }

    public static String getDisplayTime(int hour, int minute){
        String time = "";
        if (hour<10)time += 0;
        time += hour + ":";
        if (minute < 10)time += 0;
        time += minute;
        return time;
    }

    public static String getInitialChars(String name){
        String[] parts = name.split(" ");
        if (parts.length >= 2){
            return parts[0].substring(0,1) + parts[1].substring(0,1);
        }else if (parts.length == 1){
            return parts[0].substring(0,1);
        }else{
            return name.substring(0,1);
        }
    }

}