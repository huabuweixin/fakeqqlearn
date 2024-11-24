package com.example.fakeqq;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText editTextNumberSigned,editTextTextPassword,editTextNumberPassword,editTextTextEmailAddress;
    private String userName,password,passwordAgain;
    private RadioGroup SexRadio;
    private long firsttime=0;
    private long firstTime=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.item1), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }
    private void init(){
        Button button=(Button) findViewById(R.id.button);
        editTextNumberSigned=(EditText)findViewById(R.id.editTextNumberSigned);
        editTextTextPassword=(EditText)findViewById(R.id.editTextTextPassword);
        editTextNumberPassword=(EditText)findViewById(R.id.editTextNumberPassword);
        SexRadio=(RadioGroup) findViewById(R.id.SexRadio);

        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onClick(View view) {
                getEditString();
                int sex;
                int sexChoseId = SexRadio.getCheckedRadioButtonId();
                if (sexChoseId == R.id.mainRegisterRdBtnFemale) {
                    sex= 0;
                } else if (sexChoseId == R.id.radioButtonmale) {
                    sex = 1;
                } else {
                    sex = -1;
                }
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(MainActivity.this, "请输入用户名", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "请输入密码", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(passwordAgain)) {
                    Toast.makeText(MainActivity.this, "请再次输入密码", Toast.LENGTH_LONG).show();
                } else if (sex < 0) {
                    Toast.makeText(MainActivity.this, "请选择性别", Toast.LENGTH_LONG).show();
                } else if (!password.equals(passwordAgain)) {
                    Toast.makeText(MainActivity.this, "输入两次的密码不一样", Toast.LENGTH_LONG).show();
                }else if(ExistUserName(userName)){
                    Toast.makeText(MainActivity.this, "此账户名已经存在", Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(MainActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                    saveRegisterInfo(userName, password);
                    Intent data = new Intent(MainActivity.this, LoginActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("userName", userName);
                    data.putExtras(bundle);
                    MainActivity.this.startActivity(data);
                    MainActivity.this.finish();
                }
            }
        });
    }
    private void getEditString(){
        userName=editTextNumberSigned.getText().toString().trim();
        password=editTextTextPassword.getText().toString().trim();
        passwordAgain=editTextNumberPassword.getText().toString().trim();

    }
    private boolean ExistUserName(String userName){
        boolean has_name=false;
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取密码
        String spPsw=sp.getString(userName, "");//传入用户名获取密码
        //如果密码不为空则确实保存过这个用户名
        if(!TextUtils.isEmpty(spPsw)) {
           has_name=true;
        }
        return has_name;

    }
    private void saveRegisterInfo(String userName,String password){
        String md5Psw = password;//获取密码
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器， SharedPreferences.Editor  editor -> sp.edit();
        SharedPreferences.Editor editor=sp.edit();
        //以用户名为key，密码为value保存在SharedPreferences中
        //key,value,如键值对，editor.putString(用户名，密码）;
        editor.putString(userName, md5Psw);
        //提交修改 editor.commit();
        editor.apply();

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
                return true;
            } else{
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }


}