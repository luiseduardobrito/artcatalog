package com.devnup.artcatalog.view.list;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.devnup.artcatalog.R;
import com.devnup.artcatalog.view.ArtworkProfileInfoView;
import com.devnup.artcatalog.view.ArtworkProfileInfoView_;
import com.devnup.artcatalog.view.card.CardContainerView;
import com.devnup.artcatalog.view.card.CardContainerView_;
import com.devnup.artcatalog.view.card.ContainedCardView;
import com.devnup.artcatalog.view.card.ContainedCardView_;
import com.devnup.artcatalog.ws.FreebaseUtil;
import com.devnup.artcatalog.ws.model.FreebaseLocationReferenceModel;
import com.devnup.artcatalog.ws.model.FreebaseReferenceModel;
import com.devnup.artcatalog.ws.model.VisualArtworkModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EView;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.res.StringArrayRes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luiseduardobrito
 * @since 12/9/14.
 */
@EView
public class ArtworkProfileListView extends ListView {

    @StringArrayRes(R.array.artwork_profile_sections)
    String[] titles;

    VisualArtworkModel artwork;

    public ArtworkProfileListView(Context context) {
        super(context);
    }

    public ArtworkProfileListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ArtworkProfileListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @AfterViews
    void initViews() {
        setDividerHeight(0);
        setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    @UiThread
    public void setArtwork(VisualArtworkModel artwork) {

        this.artwork = artwork;

        setAdapter(new BaseAdapter() {

            @Override
            public int getCount() {
                return titles.length + 1;
            }

            @Override
            public Object getItem(int position) {
                return titles[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if (position == 0) {
                    return getArtworkProfileInfoView(convertView);
                } else {
                    return getCardContainer(position, convertView);
                }

            }
        });
    }

    private CardContainerView getCardContainer(int position, View convertView) {

        CardContainerView view;

        if (convertView != null && convertView instanceof CardContainerView) {
            view = (CardContainerView) convertView;
        } else {
            view = CardContainerView_.build(getContext());
        }

        List<ContainedCardView> cards = new ArrayList<>();
        Boolean atLeastOne = false;

        for (int i = 0; i < 3; i++) {

            ContainedCardView card = getContainedCardView(position, i);

            if(card.getVisibility() == View.VISIBLE) {
                atLeastOne = true;
            }

            cards.add(card);
        }

        view.setTitle(titles[position - 1]);
        view.setCardList(cards);

        if(!atLeastOne) {
            this.setVisibility(GONE);
        }

        return view;
    }

    private ContainedCardView getContainedCardView(int section, int position) {

        ContainedCardView containedCardView = ContainedCardView_.build(getContext());
        containedCardView.setVisibility(View.VISIBLE);
        containedCardView.setClickable(true);

        List<FreebaseReferenceModel> periods = artwork.getPeriod();
        List<FreebaseReferenceModel> subjects = artwork.getSubjects();
        List<FreebaseLocationReferenceModel> locations = artwork.getLocations();

        // Artwork periods
        if (section == 1 && periods != null && periods.size() > position) {

            containedCardView.setTitle(periods.get(position).getName());
            containedCardView.setImageUrl(FreebaseUtil.getImageURL(periods.get(position).getMid()));

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

    private ArtworkProfileInfoView getArtworkProfileInfoView(View convertView) {

        ArtworkProfileInfoView view;

        if (convertView != null && convertView instanceof ArtworkProfileInfoView) {
            view = (ArtworkProfileInfoView) convertView;
        } else {
            view = ArtworkProfileInfoView_.build(getContext());
        }

        view.setArtwork(ArtworkProfileListView.this.artwork);
        return view;
    }

    public VisualArtworkModel getArtwork() {
        return this.artwork;
    }
}
