package com.devnup.artcatalog.ws.template;

import com.devnup.artcatalog.ws.model.VisualArtFormModel;
import com.devnup.artcatalog.ws.model.VisualArtPeriodModel;
import com.devnup.artcatalog.ws.model.VisualArtistModel;
import com.devnup.artcatalog.ws.model.VisualArtworkModel;
import com.devnup.artcatalog.ws.response.MQLResponse;

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
    public MQLResponse<VisualArtistModel> readUsingMQL(String query);

    @Get("https://www.googleapis.com/freebase/v1/mqlread?query={query}&cursor={cursor}")
    public MQLResponse<VisualArtistModel> readUsingMQL(String query, String cursor);

    @Get("https://www.googleapis.com/freebase/v1/mqlread?query={query}&cursor")
    public MQLResponse<VisualArtworkModel> readArtworksUsingMQL(String query);

    @Get("https://www.googleapis.com/freebase/v1/mqlread?query={query}&cursor")
    public MQLResponse<VisualArtPeriodModel> readArtPeriodsUsingMQL(String query);

    @Get("https://www.googleapis.com/freebase/v1/mqlread?query={query}&cursor")
    public MQLResponse<VisualArtFormModel> readArtFormsUsingMQL(String query);
}
