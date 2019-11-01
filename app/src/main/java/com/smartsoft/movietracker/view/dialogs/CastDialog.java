package com.smartsoft.movietracker.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.model.cast.Cast;
import com.smartsoft.movietracker.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This dialog is used for hold the {@link Cast}
 * member name and her/his picture
 * on the {@link com.smartsoft.movietracker.view.detail.DetailPageFragment}
 */

public class CastDialog {

    /**
     * Actor/Actress picture
     */
    @BindView(R.id.cast_dialog_image)
    ImageView poster;

    /**
     * Actor/actress name
     */
    @BindView(R.id.cast_dialog_name)
    TextView name;

    /**
     * Close Button for to dismiss the {@link CastDialog}
     */
    @BindView(R.id.cast_dialog_close)
    ImageView closeImageView;

    /**
     * Where running the app
     */
    private Context context;

    /**
     * {@link Cast} member model
     */
    private Cast cast;

    /**
     * Class constructor
     * The app here sets the {@link #context}
     * the {@link #cast}, and starts the {@link CastDialog}
     *
     * @param context Where running the app
     * @param cast    {@link Cast} member
     */
    public CastDialog(Context context, Cast cast) {
        this.context = context;
        this.cast = cast;
        startCastDialog();
    }

    /**
     * Here makes the app the {@link Dialog} from the {@link #context}
     * and set it's content view from an XML file and calls the initializer function
     * @see #initViews(Dialog)
     */
    private void startCastDialog() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.cast_dialog);

        initViews(dialog);

    }

    /**
     * This function set all the {@link CastDialog}'s views
     * their properties and so on..
     * @param dialog {@link Cast} dialog what will be appeared on the UI
     */
    private void initViews(Dialog dialog) {
        ButterKnife.bind(this, dialog);

        name.setText(cast.getName());

        Glide.with(context).load(Constant.API.IMAGE_ORIGINAL_BASE_URL + cast.getProfilePath()).error(R.mipmap.unkown_person_round_v2_legacy).into(poster);

        closeImageView.setOnClickListener(view -> dialog.dismiss());

        dialog.show();


    }
}
