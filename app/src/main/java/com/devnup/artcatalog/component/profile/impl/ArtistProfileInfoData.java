package com.devnup.artcatalog.component.profile.impl;

import android.content.Context;
import android.view.View;

import com.devnup.artcatalog.R;
import com.devnup.artcatalog.activity.ArtPeriodActivity_;
import com.devnup.artcatalog.activity.ArtworkActivity_;
import com.devnup.artcatalog.component.profile.ProfileInfoData;
import com.devnup.artcatalog.view.card.CardContainerView;
import com.devnup.artcatalog.view.card.CardContainerView_;
import com.devnup.artcatalog.view.card.ContainedCardView;
import com.devnup.artcatalog.view.card.ContainedCardView_;
import com.devnup.artcatalog.ws.FreebaseUtil;
import com.devnup.artcatalog.ws.model.FreebaseReferenceModel;
import com.devnup.artcatalog.ws.model.VisualArtistModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luiseduardobrito
 * @since 12/11/14.
 */
public class ArtistProfileInfoData extends ProfileInfoData {

    private Context context;
    private final VisualArtistModel artist;

    public ArtistProfileInfoData(Context context, VisualArtistModel artist) {
        this.context = context;
        this.artist = artist;
    }

    @Override
    public String getTitle() {
        return artist.getName();
    }

    @Override
    public String getSubtitle() {

        if (artist.getBirthDate() != null) {
            SimpleDateFormat simple = new SimpleDateFormat("dd/MM/yyyy");
            return simple.format(artist.getBirthDate());
        }

        return null;
    }

    @Override
    public String getImageURL() {
        return FreebaseUtil.getImageURL(artist.getMid());
    }

    @Override
    public List<String> getShowcaseImagesURL() {

        List<String> result = new ArrayList<>();
        List<FreebaseReferenceModel> images = artist.getImage();

        if (images != null && images.size() > 0) {

            if (images.size() > 1) {
                images.remove(0);
            }

            for (FreebaseReferenceModel image : images) {
                result.add(FreebaseUtil.getImageURL(images.get(0).getMid()));
            }
        }

        return result;
    }

    @Override
    public String getDescription() {
        StringBuilder placeStr = new StringBuilder();

        if (artist.getPlaceOfBirth() != null && artist.getPlaceOfBirth().size() > 0) {
            String p = artist.getPlaceOfBirth().get(0).getName();
            placeStr.append(p);
        }

        if (artist.getNationality() != null && artist.getNationality().size() > 0) {
            String n = artist.getNationality().get(0).getName();
            placeStr.append(placeStr.length() > 0 ? ", " + n : n);
        }

        return placeStr.toString();
    }

    @Override
    public String[] getSectionTitles() {
        return context.getResources().getStringArray(R.array.artist_profile_sections);
    }

    @Override
    public CardContainerView getCardContainer(int position, View convertView) {

        CardContainerView view;

        if (convertView != null && convertView instanceof CardContainerView) {
            view = (CardContainerView) convertView;
        } else {
            view = CardContainerView_.build(context);
        }

        List<ContainedCardView> cards = new ArrayList<>();
        Boolean atLeastOne = false;

        for (int i = 0; i < 3; i++) {

            ContainedCardView card = getContainedCard(position, i);

            if (card.getVisibility() == View.VISIBLE) {
                atLeastOne = true;
            }

            cards.add(card);
        }

        view.setTitle(getSectionTitles()[position - 1]);
        view.setCardList(cards);

        if (!atLeastOne) {
            return null;
        }

        return view;
    }

    @Override
    public ContainedCardView getContainedCard(int section, int position) {

        ContainedCardView containedCardView = ContainedCardView_.build(context);
        containedCardView.setVisibility(View.VISIBLE);
        containedCardView.setClickable(true);

        // Art Forms Section
        List<FreebaseReferenceModel> artForms = artist.getArtForms();
        List<FreebaseReferenceModel> artworks = artist.getArtworks();
        List<FreebaseReferenceModel> periods = artist.getPeriods();

        // Fill Artwork cards
        if (section == 1 && artworks != null && artworks.size() > position) {

            final String mid = artworks.get(position).getMid();

            containedCardView.setTitle(artworks.get(position).getName());
            containedCardView.setImageUrl(FreebaseUtil.getImageURL(mid));

            containedCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArtworkActivity_.intent(context).mid(mid).start();
                }
            });

        }

        // Fill Periods cards
        else if (section == 2 && periods != null && periods.size() > position) {

            final String mid = periods.get(position).getMid();

            containedCardView.setTitle(periods.get(position).getName());
            containedCardView.setImageUrl(FreebaseUtil.getImageURL(mid));

            containedCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArtPeriodActivity_.intent(context).mid(mid).start();
                }
            });
        }

        // Fill Art Form cards
        else if (section == 3 && artForms != null && artForms.size() > position) {

            containedCardView.setTitle(artForms.get(position).getName());
            containedCardView.setImageUrl(FreebaseUtil.getImageURL(artForms.get(position).getMid()));

        } else {

            containedCardView.setVisibility(View.INVISIBLE);
        }

        return containedCardView;
    }
}
