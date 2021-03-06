package com.devnup.artcatalog.activity.profile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.devnup.artcatalog.R;
import com.devnup.artcatalog.view.ProfileButtonView;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * @author luiseduardobrito
 * @since 12/9/14.
 */
@EViewGroup(R.layout.view_profile_info)
public class ProfileInfoView extends FrameLayout {

    ProfileInfoData data;

    @ViewById(R.id.profile_picture)
    ImageView mImageView;

    @ViewById(R.id.profile_title)
    TextView mTitleView;

    @ViewById(R.id.profile_subtitle)
    TextView mSubtitleView;

    @ViewById(R.id.profile_description)
    TextView mDescriptionView;

    @ViewById(R.id.profile_button_left)
    ProfileButtonView mProfileLeftButtonView;

    @ViewById(R.id.profile_button_right)
    ProfileButtonView mProfileRightButtonView;

    public ProfileInfoView(Context context) {
        super(context);
    }

    public ProfileInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProfileInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @AfterViews
    void init() {

        mProfileLeftButtonView.setImage(R.drawable.ic_favorite_outline_black_36dp);
        mProfileLeftButtonView.setText("Like");

        mProfileRightButtonView.setImage(R.drawable.ic_open_in_browser_black_36dp);
        mProfileRightButtonView.setText("Wikipedia");
    }

    public void setData(final ProfileInfoData data) {

        if (data != null) {

            this.data = data;

            mTitleView.setText(data.getTitle());

            if (data.getSubtitle() != null) {
                mSubtitleView.setText(data.getSubtitle());
            }

            if (data.getSubtitle() != null) {
                mDescriptionView.setText(data.getDescription());
            }

            if (data.getImageURL() != null && !data.getImageURL().isEmpty()) {

                Picasso
                        .with(getContext())
                        .load(data.getImageURL())
                        .fit()
                        .centerCrop()
                        .into(mImageView);

            }

            if(data.getWikipediaId() != null) {

                mProfileRightButtonView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = "http://en.wikipedia.org/wiki?curid=" + data.getWikipediaId();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        getContext().startActivity(i);
                    }
                });

            } else {

                mProfileRightButtonView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = "http://data.devnup.com/api/wikipedia/redirect?query=" + data.getTitle();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        getContext().startActivity(i);
                    }
                });

            }
        }
    }
}
