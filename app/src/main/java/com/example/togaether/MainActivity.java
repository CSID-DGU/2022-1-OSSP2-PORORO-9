package com.example.togaether;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ImageView imgBack;
    ImageView imgFront;
    TextView tvDoing;
    PuppyView puppyView;
    private Schedule schedule;
    private PlanItem currentPlan;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int puppyId = 123;
        schedule = new Schedule();

        imgBack = (ImageView) findViewById(R.id.img_back);
        imgFront = (ImageView) findViewById(R.id.img_front);
        tvDoing = (TextView) findViewById(R.id.tv_doing);

        //강아지 아이디를 통해 생성, 배경 생성 후 강아지 생성해야 함
        puppyView = new PuppyView(findViewById(R.id.lay_mypuppy), this, puppyId);
        //PuppyView puppyView2 = new PuppyView(findViewById(R.id.lay_mypuppy), this);
        puppyView.setName("테스트");



        imgBack.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                puppyView.setPositionPX(imgBack.getWidth()/2,imgBack.getHeight()*4/5);
                imgBack.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                schedule.makeSchedule(puppyId);
                getPlanInfo(schedule.getPlan()); //schedule.getPlan()
            }
        });
    }

    private void getPlanInfo(int planId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://togaether.cafe24.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitPlan retrofitAPI = retrofit.create(RetrofitPlan.class);
        //HashMap<String, Object> input = new HashMap<>();
        retrofitAPI.getData(planId).enqueue(new Callback<PlanItem>() {
            @Override
            public void onResponse(Call<PlanItem> call, Response<PlanItem> response) {
                if(response.isSuccessful()) {
                    PlanItem data = response.body();
                    currentPlan = data;
                    Glide.with(getApplicationContext()).load("http://togaether.cafe24.com/images/back/" + data.getBack()).fitCenter().into(imgBack);
                    Glide.with(getApplicationContext()).load("http://togaether.cafe24.com/images/back/" + data.getFront()).fitCenter().into(imgFront);
                    puppyView.setPositionY(data.getY());
                    tvDoing.setText(puppyView.getName() + "(이)는 지금 " + data.getName());

                    if(data.getId() == 0) {
                        puppyView.setSleep(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<PlanItem> call, Throwable t) {
                Log.e("=========OMG",schedule.getPlan()+" here " + t);
            }
        });
    }
}