package com.example.fakeqq;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LostfindActivity extends AppCompatActivity {
    private String username;
    private EditText etusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lostfind);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.item1), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }
    private void init(){
        TextView tv_rezhuce=(TextView) findViewById(R.id.rezhuce);
        TextView tv_relogin=(TextView) findViewById(R.id.relogin);
        etusername=(EditText) findViewById(R.id.forgetzh);
        Button rebtn=(Button) findViewById(R.id.reforgetbtn);
        tv_rezhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iten1=new Intent(LostfindActivity.this,MainActivity.class);
                startActivityForResult(iten1,1);
            }
        });
        tv_relogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iten2 = new Intent(LostfindActivity.this, LoginActivity.class);
                startActivityForResult(iten2, 1);
            }
        });
        rebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=etusername.getText().toString().trim();
                if(finduser(username)){
                    Toast.makeText(LostfindActivity.this, "密码已经重置为123456", Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(LostfindActivity.this, "该账户不存在请注册", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    private boolean finduser(String username){
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        String spPsw=sp.getString(username, "");
        if(!TextUtils.isEmpty(spPsw)){
            spPsw="123456";
            return true;
        }else{
            return false;
        }
    }
}