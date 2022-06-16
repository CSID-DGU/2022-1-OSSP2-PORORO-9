package com.example.togaether;

public class MenuItem {
    private String title, text;
    private int icon, id;

    public MenuItem(String title, String text, int icon, int id) {
        this.title = title;
        this.text = text;
        this.icon = icon;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
