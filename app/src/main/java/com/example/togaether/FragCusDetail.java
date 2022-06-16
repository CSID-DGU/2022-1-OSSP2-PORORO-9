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
import android.widget.RadioButton;
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

public class FragCusDetail extends Fragment {

    private PuppyView puppyView;
    private CustomType t, subt;
    private OnSelectionSetListener onSelectionSetListener;
    int selected;
    public FragCusDetail(PuppyView puppyView, CustomType t, int selected) {
        this.puppyView = puppyView;
        this.t = t;
        this.selected = selected;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragcus_detail, container, false);

        // 세부 조정
        // 타입에 따라 레이아웃 활성화
        RadioGroup layCSel = (RadioGroup) v.findViewById(R.id.lay_csel);
        LinearLayout layCSize = (LinearLayout) v.findViewById(R.id.lay_csize);
        LinearLayout layCDy = (LinearLayout) v.findViewById(R.id.lay_cdy);
        LinearLayout layCDist = (LinearLayout) v.findViewById(R.id.lay_cdist);
        LinearLayout layCColor = (LinearLayout) v.findViewById(R.id.lay_ccolor);
        if(t == CustomType.FACE3) {
            layCSize.setVisibility(View.GONE);
            layCDy.setVisibility(View.GONE);
        }
        else if(t == CustomType.BODY) {
            layCDy.setVisibility(View.GONE);
        }
        else if(t == CustomType.EYE_L || t == CustomType.EYEBROW_L || t == CustomType.EAR_L ) {
            subt = CustomType.valueOf(t.name().substring(0,t.name().length()-1)+"R");
            layCSel.setVisibility(View.VISIBLE);
            layCDist.setVisibility(View.VISIBLE);
        }

        layCSel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.radio_left) {
                    selected = 0;
                }
                else if(i == R.id.radio_right) {
                    selected = 1;
                }
                else if(i == R.id.radio_both) {
                    selected = 2;
                }
                if(onSelectionSetListener != null) {
                    onSelectionSetListener.onSelectionSet(selected);
                }
            }
        });
        SeekBar seekSize = (SeekBar) v.findViewById(R.id.seek_size);
        seekSize.setProgress((puppyView.sizeMap.get(t)));
        seekSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                puppyView.setCSize(t, seekSize.getProgress());
                if(t == CustomType.EYE_L || t == CustomType.EYEBROW_L || t == CustomType.EAR_L ) {
                    puppyView.setCSize(subt, seekSize.getProgress());
                }
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
                puppyView.setCDy(t, seekDy.getProgress());
                if(t == CustomType.EYE_L || t == CustomType.EYEBROW_L || t == CustomType.EAR_L ) {
                    puppyView.setCDy(subt, seekDy.getProgress());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        SeekBar seekDist = (SeekBar) v.findViewById(R.id.seek_dist);
        seekDist.setProgress((puppyView.dyMap.get(t)));
        seekDist.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                puppyView.setDist(t, seekDist.getProgress());
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
                                if(t == CustomType.EYE_L || t == CustomType.EYEBROW_L || t == CustomType.EAR_L ) {
                                    if(selected == 0) {
                                        puppyView.setColor(t, selectedColor);
                                    }
                                    else if(selected == 1) {
                                        puppyView.setColor(subt, selectedColor);
                                    }
                                    else if(selected == 2) {
                                        puppyView.setColor(t, selectedColor);
                                        puppyView.setColor(subt, selectedColor);
                                    }
                                }
                                else {
                                    puppyView.setColor(t, selectedColor);
                                }
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

        attatchFragment(getParentFragment());

        return v;

    }

    public void attatchFragment(Fragment fragment) {
        try {
            onSelectionSetListener = (OnSelectionSetListener) fragment;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(fragment.toString() + " must implement OnPlayerSelectionSetListener");
        }
    }
}

