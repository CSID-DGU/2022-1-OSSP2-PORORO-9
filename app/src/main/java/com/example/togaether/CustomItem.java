package com.example.togaether;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

public class CustomItem {
    @SerializedName("sourceUrl")
    private String sourceUrl;
    @SerializedName("name")
    private String name;
    @SerializedName("stype")
    private String stype;
    //private CustomType type;

    public CustomItem(String sourceUrl, String name, String type) {
        this.sourceUrl = sourceUrl;
        this.name = name;
        this.stype = stype;
    }

    public String getSourceUrl() {
        return "https://togaether.cafe24.com/images/custom/" + sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomType getType() {
        return CustomType.valueOf(stype);
    }

//    public void setType(CustomType type) {
//        this.type = type;
//    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
        //setType(CustomType.valueOf(stype));
    }
}
