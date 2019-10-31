package com.smartsoft.movietracker.view.detail;

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

/**
 * @see CastVerticalGridPresenter is
 * a {@link androidx.leanback.widget.VerticalGridView} component
 * for handle the {@link Cast} element rollability in vertical direction
 */
public class CastVerticalGridPresenter extends Presenter {

    /**
     * Contains the class name
     */
    private static final String TAG = CastVerticalGridPresenter.class.getName();

    /**
     * This function create the from the XML cast_element layout a real view
     * @param parent The ViewGroup wherein the
     *               {@link android.view.LayoutInflater} will paints the XML
     * @return {@link CastVerticalGridPresenter.PresenterViewHolder}
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new PresenterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_layout, parent, false));
    }

    /**
     * Onto every single element the {@link CastVerticalGridPresenter#onBindViewHolder(ViewHolder, Object)}
     * calls the {@link CastVerticalGridPresenter.PresenterViewHolder#bind(ArrayList)} function to initializes it's views
     * how behaves in the app.
     * @param viewHolder {@link androidx.leanback.widget.Presenter.ViewHolder} what holds the elements in
     * {@link androidx.leanback.widget.VerticalGridView}
     * @param item Contains all the elements from the {@link androidx.leanback.widget.ArrayObjectAdapter}
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        PresenterViewHolder holder = (PresenterViewHolder) viewHolder;
        ArrayList<Cast> cast = ((CastList) item).getCasts();
        holder.bind(cast);
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
     * Inner class is contains a {@link Cast} views of the element
     * and it's properties
     */
    class PresenterViewHolder extends ViewHolder {

        @BindView(R.id.cast_TextView)
        TextView castTextView;
        @BindView(R.id.cast_actors_grid_view)
        HorizontalGridView hGridView;

        CastHorizontalGridPresenter castPresenter;

        /**
         * Inner class constructor
         * @param view The initialized element
         */
        public PresenterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
        /**
         * Initializer function for one element
         * @param cast {@link ArrayList} that contains the {@link Cast}
         *                              objects which will be initialize
         */
        public void bind(ArrayList<Cast> cast) {

            if (!cast.isEmpty()) {
                castPresenter = new CastHorizontalGridPresenter();

                ArrayObjectAdapter objectAdapter = new ArrayObjectAdapter();
                for (Cast it : cast) {
                    objectAdapter.add(it);
                }

                SinglePresenterSelector presenterSelector = new SinglePresenterSelector(castPresenter);

                ItemBridgeAdapter itemBridgeAdapter = new ItemBridgeAdapter(objectAdapter, presenterSelector);

                hGridView.setAdapter(itemBridgeAdapter);

                castTextView.setText(R.string.cast);
                Log.e(TAG, "cast " + cast.size());
                view.setVisibility(View.VISIBLE);
            } else {
                Log.e(TAG, "no cast list");
                castTextView.setVisibility(View.GONE);
                hGridView.setVisibility(View.GONE);
            }
        }
    }
}
