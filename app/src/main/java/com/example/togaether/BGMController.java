package com.example.togaether;

import android.content.Context;
import android.media.MediaPlayer;

public class BGMController {
    private static MediaPlayer mediaPlayer = new MediaPlayer();
    private static int rawId = -1, length;
    public static void playMusic(Context context, int id) {
        if(mediaPlayer.isPlaying()) {
            if(rawId == id) {
                return;
            }
            stopMusic();
        }
        mediaPlayer = MediaPlayer.create(context, id);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        rawId = id;
    }

    public static void stopMusic() {
        mediaPlayer.stop();
        rawId = -1;
    }

    public static void pauseMusic() {
        mediaPlayer.pause();
        length = mediaPlayer.getCurrentPosition();
    }

    public static void resumeMusic() {
        if(!mediaPlayer.isPlaying() && mediaPlayer.getCurrentPosition() > 1) {
            mediaPlayer.seekTo(length);
            mediaPlayer.start();
        }
    }

    private BGMController() {
    }

    private static class InnerInstanceClazz {
        private static final BGMController instance = new BGMController();
    }

    public static BGMController getInstance() {
        return InnerInstanceClazz.instance;
    }
}
