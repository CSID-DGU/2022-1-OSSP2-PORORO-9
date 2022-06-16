package com.example.togaether;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class DiaryItem {
    @SerializedName("did")
    private int did;
    @SerializedName("uid")
    private String uid;
    @SerializedName("uname")
    private String uname;
    @SerializedName("dtitle")
    private String dtitle;
    @SerializedName("dcontent")
    private String dcontent;
    @SerializedName("dimg")
    private String dimg;
    @SerializedName("dprivate")
    private boolean dprivate;
    @SerializedName("ddate")
    private String ddate;
    @SerializedName("dheart")
    private int dheart;
    @SerializedName("heart")
    private boolean heart;
    @SerializedName("dcomment")
    private int dcomment;
    @SerializedName("comment")
    private List<CommentItem> comment;


    public DiaryItem(int did, String uid, String uname, String dtitle, String dcontent, String dimg, boolean dprivate, String ddate, int dheart, int dcomment) {
        this.did = did;
        this.uid = uid;
        this.uname = uname;
        this.dtitle = dtitle;
        this.dcontent = dcontent;
        this.dimg = dimg;
        this.dprivate = dprivate;
        this.ddate = ddate;
        this.dheart = dheart;
        this.dcomment = dcomment;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public int getDheart() {
        return dheart;
    }

    public void setDheart(int dheart) {
        this.dheart = dheart;
    }

    public int getDcomment() {
        return dcomment;
    }

    public void setDcomment(int dcomment) {
        this.dcomment = dcomment;
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

    public String getDtitle() {
        return dtitle;
    }

    public void setDtitle(String dtitle) {
        this.dtitle = dtitle;
    }

    public String getDcontent() {
        return dcontent;
    }

    public void setDcontent(String dcontent) {
        this.dcontent = dcontent;
    }

    public String getDimg() {
        return dimg;
    }

    public void setDimg(String dimg) {
        this.dimg = dimg;
    }

    public boolean isDprivate() {
        return dprivate;
    }

    public void setDprivate(boolean dprivate) {
        this.dprivate = dprivate;
    }

    public String getDdate() {
        return ddate;
    }

    public void setDdate(String ddate) {
        this.ddate = ddate;
    }

    public boolean isHeart() {
        return heart;
    }

    public void setHeart(boolean heart) {
        this.heart = heart;
    }

    public List<CommentItem> getComment() {
        return comment;
    }

    public void setComment(List<CommentItem> comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "DiaryItem{" +
                "did=" + did +
                ", uid='" + uid + '\'' +
                ", uname='" + uname + '\'' +
                ", dtitle='" + dtitle + '\'' +
                ", dcontent='" + dcontent + '\'' +
                ", dimg='" + dimg + '\'' +
                ", dprivate=" + dprivate +
                ", ddate='" + ddate + '\'' +
                ", dheart=" + dheart +
                ", heart=" + heart +
                ", dcomment=" + dcomment +
                '}';
    }
}
