package com.devnup.artcatalog.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.devnup.artcatalog.R;
import com.devnup.artcatalog.ws.FreebaseUtil;
import com.devnup.artcatalog.ws.model.VisualArtistModel;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * @author luiseduardobrito
 * @since 12/9/14.
 */
@EViewGroup(R.layout.view_artist_info)
public class ArtistProfileInfoView extends FrameLayout {

    VisualArtistModel artist;

    @ViewById(R.id.profile_picture)
    ImageView mImageView;

    @ViewById(R.id.profile_name)
    TextView mTextView;

    public ArtistProfileInfoView(Context context) {
        super(context);
    }

    public ArtistProfileInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ArtistProfileInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public VisualArtistModel getArtist() {
        return artist;
    }

    public void setArtist(VisualArtistModel artist) {

        if (artist != null) {
            this.artist = artist;
            mTextView.setText(artist.getName());

            if (artist.getImage() != null && artist.getImage().size() > 0) {
                Picasso
                        .with(getContext())
                        .load(FreebaseUtil.getImageURL(artist.getImage().get(0).getId()))
                        .fit()
                        .centerCrop()
                        .into(mImageView);

            }
        }
    }
}
