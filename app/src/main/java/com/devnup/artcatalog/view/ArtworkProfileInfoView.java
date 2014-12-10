package com.devnup.artcatalog.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.devnup.artcatalog.R;
import com.devnup.artcatalog.ws.FreebaseUtil;
import com.devnup.artcatalog.ws.model.VisualArtworkModel;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;

/**
 * @author luiseduardobrito
 * @since 12/9/14.
 */
@EViewGroup(R.layout.view_artwork_info)
public class ArtworkProfileInfoView extends FrameLayout {

    VisualArtworkModel artwork;

    @ViewById(R.id.profile_picture)
    ImageView mImageView;

    @ViewById(R.id.profile_name)
    TextView mNameView;

    @ViewById(R.id.profile_artist)
    TextView mArtistView;

    @ViewById(R.id.profile_subtitle)
    TextView mSubtitleView;

    public ArtworkProfileInfoView(Context context) {
        super(context);
    }

    public ArtworkProfileInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ArtworkProfileInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setArtwork(VisualArtworkModel artwork) {

        if (artwork != null) {

            this.artwork = artwork;

            mNameView.setText(artwork.getName());

            if (artwork.getArtist() != null && artwork.getArtist().size() > 0) {
                mArtistView.setText(artwork.getArtist().get(0).getName());
            }

            StringBuilder subtitleStr = new StringBuilder();

            if (artwork.getMedia() != null && artwork.getMedia().size() > 0) {
                subtitleStr.append(artwork.getMedia().get(0).getName());
            }

            SimpleDateFormat simple = new SimpleDateFormat("yyyy");

            if (artwork.getDateBegun() != null) {

                subtitleStr
                        .append(subtitleStr.length() > 0 ? " - " : "")
                        .append(artwork.getDateBegun());
            }

            if (artwork.getDateCompleted() != null && artwork.getDateBegun() != artwork.getDateCompleted()) {

                subtitleStr
                        .append(subtitleStr.length() > 0 ? " to " : "")
                        .append(artwork.getDateCompleted());
            }

            mSubtitleView.setText(subtitleStr.toString());

            Picasso
                    .with(getContext())
                    .load(FreebaseUtil.getImageURL(artwork.getMid()))
                    .fit()
                    .centerCrop()
                    .into(mImageView);

        }
    }

    public VisualArtworkModel getArtwork() {
        return artwork;
    }
}
