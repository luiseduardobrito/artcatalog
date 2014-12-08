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
import android.widget.ListView;
import android.widget.Toast;

import com.devnup.artcatalog.R;
import com.devnup.artcatalog.view.CardListAdapter;
import com.devnup.artcatalog.view.SampleCardView;
import com.devnup.artcatalog.view.SampleCardView_;
import com.devnup.artcatalog.ws.model.VisualArtistModel;
import com.devnup.artcatalog.ws.response.MQLReadResponse;
import com.devnup.artcatalog.ws.service.ArtRestTemplate;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_home)
public class HomeActivity extends BaseActivity {

    @RestService
    ArtRestTemplate rest;

    @ViewById(R.id.card_list)
    ListView mCardListView;

    @Override
    @AfterViews
    void init() {
        super.init();
        performQuery("[{" +
                "\"type\": \"/visual_art/visual_artist\",   " +
                "\"id\": null," +
                "\"name\": null," +
                "\"/common/topic/image\" : [{" +
                "  \"id\" : null," +
                "  \"mid\": null" +
                "}]}]");
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
        notifyResult(rest.readUsingMQL(mql));
    }

    @UiThread
    void notifyResult(MQLReadResponse response) {

        Toast.makeText(this, String.valueOf(response.getResult().size()).concat(" artist(s) found."), Toast.LENGTH_SHORT).show();
        List<CardView> cardList = new ArrayList<CardView>();

        for (VisualArtistModel artist : response.getResult()) {
            SampleCardView card = SampleCardView_.build(this);
            card.setTitle(artist.getName());

            if (artist.getImage() != null && artist.getImage().size() > 0) {
                String image_id = artist.getImage().get(0).getId();
                card.setImageUrl("https://usercontent.googleapis.com/freebase/v1/image" + image_id + "?maxwidth=225&maxheight=225&mode=fillcropmid");
            }
            cardList.add(card);
        }

        mCardListView.setAdapter(new CardListAdapter(this, cardList));
    }
}