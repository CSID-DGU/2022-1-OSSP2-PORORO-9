package com.example.togaether;

public class SpecialPlanItem {
    private int puppyNum, _y;
    private int x[] = new int[5];
    private int y[] = new int[5];
    private String[] say;
    private String back = "img_back_1.png";
    private String front = "img_front_none.png";
    private String name;

    public SpecialPlanItem(int puppyNum, int _y, int[] x, int[] y, String[] say, String back, String front, String name) {
        this.puppyNum = puppyNum;
        this._y = _y;
        this.x = x;
        this.y = y;
        this.say = say;
        this.back = back;
        this.front = front;
        this.name = name;
    }

    public int getPuppyNum() {
        return puppyNum;
    }

    public void setPuppyNum(int puppyNum) {
        this.puppyNum = puppyNum;
    }

    public int get_y() {
        return _y;
    }

    public void set_y(int _y) {
        this._y = _y;
    }

    public int[] getX() {
        return x;
    }

    public void setX(int[] x) {
        this.x = x;
    }

    public int[] getY() {
        return y;
    }

    public void setY(int[] y) {
        this.y = y;
    }

    public String[] getSay() {
        return say;
    }

    public void setSay(String[] say) {
        this.say = say;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public String getFront() {
        return front;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
