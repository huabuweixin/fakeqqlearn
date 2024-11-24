package com.example.fakeqq;

public class newsitem {
    private int imageID;
    private String name;
    private String news;
    private String time;
    public int getImageID(){
        return imageID;
    }
    public String getName(){
        return name;
    }
    public String getNews(){
        return news;
    }
    public String getTime(){
        return time;
    }
    public newsitem(int imageID,String name,String news,String time){
        this.imageID=imageID;
        this.name=name;
        this.news=news;
        this.time=time;

    }
}
