package com.example.fakeqq;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Bookstore extends AppCompatActivity {
    private Button insertButton,updateButton,searchButton,deleteButton;
    private EditText name,price;
    private TextView show,showPrice;
    final DatabaseHelper dbHelper=new DatabaseHelper(Bookstore.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookstore);
        init();
    }
    void init(){
        insertButton=findViewById(R.id.btn_insert);
        updateButton=findViewById(R.id.btn_update);
        searchButton=findViewById(R.id.btn_search);
        deleteButton=findViewById(R.id.btn_delete);
        name=findViewById(R.id.name);
        price=findViewById(R.id.price);
        show=findViewById(R.id.tv_show);
        showPrice=findViewById(R.id.tv_showPrice);
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        myShow();
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db=dbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("name",name.getText().toString());
                values.put("price",price.getText().toString());
                long id =db.insert("information",null,values);
                Log.d("myDeBug","insert");
                myShow();
                db.close();
                name.setText(null);
                price.setText(null);
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db=dbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("price",price.getText().toString());
                db.update("information",values,"name=?",new String[]{name.getText().toString()});

                myShow();


                db.close();
                Log.d("myDebug","update");
                name.setText(null);
                price.setText(null);
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = null;
                Cursor cursor = null;
                try {
                    db = dbHelper.getWritableDatabase();
                    String name1 = name.getText().toString();
                    show.setText(null);
                    showPrice.setText(null);

                    if (name1.isEmpty()) {
                        show.setText("书名");
                        showPrice.setText("价格");
                        cursor = db.rawQuery("select * from information", null);
                    } else {
                        show.setText("书名");
                        showPrice.setText("价格");
                        cursor = db.rawQuery("select * from information where name = ?", new String[]{name1});
                    }

                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            @SuppressLint("Range") String newName = cursor.getString(cursor.getColumnIndex("name"));
                            @SuppressLint("Range") int newAge = cursor.getInt(cursor.getColumnIndex("price"));
                            show.setText(show.getText() + "\n" + newName);
                            showPrice.setText(showPrice.getText() + "\n" + newAge);
                        } while (cursor.moveToNext());
                    } else {
                        show.setText("未找到相关书籍");
                        showPrice.setText("");
                    }
                } catch (Exception e) {
                    // Handle exceptions
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (db != null) {
                        db.close();
                    }
                }
                name.setText(null);
                price.setText(null);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                db.delete("information","name=?",new String[]{name.getText().toString()});
                myShow();
                db.close();
                Log.d("myDeBug","DeleteSuccess");
                name.setText(null);
                price.setText(null);
            }
        });

    }
    public void myShow(){
        SQLiteDatabase db=dbHelper.getReadableDatabase();


        show.setText("书名");
        showPrice.setText("价格");
        Cursor cursor = db.rawQuery("select * from information",null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String newName = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") int newAge = cursor.getInt(cursor.getColumnIndex("price"));
            show.setText(show.getText() + "\n" + newName);
            showPrice.setText(showPrice.getText()+"\n" + newAge);
        }
        cursor.close();
    }


}
