package com.devnup.artcatalog.view.list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.devnup.artcatalog.R;
import com.devnup.artcatalog.activity.ArtistActivity_;
import com.devnup.artcatalog.activity.ArtworkActivity_;
import com.devnup.artcatalog.view.ArtPeriodProfileInfoView;
import com.devnup.artcatalog.view.ArtPeriodProfileInfoView_;
import com.devnup.artcatalog.view.ArtworkProfileInfoView;
import com.devnup.artcatalog.view.card.CardContainerView;
import com.devnup.artcatalog.view.card.CardContainerView_;
import com.devnup.artcatalog.view.card.ContainedCardView;
import com.devnup.artcatalog.view.card.ContainedCardView_;
import com.devnup.artcatalog.ws.FreebaseUtil;
import com.devnup.artcatalog.ws.model.FreebaseReferenceModel;
import com.devnup.artcatalog.ws.model.VisualArtPeriodModel;

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
public class ArtPeriodProfileListView extends ListView {

    @StringArrayRes(R.array.period_profile_sections)
    String[] titles;

    VisualArtPeriodModel period;

    public ArtPeriodProfileListView(Context context) {
        super(context);
    }

    public ArtPeriodProfileListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ArtPeriodProfileListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @AfterViews
    void initViews() {
        setDividerHeight(0);
        setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    @UiThread
    public void setArtPeriod(VisualArtPeriodModel period) {

        this.period = period;

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

                    return getArtPeriodProfileInfoView(convertView);

                } else {

                    CardContainerView containerView = getCardContainer(position, convertView);

                    if (containerView == null) {
                        return new View(getContext());
                    }

                    return containerView;
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

            if (card.getVisibility() == View.VISIBLE) {
                atLeastOne = true;
            }

            cards.add(card);
        }

        view.setTitle(titles[position - 1]);
        view.setCardList(cards);

        if (!atLeastOne) {
            return null;
        }

        return view;
    }

    private ContainedCardView getContainedCardView(int section, int position) {

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

            containedCardView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArtistActivity_.intent(getContext()).mid(mid).start();
                }
            });
        }

        // Period artworks
        else if (section == 2 && artworks != null && artworks.size() > position) {

            final String mid = artworks.get(position).getMid();

            containedCardView.setTitle(artworks.get(position).getName());
            containedCardView.setImageUrl(FreebaseUtil.getImageURL(mid));

            containedCardView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArtworkActivity_.intent(getContext()).mid(mid).start();
                }
            });

        }

        // Oops, card failed
        else {
            containedCardView.setVisibility(View.INVISIBLE);
        }

        return containedCardView;
    }

    private ArtPeriodProfileInfoView getArtPeriodProfileInfoView(View convertView) {

        ArtPeriodProfileInfoView view;

        if (convertView != null && convertView instanceof ArtworkProfileInfoView) {
            view = (ArtPeriodProfileInfoView) convertView;
        } else {
            view = ArtPeriodProfileInfoView_.build(getContext());
        }

        view.setArtPeriod(ArtPeriodProfileListView.this.period);
        return view;
    }

    public VisualArtPeriodModel getArtPeriod() {
        return this.period;
    }
}
