package com.example.togaether;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupActivity extends TGTActivity {
    EditText edtId;
    EditText edtPass;
    EditText edtPassCheck;
    EditText edtName;
    DatePicker dateBdate;
    AppCompatButton btnSingup;
    AppCompatButton btnLogin;
    ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtId = (EditText) findViewById(R.id.edt_id);
        edtPass = (EditText) findViewById(R.id.edt_pass);
        edtPassCheck = (EditText) findViewById(R.id.edt_pass_check);
        edtName = (EditText) findViewById(R.id.edt_name);
        dateBdate = (DatePicker) findViewById(R.id.date_bdate);

        btnSingup = (AppCompatButton) findViewById(R.id.btn_signup);

        btnSingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(inputCheck()) {
                        signup();
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean inputCheck() {
        String strPass = edtPass.getText().toString();
        String strPassCheck = edtPassCheck.getText().toString();
        String strId = edtId.getText().toString();
        String strName = edtName.getText().toString();
        Matcher match;

        TGTDialog dialog = new TGTDialog(this);
        final String patternId = "^[A-Za-z[0-9]]{5,15}$";
        match = Pattern.compile(patternId).matcher(strId);
        if(!match.matches()) {
            dialog.showDialogOneBtn("가입 실패", "아이디는 영문/숫자로 이루어진 5 ~ 15글자여야 합니다","확인");
            return false;
        }

        if(strName.length() == 0 || strName.length() > 10) {
            dialog.showDialogOneBtn("가입 실패", "닉네임은 1 ~ 10글자여야 합니다","확인");
            return false;
        }

        if(strPass.equals(strPassCheck)) {
            final String pattern = "^[A-Za-z[0-9]]{7,20}$";
            match = Pattern.compile(pattern).matcher(strPass);
            if (!match.matches()) {
                dialog.showDialogOneBtn("가입 실패", "비밀번호는 영문과 숫자를 조합하여 7 ~ 20글자여야 합니다","확인");
                return false;
            }
        }
        else {
            dialog.showDialogOneBtn("가입 실패", "비밀번호가 비밀번호 확인과 다릅니다","확인");
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void signup() throws NoSuchAlgorithmException {
        Context context = this;
        SHA256 sha256 = new SHA256();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://togaether.cafe24.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitUser retrofitAPI = retrofit.create(RetrofitUser.class);

        LocalDate bdate = LocalDate.now()
                .withYear(dateBdate.getYear())
                .withMonth(dateBdate.getMonth())
                .withDayOfMonth(dateBdate.getDayOfMonth());

        HashMap<String, Object> input = new HashMap<>();
        input.put("uid",edtId.getText().toString());
        input.put("upass",sha256.encrypt(edtPass.getText().toString()));
        input.put("uname",edtName.getText().toString());
        input.put("ubdate", bdate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));

        retrofitAPI.postSignUp(input).enqueue(new Callback<ResponseBody>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    try {
                        //Log.e("=========",response.body().string());
                        String data = response.body().string();
                        if(data.charAt(0) == '0') {
                            String errorString;
                            if(data.contains("Duplicate entry")) {
                                if(data.contains("key \'uid\'"))
                                    errorString = "중복된 아이디입니다";
                                else if(data.contains("key \'uname\'"))
                                    errorString = "중복된 닉네임입니다";
                                else
                                    errorString = data;
                            }
                            else {
                                errorString = data;
                            }

                            TGTDialog dialog = new TGTDialog(context);
                            dialog.setSound(SoundPlayer.SE_DENY);
                            dialog.showDialogOneBtn("가입 실패", errorString,"확인");
                        }
                        else {
                            TGTDialog dialog = new TGTDialog(context);
                            dialog.setSound(SoundPlayer.SE_COMPLETE);
                            dialog.showDialogOneBtn("가입 성공", "가입을 환영합니다 " + edtName.getText().toString() + "님!", "확인", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    SoundPlayer.play(SoundPlayer.SE_CLICK);
                                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                    intent.putExtra("id",edtId.getText().toString());
                                    intent.putExtra("pass",edtPass.getText().toString());
                                    setResult(RESULT_OK, intent);
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
                Log.e("=========OMG",t.toString());
            }
        });
    }
}