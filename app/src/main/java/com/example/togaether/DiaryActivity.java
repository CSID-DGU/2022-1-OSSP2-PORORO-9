package com.example.togaether;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DiaryActivity extends TGTActivity {
    private String uid;
    private int enterIndex;
    ActivityResultLauncher<Intent> resultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        UserData userData = UserData.getInstance();

        Intent in = getIntent();
        uid = in.getStringExtra("uid");
        boolean isMyDiary = uid.equals(userData.getUserId());

        AppCompatButton btnNewDiary = (AppCompatButton) findViewById(R.id.btn_newdiary);
        TextView tvDiary = (TextView) findViewById(R.id.tv_diary);
        ListView listDiary = (ListView) findViewById(R.id.list_diary);
        listDiary.setEmptyView((TextView)findViewById(R.id.tv_empty));
        SwipeRefreshLayout layRefresh = (SwipeRefreshLayout)findViewById(R.id.lay_refresh);

        tvDiary.setText("다이어리");
        DiaryAdapter adapter = new DiaryAdapter();
        // HTTP 요청&응답 처리하여 리스트에 추가
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://togaether.cafe24.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitDiary retrofitAPI = retrofit.create(RetrofitDiary.class);
        HashMap<String, Object> input = new HashMap<>();
        input.put("uid", uid);
        input.put("myuid", userData.getUserId());
        retrofitAPI.getDiaryList(input).enqueue(new Callback<List<DiaryItem>>() {
            @Override
            public void onResponse(Call<List<DiaryItem>> call, Response<List<DiaryItem>> response) {
                if(response.isSuccessful()) {
                    List<DiaryItem> data = response.body();
                    if(!data.isEmpty()) {
                        adapter.addItem(data);
                    }
                    listDiary.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<DiaryItem>> call, Throwable t) {
            }
        });

        layRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RetrofitDiary retrofitAPI = retrofit.create(RetrofitDiary.class);
                HashMap<String, Object> input = new HashMap<>();
                input.put("uid", uid);
                input.put("myuid", userData.getUserId());
                retrofitAPI.getDiaryList(input).enqueue(new Callback<List<DiaryItem>>() {
                    @Override
                    public void onResponse(Call<List<DiaryItem>> call, Response<List<DiaryItem>> response) {
                        if(response.isSuccessful()) {
                            List<DiaryItem> data = response.body();
                            boolean ch = adapter.getCount() == 0;
                            adapter.clear();

                            adapter.addItem(data);
                            adapter.notifyDataSetChanged();
                            if(ch) {
                                listDiary.setAdapter(adapter);
                            }
                            layRefresh.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<DiaryItem>> call, Throwable t) {
                        layRefresh.setRefreshing(false);
                    }
                });
            }
        });

        listDiary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //다이어리 눌렀을 때 이벤트
                enterIndex = i;
                Intent intent = new Intent(getApplicationContext(), DiaryDetailActivity.class);
                intent.putExtra("did",adapter.getItem(i).getDid());
                resultLauncher.launch(intent);
            }
        });

        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK){
                            Intent intent = result.getData();
                            int heart = intent.getIntExtra("heart", -1);
                            boolean isHeart = intent.getBooleanExtra("isHeart", false);
                            int comment = intent.getIntExtra("comment", -1);
                            if (heart != -1) {
                                adapter.getItem(enterIndex).setDheart(heart);
                                adapter.getItem(enterIndex).setHeart(isHeart);
                            }
                            if (comment != -1) {
                                adapter.getItem(enterIndex).setDcomment(comment);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

        if(isMyDiary) {
            btnNewDiary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TGTDialog dialog = new TGTDialog(view.getContext());
                    dialog.showDialogTwoBtn("새로운 다이어리 등록", "새로운 글을 남기러 갈까요?", "네", "아니요", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SoundPlayer.play(SoundPlayer.SE_COMPLETE);
                            Intent intent = new Intent(getApplicationContext(), DiaryWriteActivity.class);
                            startActivity(intent);
                            finish();
                            dialog.dissmissDailog();
                        }
                    });
                }
            });
        }
        else {
            btnNewDiary.setVisibility(View.GONE);
        }
    }
}