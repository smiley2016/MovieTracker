package com.smartsoft.movietracker.view.detail;

import com.smartsoft.movietracker.model.video.Video;

public interface DetailVideoGridInterface {

    interface VideoGridView{
        void startPlayerActivity(String videoId);
    }

    interface VideoGridIntPresenter {
        void startPlayerActivity(String videoId);
    }
}
