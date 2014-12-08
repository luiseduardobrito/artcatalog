package com.devnup.artcatalog.ws.service;

import com.devnup.artcatalog.ws.response.MQLReadResponse;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

/**
 * @author luiseduardobrito
 * @since 12/7/14.
 */
@Rest(converters = {MappingJacksonHttpMessageConverter.class}, rootUrl = "")
public interface ArtRestTemplate {

    @Get("https://www.googleapis.com/freebase/v1/mqlread?query={query}")
    public MQLReadResponse readUsingMQL(String query);
}
