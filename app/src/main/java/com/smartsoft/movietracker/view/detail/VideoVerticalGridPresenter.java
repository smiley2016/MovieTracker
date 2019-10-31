package com.smartsoft.movietracker.view.detail;

import android.content.Context;
import android.net.Uri;
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
import com.smartsoft.movietracker.interfaces.DetailVideoGridInterface;
import com.smartsoft.movietracker.model.video.Video;
import com.smartsoft.movietracker.model.video.VideoList;
import com.smartsoft.movietracker.utils.FragmentNavigation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @see VideoVerticalGridPresenter is
 * a {@link androidx.leanback.widget.VerticalGridView} component
 * for handle the {@link Video} element rollability in vertical direction
 */
public class VideoVerticalGridPresenter extends Presenter implements DetailVideoGridInterface {

    /**
     * Contains class name
     */
    private static final String TAG = VideoVerticalGridPresenter.class.getName();

    /**
     * Where running the presenter
     */
    private Context mContext;

    /**
     * Video list
     */
    private ArrayList<Video> videos;

    /**
     * Contains Youtube links in URI
     */
    private ArrayList<Uri> youtubeLinks;


    /**
     * Class constructor
     * @param youtubeLinks Youtube links in URI
     */
    VideoVerticalGridPresenter(ArrayList<Uri> youtubeLinks) {
        this.youtubeLinks = youtubeLinks;
        this.videos = new ArrayList<>();
    }

    /**
     * This function create the from the XML video_layout layout a real view
     * @param parent The ViewGroup wherein the
     *               {@link android.view.LayoutInflater} will paints the XML
     * @return {@link PresenterViewHolder}
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        mContext = parent.getContext();
        return new PresenterViewHolder(View.inflate(mContext, R.layout.video_layout, null));
    }

    /**
     * Onto every single element the {@link #onBindViewHolder(ViewHolder, Object)}
     * calls the {@link PresenterViewHolder#bind(ArrayList, DetailVideoGridInterface)} function to initializes it's views
     * how behaves in the app.
     * @param viewHolder {@link androidx.leanback.widget.Presenter.ViewHolder} what holds the elements in
     * {@link androidx.leanback.widget.VerticalGridView}
     * @param item Contains all the elements from the {@link androidx.leanback.widget.ArrayObjectAdapter}
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        PresenterViewHolder holder = (PresenterViewHolder) viewHolder;
        this.videos = ((VideoList) item).getVideos();
        holder.bind(this.videos, this);
    }

    /**
     * App doesn't use this function
     * @param viewHolder {@link androidx.leanback.widget.Presenter.ViewHolder} what holds the elements in
     *                  {@link androidx.leanback.widget.HorizontalGridView}
     */
    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }

    /**
     * This search in the video list for the video which has an id like the function input
     * make Bundle from the result and start the {@link com.smartsoft.movietracker.view.player.PlayerFragment}
     * @param videoId is used for determine exactly the video what will be play
     *                by the {@link com.google.android.exoplayer2.SimpleExoPlayer}
     */
    @Override
    public void startPlayer(String videoId) {
        int playIndex = getPositionOfVideo(videoId);
        Bundle bundle = new Bundle();
        bundle.putInt(mContext.getString(R.string.playIndex), playIndex);
        bundle.putSerializable(mContext.getString(R.string.videos), videos);
        bundle.putSerializable(mContext.getString(R.string.youtubeLinks), youtubeLinks);
        FragmentNavigation.getInstance().showPlayerFragment(bundle);
    }

    /**
     * Get the position of a video in the list
     * @param videoId searched video ID
     * @return a position in the list
     */
    private int getPositionOfVideo(String videoId) {
        for (int i = 0; i < videos.size(); ++i) {
            if (videos.get(i).getId().equals(videoId)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Inner class is contains a {@link Video} views of the element
     * and it's properties
     */
    class PresenterViewHolder extends ViewHolder {

        @BindView(R.id.video_text_view)
        TextView videoTextView;

        @BindView(R.id.videos_GridView)
        HorizontalGridView hGridView;

        VideoHorizontalGridPresenter videoHorizontalGridPresenter;

        /**
         * Inner class constructor
         * @param view The initialized element
         */
        public PresenterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            videoTextView.setText(R.string.videos);

        }

        /**
         * Initializer function for one element
         * @param videos {@link ArrayList} that contains the {@link Video}
         *                              objects which will be initialize
         * @param mInterface reference to {@link DetailVideoGridInterface}
         */
        public void bind(ArrayList<Video> videos, DetailVideoGridInterface mInterface) {
            if (!videos.isEmpty()) {

                videoHorizontalGridPresenter = new VideoHorizontalGridPresenter(mInterface);
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
