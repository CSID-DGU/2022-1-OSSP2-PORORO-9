package com.example.togaether;

import com.google.gson.annotations.SerializedName;

public class CommentItem {
    @SerializedName("dcid")
    private int dcid;
    @SerializedName("pdcid")
    private int pdcid;
    @SerializedName("did")
    private int did;
    @SerializedName("uid")
    private String uid;
    @SerializedName("uname")
    private String uname;
    @SerializedName("dccontent")
    private String dccontent;
    @SerializedName("dcdate")
    private String dcdate;
    @SerializedName("dcdelete")
    private boolean dcdelete;

    public CommentItem(int dcid, int pdcid, int did, String uid, String uname, String dccontent, String dcdate, boolean dcdelete) {
        this.dcid = dcid;
        this.pdcid = pdcid;
        this.did = did;
        this.uid = uid;
        this.uname = uname;
        this.dccontent = dccontent;
        this.dcdate = dcdate;
        this.dcdelete = dcdelete;
    }

    public int getDcid() {
        return dcid;
    }

    public void setDcid(int dcid) {
        this.dcid = dcid;
    }

    public int getPdcid() {
        return pdcid;
    }

    public void setPdcid(int pdcid) {
        this.pdcid = pdcid;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
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

    public String getDccontent() {
        return dccontent;
    }

    public void setDccontent(String dccontent) {
        this.dccontent = dccontent;
    }

    public String getDcdate() {
        return dcdate;
    }

    public void setDcdate(String dcdate) {
        this.dcdate = dcdate;
    }

    public boolean isDcdelete() {
        return dcdelete;
    }

    public void setDcdelete(boolean dcdelete) {
        this.dcdelete = dcdelete;
    }

    @Override
    public String toString() {
        return "CommentItem{" +
                "dcid=" + dcid +
                ", pdcid=" + pdcid +
                ", did=" + did +
                ", uid='" + uid + '\'' +
                ", uname='" + uname + '\'' +
                ", dccontent='" + dccontent + '\'' +
                ", dcdate='" + dcdate + '\'' +
                ", dcdelete=" + dcdelete +
                '}';
    }
}
