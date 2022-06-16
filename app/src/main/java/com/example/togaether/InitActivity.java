package com.example.togaether;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class InitActivity extends TGTActivity implements OnLoadingComplete {
    int checkNum = 1;
    boolean check[];
    UserData userData;
    private final int PERMISSIONS_REQUEST_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        SoundPlayer.initSounds(getApplicationContext());
        userData = UserData.getInstance();
        userData.setCallString("엄마");

        check = new boolean[checkNum];

//        if(chkPermission()) {
//            loadingComplete(1);
//        }

        Schedule.setPlanLength(true, this);
    }

    @Override
    public void loadingComplete(int index) {
        check[index] = true;

        boolean ch = true;
        for(int i = 0; ch && i < checkNum; i++) ch = ch && check[i];

        if(ch) {
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }
            }, 1500);
        }
    }

    public boolean chkPermission() {
        boolean mPermissionsGranted = false;
        // 권한 목록
        String[] mRequiredPermissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 필수 권한을 가지고 있는지 확인한다.
            mPermissionsGranted = hasPermissions(mRequiredPermissions);
            // 권한 없으면
            if (!mPermissionsGranted) {
                // 권한 요청
                ActivityCompat.requestPermissions(this, mRequiredPermissions, PERMISSIONS_REQUEST_CODE);
            }
        } else {
            mPermissionsGranted = true;
        }

        return mPermissionsGranted;
    }

    public boolean hasPermissions(String... permissions) {
        if (permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PERMISSIONS_REQUEST_CODE == requestCode) {
            for (int i : grantResults) {
                //허용
                if (i == PackageManager.PERMISSION_GRANTED) {
                    loadingComplete(1);
                }
                //거부
                else {
                    finish();
                }
            }
        }
    }
}