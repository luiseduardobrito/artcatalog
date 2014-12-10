package com.devnup.artcatalog.ws.response;

import com.devnup.artcatalog.ws.model.VisualArtworkModel;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * @author luiseduardobrito
 * @since 12/7/14.
 */
public class MQLArtworksResponse extends BaseResponse {

    @JsonProperty("result")
    List<VisualArtworkModel> result;

    @JsonProperty("cursor")
    String cursor;

    MQLArtworksResponse() {
    }

    public List<VisualArtworkModel> getResult() {
        return result;
    }

    public void setResult(List<VisualArtworkModel> result) {
        this.result = result;
    }

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }
}
