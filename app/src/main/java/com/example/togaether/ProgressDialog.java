package com.example.togaether;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;

import java.util.Random;


public class ProgressDialog{
    private Dialog dialog;
    private TextView tvTip;
    public ProgressDialog(Context context) {
        dialog = new Dialog(context);
        //배경 제거 및 타이틀바 제거
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //애니메이션 제거
        dialog.getWindow().setWindowAnimations(R.style.DIALOG_NO_ENTER_ANIM);
        //취소 불가능 하도록
        dialog.setCancelable(false);

        dialog.setContentView(R.layout.dialog_progress);
        dialog.show();

        //팁 텍스트 설정
        tvTip = dialog.findViewById(R.id.tv_progress_tip);
        setRandomTip();

        //로딩 이미지
        Glide.with(context).load(R.drawable.img_running_blackback).into((ImageView)dialog.findViewById(R.id.img_progress_loading));

        //화면에 가득 채우기
        dialog.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);

        //화면 누르면 팁 전환
        ((ConstraintLayout)dialog.findViewById(R.id.lay_progress)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRandomTip();
            }
        });
    }

    public void loadingComplete() {
        if(dialog.isShowing()) {
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    SoundPlayer.play(SoundPlayer.SE_COMPLETE);
                    dialog.dismiss();
                }
            }, 2000);
        }
    }

    private void setRandomTip() {
        int tipNum = 7;
        String srtTip = "";
        Random rand = new Random();
        switch (rand.nextInt(tipNum)) {
            case 0:
                srtTip = "아이를 터치하면 쓰다듬을 수 있습니다!\n마구마구 쓰다듬어 보는 건 어떨까요?";
                break;
            case 1:
                srtTip = "가끔 다른 아이가 놀러오는 경우가 있습니다!\n아이를 터치하면 아이의 정보를 볼 수 있고, 친구가 될 수 있습니다!";
                break;
            case 2:
                srtTip = "가끔 아이가 다른 아이를 만나러 놀러 가는 경우가 있습니다!\n놀라지 마시고 친구와 무얼 하고 있는지 따라가보세요!";
                break;
            case 3:
                srtTip = "공개 일기는 놀러 온 다른 유저들도 읽고 공감할 수 있습니다!\n아이와의 추억을 공유해보는 건 어떨까요?";
                break;
            case 4:
                srtTip = "비공개 일기는 나만이 볼 수 있습니다!";
                break;
            case 5:
                srtTip = "아이는 이제 무엇이든 먹을 수 있어요!\n파스타도 먹고, 가끔 국밥도 먹고... 어쩌면 하늘에만 있는 음식이 있을지두요?";
                break;
            case 6:
                srtTip = "하늘 위는 외롭지 않아요! 다른 친구들이 정말 많거든요!\n나중에 만나면... 며칠 내내 친구 소개만 들을지도 몰라요...!";
                break;
        }
        tvTip.setText(srtTip);
    }
}
