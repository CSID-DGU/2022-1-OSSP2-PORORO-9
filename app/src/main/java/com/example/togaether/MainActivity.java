package com.example.togaether;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PuppyView puppyView = new PuppyView(findViewById(R.id.lay_main), this);
        puppyView.setPosition(100,0);
    }
}