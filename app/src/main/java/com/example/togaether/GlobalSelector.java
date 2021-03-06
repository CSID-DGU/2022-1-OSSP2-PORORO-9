package com.example.togaether;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalTime;

public class GlobalSelector {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static GradientDrawable getSkyGradient(LocalTime time) {
        int c[] = new int[2];
        c[0] = getSkyColor(time);
        c[1] = getSkyColor(time.plusMinutes(20));
        GradientDrawable res = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, c);
        res.setCornerRadius(0f);

        return res;
    }

    public static int getSkyColor(LocalTime time) {
        int res;
        int hour = time.getHour(), minute = time.getMinute();
        int rgb[][] = {{38, 32, 56}, {116, 224, 241}, {241, 173, 166}};
        if (hour < 3) {
            res = Color.rgb(rgb[0][0], rgb[0][1], rgb[0][2]);
        }
        else if (hour >= 3 && hour < 5) {
            int m = (hour-3)*60+minute;
            int dr = (int)((float)(rgb[1][0] - rgb[0][0]) * (float)m/120);
            int dg = (int)((float)(rgb[1][1] - rgb[0][1]) * (float)m/120);
            int db = (int)((float)(rgb[1][2] - rgb[0][2]) * (float)m/120);
            res = Color.rgb(rgb[0][0] + dr, rgb[0][1] + dg, rgb[0][2] + db);
        }
        else if (hour < 18) {
            res = Color.rgb(rgb[1][0], rgb[1][1], rgb[1][2]);
        }
        else if (hour >= 18 && hour < 19) {
            int m = minute;
            int dr = (int)((float)(rgb[2][0] - rgb[1][0]) * (float)m/60);
            int dg = (int)((float)(rgb[2][1] - rgb[1][1]) * (float)m/60);
            int db = (int)((float)(rgb[2][2] - rgb[1][2]) * (float)m/60);
            res = Color.rgb(rgb[1][0] + dr, rgb[1][1] + dg, rgb[1][2] + db);
        }
        else if (hour >= 19 && hour < 20) {
            int m = minute;
            int dr = (int)((float)(rgb[0][0] - rgb[2][0]) * (float)m/60);
            int dg = (int)((float)(rgb[0][1] - rgb[2][1]) * (float)m/60);
            int db = (int)((float)(rgb[0][2] - rgb[2][2]) * (float)m/60);
            res = Color.rgb(rgb[2][0] + dr, rgb[2][1] + dg, rgb[2][2] + db);
        }
        else {
            res = Color.rgb(rgb[0][0], rgb[0][1], rgb[0][2]);
        }

        return res;
    }

    public static String getPostWord(String str, String val) {
        try {
            String firstVal, secondVal;
            if(val.equals("???") || val.equals("???")) {
                firstVal = "???";
                secondVal = "???";
            }
            else if(val.equals("???") || val.equals("???")) {
                firstVal = "???";
                secondVal = "???";
            }
            else if(val.equals("???") || val.equals("???")) {
                firstVal = "???";
                secondVal = "???";
            }
            else if(val.equals("???") || val.equals("???")) {
                firstVal = "???";
                secondVal = "???";
            }
            else if(val.equals("??????") || val.equals("???")) {
                firstVal = "??????";
                secondVal = "???";
            }
            else if(val.equals("???") || val.equals("???")) {
                firstVal = "???";
                secondVal = "???";
            }
            else {
                return str + val;
            }

            char laststr = str.charAt(str.length() - 1);
            // ????????? ?????? ????????? ?????? ???????????? ????????? ??????
            if (laststr < 0xAC00 || laststr > 0xD7A3) {
                return str;
            }

            int lastCharIndex = (laststr - 0xAC00) % 28;
            // ?????????????????? 0????????? ????????? ????????? ?????????????????? ????????? ??????????????? ????????? ?????? ??????
            if(lastCharIndex > 0) {
                // ????????? ????????????
                // ????????? '???'????????? '???'???????????? ????????? ????????? '???' ????????? ????????? '??????'
                if(val.equals("??????") && lastCharIndex == 8) {
                    str += secondVal;
                } else {
                    str += firstVal;
                }
            } else {
                // ????????? ?????? ??????
                str += secondVal;
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return str;
    }
}
