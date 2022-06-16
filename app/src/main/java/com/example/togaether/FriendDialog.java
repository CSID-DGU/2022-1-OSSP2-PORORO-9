package com.example.togaether;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FriendDialog {
    private Context context;
    private Dialog dialog;
    private int pid;

    public FriendDialog(Context context, int pid) {
        this.context = context;
        this.pid = pid;
    }

    public Dialog createDialog() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_frame_friendlist);
        dialog.show();
        //SoundPlayer.play(sound);
        return dialog;
    }

    public void showDialog() {
        Dialog dialog = createDialog();
        ListView listDfriend = (ListView) dialog.findViewById(R.id.list_dfriend);

        PuppyListAdapter adapter = new PuppyListAdapter(1, context);
        // HTTP 요청&응답 처리하여 리스트에 추가
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://togaether.cafe24.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitPuppy retrofitAPI = retrofit.create(RetrofitPuppy.class);
        HashMap<String, Object> input = new HashMap<>();
        input.put("pid", pid);
        retrofitAPI.getPuppyFriendList(input).enqueue(new Callback<List<PuppyInfoItem>>() {
            @Override
            public void onResponse(Call<List<PuppyInfoItem>> call, Response<List<PuppyInfoItem>> response) {
                if(response.isSuccessful()) {
                    List<PuppyInfoItem> data = response.body();
                    adapter.addItem(data);
                    listDfriend.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<PuppyInfoItem>> call, Throwable t) {
                //Log.e("=========OMG","here" + t);
            }
        });

        //TextView tvDtitle = (TextView) dialog.findViewById(R.id.tv_dtitle);
        //tvDtitle.setText(title);
    }

    public void dissmissDailog() {
        dialog.dismiss();
    }
}
