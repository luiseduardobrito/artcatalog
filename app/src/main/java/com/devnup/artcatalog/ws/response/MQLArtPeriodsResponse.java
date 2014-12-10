package com.devnup.artcatalog.ws.response;

import com.devnup.artcatalog.ws.model.VisualArtPeriodModel;
import com.devnup.artcatalog.ws.model.VisualArtworkModel;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * @author luiseduardobrito
 * @since 12/7/14.
 */
public class MQLArtPeriodsResponse extends BaseResponse {

    @JsonProperty("result")
    List<VisualArtPeriodModel> result;

    @JsonProperty("cursor")
    String cursor;

    MQLArtPeriodsResponse() {
    }

    public List<VisualArtPeriodModel> getResult() {
        return result;
    }

    public void setResult(List<VisualArtPeriodModel> result) {
        this.result = result;
    }

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }
}
