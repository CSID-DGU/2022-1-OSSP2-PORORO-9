package com.example.togaether;

import com.google.gson.annotations.SerializedName;

public class GuestItem {
    @SerializedName("gid")
    private int gid;
    @SerializedName("pid")
    private int pid;
    @SerializedName("uid")
    private String uid;
    @SerializedName("uname")
    private String uname;
    @SerializedName("gcontent")
    private String gcontent;
    @SerializedName("gdate")
    private String gdate;

    public GuestItem(int gid, int pid, String uid, String uname, String gcontent, String gdate) {
        this.gid = gid;
        this.pid = pid;
        this.uid = uid;
        this.uname = uname;
        this.gcontent = gcontent;
        this.gdate = gdate;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
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

    public String getGcontent() {
        return gcontent;
    }

    public void setGcontent(String gcontent) {
        this.gcontent = gcontent;
    }

    public String getGdate() {
        return gdate;
    }

    public void setGdate(String gdate) {
        this.gdate = gdate;
    }
}
