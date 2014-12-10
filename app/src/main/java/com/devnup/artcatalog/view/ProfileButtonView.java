package com.devnup.artcatalog.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.devnup.artcatalog.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * @author luiseduardobrito
 * @since 12/10/14.
 */
@EViewGroup(R.layout.view_profile_button)
public class ProfileButtonView extends RelativeLayout {

    @ViewById(R.id.image)
    ImageView mImageView;

    @ViewById(R.id.label)
    TextView mTextView;

    public ProfileButtonView(Context context) {
        super(context);
    }

    public ProfileButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProfileButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @AfterViews
    void init() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            }
        });
    }

    public void setText(String text) {
        this.mTextView.setText(text);
    }

    public void setImage(int res) {
        this.mImageView.setImageResource(res);
    }

    public void setImage(Drawable drawable) {
        this.mImageView.setImageDrawable(drawable);
    }
}
