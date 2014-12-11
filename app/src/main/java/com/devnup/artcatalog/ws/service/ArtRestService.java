package com.devnup.artcatalog.ws.service;

import com.devnup.artcatalog.ws.model.BaseModel;
import com.devnup.artcatalog.ws.model.VisualArtFormModel;
import com.devnup.artcatalog.ws.model.VisualArtPeriodModel;
import com.devnup.artcatalog.ws.model.VisualArtistModel;
import com.devnup.artcatalog.ws.model.VisualArtworkModel;
import com.devnup.artcatalog.ws.response.MQLResponse;
import com.devnup.artcatalog.ws.template.ArtRestTemplate;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.rest.RestService;

/**
 * @author luiseduardobrito
 * @since 12/8/14.
 */
@EBean(scope = EBean.Scope.Singleton)
public class ArtRestService {

    @RestService
    ArtRestTemplate rest;

    public String cacheCursor;

    public MQLResponse<VisualArtistModel> readUsingMQL(String query) {

        // Perform API query to check response cursor
        MQLResponse<VisualArtistModel> response = rest.readUsingMQL(query);

        // Update cache cursor
        cacheCursor = (response != null ? response.getCursor() : null);

        // Return query response
        return response;
    }

    public MQLResponse<VisualArtistModel> readUsingMQL(String query, Boolean loadMore) {

        if (!loadMore || cacheCursor == null || cacheCursor.isEmpty()) {
            return readUsingMQL(query);
        }

        // Perform API query to check response cursor
        MQLResponse<VisualArtistModel> response = rest.readUsingMQL(query, cacheCursor);

        // Update cache cursor
        cacheCursor = (response != null ? response.getCursor() : null);

        // Return query response
        return response;
    }

    public VisualArtistModel getVisualArtist(String mid) {

        MQLResponse<VisualArtistModel> response = rest.readUsingMQL(VisualArtistModel.toFreebaseQuery(mid));

        if (response != null && response.getResult() != null && response.getResult().size() > 0) {
            return response.getResult().get(0);
        } else {
            return null;
        }
    }

    public VisualArtworkModel getVisualArtwork(String mid) {

        MQLResponse<VisualArtworkModel> response = rest.readArtworksUsingMQL(VisualArtworkModel.toFreebaseQuery(mid));

        if (response != null && response.getResult() != null && response.getResult().size() > 0) {
            return response.getResult().get(0);
        } else {
            return null;
        }
    }

    public VisualArtPeriodModel getVisualArtPeriod(String mid) {

        MQLResponse<VisualArtPeriodModel> response = rest.readArtPeriodsUsingMQL(VisualArtPeriodModel.toFreebaseQuery(mid));

        if (response != null && response.getResult() != null && response.getResult().size() > 0) {
            return response.getResult().get(0);
        } else {
            return null;
        }
    }

    public VisualArtFormModel getVisualArtForm(String mid) {

        MQLResponse<VisualArtFormModel> response = rest.readArtFormsUsingMQL(VisualArtFormModel.toFreebaseQuery(mid));

        if (response != null && response.getResult() != null && response.getResult().size() > 0) {
            return response.getResult().get(0);
        } else {
            return null;
        }
    }
}
