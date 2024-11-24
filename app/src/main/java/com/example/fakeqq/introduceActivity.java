package com.example.fakeqq;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class introduceActivity extends AppCompatActivity {
    private ViewPager mviewpager;
    private Button stabtn;
    //小圆点
    private ImageView point1, point2, point3, point4;
    private List<View> viewList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        initView();
        initAdapter();
        initStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(introduceActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
    private void initView(){
        point1 = (ImageView) findViewById(R.id.point1);
        point2 = (ImageView) findViewById(R.id.point2);
        point3 = (ImageView) findViewById(R.id.point3);
        point4 = (ImageView) findViewById(R.id.point4);
        mviewpager=findViewById(R.id.introductory_viewPager);
        viewList=new ArrayList<>();
        viewList.add(getView(R.layout.introduce1));
        viewList.add(getView(R.layout.introduce2));
        viewList.add(getView(R.layout.introudce3));
        viewList.add(getView(R.layout.introduce4));
        setPointImg(true, false, false, false);
        //监听ViewPager滑动
        mviewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            //pager切换
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        setPointImg(true, false, false, false);
                        break;
                    case 1:
                        setPointImg(false, true, false, false);
                        break;
                    case 2:
                        setPointImg(false, false, true, false);
                        break;
                    case 3:
                        setPointImg(false, false, false, true);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    private void initAdapter(){
        introduceAdapter adapter=new introduceAdapter(viewList);
        mviewpager.setAdapter(adapter);
    }
    private void initStart(){
        stabtn=viewList.get(3).findViewById(R.id.startbtn);
        stabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(introduceActivity.this,LoginActivity.class));
                introduceActivity.this.finish();
            }
        });


    }
    private void setPointImg(boolean isCheck1, boolean isCheck2, boolean isCheck3, boolean isCheck4) {
        if (isCheck1) {
            point1.setBackgroundResource(R.color.white);
        } else {
            point1.setBackgroundResource(R.color.blue1);
        }

        if (isCheck2) {
            point2.setBackgroundResource(R.color.white);
        } else {
            point2.setBackgroundResource(R.color.blue1);
        }

        if (isCheck3) {
            point3.setBackgroundResource(R.color.white);
        } else {
            point3.setBackgroundResource(R.color.blue1);
        }

        if (isCheck4) {
            point4.setBackgroundResource(R.color.white);
        } else {
            point4.setBackgroundResource(R.color.blue1);
        }
    }
    private View getView(int resId) {
        return LayoutInflater.from(this).inflate(resId,null);
    }


}