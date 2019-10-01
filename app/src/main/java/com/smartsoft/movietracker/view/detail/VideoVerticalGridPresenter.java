package com.smartsoft.movietracker.view.detail;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HorizontalGridView;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.SinglePresenterSelector;

import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.model.video.Video;
import com.smartsoft.movietracker.model.video.VideoList;
import com.smartsoft.movietracker.utils.FragmentNavigation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoVerticalGridPresenter extends Presenter implements DetailVideoGridInterface.VideoGridView {

    private static final String TAG = VideoVerticalGridPresenter.class.getName();
    private Context mContext;
    private Bundle bundle;
    private ArrayList<Video> videos;

    public VideoVerticalGridPresenter(Context mContext) {
        this.mContext = mContext;
        this.bundle = new Bundle();
        this.videos = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new PresenterViewHolder(View.inflate(mContext, R.layout.video_layout, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        PresenterViewHolder holder = (PresenterViewHolder) viewHolder;
        this.videos = ((VideoList) item).getVideos();
        holder.bind(this.videos, this);
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }

    @Override
    public void startPlayerActivity(String videoId) {
        int playIndex = getPositionOfVideo(videoId);
        bundle.putInt(mContext.getString(R.string.playIndex), playIndex);
        bundle.putSerializable(mContext.getString(R.string.videos), videos);
        FragmentNavigation.getInstance().showPlayerFragment(bundle);
    }

    public int getPositionOfVideo(String videoId) {
        for (int i = 0; i < videos.size(); ++i) {
            if (videos.get(i).getId().equals(videoId)) {
                return i;
            }
        }
        return -1;
    }


    class PresenterViewHolder extends ViewHolder {

        @BindView(R.id.video_text_view)
        TextView videoTextView;

        @BindView(R.id.videos_GridView)
        HorizontalGridView hGridView;

        VideoHorizontalGridPresenter videoHorizontalGridPresenter;


        public PresenterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            videoTextView.setText(R.string.videos);

        }

        public void bind(ArrayList<Video> videos, DetailVideoGridInterface.VideoGridView mInterface) {
            if (!videos.isEmpty()) {

                videoHorizontalGridPresenter = new VideoHorizontalGridPresenter(mContext, mInterface);
                videoTextView.setText(R.string.videos);


                ArrayObjectAdapter objectAdapter = new ArrayObjectAdapter();
                for (Video it : videos) {
                    objectAdapter.add(it);
                }

                ItemBridgeAdapter itemBridgeAdapter = new ItemBridgeAdapter();
                itemBridgeAdapter.setAdapter(objectAdapter);
                itemBridgeAdapter.setPresenter(new SinglePresenterSelector(videoHorizontalGridPresenter));

                hGridView.setAdapter(itemBridgeAdapter);
                hGridView.setItemSpacing((int) mContext.getResources().getDimension(R.dimen.spacing));
                view.setVisibility(View.VISIBLE);
            } else {
                videoTextView.setVisibility(View.GONE);
                hGridView.setVisibility(View.GONE);
                Log.e(TAG, "bind: no list binded");
            }
        }


    }
}
