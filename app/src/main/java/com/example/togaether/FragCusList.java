package com.example.togaether;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragCusList extends Fragment {

    PuppyView puppyView;
    CustomType t, subt;
    int selected;
    public FragCusList(PuppyView puppyView, CustomType t, int selected) {
        this.puppyView = puppyView;
        this.t = t;
        this.selected = selected;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragcus_list, container, false);

        // 서버의 커스텀 아이템들 GridView에 넣기
        GridView listCustom = (GridView) v.findViewById(R.id.list_custom);
        CustomAdapter customAdapter = new CustomAdapter();
        // HTTP 요청&응답 처리하여 리스트에 추가
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://togaether.cafe24.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitCustom retrofitAPI = retrofit.create(RetrofitCustom.class);
        //HashMap<String, Object> input = new HashMap<>();
        retrofitAPI.getData(t.name()).enqueue(new Callback<List<CustomItem>>() {
            @Override
            public void onResponse(Call<List<CustomItem>> call, Response<List<CustomItem>> response) {
                if(response.isSuccessful()) {
                    List<CustomItem> data = response.body();
                    customAdapter.addItem(data);
                    listCustom.setAdapter(customAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<CustomItem>> call, Throwable t) {
                //Log.e("=========OMG","here" + t);
            }
        });

        if(t == CustomType.EYE_L || t == CustomType.EYEBROW_L || t == CustomType.EAR_L ) {
            subt = CustomType.valueOf(t.name().substring(0, t.name().length() - 1) + "R");
        }
        // 커스텀 아이템 선택 시 동작
        listCustom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CustomItem item = customAdapter.getItem(i);

                if(t == CustomType.EYE_L || t == CustomType.EYEBROW_L || t == CustomType.EAR_L ) {
                    if(selected == 0) {
                        puppyView.setImgSource(t, item.getSourceUrl());
                    }
                    else if(selected == 1) {
                        puppyView.setImgSource(subt, item.getSourceUrl());
                    }
                    else if(selected == 2) {
                        puppyView.setImgSource(t, item.getSourceUrl());
                        puppyView.setImgSource(subt, item.getSourceUrl());
                    }
                }
                else {
                    puppyView.setImgSource(t, item.getSourceUrl());
                }
            }
        });

        return v;

    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}
