package com.example.togaether;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;

import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class CustomActivity extends AppCompatActivity {
    Fragment[] fragArr = new Fragment[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        ViewGroup layBack = findViewById(R.id.lay_preview);
        PuppyView puppyView = new PuppyView(layBack, this);

        fragArr[0] = new FragCus(puppyView,CustomType.FACE1);
        fragArr[1] = new FragCus(puppyView,CustomType.FACE3);
        fragArr[2] = new FragCus(puppyView,CustomType.EYE_L); // fragment type 2
        fragArr[3] = new FragCus(puppyView,CustomType.NOSE);
        fragArr[4] = new FragCus(puppyView,CustomType.MOUTH);
        fragArr[5] = new FragCus(puppyView,CustomType.FACE2);
        fragArr[6] = new FragCus(puppyView,CustomType.EAR_L); // fragment type 2
        fragArr[7] = new FragCus(puppyView,CustomType.EYEBROW_L); // fragment type 2
        fragArr[8] = new FragCus(puppyView,CustomType.BODY);

        getSupportFragmentManager().beginTransaction().add(R.id.lay_cusframe, fragArr[0]).commit();
        TabLayout layTabs = (TabLayout) findViewById(R.id.lay_tabs);
        layTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment sel = fragArr[tab.getPosition()];
                getSupportFragmentManager().beginTransaction().replace(R.id.lay_cusframe, sel).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        Glide.with(this).load(R.drawable.img_back_cus2).fitCenter().into(imgBack);

        imgBack.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                puppyView.setPositionPX(imgBack.getWidth()/2,imgBack.getHeight()*4/5);
                imgBack.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }
}