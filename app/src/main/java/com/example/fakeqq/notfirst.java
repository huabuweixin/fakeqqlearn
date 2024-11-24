package com.example.fakeqq;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class notfirst extends AppCompatActivity {
    private Button sbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notfirst);
        init();
    }
    void init(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(notfirst.this, LoginActivity.class));
                notfirst.this.finish();
            }
        }, 3000);
        sbtn=findViewById(R.id.nstartbtn);
        sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.removeCallbacksAndMessages(null);
                startActivity(new Intent(notfirst.this,LoginActivity.class));
                notfirst.this.finish();
            }
        });
    }
}