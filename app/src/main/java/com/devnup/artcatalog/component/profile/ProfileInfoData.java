package com.devnup.artcatalog.component.profile;

import android.content.Context;
import android.view.View;

import com.devnup.artcatalog.view.card.CardContainerView;
import com.devnup.artcatalog.view.card.ContainedCardView;

import java.util.List;

/**
 * @author luiseduardobrito
 * @since 12/10/14.
 */
public abstract class ProfileInfoData {

    public abstract String getTitle();

    public abstract String getSubtitle();

    public abstract String getImageURL();

    public abstract List<String> getShowcaseImagesURL();

    public abstract String getDescription();

    public abstract String[] getSectionTitles();

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

    public abstract CardContainerView getCardContainer(int position, View convertView);

    public abstract ContainedCardView getContainedCard(int section, int position);
}
