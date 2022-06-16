package com.example.togaether;

import com.google.gson.annotations.SerializedName;

public class PlanItem {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("back")
    private String back;
    @SerializedName("front")
    private String front;
    @SerializedName("y")
    private int y;
    @SerializedName("say")
    private String[] say;

    public PlanItem(int id, String name, String back, String front, int y, String[] say) {
        this.id = id;
        this.name = name;
        this.back = back;
        this.front = front;
        this.y = y;
        this.say = say;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public String[] getSay() {
        return say;
    }

    public String getFront() {
        return front;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public void setSay(String[] say) {
        this.say = say;
    }
}
