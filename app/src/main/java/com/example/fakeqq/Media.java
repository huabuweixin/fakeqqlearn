package com.example.fakeqq;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class Media extends AppCompatActivity {

    ListView listView;
    private SongAdapter songAdapter;
    ArrayList<HashMap<String, String>> songList = new ArrayList<>();
    private MediaPlayer mediaPlayer;
    private boolean isPlaying=false;
    private ImageView backview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        listView = findViewById(R.id.music_me);
        init();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(Media.this, R.raw.welcometotheblackparade);
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            // 播放器准备好后开始播放
                            mediaPlayer.start();
                            isPlaying = true;
                            Toast.makeText(Media.this, "音乐播放", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // 判断是否正在播放
                    if (isPlaying) {
                        // 如果正在播放，暂停播放
                        mediaPlayer.pause();
                        isPlaying = false;
                        Toast.makeText(Media.this, "音乐暂停", Toast.LENGTH_SHORT).show();
                    } else {
                        // 如果没有在播放，继续播放
                        mediaPlayer.start();
                        isPlaying = true;
                        Toast.makeText(Media.this, "音乐播放", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // 获取当前长按的歌曲信息
                HashMap<String, String> song = songList.get(position);
                String songTitle = song.get("title");
                String songArtist = song.get("artist");
                String songPath = song.get("path");

                // 创建 Intent 并携带歌曲信息跳转到 MusicActivity
                Intent intent = new Intent(Media.this, MusicActivity.class);
                intent.putExtra("songPath", songPath);
                intent.putExtra("songTitle", songTitle);
                intent.putExtra("songArtist", songArtist);
                startActivity(intent);

                // 返回 true 表示消费了事件，不会再触发其他点击事件
                return true;
            }
        });
        // 设置ListView项的点击监听
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 获取当前点击的歌曲路径
                String songPath = songList.get(position).get("path");

                if (isPlaying) {
                    // 如果当前正在播放，暂停播放
                    mediaPlayer.pause();
                    isPlaying = false;
                    Toast.makeText(Media.this, "音乐暂停", Toast.LENGTH_SHORT).show();
                } else {
                    // 如果没有在播放，开始播放
                    try {
                        mediaPlayer.reset();  // 重置播放器，准备播放新歌曲
                        mediaPlayer.setDataSource(songPath);  // 设置播放路径
                        mediaPlayer.prepare();  // 准备播放
                        mediaPlayer.start();  // 开始播放
                        isPlaying = true;
                        Toast.makeText(Media.this, "音乐播放", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(Media.this, "播放错误", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });*/
    }

    @SuppressLint("WrongViewCast")
    void init() {
        // 获取音频文件的URI
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        // 定义要查询的字段
        String[] projection = {
                MediaStore.Audio.Media._ID, // 音频文件的ID
                MediaStore.Audio.Media.TITLE, // 歌曲标题
                MediaStore.Audio.Media.ARTIST, // 歌手
                MediaStore.Audio.Media.DATA // 歌曲路径
        };

        // 获取ContentResolver
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(uri, projection, null, null, null);

        if (cursor != null) {
            // 迭代查询到的每一条数据
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));

                HashMap<String, String> song = new HashMap<>();
                song.put("title", title);
                song.put("artist", artist);
                song.put("path", path);
                songList.add(song);
            }
            cursor.close(); // 关闭cursor
        }

        HashMap<String, String> song1 = new HashMap<>();
        song1.put("title", "Welcome To The Black Parade");  // 歌曲名称
        song1.put("artist", "My Chemical Romance");  // 歌手
        song1.put("path", "res/music/Disenchanted-My_Chemical_Romance-244624.mp3");  // 歌曲路径（模拟路径）
        songList.add(song1);  // 将数据添加到 songList

        HashMap<String, String> song2 = new HashMap<>();
        song2.put("title", "不能说的秘密");  // 歌曲名称
        song2.put("artist", "周杰伦");  // 歌手
        song2.put("path", "res/music/不能说的秘密-周杰伦-392927.mp3");  // 歌曲路径（模拟路径）
        songList.add(song2);  // 将数据添加到 songList

        // 使用SongAdapter适配器来绑定数据
        songAdapter = new SongAdapter(this, songList);
        listView.setAdapter(songAdapter);
        backview=findViewById(R.id.user_back);
        backview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Media.this.finish();
            }
        });
    }
    // 在Activity销毁时释放资源
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release(); // 释放资源
            mediaPlayer = null;
        }
    }
}
