package com.devnup.artcatalog.ws.service;

import com.devnup.artcatalog.ws.response.MQLReadResponse;
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

    public MQLReadResponse readUsingMQL(String query) {

        // Perform API query to check response cursor
        MQLReadResponse response = rest.readUsingMQL(query);

        // Update cache cursor
        cacheCursor = (response != null ? response.getCursor() : null);

        // Return query response
        return response;
    }

    public MQLReadResponse readUsingMQL(String query, Boolean loadMore) {

        if (!loadMore || cacheCursor == null || cacheCursor.isEmpty()) {
            return readUsingMQL(query);
        }

        // Perform API query to check response cursor
        MQLReadResponse response = rest.readUsingMQL(query, cacheCursor);

        // Update cache cursor
        cacheCursor = (response != null ? response.getCursor() : null);

        // Return query response
        return response;
    }
}
