package com.example.fakeqq;


import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.fakeqq.R; // 确保这是正确的包名

import androidx.appcompat.app.AppCompatActivity;

public class MusicActivity extends AppCompatActivity implements View.OnClickListener {
    private static SeekBar sb;
    private Button start, pause, cplay, bexit;
    private static TextView tv_progress;
    private static TextView tv_total;
    private TextView name_song;
    private ObjectAnimator animator;
    private MusicService musicService;
    private MusicService.MusicControlBinder musicControlBinder;
    private boolean isUnbind = false;
    private ServiceConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        init();
    }

    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            int duration = bundle.getInt("duration");
            int currentPosition = bundle.getInt("currentPosition");
            // Update the SeekBar
            sb.setMax(duration);
            sb.setProgress(currentPosition);
            // Update the text views for duration and current position
            tv_total.setText(formatTime(duration));
            tv_progress.setText(formatTime(currentPosition));
        }
    };

    private void init() {
        tv_progress = findViewById(R.id.tv_progress);
        tv_total = findViewById(R.id.tv_total);
        sb = findViewById(R.id.sb);
        name_song = findViewById(R.id.song_name);
        start = findViewById(R.id.bplay);
        pause = findViewById(R.id.bpause);
        cplay = findViewById(R.id.bcplay);
        bexit = findViewById(R.id.bexit);
        /*start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicService != null) {
                    musicService.play();
                    animator.start();
                }
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicService != null) {
                    musicService.pausePlay();
                    animator.pause();
                }
            }
        });*/

        Intent intent2 = new Intent(this, MusicService.class);
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                musicControlBinder = (MusicService.MusicControlBinder) service;
                musicService = musicControlBinder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {}
        };
        bindService(intent2, conn, BIND_AUTO_CREATE);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update progress text view
                tv_progress.setText(formatTime(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (musicService != null) {
                    musicService.seekTo(seekBar.getProgress());
                }
            }
        });

        ImageView iv_music = findViewById(R.id.iv_music);
        animator = ObjectAnimator.ofFloat(iv_music, "rotation", 0f, 360f);
        animator.setDuration(10000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ObjectAnimator.INFINITE);

        start.setOnClickListener(this);
        pause.setOnClickListener(this);
        cplay.setOnClickListener(this);
        bexit.setOnClickListener(this);

        name_song.setText("Welcome To The Black Parade"); // Set the song name
    }

    private static String formatTime(int milliseconds) {
        // Helper method to format milliseconds to mm:ss
        int seconds = milliseconds / 1000;
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void unbindService() {
        if (!isUnbind && musicService != null) {
            musicService.pausePlay();
            unbindService(conn);
            isUnbind = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bplay) {
            if (musicService != null) {
                musicService.play();
                animator.start();
            }
        } else if (v.getId() == R.id.bpause) {
            if (musicService != null) {
                musicService.pausePlay();
                animator.pause();
            }
        } else if (v.getId() == R.id.bcplay) {
            if (musicService != null) {
                musicService.continuePlay();
                animator.start();
            }
        } else if (v.getId() == R.id.bexit) {
            unbindService();
            finish();
        }
    }


}
