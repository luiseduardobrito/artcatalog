package com.devnup.artcatalog.activity.profile.data;

import android.content.Context;
import android.view.View;

import com.devnup.artcatalog.R;
import com.devnup.artcatalog.activity.ProfileActivity;
import com.devnup.artcatalog.activity.ProfileActivity_;
import com.devnup.artcatalog.activity.profile.ProfileInfoData;
import com.devnup.artcatalog.view.card.ContainedCardView;
import com.devnup.artcatalog.view.card.ContainedCardView_;
import com.devnup.artcatalog.ws.FreebaseUtil;
import com.devnup.artcatalog.ws.model.FreebaseLocationReferenceModel;
import com.devnup.artcatalog.ws.model.FreebaseReferenceModel;
import com.devnup.artcatalog.ws.model.VisualArtworkModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luiseduardobrito
 * @since 12/11/14.
 */
public class ArtworkProfileInfoData extends ProfileInfoData {

    private Context context;
    private final VisualArtworkModel artwork;

    public ArtworkProfileInfoData(Context context, VisualArtworkModel artwork) {
        this.context = context;
        this.artwork = artwork;
    }

    @Override
    public String getMid() {
        return artwork.getMid();
    }

    @Override
    public String getWikipediaId() {

        List<String> wiki = artwork.getWikipedia();

        if (wiki != null && wiki.size() > 0) {
            return wiki.get(0);
        }

        return null;
    }

    @Override
    public String getTitle() {
        return artwork.getName();
    }

    @Override
    public String getSubtitle() {

        if (artwork.getArtist() != null && artwork.getArtist().size() > 0) {
            return artwork.getArtist().get(0).getName();
        }

        return null;
    }

    @Override
    public String getImageURL() {
        return FreebaseUtil.getImageURL(artwork.getMid());
    }

    @Override
    public List<String> getShowcaseImagesURL() {
        List<String> result = new ArrayList<>();
        result.add(FreebaseUtil.getImageURL(artwork.getMid()));
        return result;
    }

    @Override
    public String getDescription() {

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

        return subtitleStr.toString();
    }

    @Override
    public String[] getSectionTitles() {
        return context.getResources().getStringArray(R.array.artwork_profile_sections);
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

        List<FreebaseReferenceModel> periods = artwork.getPeriod();
        List<FreebaseReferenceModel> subjects = artwork.getSubjects();
        List<FreebaseLocationReferenceModel> locations = artwork.getLocations();

        // Artwork periods
        if (section == 1 && periods != null && periods.size() > position) {

            final String mid = periods.get(position).getMid();

            containedCardView.setTitle(periods.get(position).getName());
            containedCardView.setImageUrl(FreebaseUtil.getImageURL(mid));

            containedCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProfileActivity_
                            .intent(getContext())
                            .type(ProfileActivity.Type.ART_PERIOD)
                            .mid(mid)
                            .start();
                }
            });

        }

        // Artwork locations
        else if (section == 2 && locations != null && locations.size() > position && locations.get(position).getLocation() != null) {

            containedCardView.setTitle(locations.get(position).getLocation().get(0).getName());
            containedCardView.setImageUrl(FreebaseUtil.getImageURL(locations.get(position).getLocation().get(0).getMid()));

        }

        // Artwork subjects
        else if (section == 3 && subjects != null && subjects.size() > position) {

            containedCardView.setTitle(subjects.get(position).getName());
            containedCardView.setImageUrl(FreebaseUtil.getImageURL(subjects.get(position).getMid()));

        }

        // Oops, card failed
        else {
            containedCardView.setVisibility(View.INVISIBLE);
        }

        return containedCardView;
    }
}
