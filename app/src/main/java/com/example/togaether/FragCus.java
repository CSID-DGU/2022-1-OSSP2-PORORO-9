package com.example.togaether;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragCus extends Fragment implements OnSelectionSetListener{

    PuppyView puppyView;
    CustomType t;
    int selected;
    Fragment fragList, fragDetail;
    public FragCus(PuppyView puppyView, CustomType t) {
        this.puppyView = puppyView;
        this.t = t;
        selected = 2;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragcus, container, false);

        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        fragList = new FragCusList(puppyView, t, selected);
        fragDetail = new FragCusDetail(puppyView, t, selected);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.lay_cusframe, fragList).commit();

        TabLayout layTabs = (TabLayout) view.findViewById(R.id.lay_tabs);
        layTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment sel = null;
                if(tab.getPosition() == 0) {
                    sel = fragList;
                    ((FragCusList)fragList).setSelected(selected);
                }
                if(tab.getPosition() == 1) {
                    sel = fragDetail;
                }
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.lay_cusframe, sel).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onSelectionSet(int sel) {
        selected = sel;
    }
}
