package com.example.togaether;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PuppyListActivity extends TGTActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puppy_list);
        UserData userData = UserData.getInstance();

        AppCompatButton btnNewPuppy = (AppCompatButton) findViewById(R.id.btn_newpuppy);
        ListView listPuppy = (ListView) findViewById(R.id.list_puppy);
        listPuppy.setEmptyView((TextView)findViewById(R.id.tv_empty));

        PuppyListAdapter adapter = new PuppyListAdapter();
        // HTTP 요청&응답 처리하여 리스트에 추가
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://togaether.cafe24.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitPuppy retrofitAPI = retrofit.create(RetrofitPuppy.class);
        HashMap<String, Object> input = new HashMap<>();
        input.put("uid", userData.getUserId());
        retrofitAPI.getPuppyListById(input).enqueue(new Callback<List<PuppyInfoItem>>() {
            @Override
            public void onResponse(Call<List<PuppyInfoItem>> call, Response<List<PuppyInfoItem>> response) {
                if(response.isSuccessful()) {
                    List<PuppyInfoItem> data = response.body();
                    adapter.addItem(data);
                    listPuppy.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<PuppyInfoItem>> call, Throwable t) {
                //Log.e("=========OMG","here" + t);
            }
        });

        listPuppy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PuppyInfoItem item = adapter.getItem(i);
                TGTDialog dialog = new TGTDialog(view.getContext());
                dialog.showDialogTwoBtn("강아지 선택", GlobalSelector.getPostWord(item.getPname(),"을") + " 만나러 가볼까요?", "네", "아니요", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SoundPlayer.play(SoundPlayer.SE_CLICK);
                        userData.setMyPid(item.getPid());
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("puppyItem", (new Gson()).toJson(item));
                        startActivity(intent);
                        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                        dialog.dissmissDailog();
                    }
                });
            }
        });

        btnNewPuppy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TGTDialog dialog = new TGTDialog(view.getContext());
                dialog.showDialogTwoBtn("새로운 강아지 등록", "새로운 아이를 등록하러 갈까요?", "네", "아니요", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SoundPlayer.play(SoundPlayer.SE_COMPLETE);
                        Intent intent = new Intent(getApplicationContext(), CustomActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                        dialog.dissmissDailog();
                    }
                });
            }
        });
        
        //브금 재생
        BGMController.getInstance().playMusic(this, R.raw.bgm_main);
    }

    @Override
    public void onBackPressed() {
        TGTDialog dialog = new TGTDialog(this);
        dialog.showDialogTwoBtn("종료 하시겠습니까?", "어플을 종료하시겠습니까?", "네", "아직이요!", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundPlayer.play(SoundPlayer.SE_CLICK);
                BGMController.getInstance().stopMusic();
                finishAndRemoveTask();
                dialog.dissmissDailog();
            }
        });
    }
}