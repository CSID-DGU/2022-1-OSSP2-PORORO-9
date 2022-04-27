package com.example.togaether;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

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

public class FragCus extends Fragment {

    PuppyView puppyView;
    CustomType t;
    public FragCus(PuppyView puppyView, CustomType t) {
        this.puppyView = puppyView;
        this.t = t;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragcus, container, false);

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

        // 커스텀 아이템 선택 시 동작
        listCustom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CustomItem item = customAdapter.getItem(i);
                puppyView.setImgSource(t, item.getSourceUrl());
            }
        });
        // 세부 조정
        SeekBar seekSize = (SeekBar) v.findViewById(R.id.seek_size);
        seekSize.setProgress((puppyView.sizeMap.get(t)));
        seekSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                puppyView.setSize(t, seekSize.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        SeekBar seekDy = (SeekBar) v.findViewById(R.id.seek_dy);
        seekDy.setProgress((puppyView.dyMap.get(t)));
        seekDy.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                puppyView.setDy(t, seekDy.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        LinearLayout layColor = (LinearLayout) v.findViewById(R.id.lay_color);
        layColor.setBackgroundColor(puppyView.colorMap.get(t));
        layColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorPickerDialogBuilder
                        .with(container.getContext())
                        .setTitle("색 선택")
                        .initialColor(((ColorDrawable)layColor.getBackground()).getColor())
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {
                                //toast("onColorSelected: 0x" + Integer.toHexString(selectedColor));
                            }
                        })
                        .setPositiveButton("선택", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                layColor.setBackgroundColor(selectedColor);
                                puppyView.setColor(t,selectedColor);
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .build()
                        .show();
            }
        });

        return v;

    }
}
