package com.example.togaetherlib;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PuppyInfoItem {
    @SerializedName("pid")
    private int pid;
    @SerializedName("uid")
    private String uid;
    @SerializedName("pname")
    private String pname;
    @SerializedName("psex")
    private String psex;
    @SerializedName("pcall")
    private String pcall;
    @SerializedName("pbdate")
    private String pbdate;
    @SerializedName("pshare")
    private int pshare;
    @SerializedName("pcustom")
    private PuppyItem pcustom;
    //visit info
    @SerializedName("visit")
    private boolean visit;
    @SerializedName("vpid")
    private int vpid;
    @SerializedName("vname")
    private String vname;
    //more info
    @SerializedName("uname")
    private String uname;
    @SerializedName("isFriend")
    private boolean friend;

    public PuppyInfoItem(int pid, String uid, String pname, String psex, String pcall, String pbdate, int pshare, PuppyItem pcustom, boolean visit, int vpid) {
        this.pid = pid;
        this.uid = uid;
        this.pname = pname;
        this.psex = psex;
        this.pcall = pcall;
        this.pbdate = pbdate;
        this.pshare = pshare;
        this.pcustom = pcustom;
        this.visit = visit;
        this.vpid = vpid;
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

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPsex() {
        return psex;
    }

    public void setPsex(String psex) {
        this.psex = psex;
    }

    public String getPcall() {
        return pcall;
    }

    public void setPcall(String pcall) {
        this.pcall = pcall;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDate getPbdate() {
        LocalDate bdate = LocalDate.parse(pbdate, DateTimeFormatter.ISO_DATE);
        return bdate;
    }

    public void setPbdate(String pbdate) {
        this.pbdate = pbdate;
    }

    public int getPshare() {
        return pshare;
    }

    public void setPshare(int pshare) {
        this.pshare = pshare;
    }

    public PuppyItem getPcustom() {
        return pcustom;
    }

    public void setPcustom(PuppyItem pcustom) {
        this.pcustom = pcustom;
    }

    // visit info

    public boolean isVisit() {
        return visit;
    }

    public void setVisit(boolean visit) {
        this.visit = visit;
    }

    public int getVpid() {
        return vpid;
    }

    public void setVpid(int vpid) {
        this.vpid = vpid;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public boolean isFriend() {
        return friend;
    }

    public void setFriend(boolean friend) {
        this.friend = friend;
    }
}
