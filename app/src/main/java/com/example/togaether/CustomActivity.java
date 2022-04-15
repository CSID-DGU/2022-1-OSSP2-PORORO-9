package com.example.togaether;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class CustomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        ViewGroup layBack = findViewById(R.id.lay_preview);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        Glide.with(this).load(R.drawable.img_back_cus2).fitCenter().into(imgBack);

        PuppyView puppyView = new PuppyView(layBack, this);
        puppyView.setColor();

        imgBack.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                puppyView.setPositionPX(imgBack.getWidth()/2,imgBack.getHeight()*4/5);
                imgBack.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }
}