package com.example.fakeqq;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ProgressBar;
import android.widget.ViewFlipper;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    ViewFlipper viewFlipper;
    TranslateAnimation rightInAnim, leftInAnim, rightOutAnim, leftOutAnim;
    int currentX;

    private ProgressBar progressBar;
    private Handler handler;
    private Runnable progressRunnable;
    private int progressDuration = 3000; // 进度条与图片轮播间隔一致
    private int progressStep = 50; // 每次更新的进度时间间隔（毫秒）

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 加载布局
        View view = inflater.inflate(R.layout.activity_bookshop, container, false);

        // 初始化视图组件
        init(view);

        // 设置触摸监听器
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        viewFlipper.stopFlipping();
                        stopProgressBar(); // 停止进度条
                        currentX = (int) event.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        if (event.getX() - currentX > 100) { // 手指向右滑动
                            viewFlipper.setInAnimation(leftInAnim);
                            viewFlipper.setOutAnimation(rightOutAnim);
                            viewFlipper.showPrevious();
                        } else if (currentX - event.getX() > 100) { // 手指向左滑动
                            viewFlipper.setInAnimation(rightInAnim);
                            viewFlipper.setOutAnimation(leftOutAnim);
                            viewFlipper.showNext();
                        }
                        viewFlipper.startFlipping();
                        startProgressBar(); // 重新开始进度条
                        break;
                }
                return true; // 返回 true，表示事件已被处理
            }
        });

        return view;
    }

    void init(View view) {
        // 初始化动画
        rightInAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        rightInAnim.setDuration(1000);

        leftOutAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        leftOutAnim.setDuration(1000);

        rightOutAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        rightOutAnim.setDuration(1000);

        leftInAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        leftInAnim.setDuration(1000);

        viewFlipper = view.findViewById(R.id.viewflipper);
        progressBar = view.findViewById(R.id.progressBar); // 绑定进度条
        progressBar.setMax(progressDuration); // 设置最大值

        viewFlipper.setFlipInterval(progressDuration);
        viewFlipper.startFlipping();

        viewFlipper.setOutAnimation(leftOutAnim);
        viewFlipper.setInAnimation(rightInAnim);

        // 初始化 Handler 和 Runnable
        handler = new Handler();
        progressRunnable = new Runnable() {
            @Override
            public void run() {
                if (progressBar.getProgress() >= progressBar.getMax()) {
                    progressBar.setProgress(0); // 重置进度条
                } else {
                    progressBar.incrementProgressBy(progressStep); // 增加进度
                }
                handler.postDelayed(this, progressStep); // 定时执行
            }
        };

        startProgressBar(); // 开始进度条
    }

    private void startProgressBar() {
        progressBar.setProgress(0); // 重置进度条
        handler.post(progressRunnable); // 启动 Handler
    }

    private void stopProgressBar() {
        handler.removeCallbacks(progressRunnable); // 停止 Handler
    }
}
