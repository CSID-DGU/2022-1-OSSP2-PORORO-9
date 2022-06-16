package com.example.togaether;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import java.util.HashMap;

public class SoundPlayer {
    public static final int SE_ACCEPT = R.raw.se_accept;
    public static final int SE_DENY = R.raw.se_deny;
    public static final int SE_CLICK = R.raw.se_click;
    public static final int SE_COMPLETE = R.raw.se_complete;
    public static final int SE_ITEM = R.raw.se_item;
    public static final int SE_PET = R.raw.se_pet;

    private static SoundPool soundPool;
    private static HashMap<Integer, Integer> soundPoolMap;

    public static void initSounds(Context context) {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();

        soundPoolMap = new HashMap(6);
        soundPoolMap.put(SE_ACCEPT, soundPool.load(context, SE_ACCEPT, 1));
        soundPoolMap.put(SE_CLICK, soundPool.load(context, SE_CLICK, 1));
        soundPoolMap.put(SE_DENY, soundPool.load(context, SE_DENY, 1));
        soundPoolMap.put(SE_COMPLETE, soundPool.load(context, SE_COMPLETE, 1));
        soundPoolMap.put(SE_ITEM, soundPool.load(context, SE_ITEM, 1));
        soundPoolMap.put(SE_PET, soundPool.load(context, SE_PET, 1));
    }

    public static void play(int raw_id){
        if(soundPoolMap.containsKey(raw_id)) {
            soundPool.play(soundPoolMap.get(raw_id), 1, 1, 1, 0, 1f);
        }
    }
    public static void play(int raw_id, float rate){
        if(soundPoolMap.containsKey(raw_id)) {
            soundPool.play(soundPoolMap.get(raw_id), 1, 1, 1, 0, rate);
        }
    }
}
