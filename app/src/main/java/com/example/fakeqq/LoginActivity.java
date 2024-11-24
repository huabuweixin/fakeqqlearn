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

public class LoginActivity extends AppCompatActivity {
    private String username,password,sppasw;
    private EditText etusername,etpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.item1), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etusername=(EditText) findViewById(R.id.zhnumber);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String userName = bundle.getString("userName");
            if (userName != null) {
                etusername.setText(userName);
            }
        }

        init();
    }
    private void init(){
        TextView tv_zhuce=(TextView) findViewById(R.id.zhuce);
        TextView tv_forgetps=(TextView) findViewById(R.id.forgrtps);
        Button btn_button=(Button) findViewById(R.id.loginbutton);
        btn_button.setSelected(false);
        etpassword=(EditText) findViewById(R.id.zpsnumber);
        //注册事件
        tv_zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivityForResult(intent,1);
            }
        });
        //忘记密码事件
        tv_forgetps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,LostfindActivity.class));
            }
        });
        btn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=etusername.getText().toString().trim();
                password=etpassword.getText().toString().trim();
                sppasw=readpasword(username);
                btn_button.setSelected(true);
                if(TextUtils.isEmpty(username)){
                    Toast.makeText(LoginActivity.this,"请输入用户名",Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this,"请输入密码",Toast.LENGTH_LONG).show();
                } else if (password.equals(sppasw)) {
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                    savloginstatus(true,username);
                    Intent data = new Intent();
                    data.putExtra("isLogin", true);
                    setResult(RESULT_OK, data);
                    //销毁登录界面
                    LoginActivity.this.finish();
                    startActivity(new Intent( LoginActivity.this, ItemActivity.class));
                }else if ((sppasw != null && !TextUtils.isEmpty(sppasw) && !password.equals(sppasw))) {
                    Toast.makeText(LoginActivity.this, "输入的用户名和密码不一致", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "此用户名不存在", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    //查询用户名所对应的密码
    private String readpasword(String username){
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        return sp.getString(username,"");
    }
    //记录登录状态
    private void savloginstatus(boolean status,String username){
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("isLogin", status);
        editor.putString("loginUserName", username);
        editor.apply();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            String username=data.getStringExtra("username");
            if(!TextUtils.isEmpty(username)){
                etusername.setText(username);
                etusername.setSelection(username.length());
            }
        }
    }


}