package com.example.togaether;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Schedule {
    //private Plan schedule[] = new Plan[48]; //24시간 30분 단위로
    public ArrayList<Plan> schedule;
    static int shortMax = 0, longMax = 0, visitMax = 3;
    public Schedule() {
    }
    public static void setPlanLength() {
        setPlanLength(false, null);
    }

    public static void setPlanLength(boolean init, OnLoadingComplete onLoadingComplete) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://togaether.cafe24.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitPlan retrofitAPI = retrofit.create(RetrofitPlan.class);
        //HashMap<String, Object> input = new HashMap<>();
        retrofitAPI.getLenData().enqueue(new Callback<PlanLenItem>() {
            @Override
            public void onResponse(Call<PlanLenItem> call, Response<PlanLenItem> response) {
                if (response.isSuccessful()) {
                    PlanLenItem data = response.body();
                    shortMax = data.getShortMax();
                    longMax = data.getLongMax();
                    if(init) {
                        onLoadingComplete.loadingComplete(0);
                    }
                }
            }

            @Override
            public void onFailure(Call<PlanLenItem> call, Throwable t) {
                Log.e("=========OMG", t.toString());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void makeSchedule(LocalDateTime d, int puppyId) {
        schedule = new ArrayList<>();
        schedule.clear();
        LocalDateTime date = d.withHour(0).withMinute(0).withSecond(0);
        Plan plan;

        //오전 8시까지 잠자기
        plan = new Plan(date, date.plusHours(8), 0);
        schedule.add(plan);
        date = date.plusHours(8);

        //같은 날짜에 같은 일정 나오게 --> 체크
        //activity code 구현한 뒤 생성자로 변경하기

        //int shortMax = 1+1, longMax = 0+1;
        // 날짜와 강아지 코드를 시드로 랜덤클래스 생성
        int randPuppyArray[] = new int[5];
        Random rand = new Random(date.getYear()*365+date.getMonthValue()*31+date.getDayOfMonth()+puppyId);
        int decideSL;
        while (date.getHour() <= 22) {
            decideSL = rand.nextInt(10);
            if(decideSL < 4) { //확률적으로 short Act 발생
                LocalDateTime nextDate = date.plusMinutes(rand.nextInt(30)+10);
                plan = new Plan(date, nextDate, rand.nextInt(shortMax)+100000);
                schedule.add(plan);
                date = nextDate;
            }
            else if(decideSL < 6) { //나머지의 경우 long Act
                LocalDateTime nextDate = date.plusMinutes(rand.nextInt(50)+40);
                plan = new Plan(date, nextDate, rand.nextInt(longMax)+200000);
                schedule.add(plan);
                date = nextDate;
            }
            else {
                LocalDateTime nextDate = date.plusMinutes(rand.nextInt(50)+40);
                for(int i = 0; i < 5; i++) {
                    randPuppyArray[i] = rand.nextInt(20000000);
                }
                plan = new Plan(date, nextDate, rand.nextInt(visitMax)+300000, randPuppyArray);
                schedule.add(plan);
                date = nextDate;
            }
        }

        //나머지 시간 잠자기
        plan = new Plan(date, date.withHour(23).withMinute(59), 0);
        schedule.add(plan);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void makeSchedule(int puppyId) {
        makeSchedule(LocalDateTime.now(), puppyId);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getPlan(LocalDateTime time) { //해당시간에 하는 일 반환
        return getPlanObject(time).getActivityCode();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getPlanEndTime(LocalDateTime time) { //해당시간에 하는 일 반환
        return getPlanObject(time).getEndTime();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int[] getRandPuppy(LocalDateTime time) { //해당시간의 방문 강아지 코드 반환
        return getPlanObject(time).getRandPuppy();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Plan getPlanObject(LocalDateTime time) { //해당시간의 Plan 객체 반환
        int i = 0;
        boolean timeCheck1=false;
        boolean timeCheck2=false;

        while(i < schedule.size()) {
            timeCheck1 = Plan.timeToInt(time) >= schedule.get(i).getStartTime();
            timeCheck2 = Plan.timeToInt(time) <= schedule.get(i).getEndTime();
            if(timeCheck1 && timeCheck2)
                break;
            else
                i++;
        }
        if(i == schedule.size()) { i--; }

        return schedule.get(i);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getPlan() {
        return getPlan(LocalDateTime.now());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getPlanEndTime() {
        return getPlanEndTime(LocalDateTime.now());
    }


    public void printSchedule() {
        for(int i = 0; i < schedule.size(); i++) {
            Plan p = schedule.get(i);
            Log.d("sc",p.getStartTime()/60 + ":" + p.getStartTime()%60 + " ~ " + p.getEndTime()/60 + ":" + p.getEndTime()%60 + " => " + p.getActivityCode() + " " + Arrays.toString(p.getRandPuppy()));
        }
    }

    public static SpecialPlanItem getSpecialPlan(int planId) {
        int puppyNum, _y;
        int x[] = new int[5];
        int y[] = new int[5];
        String[] say;
        String back = "img_back_1.png";
        String front = "img_front_none.png";
        String name;
        //강아지 방문 하루 일과
        switch (planId-300000) {
            case 0:
                puppyNum = 1;
                _y = 50;
                x[0] = -60;
                y[0] = _y;
                back = "img_back_1.png";
                front = "img_front_none.png";
                name = "무지개 동산에서 수다 떠는 중";
                say = new String[]{"안뇽!", "너는 어제 뭐했어?"};
                break;

            case 1:
                puppyNum = 3;
                _y = 40;
                x[0] = -75;
                y[0] = _y;
                x[1] = 75;
                y[1] = -20;
                x[2] = 150;
                y[2] = _y;
                back = "img_back_5.png";
                front = "img_front_5.png";
                name = "영화 보는 중";
                say = new String[]{"헉!! 반전인데?!", "ㅋㅋㅋㅋㅋㅋㅋㅋㅋ", "(몰입 중)", "흑흑...흑...\n나는 우는게 아니야..."};
                break;

            case 2:
                puppyNum = 4;
                _y = 68;
                x[0] = -120;
                y[0] = _y;
                x[1] = -60;
                y[1] = _y;
                x[2] = 60;
                y[2] = _y;
                x[3] = 120;
                y[3] = _y;
                back = "img_back_4.png";
                front = "img_front_4.png";
                name = "마피아 게임 중";
                say = new String[]{"밤이 되었습니다...\n마피아는 고개를 들어 주세요", "나는 선량한 시민이야!", "나는 의사라니까?\n날 죽이면 안돼!", "저 녀석이 아까부터 수상했어.. 흠..", "저는 정말 마피아가 아닙니다!"};
                break;

            case 3:
                puppyNum = 1;
                _y = 80;
                x[0] = -60;
                y[0] = _y;
                back = "img_back_6.png";
                front = "img_front_none.png";
                name = "놀이공원에서 노는 중!";
                say = new String[]{"꺄! 정말 재밌겠다~", "두근두근.. 츄러스 먹으러 갈까?", "관람차는 꼭 타야해~", "롤러코스터는 타고 싶지만\n너무 무서워..", "회전목마 타러가자!"};
                break;

            default:
                puppyNum = 1;
                _y = 50;
                x[0] = -20;
                y[0] = _y;
                back = "img_back_1.png";
                front = "img_front_none.png";
                name = "무지개 동산에서 수다 떠는 중";
                say = new String[]{"안뇽!", "너는 어제 뭐했어?"};
                break;
        }
        SpecialPlanItem res = new SpecialPlanItem(puppyNum, _y, x, y, say, back, front, name);
        return res;
    }
}