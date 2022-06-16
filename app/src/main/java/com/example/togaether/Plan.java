package com.example.togaether;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;

public class Plan {
    private int startTime;
    private int endTime;
    private int activityCode;
    private int randPuppy[];

    public Plan(int startTime, int endTime, int activityCode) {
        this(startTime, endTime, activityCode, new int[]{-1, -1, -1, -1, -1});
    }

    public Plan(int startTime, int endTime, int activityCode, int[] randPuppy) {
        this.startTime=startTime;
        this.endTime=endTime;
        this.activityCode=activityCode;
        setRandPuppy(randPuppy);
    }


    public int[] getRandPuppy() {
        return randPuppy;
    }

    public void setRandPuppy(int[] randPuppy) {
        this.randPuppy = new int[5];
        for(int i = 0; i < 5; i++) {
            this.randPuppy[i] = randPuppy[i];
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Plan(LocalDateTime startTime, LocalDateTime endTime, int activityCode) {
        this(startTime, endTime, activityCode, new int[]{-1, -1, -1, -1, -1});
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Plan(LocalDateTime startTime, LocalDateTime endTime, int activityCode, int[] randPuppy) {
        this.startTime=timeToInt(startTime);
        this.endTime=timeToInt(endTime);
        this.activityCode=activityCode;
        setRandPuppy(randPuppy);
    }

    public int getStartTime() {
        return startTime;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setStartTime(LocalDateTime start) {
        setStartTime(timeToInt(start));
    }

    public void setStartTime(int start) {
        this.startTime=start;
    }

    public int getEndTime() {
        return endTime;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setEndTime(LocalDateTime end) {
        setEndTime(timeToInt(end));
    }

    public void setEndTime(int end) {
        this.endTime=end;
    }

    public int getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(int code) {
        this.activityCode=code;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static int timeToInt(LocalDateTime time) {
        int res;
        res = time.getHour()*60;
        res += time.getMinute();
        return res;
    }
//	public static void main(String[] args) {
//		Schedule s = new Schedule(10);
//		s.printSchedule();
//		System.out.print("현재 코드: " + s.getPlan());
//	}
}
