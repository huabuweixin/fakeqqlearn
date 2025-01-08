package com.example.fakeqq;

public class Song {
    private String songName;
    private String author;
    private String songPath;
    private String songStatus; // 播放状态

    public Song(String songName, String author, String songPath, String songStatus) {
        this.songName = songName;
        this.author = author;
        this.songPath = songPath;
        this.songStatus = songStatus;
    }

    // Getter and Setter methods
    public String getSongName() {
        return songName;
    }

    public String getAuthor() {
        return author;
    }

    public String getSongPath() {
        return songPath;
    }

    public String getSongStatus() {
        return songStatus;
    }
}
