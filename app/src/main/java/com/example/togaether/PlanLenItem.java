package com.example.togaether;

import com.google.gson.annotations.SerializedName;

public class PlanLenItem {
    @SerializedName("shortMax")
    private int shortMax;
    @SerializedName("longMax")
    private int longMax;

    public PlanLenItem(int shortMax, int longMax) {
        this.shortMax = shortMax;
        this.longMax = longMax;
    }

    public void setLongMax(int longMax) {
        this.longMax = longMax;
    }

    public int getShortMax() {
        return shortMax;
    }

    public void setShortMax(int shortMax) {
        this.shortMax = shortMax;
    }

    public int getLongMax() {
        return longMax;
    }
}
