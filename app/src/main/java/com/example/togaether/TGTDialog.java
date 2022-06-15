package com.example.togaether;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

public class TGTDialog {
    private Context context;
    private Dialog dialog;
    private int sound;

    public TGTDialog(Context context) {
        sound = SoundPlayer.SE_ACCEPT;
        this.context = context;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }

    public Dialog createDialog(int type) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        switch (type) {
            case 0: // one btn
                dialog.setContentView(R.layout.dialog_frame_onebtn);
                break;
            case 1: // two btn
                dialog.setContentView(R.layout.dialog_frame_twobtn);
                break;
        }
        dialog.show();
        SoundPlayer.play(sound);
        return dialog;
    }

    public void showDialogOneBtn(String title, String text, String btnText, View.OnClickListener onClickListener1) {
        Dialog dialog = createDialog(0);
        TextView tvDtext = (TextView) dialog.findViewById(R.id.tv_dtext);
        TextView tvDtitle = (TextView) dialog.findViewById(R.id.tv_dtitle);
        AppCompatButton btnDialog1 = (AppCompatButton) dialog.findViewById(R.id.btn_dialog_1);

        tvDtitle.setText(title);
        tvDtext.setText(text);
        btnDialog1.setText(btnText);

        btnDialog1.setOnClickListener(onClickListener1);
    }

    public void showDialogOneBtn(String title, String text, String btnText) {
        showDialogOneBtn(title, text, btnText, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundPlayer.play(SoundPlayer.SE_CLICK);
                dialog.dismiss();
            }
        });
    }

    public void showDialogTwoBtn(String title, String text, String btnText1, String btnText2, View.OnClickListener onClickListener1, View.OnClickListener onClickListener2) {
        Dialog dialog = createDialog(1);
        TextView tvDtext = (TextView) dialog.findViewById(R.id.tv_dtext);
        TextView tvDtitle = (TextView) dialog.findViewById(R.id.tv_dtitle);
        AppCompatButton btnDialog1 = (AppCompatButton) dialog.findViewById(R.id.btn_dialog_1);
        AppCompatButton btnDialog2 = (AppCompatButton) dialog.findViewById(R.id.btn_dialog_2);

        tvDtitle.setText(title);
        tvDtext.setText(text);
        btnDialog1.setText(btnText1);
        btnDialog2.setText(btnText2);

        btnDialog1.setOnClickListener(onClickListener1);
        btnDialog2.setOnClickListener(onClickListener2);
    }

    public void showDialogTwoBtn(String title, String text, String btnText1, String btnText2, View.OnClickListener onClickListener1) {
        showDialogTwoBtn(title, text, btnText1, btnText2, onClickListener1
                , new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SoundPlayer.play(SoundPlayer.SE_CLICK);
                        dialog.dismiss();
                    }
                });
    }

    public void showDialogTwoBtn(String title, String text, String btnText1, String btnText2) {
        showDialogTwoBtn(title, text, btnText1, btnText2, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SoundPlayer.play(SoundPlayer.SE_CLICK);
                        dialog.dismiss();
                    }
                }
                , new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        SoundPlayer.play(SoundPlayer.SE_CLICK);
                        dialog.dismiss();
                    }
                });
    }

    public void dissmissDailog() {
        dialog.dismiss();
    }
}
