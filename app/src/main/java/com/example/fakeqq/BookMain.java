package com.example.fakeqq;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BookMain extends AppCompatActivity {
    ImageView backimage;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_main);
        init();
    }

    void init(){
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        // 默认加载 HomeFragment
        loadFragment(new HomeFragment());

        // 设置底部导航栏的选择监听
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            if(item.getItemId() == R.id.navigation_news) {
                fragment = new HomeFragment(); // 加载首页Fragment
            } else if (item.getItemId() == R.id.navigation_search) {
                fragment = new searchfragment(); // 加载搜索Fragment
            }
            return loadFragment(fragment); // 加载选择的Fragment
        });

        // 返回按钮点击事件
        backimage = findViewById(R.id.user_back);
        backimage.setOnClickListener(v -> BookMain.this.finish());
    }

    // 加载Fragment的通用方法
    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.framelay, fragment) // 替换容器中的Fragment
                    .commit();
            return true;
        }
        return false;
    }
}
