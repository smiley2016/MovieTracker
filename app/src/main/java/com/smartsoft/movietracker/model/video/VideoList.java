package com.smartsoft.movietracker.model.video;

import java.util.ArrayList;

public class VideoList {
    private ArrayList<Video> videos;

    public VideoList(ArrayList<Video> videos) {
        this.videos = videos;
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }
}
