package com.example.togaether;

import com.google.gson.annotations.SerializedName;

public class PuppyPartItem {
    @SerializedName("source")
    private String source;
    @SerializedName("dy")
    private int dy;
    @SerializedName("dist")
    private int dist;
    @SerializedName("size")
    private int size;
    @SerializedName("color")
    private int color;

    public PuppyPartItem(String source, int dy, int dist, int size, int color) {
        this.source = source;
        this.dy = dy;
        this.dist = dist;
        this.size = size;
        this.color = color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getColor() {
        return color;
    }

    public int getDist() {
        return dist;
    }

    public int getDy() {
        return dy;
    }

    public int getSize() {
        return size;
    }

    public String getSource() {
        return source;
    }
}
