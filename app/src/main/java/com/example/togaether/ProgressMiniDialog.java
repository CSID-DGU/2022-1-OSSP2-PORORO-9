package com.example.togaether;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
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


public class ProgressMiniDialog {
    private Dialog dialog;
    private TextView tvTitle;
    public ProgressMiniDialog(Context context, String text) {
        dialog = new Dialog(context);
        //배경 제거 및 타이틀바 제거
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //애니메이션 제거
        dialog.getWindow().setWindowAnimations(R.style.DIALOG_NO_ENTER_ANIM);
        //취소 불가능 하도록
        dialog.setCancelable(false);

        dialog.setContentView(R.layout.dialog_progress_mini);
        dialog.show();

        //팁 텍스트 설정
        tvTitle = dialog.findViewById(R.id.tv_dtitle);
        tvTitle.setText(text);
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
            }, 500);
        }
    }
}
