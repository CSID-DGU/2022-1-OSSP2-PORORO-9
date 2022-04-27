package com.example.togaether;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;


import java.util.HashMap;

import jp.wasabeef.glide.transformations.MaskTransformation;

public class PuppyView {
    LayoutInflater layoutInflater;
    AppCompatActivity act;
    View puppyView, puppy;
    //ImageView imgFace, imgBody, imgNose, imgMouth, imgEyeLeft, imgEyeRight, imgEarLeft, imgEarRight;
    HashMap<CustomType, ImageView> imgMap = new HashMap<>();
    HashMap<CustomType, PartXY> xyMap = new HashMap<>();
    HashMap<CustomType, Integer> dyMap = new HashMap<>();
    HashMap<CustomType, Integer> distMap = new HashMap<>();
    HashMap<CustomType, Integer> sizeMap = new HashMap<>();
    HashMap<CustomType, Integer> colorMap = new HashMap<>();
    HashMap<CustomType, String> sourceMap = new HashMap<>();
    int w, h;
    int oriS;
    ViewGroup lay;

    PuppyView(ViewGroup lay, AppCompatActivity act) {
        this.lay = lay;
        this.act = act;
        layoutInflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        puppyView = (View) layoutInflater.inflate(R.layout.puppy_view, lay, true);
        puppy = puppyView.findViewById(R.id.lay_puppy);

        /*
        imgFace = (ImageView) puppyView.findViewById(R.id.img_face1);
        imgBody = (ImageView) puppyView.findViewById(R.id.img_body);
        imgMouth = (ImageView) puppyView.findViewById(R.id.img_mouth);
        imgNose = (ImageView) puppyView.findViewById(R.id.img_nose);
        imgEarLeft = (ImageView) puppyView.findViewById(R.id.img_ear_left);
        imgEarRight = (ImageView) puppyView.findViewById(R.id.img_ear_right);
        imgEyeLeft = (ImageView) puppyView.findViewById(R.id.img_eye_left);
        imgEyeRight = (ImageView) puppyView.findViewById(R.id.img_eye_right);
        */
        imgMap.put(CustomType.FACE1,(ImageView) puppyView.findViewById(R.id.img_face1));
        imgMap.put(CustomType.FACE2,(ImageView) puppyView.findViewById(R.id.img_face2));
        imgMap.put(CustomType.FACE3,(ImageView) puppyView.findViewById(R.id.img_face3));
        imgMap.put(CustomType.BODY,(ImageView) puppyView.findViewById(R.id.img_body));
        imgMap.put(CustomType.MOUTH,(ImageView) puppyView.findViewById(R.id.img_mouth));
        imgMap.put(CustomType.NOSE,(ImageView) puppyView.findViewById(R.id.img_nose));
        imgMap.put(CustomType.EAR_L,(ImageView) puppyView.findViewById(R.id.img_ear_left));
        imgMap.put(CustomType.EAR_R,(ImageView) puppyView.findViewById(R.id.img_ear_right));
        imgMap.put(CustomType.EYE_L,(ImageView) puppyView.findViewById(R.id.img_eye_left));
        imgMap.put(CustomType.EYE_R,(ImageView) puppyView.findViewById(R.id.img_eye_right));
        imgMap.put(CustomType.EYEBROW_L,(ImageView) puppyView.findViewById(R.id.img_eyebrow_left));
        imgMap.put(CustomType.EYEBROW_R,(ImageView) puppyView.findViewById(R.id.img_eyebrow_right));

        sourceMap.put(CustomType.FACE1,"https://togaether.cafe24.com/images/custom/img_cus_f1_1.png");
        sourceMap.put(CustomType.FACE2,"https://togaether.cafe24.com/images/custom/img_cus_none.png");
        sourceMap.put(CustomType.FACE3,"https://togaether.cafe24.com/images/custom/img_cus_none.png");
        sourceMap.put(CustomType.BODY,"https://togaether.cafe24.com/images/custom/img_cus_body_1.png");
        sourceMap.put(CustomType.MOUTH,"https://togaether.cafe24.com/images/custom/img_cus_mouth_1.png");
        sourceMap.put(CustomType.NOSE,"https://togaether.cafe24.com/images/custom/img_cus_nose_1.png");
        sourceMap.put(CustomType.EAR_L,"https://togaether.cafe24.com/images/custom/img_cus_ear_1.png");
        sourceMap.put(CustomType.EAR_R,"https://togaether.cafe24.com/images/custom/img_cus_ear_1.png");
        sourceMap.put(CustomType.EYE_L,"https://togaether.cafe24.com/images/custom/img_cus_eye_1.png");
        sourceMap.put(CustomType.EYE_R,"https://togaether.cafe24.com/images/custom/img_cus_eye_1.png");
        sourceMap.put(CustomType.EYEBROW_L,"https://togaether.cafe24.com/images/custom/img_cus_none.png");
        sourceMap.put(CustomType.EYEBROW_R,"https://togaether.cafe24.com/images/custom/img_cus_none.png");

        Glide.with(lay).load(sourceMap.get(CustomType.FACE3))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .apply(bitmapTransform(new MultiTransformation<Bitmap>(new CenterCrop(), new MaskTransformation2(imgMap.get(CustomType.FACE1).getDrawable())))).into(imgMap.get(CustomType.FACE3));

        for(CustomType t: CustomType.values()) {
            ImageView targetView = imgMap.get(t);
            dyMap.put(t,0);
            distMap.put(t,0);
            sizeMap.put(t,100);
            colorMap.put(t,Color.parseColor("#ffffff"));
            xyMap.put(t,new PartXY(targetView.getX(),targetView.getY()));
        }
        colorMap.put(CustomType.FACE2,Color.parseColor("#e6e6e6"));
        colorMap.put(CustomType.FACE3,Color.parseColor("#6e6e6e"));
        colorMap.put(CustomType.EYEBROW_L,Color.parseColor("#6e6e6e"));
        colorMap.put(CustomType.EYEBROW_R,Color.parseColor("#6e6e6e"));
        colorMap.put(CustomType.EAR_L,Color.parseColor("#452d00"));
        colorMap.put(CustomType.EAR_R,Color.parseColor("#452d00"));

        //색상 초기화
        for(CustomType t: CustomType.values()) {
            setColor(t, colorMap.get(t));
        }

        puppy.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //Log.e("Width",""+puppy.getWidth());
                w = puppy.getWidth();
                h = puppy.getHeight();
                oriS = imgMap.get(CustomType.FACE1).getWidth();
                //oriS = puppy.getWidth();
                puppy.setTranslationX(puppy.getX() - w/2);
                puppy.setTranslationY(puppy.getY() - h/2);
                puppy.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }
    public void setPosition(int x, int y) {
        puppy.setTranslationX((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, x, act.getResources().getDisplayMetrics()) - w/2); //puppy.findViewById(R.id.lay_puppy).
        puppy.setTranslationY((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, y, act.getResources().getDisplayMetrics()) - h/2);
    }
    public void setPositionPX(int x, int y) {
        puppy.setTranslationX(x - w/2);
        puppy.setTranslationY(y - h/2);
    }
    public void setColor(CustomType type, int color) {
        imgMap.get(type).setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        colorMap.put(type,color);
    }
    public void setImgSource(CustomType type, String url) {
        sourceMap.put(type, url);
        if(type == CustomType.FACE1) {
            Glide.with(lay).load(url)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            Glide.with(lay).load(sourceMap.get(CustomType.FACE3))
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)
                                    .apply(bitmapTransform(new MultiTransformation<Bitmap>(new CenterCrop(), new MaskTransformation2(resource)))).into(imgMap.get(CustomType.FACE3));
                            return false;
                        }
                    })
                    .into(imgMap.get(type));
        }
        else if(type == CustomType.FACE3) {
            Glide.with(lay).load(url)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .apply(bitmapTransform(new MultiTransformation<Bitmap>(new CenterCrop(), new MaskTransformation2(imgMap.get(CustomType.FACE1).getDrawable())))).into(imgMap.get(CustomType.FACE3));
        }
        else {
            Glide.with(lay).load(url).into(imgMap.get(type));
        }
    }
    public void setSize(CustomType type, int size) {
        int s = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, act.getResources().getDisplayMetrics());
        ImageView targetView = imgMap.get(type);
        ViewGroup.LayoutParams newSize = targetView.getLayoutParams();
        newSize.width = s;
        newSize.height = s;
        targetView.setLayoutParams(newSize);
        sizeMap.put(type, size);
        setPartPosition(type);
        if(type == CustomType.FACE1) {
            setSize(CustomType.FACE3, size);
            sizeMap.put(CustomType.FACE3, size);
            setPartPosition(CustomType.FACE3);
        }
    }
    public void setDy(CustomType type, int dy) {
        dyMap.put(type, dy);
        setPartPosition(type);
        if(type == CustomType.FACE1) {
            CustomType[] ts = {CustomType.FACE2, CustomType.FACE3, CustomType.EAR_L, CustomType.EAR_R, CustomType.NOSE, CustomType.MOUTH, CustomType.EYE_L, CustomType.EYE_R, CustomType.EYEBROW_L, CustomType.EYEBROW_R};
            for(CustomType t: ts) {
                dyMap.put(t, dy);
                setPartPosition(t);
            }
        }
    }
    public void setPartPosition(CustomType type) {
        int size = sizeMap.get(type);
        int s = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, act.getResources().getDisplayMetrics());
        float dy = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dyMap.get(type), act.getResources().getDisplayMetrics());
        ImageView targetView = imgMap.get(type);
        targetView.setTranslationX(xyMap.get(type).getX() - (float)(s - oriS)/2);
        targetView.setTranslationY(xyMap.get(type).getY() + dy - (float)(s - oriS)/2);
    }
}

class PartXY{
    private float x, y;

    public PartXY(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}