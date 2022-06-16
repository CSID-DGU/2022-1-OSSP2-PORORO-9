package com.example.togaether;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Array;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;

public class DiaryWriteActivity extends TGTActivity {
    EditText edtTitle, edtContent;
    ImageView imgDiary;
    RadioGroup layPrivate;
    AppCompatButton btnUpload;
    ActivityResultLauncher<Intent> resultLauncher;
    private String convertImage = "no image";
    UserData userData;
    //private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_write);

        userData = UserData.getInstance();
        //context = this;
        edtTitle = (EditText) findViewById(R.id.edt_title);
        edtContent = (EditText) findViewById(R.id.edt_content);
        imgDiary = (ImageView) findViewById(R.id.img_diary);
        layPrivate = (RadioGroup) findViewById(R.id.lay_diary_private);
        btnUpload = (AppCompatButton) findViewById(R.id.btn_upload);

        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK){
                            Intent intent = result.getData();
                            Uri uri = intent.getData();
                            Glide.with(getApplicationContext()).load(uri).override(250).fitCenter().into(imgDiary);
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                //bitmap to String
                                ByteArrayOutputStream byteArrayOutputStreamObject = new ByteArrayOutputStream();
                                //bitmap = Bitmap.createScaledBitmap(bitmap, 100,100,true);
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStreamObject);
                                byte[] byteArrayBar = byteArrayOutputStreamObject.toByteArray();
                                convertImage = Base64.encodeToString(byteArrayBar, Base64.DEFAULT);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

        imgDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setType("image/*");
                resultLauncher.launch(intent);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressMiniDialog progressDialog = new ProgressMiniDialog(DiaryWriteActivity.this,"다이어리 업로드 중");
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://togaether.cafe24.com")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitDiary retrofitAPI = retrofit.create(RetrofitDiary.class);

                int dprivate = layPrivate.getCheckedRadioButtonId() == R.id.radio_diary_private? 1 : 0;

                HashMap<String, Object> input = new HashMap<>();
                input.put("dprivate",dprivate);
                input.put("dtitle",edtTitle.getText().toString());
                input.put("dcontent",edtContent.getText().toString());
                input.put("dimg",convertImage);
                input.put("uid",userData.getUserId());

                //여기 수정하기
                retrofitAPI.insertDiary(input).enqueue(new Callback<ResponseBody>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            progressDialog.loadingComplete();
                            try {
                                String data = response.body().string();
                                if(data.charAt(0) == '0') {
                                    String errorString;
                                    errorString = data;
//                            if(data.contains("Duplicate entry")) {
//                                if(data.contains("key \'uid\'"))
//                                    errorString = "중복된 아이디입니다";
//                                else if(data.contains("key \'uname\'"))
//                                    errorString = "중복된 닉네임입니다";
//                                else
//                                    errorString = data;
//                            }
//                            else {
//                                errorString = data;
//                            }
                                    TGTDialog dialog = new TGTDialog(view.getContext());
                                    dialog.setSound(SoundPlayer.SE_DENY);
                                    dialog.showDialogOneBtn("등록 실패", errorString,"확인");
                                }
                                else {
                                    TGTDialog dialog = new TGTDialog(view.getContext());
                                    dialog.setSound(SoundPlayer.SE_COMPLETE);
                                    dialog.showDialogOneBtn("등록 성공", "다이어리 등록 성공!", "확인", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            SoundPlayer.play(SoundPlayer.SE_CLICK);
                                            Intent intent = new Intent(getApplicationContext(), DiaryActivity.class);
                                            intent.putExtra("uid", userData.getUserId());
                                            startActivity(intent);
                                            finish();
                                            dialog.dissmissDailog();
                                        }
                                    });
                                }
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        progressDialog.loadingComplete();
                        Log.e("=========OMG",t.toString());
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        TGTDialog dialog = new TGTDialog(this);
        dialog.showDialogTwoBtn("작성 취소", "글 작성을 취소할까요?", "네", "아직이요!", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundPlayer.play(SoundPlayer.SE_CLICK);
                Intent intent = new Intent(getApplicationContext(), DiaryActivity.class);
                intent.putExtra("uid", userData.getUserId());
                startActivity(intent);
                finish();
                dialog.dissmissDailog();
            }
        });
    }
}