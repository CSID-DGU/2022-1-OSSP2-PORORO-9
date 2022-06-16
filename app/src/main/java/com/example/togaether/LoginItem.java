package com.example.togaether;

import com.google.gson.annotations.SerializedName;

public class LoginItem {
    @SerializedName("uid")
    private String uid;
    @SerializedName("uname")
    private String uname;
    @SerializedName("ubdate")
    private String ubdate;
    @SerializedName("udate")
    private String udate;
    @SerializedName("fail")
    private int fail = -1;

    public LoginItem(String uid, String uname, String ubdate, String udate) {
        this.uid = uid;
        this.uname = uname;
        this.ubdate = ubdate;
        this.udate = udate;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUbdate() {
        return ubdate;
    }

    public void setUbdate(String ubdate) {
        this.ubdate = ubdate;
    }

    public String getUdate() {
        return udate;
    }

    public void setUdate(String udate) {
        this.udate = udate;
    }
}
