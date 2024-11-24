package com.example.fakeqq;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class ItemActivity extends AppCompatActivity {
    ListView listView;
    TextView linkview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.item1), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }
    public void init(){
        listView=(ListView) findViewById(R.id.list_view);
        List<newsitem> newsitemList=new ArrayList<>();
        newsitem new1=new newsitem(R.mipmap.num1,"Lee","What's wrong?","9:30");
        newsitemList.add(new1);
        newsitem new2=new newsitem(R.mipmap.num2,"Tisher","No,I don't need it.","9:25");
        newsitemList.add(new2);
        newsitem new3=new newsitem(R.mipmap.num3,"Sir","Sure","9:10");
        newsitemList.add(new3);
        newsitem new4=new newsitem(R.mipmap.num4,"Ray","OK,i get it.","8:57");
        newsitemList.add(new4);
        newsitem new5=new newsitem(R.mipmap.num5,"Tom","Are you kid me?","8:41");
        newsitemList.add(new5);
        newsitem new6=new newsitem(R.mipmap.num6,"Tina","If...I don't no","8:30");
        newsitemList.add(new6);
        newsitem new7=new newsitem(R.mipmap.num7,"Rose","Damn!","8:20");
        newsitemList.add(new7);
        newsitemAdapter adapter=new newsitemAdapter(ItemActivity.this,R.layout.news_item,newsitemList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    Intent intent=new Intent(ItemActivity.this, chatview.class);
                    ItemActivity.this.finish();
                    startActivity(intent);
                }
            }
        });
        linkview=findViewById(R.id.tvbar_friend);
        linkview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ItemActivity.this,findlinkviews.class);
                ItemActivity.this.finish();
                startActivity(intent);
            }
        });

    }

}