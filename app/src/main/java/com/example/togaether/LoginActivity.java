package com.example.togaether;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends TGTActivity {
    EditText edtId;
    EditText edtPass;
    AppCompatButton btnSingup;
    AppCompatButton btnLogin;
    ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtId = (EditText) findViewById(R.id.edt_id);
        edtPass = (EditText) findViewById(R.id.edt_pass);

        btnSingup = (AppCompatButton) findViewById(R.id.btn_signup);
        btnLogin = (AppCompatButton) findViewById(R.id.btn_login);

        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        String strId = auto.getString("id", null);
        String strPass = auto.getString("pass", null);
        Log.e("id", strId + " " + strPass);
        if(strId != null && strPass != null) {
            try {
                login(strId, strPass, true);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        Intent intent = result.getData();
                        String strId = intent.getStringExtra("id");
                        String strPass = intent.getStringExtra("pass");

                        edtId.setText(strId);
                        edtPass.setText(strPass);
                    }
                }
            });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SHA256 sha256 = new SHA256();
                    login(edtId.getText().toString(), sha256.encrypt(edtPass.getText().toString()), false);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        });

        btnSingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                resultLauncher.launch(intent);
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

    private void login(String strId, String strPass, boolean autoLogin) throws NoSuchAlgorithmException {
        Context context = this;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://togaether.cafe24.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitUser retrofitAPI = retrofit.create(RetrofitUser.class);
        HashMap<String, Object> input = new HashMap<>();
        input.put("uid",strId);
        input.put("upass",strPass);

        retrofitAPI.postLogIn(input).enqueue(new Callback<LoginItem>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<LoginItem> call, Response<LoginItem> response) {
                if(response.isSuccessful()) {
                    Log.e("=========",response.body().toString());
                    LoginItem data = response.body();
                    if(data.getFail() != -1) {
                        String errorString;
                        int errorCode = data.getFail();
                        switch (errorCode) {
                            case 0: errorString = "존재하지 않는 아이디 입니다"; break;
                            case 1: errorString = "잘못된 비밀번호 입니다"; break;
                            default: errorString = "알 수 없는 오류가 발생했습니다. ERROR CODE: " + errorCode; break;
                        }
                        if(autoLogin) {
                            errorString = "정보가 변경되어 다시 로그인 해주셔야 합니다";
                        }
                        TGTDialog dialog = new TGTDialog(context);
                        dialog.setSound(SoundPlayer.SE_DENY);
                        dialog.showDialogOneBtn("로그인 실패", errorString,"확인");
                    }
                    else {
                        UserData userData = UserData.getInstance();
                        userData.setUserId(data.getUid());
                        userData.setUserName(data.getUname());
                        LocalDate bdate = LocalDate.parse(data.getUbdate(), DateTimeFormatter.ISO_DATE);
                        userData.setUserBdate(bdate);
                        LocalDate date = LocalDate.parse(data.getUbdate(), DateTimeFormatter.ISO_DATE);
                        userData.setUserDate(date);
                        userData.setUserPass(strPass);

                        if(!autoLogin) {
                            SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = auto.edit();
                            editor.putString("id", strId);
                            editor.putString("pass", strPass);
                            editor.commit();
                        }

                        SoundPlayer.play(SoundPlayer.SE_ITEM);
                        Intent intent = new Intent(getApplicationContext(), PuppyListActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginItem> call, Throwable t) {
                Log.e("=========OMG",t.toString());
            }
        });
    }
}