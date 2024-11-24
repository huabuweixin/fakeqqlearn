package com.example.fakeqq;
import static android.widget.Toast.*;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class Lookpis extends AppCompatActivity implements ViewSwitcher.ViewFactory, View.OnTouchListener {
    private SeekBar seekBar;
    private ImageSwitcher is;
    private LinearLayout isll;
    int[] image={R.mipmap.num1,R.mipmap.num2,R.mipmap.num3,R.mipmap.num4,R.mipmap.num5};
    ArrayList<ImageView>points=new ArrayList<>();
    int index;
    float startx;
    float endx;
    private ImageView imageView;
    private Button closebtn;
    private static final int MIN_DISTANCE = 100; // 最小滑动距离
    private static final int MIN_VELOCITY = 1000; // 最小滑动速度
    private float xdown,ydown,xmove,ymove;
    private VelocityTracker mVelocityTracker;
    private static final int STORAGE_PERMISSION_CODE = 1;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookpis);
        requestStoragePermission();
        init();
        is=(ImageSwitcher)findViewById(R.id.imsw);
        is.setFactory(this);
        is.setOnTouchListener(this);
        initpoint();

    }
   private void initpoint(){
        isll=(LinearLayout) findViewById(R.id.islayout);
        int count=isll.getChildCount();
        for(int i=0;i<count;i++){
            points.add((ImageView)isll.getChildAt(i));
        }
        points.get(0).setImageResource(R.mipmap.touch);
    }
    public void setImageBackground(int selectimage){
        for (int i=0;i<points.size();i++){
            if(i==selectimage){
                points.get(i).setImageResource(R.mipmap.touch);
            }else {
                points.get(i).setImageResource(R.mipmap.deafault);
            }
        }
    }
    @Override
    public View makeView() {
        ImageView iv=new ImageView(this);
        iv.setImageResource(image[0]);
        iv.setImageAlpha(255);
        return iv;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            startx=event.getX();
        } else if (event.getAction()==MotionEvent.ACTION_UP) {
            endx=event.getX();
            if(startx-endx>30){
                index = index+1<image.length?++index:0;
                is.setInAnimation(this, android.R.anim.fade_in);
                is.setOutAnimation(this, android.R.anim.fade_out);
            } else if (endx-startx>30) {
                index = index-1>=0?--index:image.length-1;
                is.setInAnimation(this, android.R.anim.fade_in);
                is.setOutAnimation(this, android.R.anim.fade_out);
            }
            is.setImageResource(image[index]);
            setImageBackground(index);

        }
        return true;
    }

    @SuppressLint("ClickableViewAccessibility")
    void init(){
        imageView=(ImageView) findViewById(R.id.seekimage1);
        seekBar=(SeekBar) findViewById(R.id.SeekBar);
        ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(this, new MyGestureListener());
        imageView.setClickable(true);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                saveImageToGallery();
                //Toast.makeText(Lookpis.this, "图片已保存", Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(Lookpis.this);
                builder.setMessage("图片已保存");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scaleGestureDetector.onTouchEvent(event);
                return false;
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                imageView.setImageAlpha(progress);
                View currentView = is.getCurrentView();
                if (currentView instanceof ImageView) {
                    ((ImageView) currentView).setImageAlpha(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        closebtn=(Button) findViewById(R.id.closebutton);
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // 请求权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_CODE);
        } else {
            // 已获得权限
            saveImageToGallery();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 权限被授予
                saveImageToGallery();
            } else {
                // 权限被拒绝
                makeText(this, "保存图片的权限被拒绝，无法保存图片", LENGTH_SHORT).show();
            }
        }
    }
    private void saveImageToGallery() {
        // 获取当前显示的图片
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        // 获取图片的 MIME 类型
        String mimeType = "image/jpeg";

        // 获取当前时间戳作为图片的文件名
        String title = "Image_" + System.currentTimeMillis();

        // 获取 ContentResolver
        ContentResolver contentResolver = getContentResolver();

        // 获取 MediaStore 的 URI（图片存储位置）
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, title);
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType);
        values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);

        // 向 MediaStore 插入新的图片记录，获取图片的 URI
        Uri imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        // 将图片保存到该 URI
        if (imageUri != null) {
            try (OutputStream outputStream = contentResolver.openOutputStream(imageUri)) {
                if (outputStream != null) {
                    // 将图片保存到输出流
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    makeText(this, "图片保存成功", LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                makeText(this, "图片保存失败", LENGTH_SHORT).show();
            }
        } else {
            makeText(this, "无法访问存储，图片保存失败", LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xdown = ev.getX();
                ydown = ev.getY();
                if (mVelocityTracker == null) {
                    mVelocityTracker = VelocityTracker.obtain();
                } else {
                    mVelocityTracker.clear();
                }
                mVelocityTracker.addMovement(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                xmove = ev.getX();
                ymove = ev.getY();
                mVelocityTracker.addMovement(ev);
                break;
            case MotionEvent.ACTION_UP:
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                float yVelocity = mVelocityTracker.getYVelocity();
                if (Math.abs(xVelocity) > MIN_VELOCITY && Math.abs(xVelocity) > Math.abs(yVelocity)) {
                    if (xVelocity >0) {
                        // 向右滑动
                        finish();
                        startActivity(new Intent( Lookpis.this, chatview.class));
                    }
                }
                if (mVelocityTracker != null) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
    private class MyGestureListener implements ScaleGestureDetector.OnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();
            imageView.setScaleX(imageView.getScaleX() * scaleFactor);
            imageView.setScaleY(imageView.getScaleY() * scaleFactor);
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            // 返回true表示开始缩放
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            // 缩放结束时的操作
        }
    }


}

