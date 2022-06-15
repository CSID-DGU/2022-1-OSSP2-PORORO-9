package com.example.togaether;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.NoSuchAlgorithmException;
import java.security.acl.Group;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragCusInfo extends Fragment {

    private PuppyView puppyView;
    RadioGroup layCsex, layCcall;
    EditText edtName;
    DatePicker dateBdate;

    public FragCusInfo(PuppyView puppyView) {
        this.puppyView = puppyView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragcus_info, container, false);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RadioButton radioSister = (RadioButton) view.findViewById(R.id.radio_sister);
        RadioButton radioBrother = (RadioButton) view.findViewById(R.id.radio_brother);
        layCsex = (RadioGroup) view.findViewById(R.id.lay_csex);
        layCcall = (RadioGroup) view.findViewById(R.id.lay_ccall);
        edtName = (EditText) view.findViewById(R.id.edt_name);
        dateBdate = (DatePicker) view.findViewById(R.id.date_bdate);

        layCsex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.radio_girl) {
                    radioBrother.setText("오빠");
                    radioSister.setText("언니");
                }
                else {
                    radioBrother.setText("형아");
                    radioSister.setText("누나");
                }
            }
        });

        AppCompatButton btnCMake = (AppCompatButton) view.findViewById(R.id.btn_cmake);
        btnCMake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DB에 insert하는 PHP에 request
                TGTDialog dialog = new TGTDialog(view.getContext());
                dialog.showDialogTwoBtn("끝난 건가요?", "아이의 모습을 완성하셨나요?", "네", "아직이에요", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SoundPlayer.play(SoundPlayer.SE_CLICK);
                        dialog.dissmissDailog();
                        dialog.showDialogTwoBtn("아이를 공개할까요?"
                                , GlobalSelector.getPostWord(edtName.getText().toString(), "가")
                                        + " 하늘에 있는 다른 친구들과도 만날 수 있게 할까요?", "좋아요", "그건 싫어요", new View.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.O)
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            SoundPlayer.play(SoundPlayer.SE_CLICK);
                                            puppyInsert(view, 1);
                                        } catch (NoSuchAlgorithmException e) {
                                            e.printStackTrace();
                                        }
                                        dialog.dissmissDailog();
                                    }
                                }, new View.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.O)
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            SoundPlayer.play(SoundPlayer.SE_CLICK);
                                            puppyInsert(view, 0);
                                        } catch (NoSuchAlgorithmException e) {
                                            e.printStackTrace();
                                        }
                                        dialog.dissmissDailog();
                                    }
                                });
                    }
                });
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void puppyInsert(View view, int pshare) throws NoSuchAlgorithmException {
        Context context = view.getContext();
        SHA256 sha256 = new SHA256();
        //Gson gsonBuilder = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://togaether.cafe24.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitPuppy retrofitAPI = retrofit.create(RetrofitPuppy.class);

        LocalDate bdate = LocalDate.now()
                .withYear(dateBdate.getYear())
                .withMonth(dateBdate.getMonth())
                .withDayOfMonth(dateBdate.getDayOfMonth());
        UserData userData = UserData.getInstance();
        //String psex = ((RadioButton)layCsex.findViewById(layCsex.getCheckedRadioButtonId())).getText().toString();
        String psex = layCsex.getCheckedRadioButtonId() == R.id.radio_girl? "W" : "M";
        String pcall = ((RadioButton)layCcall.findViewById(layCcall.getCheckedRadioButtonId())).getText().toString();

        HashMap<String, Object> input = new HashMap<>();
        input.put("uid",userData.getUserId());
        input.put("psex",psex);
        input.put("pcall",pcall);
        input.put("pname",edtName.getText().toString());
        input.put("pbdate", bdate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        input.put("pcustom",puppyView.getCustomJSON());
        input.put("pshare", pshare);

        retrofitAPI.insertPuppy(input).enqueue(new Callback<ResponseBody>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
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
                            TGTDialog dialog = new TGTDialog(context);
                            dialog.setSound(SoundPlayer.SE_DENY);
                            dialog.showDialogOneBtn("등록 실패", errorString,"확인");
                        }
                        else {
                            RetrofitPuppy retrofitAPI = retrofit.create(RetrofitPuppy.class);
                            //HashMap<String, Object> input = new HashMap<>();
                            retrofitAPI.getData(Integer.parseInt(data)).enqueue(new Callback<PuppyInfoItem>() {
                                @Override
                                public void onResponse(Call<PuppyInfoItem> call, Response<PuppyInfoItem> response) {
                                    if(response.isSuccessful()) {
                                        PuppyInfoItem data = response.body();
                                        data.setVisit(false);
                                        if(data.getPid() != -1) {
                                            TGTDialog dialog = new TGTDialog(context);
                                            dialog.setSound(SoundPlayer.SE_COMPLETE);
                                            dialog.showDialogOneBtn("등록 성공",  "등록에 성공했습니다!" +
                                                            "\n투개더에 온 것을 환영해 " + GlobalSelector.getPostWord(edtName.getText().toString(),"야") + "!"
                                                    , GlobalSelector.getPostWord(edtName.getText().toString(),"를") + " 만나러 가볼까요?", new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            SoundPlayer.play(SoundPlayer.SE_CLICK);
                                                            Intent intent = new Intent(getActivity(), MainActivity.class);
                                                            intent.putExtra("puppyItem", (new Gson()).toJson(data));
                                                            startActivity(intent);
                                                            getActivity().finish();
                                                            dialog.dissmissDailog();
                                                        }
                                                    });
                                        }
                                        else {

                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<PuppyInfoItem> call, Throwable t) {
                                    Log.e("=========OMG","here" + t);
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
