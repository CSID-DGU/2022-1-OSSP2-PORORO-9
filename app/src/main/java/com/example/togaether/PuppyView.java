package com.example.togaether;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ProgressBar;

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


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import jp.wasabeef.glide.transformations.MaskTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PuppyView {
    LayoutInflater layoutInflater;
    AppCompatActivity act;
    View puppyView, puppy, vShadow, puppyOnly;
    ProgressBar pgPuppy;

    HashMap<CustomType, ImageView> imgMap = new HashMap<>();
    ImageView eyeWL, eyeWR;
    HashMap<CustomType, PartXY> xyMap = new HashMap<>();
    HashMap<CustomType, Integer> dyMap = new HashMap<>();
    HashMap<CustomType, Integer> distMap = new HashMap<>();
    HashMap<CustomType, Integer> sizeMap = new HashMap<>();
    HashMap<CustomType, Integer> colorMap = new HashMap<>();
    HashMap<CustomType, String> sourceMap = new HashMap<>();
    int w, h;
    int oriS;
    ViewGroup lay;
    boolean isSleep = false;


    private String name, callString, sex, uid;
    private int id;
    private LocalDate bdate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getBdate() {
        return bdate;
    }
    public void setBdate(LocalDate bdate) {
        this.bdate = bdate;
    }
    public String getCallString() {
        return callString;
    }
    public void setCallString(String callString) {
        this.callString = callString;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    PuppyView(ViewGroup lay, AppCompatActivity act) {
        setInit(lay, act, 1);
        setInitCustom();
        pgPuppy.setVisibility(View.GONE);
    }

    PuppyView(ViewGroup lay, AppCompatActivity act, int id) {
        setInit(lay, act, 1);
        setId(id);
        setInitCustom(id);
    }

    PuppyView(ViewGroup lay, AppCompatActivity act, PuppyItem item, float scale) {
        setInit(lay, act, scale);
        setInitCustom(item);
    }

    PuppyView(ViewGroup lay, AppCompatActivity act, PuppyInfoItem item, float scale) {
        setInit(lay, act, scale);
        setName(item.getPname());
        setCallString(item.getPcall());
        setSex(item.getPsex());
        setUid(item.getUid());
        setBdate(item.getPbdate());
        setId(item.getPid());
        setInitCustom(item.getPcustom());
    }

    PuppyView(ViewGroup lay, AppCompatActivity act, PuppyInfoItem item) {
        this(lay, act, item, 1);
    }

    PuppyView(ViewGroup lay, AppCompatActivity act, PuppyItem item) {
        this(lay, act, item, 1);
    }


    public void setInit(ViewGroup lay, AppCompatActivity act, float scale) {
        this.lay = lay;
        this.act = act;
        layoutInflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        puppyView = (View) layoutInflater.inflate(R.layout.puppy_view, lay, false);
        lay.addView(puppyView);
        puppy = puppyView.findViewById(R.id.lay_puppy);
        puppyOnly = puppy.findViewById(R.id.lay_puppy_only);
        pgPuppy = puppy.findViewById(R.id.pg_puppy);

        puppy.setScaleX(scale);
        puppy.setScaleY(scale);

        oriS = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, act.getResources().getDisplayMetrics());

        imgMap.put(CustomType.FACE1,(ImageView) puppy.findViewById(R.id.img_face1));
        imgMap.put(CustomType.FACE2,(ImageView) puppy.findViewById(R.id.img_face2));
        imgMap.put(CustomType.FACE3,(ImageView) puppy.findViewById(R.id.img_face3));
        imgMap.put(CustomType.BODY,(ImageView) puppy.findViewById(R.id.img_body));
        imgMap.put(CustomType.MOUTH,(ImageView) puppy.findViewById(R.id.img_mouth));
        imgMap.put(CustomType.NOSE,(ImageView) puppy.findViewById(R.id.img_nose));
        imgMap.put(CustomType.EAR_L,(ImageView) puppy.findViewById(R.id.img_ear_left));
        imgMap.put(CustomType.EAR_R,(ImageView) puppy.findViewById(R.id.img_ear_right));
        imgMap.put(CustomType.EYE_L,(ImageView) puppy.findViewById(R.id.img_eye_left));
        imgMap.put(CustomType.EYE_R,(ImageView) puppy.findViewById(R.id.img_eye_right));
        imgMap.put(CustomType.EYEBROW_L,(ImageView) puppy.findViewById(R.id.img_eyebrow_left));
        imgMap.put(CustomType.EYEBROW_R,(ImageView) puppy.findViewById(R.id.img_eyebrow_right));
        vShadow = (View) puppy.findViewById(R.id.v_shadow);
        eyeWL = (ImageView) puppy.findViewById(R.id.img_eyew_left);
        eyeWR = (ImageView) puppy.findViewById(R.id.img_eyew_right);

        for(CustomType t: CustomType.values()) {
            ImageView targetView = imgMap.get(t);
            xyMap.put(t,new PartXY(targetView.getX(),targetView.getY()));
        }

        puppy.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                w = puppy.getWidth();
                h = puppy.getHeight();
                //oriS = imgMap.get(CustomType.FACE1).getWidth();
                puppy.setTranslationX(puppy.getX() - w/2);
                puppy.setTranslationY(puppy.getY() - h/2);
                puppy.setPivotX((float) w/2);
                puppy.setPivotY((float) h*3/4);

                final ValueAnimator bubbleAnim = ValueAnimator.ofFloat(-0.25f, 0.25f);
                bubbleAnim.setDuration(1000);
                bubbleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        puppy.setScaleY(puppy.getScaleX()-(float) animation.getAnimatedValue()/20);
                    }
                });
                bubbleAnim.setRepeatCount(-1);
                bubbleAnim.setRepeatMode(ValueAnimator.REVERSE);
                bubbleAnim.start();

                puppy.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        puppy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random rand = new Random();
                Float rate = rand.nextFloat()+0.7F;
                SoundPlayer.play(SoundPlayer.SE_PET, rate);
                final ValueAnimator anim = ValueAnimator.ofFloat(-puppy.getScaleX()*0.5f, puppy.getScaleX()*0.5f);
                anim.setDuration((int)(200/rate));
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        puppy.setScaleY(puppy.getScaleX()-(float) animation.getAnimatedValue()/20);
                    }
                });
                anim.setRepeatCount(2);
                anim.setRepeatMode(ValueAnimator.REVERSE);
                anim.start();
            }
        });
    }
    //커스터마이징 화면에서 초기화
    public void setInitCustom() {
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
        colorMap.put(CustomType.EYE_L,Color.parseColor("#000000"));
        colorMap.put(CustomType.EYE_R,Color.parseColor("#000000"));

        //색상 초기화
        for(CustomType t: CustomType.values()) {
            setColor(t, colorMap.get(t));
        }
    }
    //id로 가져와서 초기화
    public void setInitCustom(int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://togaether.cafe24.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitPuppy retrofitAPI = retrofit.create(RetrofitPuppy.class);
        //HashMap<String, Object> input = new HashMap<>();
        retrofitAPI.getData(id).enqueue(new Callback<PuppyInfoItem>() {
            @Override
            public void onResponse(Call<PuppyInfoItem> call, Response<PuppyInfoItem> response) {
                if(response.isSuccessful()) {
                    PuppyInfoItem data = response.body();
                    if(data.getPid() != -1) {
                        setName(data.getPname());
                        setCallString(data.getPcall());
                        setSex(data.getPsex());
                        setUid(data.getUid());
                        setBdate(data.getPbdate());
                        setId(data.getPid());
                        setInitCustom(data.getPcustom());
                    }
                    else {
                        TGTDialog dialog = new TGTDialog(lay.getContext());
                        dialog.showDialogOneBtn("오류 발생", "정보를 가져오는데 문제가 발생했습니다!", "확인", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(act, PuppyListActivity.class);
                                act.startActivity(intent);
                                act.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                act.finish();
                                dialog.dissmissDailog();
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<PuppyInfoItem> call, Throwable t) {
                Log.e("=========OMG","here" + t);
            }
        });
    }
    //PuppyItem 받아서 초기화
    public void setInitCustom(PuppyItem data) {
        PuppyPartItem partData[] = {data.getEar_l(), data.getEar_r(), data.getEye_l(), data.getEye_r(), data.getEyebrow_l(), data.getEyebrow_r(), data.getFace1(), data.getFace2(), data.getFace3(), data.getMouth(), data.getNose(), data.getBody()};
        // EAR_L, EAR_R, EYE_L, EYE_R, EYEBROW_L, EYEBROW_R, FACE1, FACE2, FACE3, MOUTH, NOSE, BODY
        int i = 0;
        for(CustomType t: CustomType.values()) {
            setMap(t, partData[i++]);
        }
        i = 0;
        for(CustomType t: CustomType.values()) {
            setCustom(t, partData[i++]);
        }
    }
    private void setMap(CustomType t, PuppyPartItem item) {
        dyMap.put(t,item.getDy());
        distMap.put(t,-item.getDist());
        sizeMap.put(t,item.getSize());
        colorMap.put(t,item.getColor());
        sourceMap.put(t,item.getSource());
    }

    private void setCustom(CustomType t, PuppyPartItem item) {
        if(t!=CustomType.FACE3 || (!isSleep && (t==CustomType.EYE_L || t==CustomType.EYE_R))) {
            setImgSource(t, item.getSource());
        }
        setSize(t, item.getSize());
        setDy(t, item.getDy());
        setColor(t, item.getColor());
        setPartPosition(t);
        if(t==CustomType.EAR_L || t==CustomType.EYE_L || t==CustomType.EYEBROW_L) {
            setDist(t, -item.getDist());
        }
    }

    public void setPosition(int x, int y) {
        puppy.setTranslationX((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, x, act.getResources().getDisplayMetrics()) - w/2); //puppy.findViewById(R.id.lay_puppy).
        puppy.setTranslationY((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, y, act.getResources().getDisplayMetrics()) - h/2);
    }
    public void setPositionPXX(float x) {
        float x60 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, act.getResources().getDisplayMetrics());
        puppy.setTranslationX(x - x60);
    }
    public void setPositionPXY(float y) {
        float y142 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 142.5f, act.getResources().getDisplayMetrics());
        puppy.setTranslationY(y - y142);
    }
    public float getPositionPXX() {
        return puppy.getX() + w/2;
    }
    public float getPositionPXY() {
        return puppy.getY();
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
            pgPuppy.setVisibility(View.VISIBLE);
            puppyOnly.setVisibility(View.GONE);
            Glide.with(lay).load(url)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            pgPuppy.setVisibility(View.GONE);
                            puppyOnly.setVisibility(View.VISIBLE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            Glide.with(lay).load(sourceMap.get(CustomType.FACE3))
                                    .listener(new RequestListener<Drawable>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                            pgPuppy.setVisibility(View.GONE);
                                            puppyOnly.setVisibility(View.VISIBLE);
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                            pgPuppy.setVisibility(View.GONE);
                                            puppyOnly.setVisibility(View.VISIBLE);
                                            return false;
                                        }
                                    })
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
            if (type == CustomType.EYE_L)
                Glide.with(lay).load(url.replace("eye","eyew")).into(eyeWL);
            if (type == CustomType.EYE_R)
                Glide.with(lay).load(url.replace("eye","eyew")).into(eyeWR);
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
        if (type == CustomType.EYE_L) {
            eyeWL.setLayoutParams(newSize);
        }
        else if (type == CustomType.EYE_R) {
            eyeWR.setLayoutParams(newSize);
        }
    }
    public void setCSize(CustomType type, int size) {
        setSize(type, size);
        if(type == CustomType.FACE1) {
            setSize(CustomType.FACE3, size);
            sizeMap.put(CustomType.FACE3, size);
            setPartPosition(CustomType.FACE3);
        }
    }
    public void setDy(CustomType type, int dy) {
        dyMap.put(type, dy);
        setPartPosition(type);
    }
    public void setCDy(CustomType type, int dy) {
        // dDy, preDy는 FACE1 움직일 때 따라 움직이는 애들 용
        int dDy, preDy = dyMap.get(type);
        setDy(type, dy);
        dDy = dy - preDy;
        if(type == CustomType.FACE1) {
            CustomType[] ts = {CustomType.FACE2, CustomType.FACE3, CustomType.EAR_L, CustomType.EAR_R, CustomType.NOSE, CustomType.MOUTH, CustomType.EYE_L, CustomType.EYE_R, CustomType.EYEBROW_L, CustomType.EYEBROW_R};
            for(CustomType t: ts) {
                dyMap.put(t, dyMap.get(t) + dDy);
                setPartPosition(t);
            }
        }
    }
    // 왼쪽 타입에만 적용
    public void setDist(CustomType type, int dist) {
        distMap.put(type, -dist);
        setPartPosition(type);
        CustomType targetType = CustomType.valueOf(type.name().substring(0, type.name().length() - 1) + "R");
        distMap.put(targetType, dist);
        setPartPosition(targetType);
    }
    public void setPartPosition(CustomType type) {
        int size = sizeMap.get(type);
        int s = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, act.getResources().getDisplayMetrics());
        float dy = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dyMap.get(type), act.getResources().getDisplayMetrics());
        float dist = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, distMap.get(type), act.getResources().getDisplayMetrics());

        ImageView targetView = imgMap.get(type);
        targetView.setTranslationX(xyMap.get(type).getX() + dist - (float)(s - oriS)/2);

        float tempYCenter;
        if(type == CustomType.BODY) { tempYCenter = (float)(s - oriS)*3/4; }
        else { tempYCenter = (float)(s - oriS)/2; }

        targetView.setTranslationY(xyMap.get(type).getY() + dy - tempYCenter);
        if(type == CustomType.EYE_L || type == CustomType.EYE_R) {
            eyeWL.setTranslationX(imgMap.get(CustomType.EYE_L).getX());
            eyeWL.setTranslationY(imgMap.get(CustomType.EYE_L).getY());

            eyeWR.setTranslationX(imgMap.get(CustomType.EYE_R).getX());
            eyeWR.setTranslationY(imgMap.get(CustomType.EYE_R).getY());
        }

        if(type == CustomType.BODY) {
            float _dy = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 65, act.getResources().getDisplayMetrics());
            vShadow.setTranslationY(imgMap.get(CustomType.BODY).getY() + tempYCenter + _dy);
        }
    }
    public String getCustomJSON() {
        String jString = "{";
        for(CustomType t: CustomType.values()) {
            jString += "\"" + t.name() + "\":";
            jString += "{";
            jString += "\"source\":" + "\"" + sourceMap.get(t) + "\",";
            jString += "\"dy\":" + dyMap.get(t) + ",";
            jString += "\"dist\":" + distMap.get(t) + ",";
            jString += "\"size\":" + sizeMap.get(t) + ",";
            jString += "\"color\":" + colorMap.get(t);
            jString += "}";
            if(t != CustomType.BODY) { jString += ","; }
        }
        jString += "}";
        return jString;
    }

    public void setSleep(boolean isSleep){
        this.isSleep = isSleep;
        if(isSleep) {
            setImgSource(CustomType.EYE_L, "http://togaether.cafe24.com/images/custom/img_cus_eye_0_sleep.png");
            setImgSource(CustomType.EYE_R, "http://togaether.cafe24.com/images/custom/img_cus_eye_0_sleep.png");
        }
        else {
            //setImgSource(CustomType.EYE_L, sourceMap.get(CustomType.EYE_L));
            //setImgSource(CustomType.EYE_R, sourceMap.get(CustomType.EYE_R));
        }
    }

    public float pxToDp(Context context, float px) {

        // 해상도 마다 다른 density 를 반환. xxxhdpi는 density = 4
        float density = context.getResources().getDisplayMetrics().density;

        if (density == 1.0)      // mpdi  (160dpi) -- xxxhdpi (density = 4)기준으로 density 값을 재설정 한다
            density *= 4.0;
        else if (density == 1.5) // hdpi  (240dpi)
            density *= (8 / 3);
        else if (density == 2.0) // xhdpi (320dpi)
            density *= 2.0;

        return px / density;     // dp 값 반환
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