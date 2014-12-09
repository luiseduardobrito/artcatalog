package com.devnup.artcatalog.view.list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.devnup.artcatalog.R;
import com.devnup.artcatalog.view.ArtistProfileInfoView;
import com.devnup.artcatalog.view.ArtistProfileInfoView_;
import com.devnup.artcatalog.view.card.CardContainerView;
import com.devnup.artcatalog.view.card.CardContainerView_;
import com.devnup.artcatalog.view.card.ContainedCardView;
import com.devnup.artcatalog.view.card.ContainedCardView_;
import com.devnup.artcatalog.ws.FreebaseUtil;
import com.devnup.artcatalog.ws.model.FreebaseReferenceModel;
import com.devnup.artcatalog.ws.model.VisualArtistModel;
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

    @StringArrayRes(R.array.artist_profile_sections)
    String[] titles;

    VisualArtistModel artist;

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
    public void setArtist(VisualArtistModel artist) {

        this.artist = artist;

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
                    return getArtistProfileInfoView(convertView);
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

        for (int i = 0; i < 3; i++) {
            cards.add(getContainedCardView(position, i));
        }

        view.setTitle(titles[position - 1]);
        view.setCardList(cards);
        return view;
    }

    private ContainedCardView getContainedCardView(int section, int position) {

        ContainedCardView containedCardView = ContainedCardView_.build(getContext());
        containedCardView.setVisibility(View.VISIBLE);

        // Art Forms Section
        List<VisualArtworkModel> artworks = artist.getArtworks();
        List<FreebaseReferenceModel> artForms = artist.getArtForms();
        List<FreebaseReferenceModel> periods = artist.getPeriods();

        // Fill Artwork cards
        if (section == 1 && artworks != null && artworks.size() > position) {

            containedCardView.setTitle(artworks.get(position).getName());
            containedCardView.setImageUrl(FreebaseUtil.getImageURL(artworks.get(position).getMid()));

        }

        // Fill Periods cards
        else if (section == 2 && periods != null && periods.size() > position) {

            containedCardView.setTitle(periods.get(position).getName());
            containedCardView.setImageUrl(FreebaseUtil.getImageURL(periods.get(position).getMid()));

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

    private ArtistProfileInfoView getArtistProfileInfoView(View convertView) {

        ArtistProfileInfoView view;

        if (convertView != null && convertView instanceof ArtistProfileInfoView) {
            view = (ArtistProfileInfoView) convertView;
        } else {
            view = ArtistProfileInfoView_.build(getContext());
        }

        view.setArtist(ArtworkProfileListView.this.artist);
        return view;
    }

    public VisualArtistModel getArtist() {
        return this.artist;
    }
}