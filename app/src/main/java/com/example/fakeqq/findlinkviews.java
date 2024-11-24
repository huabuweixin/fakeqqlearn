package com.example.fakeqq;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.net.Uri;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class findlinkviews extends AppCompatActivity {

    RecyclerView recyclerView;
    Cursor cursor;
    List<Communication> communications=new ArrayList<>();
    private  linkviewAdapter mAdapter;
    ImageView madd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findlinkviews);
        recyclerView=findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter=new linkviewAdapter(this,recyclerView);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setDatasource(communications);
        getContacts();
        refreshData(); // 查询数据库并填充 RecyclerView
        init();
    }
    private void init(){
        madd=findViewById(R.id.menu_add);
        madd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建弹窗
                AlertDialog.Builder builder = new AlertDialog.Builder(findlinkviews.this);
                builder.setTitle("新增联系人");
                // 创建弹窗中的布局
                LinearLayout layout = new LinearLayout(findlinkviews.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(50, 40, 50, 40);
                // 输入姓名的编辑框
                EditText nameInput = new EditText(findlinkviews.this);
                nameInput.setHint("请输入姓名");
                layout.addView(nameInput);
                // 输入号码的编辑框
                EditText numberInput = new EditText(findlinkviews.this);
                numberInput.setHint("请输入号码");
                numberInput.setInputType(InputType.TYPE_CLASS_PHONE); // 设置为电话号码输入类型
                layout.addView(numberInput);
                builder.setView(layout);
                // 设置确认按钮
                builder.setPositiveButton("确认", (dialog, which) -> {
                    String name = nameInput.getText().toString().trim();
                    String number = numberInput.getText().toString().trim();
                    if (name.isEmpty() || number.isEmpty()) {
                        Toast.makeText(findlinkviews.this, "姓名和号码不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        // 插入数据到数据库
                        ContentValues values = new ContentValues();
                        values.put("name", name);
                        values.put("price", number);
                        Uri newUri = getContentResolver().insert(
                                Uri.parse("content://com.example.fakeqq.provider/information"),
                                values
                        );
                        if (newUri != null) {
                            Toast.makeText(findlinkviews.this, "新增成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(findlinkviews.this, "新增失败", Toast.LENGTH_SHORT).show();
                        }
                        refreshData(); // 刷新数据
                    }
                });
                // 设置取消按钮
                builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
                // 显示弹窗
                builder.create().show();
            }
        });

    }
    private void getContacts() {
        ContentResolver resolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };
        // 查询联系人数据
        Cursor cursor = resolver.query(uri, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                String cName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String cNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                communications.add(new Communication(cName, cNum,id));
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
    }
    public void refreshData() {
        communications.clear(); // 清空旧数据
        Cursor cursor = getContentResolver().query(
                Uri.parse("content://com.example.fakeqq.provider/information"),
                null, null, null, null
        );
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String number = cursor.getString(cursor.getColumnIndexOrThrow("price")); // 假设 `price` 存储电话号码
                communications.add(new Communication(name, number,id));
            }
            cursor.close();
        }
        mAdapter.setDatasource(communications); // 更新 RecyclerView
    }



}