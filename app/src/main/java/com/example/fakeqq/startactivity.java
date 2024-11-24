package com.example.fakeqq;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class startactivity extends AppCompatActivity {

    private static final int HANDLER_SPLASH = 2001;
    //判断程序是否是第一次运行
    private static final String SHARE_IS_FIRST = "isFirst";
    private ProgressBar mProgressBar;
    private TextView mTextView;
    private int start=0,maxprogress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startactivity);
        mProgressBar = findViewById(R.id.progress_01);
        mTextView=findViewById(R.id.tv_progress);
        maxprogress=mProgressBar.getMax();
        init();
    }

    //闪屏业延时
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mTextView.setText(start+" %");//更新进度
                    mProgressBar.setProgress(start);break;
                case HANDLER_SPLASH:
                    if (isFirst()) {
                        startActivity(new Intent(startactivity.this, introduceActivity.class));
                    } else {
                        startActivity(new Intent(startactivity.this, notfirst.class));
                    }
                    finish();
                    break;
                default:
                    break;
            }
            return false;
        }

    });
    protected void onStart() {
        super.onStart();
        //启动线程加载
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(50);//线程休眠1s
                        int a = new Random().nextInt(10);//产生一个10以内的随机数
                        start += a;
                        if (start > maxprogress)//如果进程超过最大值
                            break;
                        handler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private void init(){
        //延时2000ms
        handler.sendEmptyMessageDelayed(HANDLER_SPLASH, 1500);
    }

    //判断程序是否第一次运行
    private boolean isFirst() {
        boolean isFirst = ShareUtils.getBoolean(this, SHARE_IS_FIRST, true);
        if (isFirst) {
            ShareUtils.putBoolean(this, SHARE_IS_FIRST, false);
            //是第一次运行
            return true;
        } else {
            return false;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}