package com.example.fakeqq;

import static com.example.fakeqq.R.id.news_news;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class newsitemAdapter extends ArrayAdapter<newsitem> {
    public newsitemAdapter(@NonNull Context context, int resource, @NonNull List<newsitem> objects){
        super(context,resource,objects);
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        newsitem newsitem=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(R.layout.news_item,parent,false);
        ImageView newsimage=view.findViewById(R.id.news_image);
        TextView newsname=view.findViewById(R.id.news_name);
        TextView newsnews=view.findViewById(R.id.news_news);
        TextView newstime=view.findViewById(R.id.news_time);
        newsimage.setImageResource(newsitem.getImageID());
        newsname.setText(newsitem.getName());
        newsnews.setText(newsitem.getNews());
        newstime.setText(newsitem.getTime());
        return view;
    }
}