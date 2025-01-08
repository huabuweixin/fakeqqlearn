package com.example.fakeqq;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class SongAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<HashMap<String, String>> songList;
    private LayoutInflater inflater;

    public SongAdapter(Context context, ArrayList<HashMap<String, String>> songList) {
        this.context = context;
        this.songList = songList;
        this.inflater = LayoutInflater.from(context);  // 获取LayoutInflater实例
    }

    @Override
    public int getCount() {
        return songList.size();
    }

    @Override
    public Object getItem(int position) {
        return songList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 如果convertView为空，则创建新的视图
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.music_item, parent, false);
        }

        // 获取当前歌曲的HashMap数据
        HashMap<String, String> song = songList.get(position);

        // 设置歌曲封面图片
        ImageView ivImage = convertView.findViewById(R.id.siv_img);
        ivImage.setImageResource(R.mipmap.ic_launcher);

        // 设置歌曲名
        TextView tvName = convertView.findViewById(R.id.tv_name);
        tvName.setText(song.get("title"));

        // 设置作者
        TextView tvAuthor = convertView.findViewById(R.id.tv_author);
        tvAuthor.setText(song.get("artist"));

        // 设置歌曲播放状态
        TextView tvStatus = convertView.findViewById(R.id.tv_type);
        tvStatus.setText("Paused");  // 初始状态为 "Paused"

        return convertView;
    }
}
