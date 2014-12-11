package com.devnup.artcatalog.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.devnup.artcatalog.R;
import com.devnup.artcatalog.ws.FreebaseUtil;
import com.devnup.artcatalog.ws.model.VisualArtPeriodModel;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;

/**
 * @author luiseduardobrito
 * @since 12/9/14.
 */
@EViewGroup(R.layout.view_artwork_info)
public class ArtPeriodProfileInfoView extends FrameLayout {

    VisualArtPeriodModel period;

    @ViewById(R.id.profile_picture)
    ImageView mImageView;

    @ViewById(R.id.profile_name)
    TextView mNameView;

    @ViewById(R.id.profile_artist)
    TextView mArtistView;

    @ViewById(R.id.profile_subtitle)
    TextView mSubtitleView;

    public ArtPeriodProfileInfoView(Context context) {
        super(context);
    }

    public ArtPeriodProfileInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ArtPeriodProfileInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setArtPeriod(VisualArtPeriodModel period) {

        if (period != null) {

            this.period = period;

            mNameView.setText(period.getName());

            StringBuilder subtitleStr = new StringBuilder();
            SimpleDateFormat simple = new SimpleDateFormat("yyyy");

            if (period.getDateBegun() != null) {

                subtitleStr
                        .append(subtitleStr.length() > 0 ? " - " : "")
                        .append(period.getDateBegun());
            }

            if (period.getDateEnded() != null && !period.getDateBegun().equals(period.getDateEnded())) {

                subtitleStr
                        .append(subtitleStr.length() > 0 ? " to " : "")
                        .append(period.getDateEnded());
            }

            mSubtitleView.setText(subtitleStr.toString());

            Picasso
                    .with(getContext())
                    .load(FreebaseUtil.getImageURL(period.getMid()))
                    .fit()
                    .centerCrop()
                    .into(mImageView);

        }
    }

    public VisualArtPeriodModel getArtPeriod() {
        return period;
    }
}
