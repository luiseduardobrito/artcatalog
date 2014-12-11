package com.devnup.artcatalog.component.profile.impl;

import android.view.View;

import com.devnup.artcatalog.R;
import com.devnup.artcatalog.component.profile.ProfileActivity;
import com.devnup.artcatalog.component.profile.ProfileInfoData;
import com.devnup.artcatalog.ws.model.BaseModel;
import com.devnup.artcatalog.ws.model.VisualArtistModel;
import com.devnup.artcatalog.ws.service.ArtRestService;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;

/**
 * @author luiseduardobrito
 * @since 12/11/14.
 */
@EActivity(R.layout.activity_artist)
public class ArtistProfileActivity extends ProfileActivity {

    @Extra
    String mid;

    @Bean
    ArtRestService rest;

    ProfileInfoData data;

    VisualArtistModel artist;

    @Override
    @Background
    protected void getDataForInit(String mid) {
        notifyDataForInit(rest.getVisualArtist(mid));
    }

    @Override
    protected ProfileInfoData getData() {
        return data;
    }

    @Override
    protected String getMid() {
        return mid;
    }

    @UiThread
    protected void notifyDataForInit(BaseModel model) {

        mProgressBar.setVisibility(View.GONE);

        if (model != null && model instanceof VisualArtistModel) {

            artist = (VisualArtistModel) model;
            data = new ArtistProfileInfoData(this, artist);

            notifyDataIsReady();
        }
    }
}
