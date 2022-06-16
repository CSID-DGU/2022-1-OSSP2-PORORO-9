package com.example.togaether;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.util.Log;

import java.net.InetAddress;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.time.ZoneId;
import java.util.TimeZone;

public class ServerDateTime {
    public static final String TIME_SERVER = "time-a.nist.gov"; //time-a.nist.gov   time.kriss.re.kr

    public ServerDateTime() {

    }

    public static LocalDateTime now(AppCompatActivity activity) {
        LocalDateTime res = null;
        try {
//            NTPUDPClient timeClient = new NTPUDPClient();
//            InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
//            TimeInfo timeInfo = timeClient.getTime(inetAddress);
//            Date date = new Date(timeInfo.getMessage().getReceiveTimeStamp().getTime());

//            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                LocationManager locMan = (LocationManager) activity.getSystemService(activity.LOCATION_SERVICE);
//                long time = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getTime();
//                Date date = new Date(time);
//                res = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
//            }
//            else {
//                Log.e("ttttttt","hereeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//                res = LocalDateTime.now();
//            }
//            URL url=new URL("https://togaether.cafe24.com/");
//            URLConnection uc=url.openConnection();
//            uc.connect();
//            long ld=uc.getDate();
//            Date date=new Date(ld);
//            res = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
//            SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            dff.setTimeZone(TimeZone.getTimeZone("GMT+09"));
//            String ee = dff.format(new Date());
//            Log.e("asdasds",ee);
//            res = LocalDateTime.parse(ee, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }catch (Exception e) {
            e.printStackTrace();
            res = LocalDateTime.now();
        }
        return res;
    }
}
