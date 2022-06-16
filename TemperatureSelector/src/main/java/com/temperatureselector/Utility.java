package com.temperatureselector;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;

public class Utility {
    public static Point getTextPosition(float rotation, int sectionWidth, int sectionHeight, boolean relativeProgressBigger) {
        Point point = new Point();
        sectionWidth /= 2;
        sectionHeight /= 2;
        if (relativeProgressBigger) {
            if (rotation < 25) {
                point.x = sectionWidth + 10;
                point.y = sectionHeight + 10;
            } else if (rotation >= 25 && rotation < 54) {
                point.x = sectionWidth - 5;
                point.y = sectionHeight + 20;
            } else if (rotation >= 54 && rotation < 61) {
                point.x = sectionWidth - 15;
                point.y = sectionHeight + 30;
            } else if (rotation >= 61 && rotation < 75) {
                point.x = sectionWidth - 25;
                point.y = sectionHeight + 40;
            } else if (rotation >= 75 && rotation < 90) {
                point.x = sectionWidth - 44;
                point.y = sectionHeight + 56;
            } else if (rotation >= 90 && rotation < 104) {
                point.x = sectionWidth - 55;
                point.y = sectionHeight + 52;
            } else if (rotation >= 104 && rotation < 127) {
                point.x = sectionWidth - 85;
                point.y = sectionHeight + 34;
            } else if (rotation >= 127 && rotation < 140) {
                point.x = sectionWidth - 95;
                point.y = sectionHeight + 15;
            } else if (rotation >= 140 && rotation < 163) {
                point.x = sectionWidth - 101;
                point.y = sectionHeight + 8;
            } else if (rotation >= 163 && rotation < 177) {
                point.x = sectionWidth - 103;
                point.y = sectionHeight - 5;
            } else if (rotation >= 177 && rotation < 184) {
                point.x = sectionWidth - 102;
                point.y = sectionHeight - 15;
            } else if (rotation >= 184 && rotation < 190) {
                point.x = sectionWidth - 95;
                point.y = sectionHeight - 25;
            } else if (rotation >= 190 && rotation < 205) {
                point.x = sectionWidth - 87;
                point.y = sectionHeight - 30;
            } else if (rotation >= 205 && rotation < 221) {
                point.x = sectionWidth - 77;
                point.y = sectionHeight - 35;
            } else if (rotation >= 221 && rotation < 235) {
                point.x = sectionWidth - 66;
                point.y = sectionHeight - 40;
            } else if (rotation >= 235 && rotation < 242) {
                point.x = sectionWidth - 56;
                point.y = sectionHeight - 50;
            } else if (rotation >= 242 && rotation < 256) {
                point.x = sectionWidth - 48;
                point.y = sectionHeight - 60;
            } else if (rotation >= 256 && rotation < 263) {
                point.x = sectionWidth - 38;
                point.y = sectionHeight - 68;
            } else {
                point.x = sectionWidth - 24;
                point.y = sectionHeight - 76;
            }
        } else {
            if (rotation < 25) {
                point.x = sectionWidth - 50;
                point.y = sectionHeight - 67;
            } else if (rotation >= 25 && rotation < 54) {
                point.x = sectionWidth - 30;
                point.y = sectionHeight - 55;
            } else if (rotation >= 54 && rotation < 69) {
                point.x = sectionWidth - 8;
                point.y = sectionHeight - 63;
            } else if (rotation >= 69 && rotation < 84) {
                point.x = sectionWidth + 10;
                point.y = sectionHeight - 50;
            } else if (rotation >= 84 && rotation < 111) {
                point.x = sectionWidth + 10;
                point.y = sectionHeight - 33;
            } else if (rotation >= 111 && rotation < 119) {
                point.x = sectionWidth + 10;
                point.y = sectionHeight - 9;
            } else if (rotation >= 119 && rotation < 141) {
                point.x = sectionWidth + 10;
                point.y = sectionHeight + 10;
            } else if (rotation >= 141 && rotation < 155) {
                point.x = sectionWidth + 10;
                point.y = sectionHeight + 17;
            } else if (rotation >= 155 && rotation < 170) {
                point.x = sectionWidth;
                point.y = sectionHeight + 20;
            } else if (rotation >= 170 && rotation < 191) {
                point.x = sectionWidth - 22;
                point.y = sectionHeight + 34;
            } else if (rotation >= 191 && rotation < 205) {
                point.x = sectionWidth - 34;
                point.y = sectionHeight + 42;
            } else if (rotation >= 205 && rotation < 219) {
                point.x = sectionWidth - 46;
                point.y = sectionHeight + 42;
            } else if (rotation >= 219 && rotation < 226) {
                point.x = sectionWidth - 53;
                point.y = sectionHeight + 42;
            } else if (rotation >= 226 && rotation < 234) {
                point.x = sectionWidth - 58;
                point.y = sectionHeight + 42;
            } else if (rotation >= 234 && rotation < 241) {
                point.x = sectionWidth - 64;
                point.y = sectionHeight + 40;
            } else if (rotation >= 241 && rotation < 250) {
                point.x = sectionWidth - 68;
                point.y = sectionHeight + 37;
            } else if (rotation >= 250 && rotation < 255) {
                point.x = sectionWidth - 76;
                point.y = sectionHeight + 34;
            } else if (rotation >= 255 && rotation < 265) {
                point.x = sectionWidth - 80;
                point.y = sectionHeight + 30;
            } else if (rotation >= 265 && rotation < 270) {
                point.x = sectionWidth - 86;
                point.y = sectionHeight + 26;
            } else {
                point.x = sectionWidth - 90;
                point.y = sectionHeight + 23;
            }
        }
        return point;
    }
    public static int roundToInt (float value) {
        return Math.round(value);
    }
    public static float roundToHalf(double d) {
        return (float) (Math.round(d * 2) / 2.0);
    }
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}

