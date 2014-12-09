package com.devnup.artcatalog.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.ImageView;

import com.devnup.artcatalog.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EView;

/**
 * @author luiseduardobrito
 * @since 12/8/14.
 */
@EView
public class FavoriteToggleView extends ImageView {

    Boolean state = false;

    public FavoriteToggleView(Context context) {
        super(context);
    }

    public FavoriteToggleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FavoriteToggleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @AfterViews
    void initViews() {

        this.setClickable(true);
        this.setImageResource(R.drawable.ic_favorite_outline_grey600_36dp);

        this.setClickable(true);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                FavoriteToggleView.this.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY
                );
                state = !state;

                if(state) {
                    FavoriteToggleView.this.setImageResource(R.drawable.ic_favorite_black_36dp);
                } else {
                    FavoriteToggleView.this.setImageResource(R.drawable.ic_favorite_outline_grey600_36dp);
                }
            }
        });
    }


}
