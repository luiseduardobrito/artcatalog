package com.devnup.artcatalog.ws.template;

import com.devnup.artcatalog.ws.response.MQLArtPeriodsResponse;
import com.devnup.artcatalog.ws.response.MQLArtistsResponse;
import com.devnup.artcatalog.ws.response.MQLArtworksResponse;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.core.io.Resource;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

/**
 * @author luiseduardobrito
 * @since 12/7/14.
 */
@Rest(converters = {MappingJacksonHttpMessageConverter.class}, rootUrl = "")
public interface ArtRestTemplate {

    @Get("https://usercontent.googleapis.com/freebase/v1/image{id}?maxwidth=225&maxheight=225&mode=fillcropmid")
    public Resource getImage(String id);

    @Get("https://www.googleapis.com/freebase/v1/mqlread?query={query}&cursor")
    public MQLArtistsResponse readUsingMQL(String query);

    @Get("https://www.googleapis.com/freebase/v1/mqlread?query={query}&cursor={cursor}")
    public MQLArtistsResponse readUsingMQL(String query, String cursor);

    @Get("https://www.googleapis.com/freebase/v1/mqlread?query={query}&cursor")
    public MQLArtworksResponse readArtworksUsingMQL(String query);

    @Get("https://www.googleapis.com/freebase/v1/mqlread?query={query}&cursor")
    public MQLArtPeriodsResponse readArtPeriodsUsingMQL(String query);
}
