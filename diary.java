package com.example.test2;

import com.google.gson.annotations.SerializedName;

public class diary {
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;
    @SerializedName("onprivate")
    private String onprivate;
    @SerializedName("image")
    private String image;

    public diary(String title, String content, String onprivate, String image){
        this.title = title;
        this.content = content;
        this.onprivate = onprivate;
        this.image = image;
    }

    @Override
    public String toString(){
        return "diary{"+
                "diary_title=" + title +
                ", diary_content=" + content +
                ", privateOn=" + onprivate +
                ", image_path=" + image +
                "}";
    }
}
