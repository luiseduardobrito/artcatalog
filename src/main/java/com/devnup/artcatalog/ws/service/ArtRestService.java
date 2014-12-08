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
        return rest.readUsingMQL(query);
    }

    public MQLReadResponse readUsingMQL(String query, String cursor) {

        MQLReadResponse response;

        if (cursor == null || !cursor.isEmpty()) {
            response = rest.readUsingMQL(query, "");
        } else {
            response = rest.readUsingMQL(query, cursor);
        }

        // Update cache cursor
        cacheCursor = (response != null ? response.getCursor() : null);

        // Return query response
        return response;
    }

    public MQLReadResponse readUsingMQL(String query, Boolean loadMore) {
        if (!loadMore) {
            return readUsingMQL(query);
        }
        return readUsingMQL(query, cacheCursor);
    }
}
