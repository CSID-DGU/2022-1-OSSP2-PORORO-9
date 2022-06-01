package com.example.togaether;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

public class TGTDialog {
    private Context context;
    private Dialog dialog;

    public TGTDialog(Context context) {
        this.context = context;
    }

    public Dialog createDialog(int type) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        switch (type) {
            case 0: // one btn
                dialog.setContentView(R.layout.dialog_frame_onebtn);
                break;
        }
        dialog.show();
        return dialog;
    }

    public void showDialogOneBtn(String text, String btnText, View.OnClickListener onClickListener1) {
        Dialog dialog = createDialog(0);
        TextView tvDtext = (TextView) dialog.findViewById(R.id.tv_dtext);
        AppCompatButton btnDialog1 = (AppCompatButton) dialog.findViewById(R.id.btn_dialog_1);

        tvDtext.setText(text);
        btnDialog1.setText(btnText);

        btnDialog1.setOnClickListener(onClickListener1);
    }

    public void showDialogOneBtn(String text, String btnText) {
        showDialogOneBtn(text, btnText, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void dissmissDailog() {
        dialog.dismiss();
    }
}
