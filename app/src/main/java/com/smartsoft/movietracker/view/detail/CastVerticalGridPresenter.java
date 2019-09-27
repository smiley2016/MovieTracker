package com.smartsoft.movietracker.view.detail;

import android.content.ClipData;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HorizontalGridView;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.SinglePresenterSelector;

import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.model.cast.Cast;
import com.smartsoft.movietracker.model.cast.CastList;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CastVerticalGridPresenter extends Presenter {

    private static final String TAG = CastVerticalGridPresenter.class.getName();

    private Context mContext;

    public CastVerticalGridPresenter(Context mContext) {
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

        CastHorizontalGridPresenter castPresenter;


        public PresenterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

        public void bind(ArrayList<Cast> cast){

            if(!cast.isEmpty()){
                castPresenter = new CastHorizontalGridPresenter(mContext);

                ArrayObjectAdapter objectAdapter = new ArrayObjectAdapter();
                for(Cast it: cast){
                    objectAdapter.add(it);
                }

                SinglePresenterSelector presenterSelector = new SinglePresenterSelector(castPresenter);

                ItemBridgeAdapter itemBridgeAdapter = new ItemBridgeAdapter(objectAdapter, presenterSelector);

                hGridView.setAdapter(itemBridgeAdapter);

                castTextView.setText(R.string.cast); Log.e(TAG, "cast " + cast.size());
                view.setVisibility(View.VISIBLE);
            }else{
                Log.e(TAG, "no cast list");
                castTextView.setVisibility(View.GONE);
                hGridView.setVisibility(View.GONE);
            }
        }
    }
}
