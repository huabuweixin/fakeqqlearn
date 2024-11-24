package com.example.fakeqq;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class chatview extends AppCompatActivity {
    private TextView chatnews1;
    private TextView chatnews2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatview);
        init();
    }
    void init(){
        chatnews1=(TextView) findViewById(R.id.chatnew1);
        chatnews2=(TextView)findViewById(R.id.chatnew2);
        chatnews1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(chatview.this,Lookpis.class);
                startActivity(intent);
            }
        });
        chatnews2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(chatview.this,Bookstore.class);
                startActivity(intent);
            }
        });

    }
}