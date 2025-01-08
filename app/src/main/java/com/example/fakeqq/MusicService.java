package com.example.fakeqq;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

public class MusicService extends Service {
    private MediaPlayer player;
    private Timer timer;

    // Binder given to clients
    private final IBinder binder = new MusicControlBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.welcometotheblackparade); // Ensure you have the correct audio resource.
        player.setOnCompletionListener(mp -> stopSelf()); // Optional: stop the service when the song ends.
    }

    public void addTimer() {
        if (timer == null) {
            timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if (player == null) return;
                    int duration = player.getDuration();
                    int currentPosition = player.getCurrentPosition();
                    Message msg = MusicActivity.handler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putInt("duration", duration);
                    bundle.putInt("currentPosition", currentPosition);
                    msg.setData(bundle);
                    MusicActivity.handler.sendMessage(msg);
                }
            };
            timer.scheduleAtFixedRate(task, 0, 500); // Update every 500ms
        }
    }

    public void removeTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public class MusicControlBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    // Control methods for the music player
    public void play() {
        if (player != null && !player.isPlaying()) {
            player.start();
            addTimer();
        }
    }

    public void pausePlay() {
        if (player != null && player.isPlaying()) {
            player.pause();
            removeTimer();
        }
    }

    public void continuePlay() {
        if (player != null && !player.isPlaying()) {
            player.start();
            addTimer();
        }
    }

    public void seekTo(int progress) {
        if (player != null) {
            player.seekTo(progress);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
        removeTimer(); // Ensure the timer is also cleaned up
    }
}
