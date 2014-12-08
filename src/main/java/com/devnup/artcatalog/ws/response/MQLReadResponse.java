package com.devnup.artcatalog.ws.response;

import com.devnup.artcatalog.ws.model.VisualArtistModel;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * @author luiseduardobrito
 * @since 12/7/14.
 */
public class MQLReadResponse extends BaseResponse {

    @JsonProperty("result")
    List<VisualArtistModel> result;

    MQLReadResponse() {
    }

    public List<VisualArtistModel> getResult() {
        return result;
    }

    public void setResult(List<VisualArtistModel> result) {
        this.result = result;
    }
}
