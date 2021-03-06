package com.example.togaether;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends TGTActivity {
    ConstraintLayout layMain, layOtherPuppy;
    ImageView imgBack;
    ImageView imgFront;
    TextView tvDoing, tvSaying;
    Button btnMenu;
    AppCompatButton btnFollow;
    PuppyView puppyView;
    ListView listMenu;
    private Schedule schedule;
    private PlanItem currentPlan;
    private PuppyInfoItem puppyInfoItem;
    int puppyId;
    UserData userData;
    AddressConverter addressConverter;
    DrawerLayout drawer;

    private ProgressDialog loadingDialog;

    //visit info
    private boolean visit;
    private String vname;
    private int vpid;
    private boolean isMyPuppy;
    //info view
    TextView tvIname, tvIsex, tvIbdate, tvIaddress, tvIcall, tvIuname, tvIfriend;
    AppCompatButton btnIfriend;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadingDialog = new ProgressDialog(this);
        userData = UserData.getInstance();
        addressConverter = AddressConverter.getInstance();
        Intent in = getIntent();

        Log.e("tt",LocalDateTime.now().toString());
        visit = false;
        isMyPuppy = false;
        puppyId = in.getIntExtra("pid", -1);
        if(puppyId == -1) {
            String puppyInfoJson = in.getStringExtra("puppyItem");
            puppyInfoItem = new Gson().fromJson(puppyInfoJson, PuppyInfoItem.class);
            puppyView = new PuppyView(findViewById(R.id.lay_mypuppy), this, puppyInfoItem);
            puppyId = puppyInfoItem.getPid();
            visit = puppyInfoItem.isVisit();
            if(!visit) {
                vpid = puppyInfoItem.getVpid();
                vname = puppyInfoItem.getVname();
            }
            else {
                puppyView.puppy.setVisibility(View.GONE);
            }

            if (puppyInfoItem.getUid().equals(userData.getUserId())) {
                userData.setCallString(puppyInfoItem.getPcall());
                isMyPuppy = true;
            }
        }
        else {
            puppyView = new PuppyView(findViewById(R.id.lay_mypuppy), this, puppyId);
        }
        schedule = new Schedule();
        schedule.makeSchedule(puppyId);
        schedule.printSchedule();

        layMain = (ConstraintLayout) findViewById(R.id.lay_main);
        layOtherPuppy = (ConstraintLayout) findViewById(R.id.lay_otherpuppy);
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgFront = (ImageView) findViewById(R.id.img_front);
        tvDoing = (TextView) findViewById(R.id.tv_doing);
        tvSaying = (TextView) findViewById(R.id.tv_puppysay);
        listMenu = (ListView) findViewById(R.id.list_menu);
        btnMenu = (Button) findViewById(R.id.btn_menu);

        drawer = (DrawerLayout) findViewById(R.id.draw_menu);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //info view
        tvIname = (TextView) findViewById(R.id.tv_iname);
        tvIsex = (TextView) findViewById(R.id.tv_isex);
        tvIbdate = (TextView) findViewById(R.id.tv_ibdate);
        tvIaddress = (TextView) findViewById(R.id.tv_iaddress);
        tvIcall = (TextView) findViewById(R.id.tv_icall);
        tvIuname = (TextView) findViewById(R.id.tv_iuname);
        tvIfriend = (TextView) findViewById(R.id.tv_ifriend);
        btnIfriend = (AppCompatButton) findViewById(R.id.btn_ifriend);
        //visit
        //btnFollow = (AppCompatButton) findViewById(R.id.btn_follow);

        layMain.setBackground(GlobalSelector.getSkyGradient(LocalTime.now()));

        if(!visit) {
            final ValueAnimator bubbleAnim = ValueAnimator.ofFloat(-0.25f, 0.25f);
            bubbleAnim.setDuration(1000);
            bubbleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    tvSaying.setTranslationY(tvSaying.getY() + (float) animation.getAnimatedValue());
                    //puppyView.puppy.setScaleY(1f-(float) animation.getAnimatedValue()/20);
                }
            });
            bubbleAnim.setRepeatCount(-1);
            bubbleAnim.setRepeatMode(ValueAnimator.REVERSE);
            bubbleAnim.start();

            //1???/?????? ?????? ??????
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_TIME_TICK);
            filter.addAction(Intent.ACTION_DATE_CHANGED);
            BroadcastReceiver receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                        //1????????? ??????
                        try {
                            if (currentPlan.getId() != schedule.getPlan()) {
                                getPlanInfo(schedule.getPlan());
                            } else {
                                setBubble();
                            }
                        } catch (NullPointerException e) {
                            Log.e("Schedule", "?????? Plan??? ?????? ??????????????? ???????????????.");
                        }
                        layMain.setBackground(GlobalSelector.getSkyGradient(LocalTime.now()));
                    } else if (intent.getAction().equals(Intent.ACTION_DATE_CHANGED)) {
                        //????????? ????????? ??????
                        schedule.makeSchedule(puppyId);
                        schedule.printSchedule();
                    }
                }
            };
            registerReceiver(receiver, filter);

            //??? ????????? ??? ?????? ??????????????? ??????
            imgBack.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    //puppyView.setPositionPX(imgBack.getWidth() / 2, imgBack.getHeight() * 4 / 5);
                    imgBack.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    getPlanInfo(schedule.getPlan()); //schedule.getPlan()
                }
            });

            //????????? ?????? ?????????
            if(!isMyPuppy) {
                puppyView.puppy.setOnClickListener(createInfoClick(puppyInfoItem));
            }
        }
        //???????????? ????????? ??????
        else {
            Glide.with(getApplicationContext()).load("http://togaether.cafe24.com/images/back/img_back_0.png").centerCrop().into(imgBack);
            Glide.with(getApplicationContext()).load("http://togaether.cafe24.com/images/back/img_front_none.png").centerCrop().into(imgFront);

            tvDoing.setText(GlobalSelector.getPostWord(puppyView.getName(),"???") + " ?????? " + GlobalSelector.getPostWord(puppyInfoItem.getVname(),"??? ????????? ?????????!"));
            tvSaying.setVisibility(View.GONE);
            loadingDialog.loadingComplete();

//            btnFollow.setVisibility(View.VISIBLE);
//            btnFollow.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });
        }

        //?????? ??????
        MenuAdapter menuAdapter = new MenuAdapter();
        if(isMyPuppy) {
            menuAdapter.addItem(new MenuItem("????????????", GlobalSelector.getPostWord(puppyView.getName(),"???") + " ????????? ?????????????", R.drawable.img_menu_talk, 0));
            menuAdapter.addItem(new MenuItem("????????????", "?????? ??????????????? ???????????????????", R.drawable.img_menu_visit, 1));
            menuAdapter.addItem(new MenuItem("?????? ??????", GlobalSelector.getPostWord(puppyView.getName(),"???") + " ????????? ??? ???????????? ??????????????????!", R.drawable.img_menu_friend, 2));
            menuAdapter.addItem(new MenuItem("????????????", GlobalSelector.getPostWord(puppyView.getName(),"???") + "??? ????????? ???????????????", R.drawable.img_menu_diary, 3));
            menuAdapter.addItem(new MenuItem("?????????", "?????? ???????????? ????????? ??? ???????????? ???????????????????", R.drawable.img_menu_guest, 4));
            menuAdapter.addItem(new MenuItem("????????????", "???????????? ????????????", R.drawable.ic_img_send, -1));
        }
        else {
            menuAdapter.addItem(new MenuItem("????????????", GlobalSelector.getPostWord(puppyView.getName(),"???") + "??? ????????? ??????????????????!", R.drawable.img_menu_diary, 3));
            menuAdapter.addItem(new MenuItem("?????????", puppyView.getName() + "?????? ????????? ???????????? ????????????????", R.drawable.img_menu_guest, 4));
        }

        listMenu.setAdapter(menuAdapter);

        listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SoundPlayer.play(SoundPlayer.SE_CLICK);
                MenuItem item = menuAdapter.getItem(i);
                Intent intent;
                switch (item.getId()) {
                    case 0: //????????????
                        intent = new Intent(getApplicationContext(), Chatbot.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;

                    case 1: //????????????
                        VisitDialog visitDialog = new VisitDialog(MainActivity.this, puppyView.getId());
                        visitDialog.showDialog();
                        break;

                    case 2: //?????? ??????
                        FriendDialog friendDialog = new FriendDialog(MainActivity.this, puppyView.getId());
                        friendDialog.showDialog();
                        break;

                    case 3: //????????????
                        intent = new Intent(getApplicationContext(), DiaryActivity.class);
                        intent.putExtra("uid",puppyView.getUid());
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;
                        
                    case 4: //?????????
                        intent = new Intent(getApplicationContext(), GuestActivity.class);
                        intent.putExtra("pid",puppyView.getId());
                        startActivity(intent);
                        drawer.closeDrawer(Gravity.RIGHT);
                        break;

                    case -1:
                        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = auto.edit();
                        editor.clear();
                        editor.commit();

                        intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                        break;
                }
            }
        });

        btnMenu.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPlayer.play(SoundPlayer.SE_CLICK);
                if (!drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.openDrawer(Gravity.RIGHT);
                }
            }
        });

        btnIfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pid = (Integer) btnIfriend.getTag(R.string.friend_pid);
                int fpid = (Integer) btnIfriend.getTag(R.string.friend_fpid);
                String fname = (String) btnIfriend.getTag(R.string.friend_name);
                //?????? request
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://togaether.cafe24.com")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitPuppy retrofitAPI = retrofit.create(RetrofitPuppy.class);
                HashMap<String, Object> input = new HashMap<>();
                input.put("pid",pid);
                input.put("fpid",fpid);
                retrofitAPI.setFriend(input).enqueue(new Callback<ResponseBody>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            try {
                                String data = response.body().string();
                                if(data.charAt(0) == '0') {
                                    String errorString;
                                    if(data.contains("Duplicate entry")) {
                                        if(data.contains("key \'pid\'"))
                                            errorString = "???! ?????? ????????????!";
                                        else
                                            errorString = data;
                                    }
                                    else {
                                        errorString = data;
                                    }

                                    TGTDialog dialog = new TGTDialog(MainActivity.this);
                                    dialog.setSound(SoundPlayer.SE_DENY);
                                    dialog.showDialogOneBtn("?????? ?????? ??????", errorString,"??????");
                                }
                                else {
                                    TGTDialog dialog = new TGTDialog(MainActivity.this);
                                    dialog.setSound(SoundPlayer.SE_COMPLETE);
                                    dialog.showDialogOneBtn("?????? ?????? ??????", GlobalSelector.getPostWord(fname, "???") + " ????????? ???????????????!", "??????!", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            SoundPlayer.play(SoundPlayer.SE_CLICK);
                                            tvIfriend.setVisibility(View.VISIBLE);
                                            btnIfriend.setVisibility(View.GONE);
                                            dialog.dissmissDailog();
                                        }
                                    });
                                }
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("=========OMG",t.toString());
                    }
                });
            }
        });

        //?????? ??????
        BGMController.getInstance().playMusic(this, R.raw.bgm_schedule1);
    }
    
    // ?????? ????????? ????????? ???????????? ??????
    private void getPlanInfo(int planId) {
        layOtherPuppy.removeAllViews();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://togaether.cafe24.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //?????? ?????????
        if(planId < 300000) {
            RetrofitPlan retrofitAPI = retrofit.create(RetrofitPlan.class);
            //HashMap<String, Object> input = new HashMap<>();
            retrofitAPI.getData(planId).enqueue(new Callback<PlanItem>() {
                @Override
                public void onResponse(Call<PlanItem> call, Response<PlanItem> response) {
                    if (response.isSuccessful()) {
                        PlanItem data = response.body();
                        currentPlan = data;
                        Glide.with(getApplicationContext()).load("http://togaether.cafe24.com/images/back/" + data.getBack()).centerCrop().into(imgBack);
                        Glide.with(getApplicationContext()).load("http://togaether.cafe24.com/images/back/" + data.getFront()).centerCrop().into(imgFront);
                        //Glide.with(getApplicationContext()).load(R.drawable.img_back_test).centerCrop().into(imgFront);

                        //int y = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, data.getY(), getResources().getDisplayMetrics());

                        setPuppyXY(puppyView, 0, (float) data.getY());

                        setBubble();

                        tvDoing.setText(GlobalSelector.getPostWord(puppyView.getName(), "???") + " ?????? " + data.getName() + "!");

                        if(data.getId() == 0) {
                            puppyView.setSleep(data.getId() == 0);
                        }
                        loadingDialog.loadingComplete();
                    }
                }

                @Override
                public void onFailure(Call<PlanItem> call, Throwable t) {
                    Log.e("=========OMG", schedule.getPlan() + " here " + t);
                }
            });
        }
        //?????? ?????????
        else if(planId < 400000) {
            SpecialPlanItem sp = Schedule.getSpecialPlan(planId);
            int puppyNum = sp.getPuppyNum(), _y = sp.get_y();
            int x[] = sp.getX();
            int y[] = sp.getY();
            String[] say = sp.getSay();
            String back = sp.getBack();
            String front = sp.getFront();
            String name = sp.getName();
            currentPlan = new PlanItem(planId, name, back, front, _y, say);
            puppyView.setSleep(false);

            puppyView.puppy.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    setPuppyXY(puppyView, 0, (float)_y);

                    puppyView.puppy.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });

            Glide.with(getApplicationContext()).load("http://togaether.cafe24.com/images/back/" + back).centerCrop().into(imgBack);
            Glide.with(getApplicationContext()).load("http://togaether.cafe24.com/images/back/" + front).centerCrop().into(imgFront);
            //Glide.with(getApplicationContext()).load(R.drawable.img_back_test).centerCrop().into(imgFront);

            RetrofitPuppy retrofitAPI = retrofit.create(RetrofitPuppy.class);
            HashMap<String, Object> input = new HashMap<>();
            input.put("pid", puppyView.getId());
            input.put("uid", puppyView.getUid()); //userData.getUserId()
            int intEndTime = schedule.getPlanEndTime();
            LocalDateTime endTime = LocalDateTime.now().withHour(intEndTime/60).withMinute(intEndTime%60);
            input.put("end", endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            loadOtherPuppy(retrofitAPI, input, puppyNum, 0, x, y, name);
        }
    }

    private void loadOtherPuppy(RetrofitPuppy retrofitAPI, HashMap<String, Object> input, int puppyNum, int i, int[] x, int[] y, String name) {
        if(i >= puppyNum) {
            return;
        }
        input.put("random", schedule.getRandPuppy(LocalDateTime.now())[i]);
        input.put("vnum", i);
        retrofitAPI.getVisitPuppy(input).enqueue(new Callback<PuppyInfoItem>() {
            @Override
            public void onResponse(Call<PuppyInfoItem> call, Response<PuppyInfoItem> response) {
                if(response.isSuccessful()) {
                    Log.e("h", i+"");
                    PuppyInfoItem data = response.body();
                    PuppyView visitPuppyView = new PuppyView(layOtherPuppy, MainActivity.this, data);
                    if(!visitPuppyView.getUid().equals(userData.getUserId())) {
                        visitPuppyView.puppy.setOnClickListener(createInfoClick(data));
                    }

                    //int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, x[i], getResources().getDisplayMetrics());
                    //int py = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, y[i], getResources().getDisplayMetrics());
                    //visitPuppyView.setPositionPX((int)puppyView.getPositionPXX() + px,(int)puppyView.getPositionPXY() +  py + puppyView.puppy.getHeight()/2);
                    visitPuppyView.puppy.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            setPuppyXY(visitPuppyView, (float)x[i], (float)y[i]);

                            visitPuppyView.puppy.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    });
                    //????????? ??????
                    if(i == puppyNum - 1) {
                        if(i == 0) {
                            tvDoing.setText(GlobalSelector.getPostWord(puppyView.getName(), "???") + " ?????? " + GlobalSelector.getPostWord(visitPuppyView.getName(), "???") + " " + name + "!");
                        }
                        else {
                            tvDoing.setText(GlobalSelector.getPostWord(puppyView.getName(), "???") + " ?????? " + "???????????? " + name + "!");
                        }
                        setBubble();
                        loadingDialog.loadingComplete();
                    }
                    loadOtherPuppy(retrofitAPI, input, puppyNum, i+1, x, y, name);
                }
            }

            @Override
            public void onFailure(Call<PuppyInfoItem> call, Throwable t) {
                //Log.e("=========OMG","here" + t);
            }
        });
    }

    private void setBubble() {
        Random rand = new Random();
        tvSaying.setText(currentPlan.getSay()[rand.nextInt(currentPlan.getSay().length)].replace("[you]",puppyView.getCallString()));
        tvSaying.measure(0,0);
        tvSaying.setTranslationX(puppyView.getPositionPXX() - tvSaying.getMeasuredWidth()/2);
        tvSaying.setTranslationY(puppyView.getPositionPXY() - tvSaying.getMeasuredHeight() - 10);
    }

    private void setPuppyXY(PuppyView target, float px, float py) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        //????????? ?????? ?????????
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) statusBarHeight = getResources().getDimensionPixelSize(resourceId);

        int screenHeight = metrics.heightPixels - statusBarHeight;
        int screenWidth = metrics.widthPixels;
        float x = screenWidth / 2 + ((float)screenHeight/200 * px);
        float y = screenHeight / 2 + ((float)screenHeight / 2 * py / 100);
        target.setPositionPXX(x);
        target.setPositionPXY(y);
    }

    private View.OnClickListener createInfoClick(PuppyInfoItem puppyInfoItem) {
        View.OnClickListener res = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundPlayer.play(SoundPlayer.SE_CLICK);
                tvIname.setText(puppyInfoItem.getPname());
                tvIsex.setText(puppyInfoItem.getPsex().equals("M")?"?????? ??????":"?????? ??????");
                tvIbdate.setText(puppyInfoItem.getPbdate().format(DateTimeFormatter.ofPattern("yyyy??? MM??? dd???")));
                tvIcall.setText(puppyInfoItem.getPcall());
                tvIaddress.setText(addressConverter.getAddrByPid(puppyInfoItem.getPid()));
                //????????? ??????(?????? ?????? ???)
                btnIfriend.setTag(R.string.friend_pid, userData.getMyPid()); //userData??? pid???!!
                btnIfriend.setTag(R.string.friend_fpid, puppyInfoItem.getPid());
                btnIfriend.setTag(R.string.friend_name, puppyInfoItem.getPname());
                // ?????? ?????? ?????????
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://togaether.cafe24.com")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitPuppy retrofitAPI = retrofit.create(RetrofitPuppy.class);
                HashMap<String, Object> input = new HashMap<>();
                input.put("pid", puppyInfoItem.getPid());
                input.put("mpid", userData.getMyPid());
                retrofitAPI.getMoreInfo(input).enqueue(new Callback<PuppyInfoItem>() {
                    @Override
                    public void onResponse(Call<PuppyInfoItem> call, Response<PuppyInfoItem> response) {
                        PuppyInfoItem data = response.body();
                        tvIuname.setText(data.getUname());
                        if(data.isFriend()) {
                            tvIfriend.setVisibility(View.VISIBLE);
                            btnIfriend.setVisibility(View.GONE);
                        }
                        else {
                            tvIfriend.setVisibility(View.GONE);
                            btnIfriend.setVisibility(View.VISIBLE);
                        }
                        if (!drawer.isDrawerOpen(Gravity.LEFT)) {
                            drawer.openDrawer(Gravity.LEFT);
                        }
                    }

                    @Override
                    public void onFailure(Call<PuppyInfoItem> call, Throwable t) {
                        Log.e("here",t.toString());
                    }
                });

            }
        };
        return res;
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawer(Gravity.RIGHT);
        }
        else if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
        }
        else {
            TGTDialog dialog = new TGTDialog(this);
            if (isMyPuppy) {
                dialog.showDialogTwoBtn("?????? ????????? ?????????????", "?????? ???????????? ?????????????????????????", "???", "????????????!", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SoundPlayer.play(SoundPlayer.SE_CLICK);
                        Intent intent = new Intent(getApplicationContext(), PuppyListActivity.class);
                        startActivity(intent);
                        finish();
                        dialog.dissmissDailog();
                    }
                });
            } else {
                dialog.showDialogTwoBtn("????????????????", "?????? ????????? ????????? ?????? ????????????????", "???", "????????????!", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SoundPlayer.play(SoundPlayer.SE_CLICK);
                        finish();
                        dialog.dissmissDailog();
                    }
                });
            }
        }
    }

}