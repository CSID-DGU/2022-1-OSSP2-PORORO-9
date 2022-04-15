package com.example.togaether;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class PuppyView {
    LayoutInflater layoutInflater;
    AppCompatActivity act;
    View puppyView, puppy;
    ImageView imgFace, imgBody, imgNose, imgMouth, imgEyeLeft, imgEyeRight, imgEarLeft, imgEarRight;
    int w, h;

    PuppyView(ViewGroup lay, AppCompatActivity act) {
        this.act = act;
        layoutInflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        puppyView = (View) layoutInflater.inflate(R.layout.puppy_view, lay, true);
        puppy = puppyView.findViewById(R.id.lay_puppy);

        puppy.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //Log.e("Width",""+puppy.getWidth());
                w = puppy.getWidth();
                h = puppy.getHeight();
                puppy.setTranslationX(puppy.getX() - w/2);
                puppy.setTranslationY(puppy.getY() - h/2);
                puppy.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        imgFace = (ImageView) puppyView.findViewById(R.id.img_face1);
        imgBody = (ImageView) puppyView.findViewById(R.id.img_body);
        imgMouth = (ImageView) puppyView.findViewById(R.id.img_mouth);
        imgNose = (ImageView) puppyView.findViewById(R.id.img_nose);
        imgEarLeft = (ImageView) puppyView.findViewById(R.id.img_ear_left);
        imgEarRight = (ImageView) puppyView.findViewById(R.id.img_ear_right);
        imgEyeLeft = (ImageView) puppyView.findViewById(R.id.img_eye_left);
        imgEyeRight = (ImageView) puppyView.findViewById(R.id.img_eye_right);
    }
    public void setPosition(int x, int y) {
        puppy.setTranslationX((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, x, act.getResources().getDisplayMetrics()) - w/2); //puppy.findViewById(R.id.lay_puppy).
        puppy.setTranslationY((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, y, act.getResources().getDisplayMetrics()) - h/2);
    }
    public void setPositionPX(int x, int y) {
        puppy.setTranslationX(x - w/2);
        puppy.setTranslationY(y - h/2);
    }
    public void setColor() {
        imgFace.setColorFilter(Color.parseColor("#e28743"), PorterDuff.Mode.MULTIPLY);
        imgBody.setColorFilter(Color.parseColor("#e28743"), PorterDuff.Mode.MULTIPLY);
    }
}
