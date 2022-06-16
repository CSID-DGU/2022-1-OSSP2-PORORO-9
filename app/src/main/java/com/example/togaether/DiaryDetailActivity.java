package com.example.togaether;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DiaryDetailActivity extends TGTActivity {
    private TextView tvTitle, tvText, tvName, tvDate, tvPrivate, tvHeart, tvComment;
    private TextView tvSub;
    private ImageView imgPrivate, imgImg, imgHeart;
    private LinearLayout layHeart;
    private ListView listComment;
    //덧글 입력
    private ImageButton btnComment;
    private EditText edtComment;
    private int did, pdcid;
    private boolean commentLock;
    DiaryItem data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_detail);
        UserData userData = UserData.getInstance();
        Intent in = getIntent();
        did = in.getIntExtra("did", -1);
        pdcid = -1;
        commentLock = false;

        if(did == -1) {
            TGTDialog dialog = new TGTDialog(this);
            dialog.setSound(SoundPlayer.SE_DENY);
            dialog.showDialogOneBtn("오류 발생", "문제가 발생하여 선택 화면으로 돌아갑니다!", "확인", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SoundPlayer.play(SoundPlayer.SE_CLICK);
                    Intent intent = new Intent(getApplicationContext(), PuppyListActivity.class);
                    startActivity(intent);
                    finish();
                    dialog.dissmissDailog();
                }
            });
        }

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvText = (TextView) findViewById(R.id.tv_text);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvDate = (TextView) findViewById(R.id.tv_date);
        tvPrivate = (TextView) findViewById(R.id.tv_private);
        tvHeart = (TextView) findViewById(R.id.tv_heart);
        tvComment = (TextView) findViewById(R.id.tv_comment);
        imgPrivate = (ImageView) findViewById(R.id.img_private);
        imgImg = (ImageView) findViewById(R.id.img_img);
        imgHeart = (ImageView) findViewById(R.id.img_heart);
        layHeart = (LinearLayout) findViewById(R.id.lay_heart);
        listComment = (ListView) findViewById(R.id.list_comment);
        //listComment.setScrollContainer(false);

        tvSub = (TextView) findViewById(R.id.tv_sub);
        edtComment = (EditText) findViewById(R.id.edt_comment);
        btnComment = (ImageButton) findViewById(R.id.btn_comment);

        //댓글 어댑터
        DiaryCommentAdapter adapter = new DiaryCommentAdapter();
        
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://togaether.cafe24.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitDiary retrofitAPI = retrofit.create(RetrofitDiary.class);
        HashMap<String, Object> input = new HashMap<>();
        input.put("did", did);
        input.put("myuid", userData.getUserId());
        retrofitAPI.getDiaryDetail(input).enqueue(new Callback<DiaryItem>() {
            @Override
            public void onResponse(Call<DiaryItem> call, Response<DiaryItem> response) {
                if(response.isSuccessful()) {
                    data = response.body();

                    //Log.e("data",data.toString());

                    tvTitle.setText(data.getDtitle());
                    tvText.setText(data.getDcontent());
                    tvName.setText(data.getUname());
                    tvDate.setText(data.getDdate());
                    tvHeart.setText("하트 " + data.getDheart() + " 개");
                    setHeart(data.isHeart());
                    if(data.getDimg() == null) {
                        imgImg.setVisibility(View.GONE);
                    }
                    else {
                        Glide.with(getApplicationContext()).load(data.getDimg()).centerCrop().into(imgImg);
                    }
                    if(data.isDprivate()) {
                        layHeart.setVisibility(View.GONE);
                        tvPrivate.setText("비공개");
                        Glide.with(getApplicationContext()).load(R.drawable.ic_baseline_lock_24).fitCenter().into(imgPrivate);
                    }
                    else {
                        tvPrivate.setText("공개");
                        Glide.with(getApplicationContext()).load(R.drawable.ic_baseline_lock_open_24).fitCenter().into(imgPrivate);
                    }
                    //댓글
                    adapter.addItem(data.getComment());
                    listComment.setAdapter(adapter);
                    setCommentListHeight(adapter);
                }
            }

            @Override
            public void onFailure(Call<DiaryItem> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
        listComment.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                CommentItem selectedItem = adapter.getItem(i);
                if(userData.getUserId().equals(selectedItem.getUid())) {
                    TGTDialog dialog = new TGTDialog(DiaryDetailActivity.this);
                    dialog.setSound(SoundPlayer.SE_DENY);
                    dialog.showDialogTwoBtn("댓글 삭제", "댓글을 삭제하시겠습니까?", "네", "아니요", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SoundPlayer.play(SoundPlayer.SE_CLICK);
                            HashMap<String, Object> input = new HashMap<>();
                            input.put("dcid", selectedItem.getDcid());
                            input.put("upass", userData.getUserPass());
                            input.put("uid", userData.getUserId());
                            retrofitAPI.deleteDiaryComment(input).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    String data = null;
                                    try {
                                        data = response.body().string();
                                        if(data.charAt(0) == '0') {
                                            String errorString = data;
                                            TGTDialog dialog = new TGTDialog(DiaryDetailActivity.this);
                                            dialog.setSound(SoundPlayer.SE_DENY);
                                            dialog.showDialogOneBtn("삭제 실패", errorString,"확인");
                                        }
                                        else {
                                            selectedItem.setDcdelete(true);
                                            adapter.notifyDataSetChanged();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });
                            dialog.dissmissDailog();
                        }
                    });
                }
                return true;
            }
        });
        listComment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CommentItem selectedItem = adapter.getItem(i);
                if(!selectedItem.isDcdelete()) {
                    //답글창 띄우기
                    if (pdcid == selectedItem.getPdcid()) {
                        pdcid = -1;
                    } else {
                        pdcid = selectedItem.getPdcid();
                        tvSub.setText(selectedItem.getUname() + "님에게 남기는 답글");
                    }
                    setTvSub();
                }
            }
        });
        //답글 취소
        tvSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdcid = -1;
                setTvSub();
            }
        });

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressMiniDialog progressDialog = new ProgressMiniDialog(DiaryDetailActivity.this, "댓글 등록 중");
                String commentText = edtComment.getText().toString();
                //두 글자 이상 입력해야 함
                if (commentText.trim().length() >= 2) {

                    commentLock = true;
                    
                    edtComment.setText("");
//                $did = $_POST['did'];
//                $pdcid = $_POST['pdcid'];
//                $uid = $_POST['uid'];
//                $dccontent = $_POST['dccontent'];
                    HashMap<String, Object> input = new HashMap<>();
                    input.put("did", did);
                    input.put("pdcid", pdcid);
                    input.put("uid", userData.getUserId());
                    input.put("dccontent", commentText);
                    retrofitAPI.insertDiaryComment(input).enqueue(new Callback<List<CommentItem>>() {
                        @Override
                        public void onResponse(Call<List<CommentItem>> call, Response<List<CommentItem>> response) {
                            progressDialog.loadingComplete();
                            List<CommentItem> data = response.body();
                            adapter.clear();
                            adapter.addItem(data);
                            adapter.notifyDataSetChanged();
                            setCommentListHeight(adapter);
                            commentLock = false;
                            pdcid = -1;
                        }

                        @Override
                        public void onFailure(Call<List<CommentItem>> call, Throwable t) {
                            progressDialog.loadingComplete();
                            TGTDialog dialog = new TGTDialog(DiaryDetailActivity.this);
                            dialog.setSound(SoundPlayer.SE_DENY);
                            dialog.showDialogOneBtn("댓글 작성 실패", "오류가 발생했습니다\n" + t.toString(),"확인");
                            commentLock = false;
                            pdcid = -1;
                        }
                    });
                }
                else {
                    progressDialog.loadingComplete();
                    TGTDialog dialog = new TGTDialog(DiaryDetailActivity.this);
                    dialog.setSound(SoundPlayer.SE_DENY);
                    dialog.showDialogOneBtn("댓글 작성 실패", "두 글자 이상을 입력해주세요!","알겠어요!");
                }

            }
        });

        layHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // did에 하트 요청 (parameter: 유저 아이디, did)
                HashMap<String, Object> input = new HashMap<>();
                input.put("did", data.getDid());
                input.put("uid", userData.getUserId());
                retrofitAPI.flipHeart(input).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            try {
                                String res = response.body().string();
                                data.setDheart(Integer.parseInt(res));
                                data.setHeart(!data.isHeart());
                                setHeart(data.isHeart());
                                tvHeart.setText("하트 " + (data.getDheart()) + " 개");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        //Log.e("=========OMG","here" + t);
                    }
                });
            }
        });
    }

    private void setHeart(boolean isHeart) {
        if(isHeart) {
            Glide.with(this).load(R.drawable.ic_baseline_favorite_24).into(imgHeart);
            tvHeart.setTextColor(Color.parseColor("#E74141"));
        }
        else {
            Glide.with(this).load(R.drawable.ic_baseline_favorite_border_24).into(imgHeart);
            tvHeart.setTextColor(Color.parseColor("#737373"));
        }
    }

    private void setTvSub() {
        if(pdcid == -1) {
            tvSub.setVisibility(View.GONE);
        }
        else {
            tvSub.setVisibility(View.VISIBLE);
        }
    }

    private void setCommentListHeight(BaseAdapter adapter) {
        int listHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listComment);
            listItem.measure(0, 0);
            listHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listComment.getLayoutParams();
        params.height = listHeight + (listComment.getDividerHeight() * (listComment.getCount() - 1));
        listComment.setLayoutParams(params);
    }

    @Override
    public void onBackPressed() {
        TGTDialog dialog = new TGTDialog(this);
        dialog.showDialogTwoBtn("돌아갈까요?", "다이어리 목록으로 돌아갈까요?", "네", "아직이요!", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundPlayer.play(SoundPlayer.SE_CLICK);
                Intent intent = new Intent(DiaryDetailActivity.this, DiaryActivity.class);
                intent.putExtra("heart",data.getDheart());
                intent.putExtra("isHeart",data.isHeart());
                intent.putExtra("comment",listComment.getCount());
                setResult(RESULT_OK, intent);
                finish();
                dialog.dissmissDailog();
            }
        });
    }
}