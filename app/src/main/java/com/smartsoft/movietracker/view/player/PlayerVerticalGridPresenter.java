package com.smartsoft.movietracker.view.player;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.leanback.widget.Presenter;

import com.smartsoft.movietracker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerVerticalGridPresenter extends Presenter {

    private Context mContext;

    public PlayerVerticalGridPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new PresenterViewHolder(View.inflate(mContext, R.layout.player_control_layout, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        PresenterViewHolder holder = (PresenterViewHolder) viewHolder;
        String title = ((String)item);
        holder.bind(title);
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }

    class PresenterViewHolder extends ViewHolder{

        @BindView(R.id.player_video_title)
        TextView title;


        public PresenterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(String videoTitle) {
            title.setText(videoTitle);
        }
    }
}
