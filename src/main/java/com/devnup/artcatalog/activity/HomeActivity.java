/*
 * Copyright (C) 2014 Antonio Leiva Gordillo.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.devnup.artcatalog.activity;

import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.devnup.artcatalog.R;
import com.devnup.artcatalog.view.CardListAdapter;
import com.devnup.artcatalog.view.CardListView;
import com.devnup.artcatalog.view.FeaturedCardView;
import com.devnup.artcatalog.view.FeaturedCardView_;
import com.devnup.artcatalog.ws.model.VisualArtistModel;
import com.devnup.artcatalog.ws.response.MQLReadResponse;
import com.devnup.artcatalog.ws.service.ArtRestService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_home)
public class HomeActivity extends BaseActivity {

    List<CardView> cardList = new ArrayList<CardView>();

    private static final String QUERY = "[{" +
            "\"type\": \"/visual_art/visual_artist\",   " +
            "\"id\": null," +
            "\"name\": null," +
            "\"limit\": 20," +
            "\"/common/topic/image\" : [{" +
            "  \"id\" : null," +
            "  \"mid\": null" +
            "}]}]";

    @Bean
    ArtRestService rest;

    @ViewById(R.id.card_list)
    CardListView mCardListView;

    boolean loading = false;

    @Override
    @AfterViews
    void init() {
        super.init();
        performQuery(QUERY);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_home;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Background
    void performQuery(String mql) {
        loading = true;
        notifyResult(rest.readUsingMQL(mql, true));
    }

    @UiThread
    void notifyResult(MQLReadResponse response) {

        loading = false;
        boolean first = false;

        if (cardList.size() < 1) {
            first = true;
        }

        Toast.makeText(this, String.valueOf(response.getResult().size()).concat(" artist(s) found."), Toast.LENGTH_SHORT).show();

        for (VisualArtistModel artist : response.getResult()) {
            FeaturedCardView card = FeaturedCardView_.build(this);
            card.setTitle(artist.getName());

            if (artist.getImage() != null && artist.getImage().size() > 0) {
                String image_id = artist.getImage().get(0).getId();
                card.setImageUrl("https://usercontent.googleapis.com/freebase/v1/image" + image_id + "?maxwidth=225&maxheight=225&mode=fillcropmid");
            }
            cardList.add(card);
        }

        if (first) {
            mCardListView.setAdapter(new CardListAdapter(this, cardList));
            mCardListView.setEndlessScrollListener(new CardListView.EndlessScrollListener() {

                @Override
                public void onLoadMore(int page, int totalItemsCount) {
                    if (!loading) {
                        loading = true;
                        Toast.makeText(HomeActivity.this, "Loading more...", Toast.LENGTH_SHORT).show();
                        performQuery(QUERY);
                    }
                }
            });

        } else if (mCardListView.getAdapter() instanceof CardListAdapter) {
            ((CardListAdapter) mCardListView.getAdapter()).setCardList(cardList);
        }
    }
}