package com.example.togaether;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GuestActivity extends TGTActivity {
    private ListView listComment;
    //덧글 입력
    private ImageButton btnComment;
    private EditText edtComment;
    private int pid;
    private boolean commentLock;
    UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);
        Intent in = getIntent();
        pid = in.getIntExtra("pid", -1);

        listComment = (ListView) findViewById(R.id.list_comment);

        edtComment = (EditText) findViewById(R.id.edt_comment);
        btnComment = (ImageButton) findViewById(R.id.btn_comment);

        //댓글 어댑터
        GuestAdapter adapter = new GuestAdapter();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://togaether.cafe24.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitDiary retrofitAPI = retrofit.create(RetrofitDiary.class);
        HashMap<String, Object> input = new HashMap<>();
        input.put("pid", pid);
        input.put("uid", userData.getUserId());
        retrofitAPI.getGuestMessageList(input).enqueue(new Callback<List<GuestItem>>() {
            @Override
            public void onResponse(Call<List<GuestItem>> call, Response<List<GuestItem>> response) {
                if(response.isSuccessful()) {
                    List<GuestItem> data = response.body();
                    adapter.addItem(data);
                    listComment.setAdapter(adapter);
                    listComment.setSelection(adapter.getCount()-1);
                }
            }

            @Override
            public void onFailure(Call<List<GuestItem>> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressMiniDialog progressDialog = new ProgressMiniDialog(GuestActivity.this,"방명록 등록 중");
                String commentText = edtComment.getText().toString();
                //두 글자 이상 입력해야 함
                if (commentText.trim().length() >= 2) {

                    commentLock = true;

                    edtComment.setText("");
                    HashMap<String, Object> input = new HashMap<>();
                    input.put("pid", pid);
                    input.put("uid", userData.getUserId());
                    input.put("gcontent", commentText);
                    retrofitAPI.insertGuestMessage(input).enqueue(new Callback<List<GuestItem>>() {
                        @Override
                        public void onResponse(Call<List<GuestItem>> call, Response<List<GuestItem>> response) {
                            progressDialog.loadingComplete();
                            List<GuestItem> data = response.body();
                            adapter.clear();
                            adapter.addItem(data);
                            adapter.notifyDataSetChanged();
                            if(adapter.getCount() == 1) {
                                listComment.setAdapter(adapter);
                            }
                            listComment.setSelection(adapter.getCount()-1);
                            commentLock = false;
                        }

                        @Override
                        public void onFailure(Call<List<GuestItem>> call, Throwable t) {
                            progressDialog.loadingComplete();
                            TGTDialog dialog = new TGTDialog(GuestActivity.this);
                            dialog.setSound(SoundPlayer.SE_DENY);
                            dialog.showDialogOneBtn("방명록 작성 실패", "오류가 발생했습니다\n" + t.toString(),"확인");
                            commentLock = false;
                        }
                    });
                }
                else {
                    progressDialog.loadingComplete();
                    TGTDialog dialog = new TGTDialog(GuestActivity.this);
                    dialog.setSound(SoundPlayer.SE_DENY);
                    dialog.showDialogOneBtn("방명록 작성 실패", "두 글자 이상 입력해주세요!\n","확인");
                }

            }
        });
    }
}