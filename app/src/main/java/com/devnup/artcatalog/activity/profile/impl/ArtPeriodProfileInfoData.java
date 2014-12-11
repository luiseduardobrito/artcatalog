package com.devnup.artcatalog.activity.profile.impl;

import android.content.Context;
import android.view.View;

import com.devnup.artcatalog.R;
import com.devnup.artcatalog.activity.ArtistProfileActivity_;
import com.devnup.artcatalog.activity.ArtworkProfileActivity_;
import com.devnup.artcatalog.activity.profile.ProfileInfoData;
import com.devnup.artcatalog.view.card.ContainedCardView;
import com.devnup.artcatalog.view.card.ContainedCardView_;
import com.devnup.artcatalog.ws.FreebaseUtil;
import com.devnup.artcatalog.ws.model.FreebaseReferenceModel;
import com.devnup.artcatalog.ws.model.VisualArtPeriodModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luiseduardobrito
 * @since 12/11/14.
 */
public class ArtPeriodProfileInfoData extends ProfileInfoData {

    private Context context;
    private final VisualArtPeriodModel period;

    public ArtPeriodProfileInfoData(Context context, VisualArtPeriodModel period) {
        this.context = context;
        this.period = period;
    }

    @Override
    public String getMid() {
        return period.getMid();
    }

    @Override
    public String getWikipediaId() {

        List<String> wiki = period.getWikipedia();

        if (wiki != null && wiki.size() > 0) {
            return wiki.get(0);
        }

        return null;
    }

    @Override
    public String getTitle() {
        return period.getName();
    }

    @Override
    public String getSubtitle() {

        StringBuilder subtitleStr = new StringBuilder();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy");

        if (period.getDateBegun() != null) {

            subtitleStr
                    .append(subtitleStr.length() > 0 ? " - " : "")
                    .append(period.getDateBegun());
        }

        if (period.getDateEnded() != null) {

            // TODO: resolver essa porquice
            if (period.getDateBegun() != null && period.getDateBegun().equals(period.getDateEnded())) {
                return subtitleStr.toString();
            }

            subtitleStr
                    .append(subtitleStr.length() > 0 ? " to " : "")
                    .append(period.getDateEnded());
        }

        return subtitleStr.toString();
    }

    @Override
    public String getImageURL() {
        return FreebaseUtil.getImageURL(period.getMid());
    }

    @Override
    public List<String> getShowcaseImagesURL() {

        List<String> result = new ArrayList<>();

        for (FreebaseReferenceModel artwork : period.getArtworks()) {
            result.add(FreebaseUtil.getImageURL(artwork.getMid()));
        }

        return result;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String[] getSectionTitles() {
        return getContext().getResources().getStringArray(R.array.period_profile_sections);
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public ContainedCardView getContainedCard(int section, int position) {

        ContainedCardView containedCardView = ContainedCardView_.build(getContext());
        containedCardView.setVisibility(View.VISIBLE);
        containedCardView.setClickable(true);

        List<FreebaseReferenceModel> artists = period.getArtists();
        List<FreebaseReferenceModel> artworks = period.getArtworks();

        // Period artists
        if (section == 1 && artists != null && artists.size() > position) {

            final String mid = artists.get(position).getMid();

            containedCardView.setTitle(artists.get(position).getName());
            containedCardView.setImageUrl(FreebaseUtil.getImageURL(mid));

            containedCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArtistProfileActivity_.intent(getContext()).mid(mid).start();
                }
            });
        }

        // Period artworks
        else if (section == 2 && artworks != null && artworks.size() > position) {

            final String mid = artworks.get(position).getMid();

            containedCardView.setTitle(artworks.get(position).getName());
            containedCardView.setImageUrl(FreebaseUtil.getImageURL(mid));

            containedCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArtworkProfileActivity_.intent(getContext()).mid(mid).start();
                }
            });

        }

        // Oops, card failed
        else {
            containedCardView.setVisibility(View.INVISIBLE);
        }

        return containedCardView;
    }
}
