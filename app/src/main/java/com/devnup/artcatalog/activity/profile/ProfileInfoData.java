package com.devnup.artcatalog.activity.profile;

import android.content.Context;
import android.view.View;

import com.devnup.artcatalog.view.card.CardContainerView;
import com.devnup.artcatalog.view.card.CardContainerView_;
import com.devnup.artcatalog.view.card.ContainedCardView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luiseduardobrito
 * @since 12/10/14.
 */
public abstract class ProfileInfoData {

    public abstract String getMid();

    public abstract String getWikipediaId();

    public abstract String getTitle();

    public abstract String getSubtitle();

    public abstract String getImageURL();

    public abstract List<String> getShowcaseImagesURL();

    public abstract String getDescription();

    public abstract String[] getSectionTitles();

    public abstract Context getContext();

    public ProfileInfoView getInfoView(Context context, View convertView) {

        ProfileInfoView view;

        if (convertView != null && convertView instanceof ProfileInfoView) {
            view = (ProfileInfoView) convertView;
        } else {
            view = ProfileInfoView_.build(context);
        }

        view.setData(this);
        return view;
    }

    public CardContainerView getCardContainer(int position, View convertView) {

        CardContainerView view;

        if (convertView != null && convertView instanceof CardContainerView) {
            view = (CardContainerView) convertView;
        } else {
            view = CardContainerView_.build(getContext());
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


    public abstract ContainedCardView getContainedCard(int section, int position);
}
