package com.example.togaether;

import android.content.Context;
import android.media.MediaPlayer;

import java.time.LocalDate;

public class UserData {

    private static String callString, userId, userName, userPass;
    private static LocalDate userBdate, userDate;
    private static int myPid;

    public static int getMyPid() {
        return myPid;
    }

    public static void setMyPid(int myPid) {
        UserData.myPid = myPid;
    }

    public static LocalDate getUserBdate() {
        return userBdate;
    }

    public static void setUserBdate(LocalDate userBdate) {
        UserData.userBdate = userBdate;
    }

    public static LocalDate getUserDate() {
        return userDate;
    }

    public static void setUserDate(LocalDate userDate) {
        UserData.userDate = userDate;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        UserData.userId = userId;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        UserData.userName = userName;
    }

    public static String getCallString() {
        return callString;
    }

    public static void setCallString(String callString) {
        UserData.callString = callString;
    }

    public static String getUserPass() {
        return userPass;
    }

    public static void setUserPass(String userPass) {
        UserData.userPass = userPass;
    }

    private UserData() {}

    private static class InnerInstanceClazz {
        private static final UserData instance = new UserData();
    }

    public static UserData getInstance() {
        return InnerInstanceClazz.instance;
    }
}
