package com.devnup.artcatalog.activity;

import android.view.View;

import com.devnup.artcatalog.R;
import com.devnup.artcatalog.activity.base.BaseProfileActivity;
import com.devnup.artcatalog.activity.profile.ProfileInfoData;
import com.devnup.artcatalog.activity.profile.impl.ArtistProfileInfoData;
import com.devnup.artcatalog.activity.profile.impl.ArtworkProfileInfoData;
import com.devnup.artcatalog.ws.model.BaseModel;
import com.devnup.artcatalog.ws.model.VisualArtistModel;
import com.devnup.artcatalog.ws.model.VisualArtworkModel;
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
@EActivity(R.layout.activity_profile)
public class ArtworkProfileActivity extends BaseProfileActivity {

    @Extra
    String mid;

    @Bean
    ArtRestService rest;

    ProfileInfoData data;

    VisualArtworkModel artwork;

    @Override
    @Background
    protected void getDataForInit(String mid) {
        notifyDataForInit(rest.getVisualArtwork(mid));
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

        if (model != null && model instanceof VisualArtworkModel) {

            artwork = (VisualArtworkModel) model;
            data = new ArtworkProfileInfoData(this, artwork);

            notifyDataIsReady();
        }
    }
}
