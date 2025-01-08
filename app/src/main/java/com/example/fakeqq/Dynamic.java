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

public class Dynamic extends AppCompatActivity {

    TextView itemview,linkview,book,media,BS,BM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);
        init();
    }
    void init(){
        itemview=findViewById(R.id.tvbar_user);
        itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dynamic.this,ItemActivity.class);
                Dynamic.this.finish();
                startActivity(intent);
            }
        });
        linkview=findViewById(R.id.tvbar_friend);
        linkview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dynamic.this,findlinkviews.class);
                Dynamic.this.finish();
                startActivity(intent);
            }
        });
        book=findViewById(R.id.book);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dynamic.this, BookMain.class);
                startActivity(intent);
            }
        });
        media=findViewById(R.id.media);
        media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dynamic.this,Media.class);
                startActivity(intent);
            }
        });
        BS=findViewById(R.id.BS);
        BS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dynamic.this,Lookpis.class);
                startActivity(intent);
            }
        });
        BM=findViewById(R.id.BM);
        BM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dynamic.this,Bookstore.class);
                startActivity(intent);
            }
        });
    }
}