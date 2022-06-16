package com.example.togaether;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class TGTActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        BGMController.resumeMusic();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onPause() {
//        new Handler().postDelayed(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                if (isApplicationSentToBackground(TGTActivity.this)) {
//                    BGMController.pauseMusic();
//                }
//            }
//        }, 1500);
        BGMController.pauseMusic();
        super.onPause();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean isApplicationSentToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks2 = am.getRunningTasks(1);
        Log.e("",tasks2.get(0).topActivity.getPackageName());

        List<ActivityManager.AppTask> tasks = am.getAppTasks();
        if (!tasks.isEmpty()) {
            ActivityManager.RecentTaskInfo recentTaskInfo = tasks.get(0).getTaskInfo();
            ComponentName topActivity = recentTaskInfo.topActivity;
            Log.e("",topActivity.getPackageName() + " " + context.getPackageName());
            if (topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
}
