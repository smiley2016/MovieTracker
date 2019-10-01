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

public class CastDialog {

    private Context context;
    private Cast cast;


    @BindView(R.id.cast_dialog_image)
    ImageView poster;

    @BindView(R.id.cast_dialog_name)
    TextView name;

    @BindView(R.id.cast_dialog_close)
    ImageView closeImageView;

    public CastDialog(Context context, Cast cast) {
        this.context = context;
        this.cast = cast;
    }

    public void startCastDialog() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.cast_dialog);

        initViews(dialog);

    }

    private void initViews(Dialog dialog) {
        ButterKnife.bind(this, dialog);

        name.setText(cast.getName());

        Glide.with(context).load(Constant.API.IMAGE_ORIGINAL_BASE_URL + cast.getProfilePath()).error(R.mipmap.unkown_person_round_v2_legacy).into(poster);

        closeImageView.setOnClickListener(view -> dialog.dismiss());

        dialog.show();


    }
}
