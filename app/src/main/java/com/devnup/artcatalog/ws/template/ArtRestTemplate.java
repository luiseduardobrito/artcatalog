package com.devnup.artcatalog.ws.template;

import com.devnup.artcatalog.ws.response.MQLReadResponse;

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

    @Get("https://www.googleapis.com/freebase/v1/mqlread?query={query}&cursor")
    public MQLReadResponse readUsingMQL(String query);

    @Get("https://www.googleapis.com/freebase/v1/mqlread?query={query}&cursor={cursor}")
    public MQLReadResponse readUsingMQL(String query, String cursor);

    @Get("https://usercontent.googleapis.com/freebase/v1/image{id}?maxwidth=225&maxheight=225&mode=fillcropmid")
    public Resource getImage(String id);


}
