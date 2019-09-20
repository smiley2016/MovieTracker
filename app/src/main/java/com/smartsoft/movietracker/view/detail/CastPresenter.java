package com.smartsoft.movietracker.view.detail;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.leanback.widget.HorizontalGridView;
import androidx.leanback.widget.Presenter;

import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.model.cast.Cast;
import com.smartsoft.movietracker.model.cast.CastList;
import com.smartsoft.movietracker.model.cast.CastResult;
import com.smartsoft.movietracker.presenter.DetailPagePresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CastPresenter extends Presenter {

    private static final String TAG = CastPresenter.class.getName();

    private Context mContext;

    public CastPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new PresenterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        PresenterViewHolder holder = (PresenterViewHolder) viewHolder;
        ArrayList<Cast> cast = ((CastList)item).getCasts();
        holder.bind(cast);
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }


    class PresenterViewHolder extends ViewHolder{

        @BindView(R.id.cast_TextView)
        TextView castTextView;
        @BindView(R.id.cast_actors_grid_view)
        HorizontalGridView hGridView;

        DetailPageCastAdapter castAdapter;


        public PresenterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            castAdapter  = new DetailPageCastAdapter(mContext);
            hGridView.setAdapter(castAdapter);
            castTextView.setText(R.string.cast);
        }

        public void bind(ArrayList<Cast> cast){
            if(!cast.isEmpty()){
                castAdapter.addAllToList(cast);
                Log.e(TAG, "cast " + cast.size());
                view.setVisibility(View.VISIBLE);
            }else{
                Log.e(TAG, "no cast list");
                view.setVisibility(View.GONE);
            }
        }
    }
}
