package com.devnup.artcatalog.ws.service;

import com.devnup.artcatalog.ws.model.VisualArtistModel;
import com.devnup.artcatalog.ws.model.VisualArtworkModel;
import com.devnup.artcatalog.ws.response.MQLArtistsResponse;
import com.devnup.artcatalog.ws.response.MQLArtworksResponse;
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

    public MQLArtistsResponse readUsingMQL(String query) {

        // Perform API query to check response cursor
        MQLArtistsResponse response = rest.readUsingMQL(query);

        // Update cache cursor
        cacheCursor = (response != null ? response.getCursor() : null);

        // Return query response
        return response;
    }

    public MQLArtistsResponse readUsingMQL(String query, Boolean loadMore) {

        if (!loadMore || cacheCursor == null || cacheCursor.isEmpty()) {
            return readUsingMQL(query);
        }

        // Perform API query to check response cursor
        MQLArtistsResponse response = rest.readUsingMQL(query, cacheCursor);

        // Update cache cursor
        cacheCursor = (response != null ? response.getCursor() : null);

        // Return query response
        return response;
    }

    public VisualArtistModel getVisualArtist(String mid) {

        MQLArtistsResponse response = rest.readUsingMQL(VisualArtistModel.toFreebaseQuery(mid));

        if (response != null && response.getResult() != null && response.getResult().size() > 0) {
            return response.getResult().get(0);
        } else {
            return null;
        }
    }

    public VisualArtworkModel getVisualArtwork(String mid) {

        MQLArtworksResponse response = rest.readArtworksUsingMQL(VisualArtworkModel.toFreebaseQuery(mid));

        if (response != null && response.getResult() != null && response.getResult().size() > 0) {
            return response.getResult().get(0);
        } else {
            return null;
        }
    }
}
