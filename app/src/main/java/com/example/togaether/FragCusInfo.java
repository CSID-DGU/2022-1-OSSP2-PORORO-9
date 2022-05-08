package com.example.togaether;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

import java.security.acl.Group;

public class FragCusInfo extends Fragment {

    private PuppyView puppyView;
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
        RadioGroup layCsex = (RadioGroup) view.findViewById(R.id.lay_csex);
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
                String customJSON = puppyView.getCustomJSON();
                // DB에 insert하는 PHP에 request
                TGTDialog dialog = new TGTDialog(view.getContext());
                dialog.showDialogOneBtn("테스트입니당","확인");
            }
        });
    }
}
