package com.example.togaether;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        Glide.with(this).load("https://togaether.cafe24.com/images/back/img_back_1.png").fitCenter().into(imgBack);

        //강아지 아이디를 통해 생성, 배경 생성 후 강아지 생성해야 함
        PuppyView puppyView = new PuppyView(findViewById(R.id.lay_main), this, 123);

        imgBack.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                puppyView.setPositionPX(imgBack.getWidth()/2,imgBack.getHeight()*4/5);
                imgBack.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }
}