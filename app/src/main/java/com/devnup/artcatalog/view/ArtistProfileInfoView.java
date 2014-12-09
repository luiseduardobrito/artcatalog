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

import java.text.SimpleDateFormat;

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
    TextView mNameView;

    @ViewById(R.id.profile_nationality)
    TextView mNationalityView;

    @ViewById(R.id.profile_birth)
    TextView mBirthView;

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

            mNameView.setText(artist.getName());

            if (artist.getBirthDate() != null) {
                SimpleDateFormat simple = new SimpleDateFormat("dd/MM/yyyy");
                mBirthView.setText(simple.format(artist.getBirthDate()));
            }

            if (artist.getImage() != null && artist.getImage().size() > 0) {
                Picasso
                        .with(getContext())
                        .load(FreebaseUtil.getImageURL(artist.getImage().get(0).getId()))
                        .fit()
                        .centerCrop()
                        .into(mImageView);

            }

            StringBuilder placeStr = new StringBuilder();

            if (artist.getPlaceOfBirth() != null && artist.getPlaceOfBirth().size() > 0) {
                String p = artist.getPlaceOfBirth().get(0).getName();
                placeStr.append(p);
            }

            if (artist.getNationality() != null && artist.getNationality().size() > 0) {
                String n = artist.getNationality().get(0).getName();
                placeStr.append(placeStr.length() > 0 ? ", " + n : n);
            }

            mNationalityView.setText(placeStr.toString());
        }
    }
}
