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

import android.app.SearchManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.devnup.artcatalog.R;
import com.devnup.artcatalog.activity.base.BaseDrawerActivity;
import com.devnup.artcatalog.view.card.FeaturedCardView;
import com.devnup.artcatalog.view.card.FeaturedCardView_;
import com.devnup.artcatalog.view.list.CardListView;
import com.devnup.artcatalog.view.list.adapter.CardListAdapter;
import com.devnup.artcatalog.ws.FreebaseUtil;
import com.devnup.artcatalog.ws.model.VisualArtistModel;
import com.devnup.artcatalog.ws.response.MQLResponse;
import com.devnup.artcatalog.ws.service.ArtRestService;
import com.jpardogo.android.googleprogressbar.library.FoldingCirclesDrawable;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@OptionsMenu(R.menu.main)
@EActivity(R.layout.activity_home)
public class HomeActivity extends BaseDrawerActivity {

    List<CardView> cardList = new ArrayList<CardView>();

    @Bean
    ArtRestService rest;

    @ViewById(R.id.card_list)
    CardListView mCardListView;

    @SystemService
    SearchManager searchManager;

    @ViewById(R.id.google_progress)
    ProgressBar mProgressBar;

    boolean loading = false;

    @AfterViews
    void init() {

        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setIndeterminateDrawable(new FoldingCirclesDrawable.Builder(this).build());

        performQuery(FreebaseUtil.getQuery(), true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Associate searchable configuration with the SearchView
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Background
    void performQuery(String mql) {
        performQuery(mql, false);
    }

    @Background
    void performQuery(String mql, Boolean fromStart) {
        if (!loading) {
            loading = true;
            notifyResult(rest.readUsingMQL(mql, !fromStart));
        }
    }

    @UiThread
    void notifyResult(MQLResponse<VisualArtistModel> response) {

        if (loading) {

            mProgressBar.setVisibility(View.GONE);

            loading = false;
            boolean first = false;

            if (cardList.size() < 1) {
                first = true;
            }

            Toast.makeText(this, String.valueOf(response.getResult().size()).concat(" artist(s) found."), Toast.LENGTH_SHORT).show();

            for (VisualArtistModel artist : response.getResult()) {

                FeaturedCardView card = FeaturedCardView_.build(this);

                card.setTitle(artist.getName());
                card.setImageUrl(FreebaseUtil.getImageURL(artist.getMid()));

                final String mid = artist.getMid();

                card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ProfileActivity_
                                .intent(HomeActivity.this)
                                .type(ProfileActivity.Type.ARTIST)
                                .mid(mid)
                                .start();

                        //ArtistProfileActivity_.intent(HomeActivity.this).mid(mid).start();
                    }
                });

                cardList.add(card);
            }

            if (first) {
                mCardListView.setAdapter(new CardListAdapter(this, cardList));
                mCardListView.setEndlessScrollListener(new CardListView.EndlessScrollListener() {

                    @Override
                    public void onLoadMore(int page, int totalItemsCount) {
                        if (!loading) {
                            Toast.makeText(HomeActivity.this, "Loading more...", Toast.LENGTH_SHORT).show();
                            performQuery(FreebaseUtil.getQuery());
                        }
                    }
                });

            } else if (mCardListView.getAdapter() instanceof CardListAdapter) {
                ((CardListAdapter) mCardListView.getAdapter()).setCardList(cardList);
            }
        }
    }
}