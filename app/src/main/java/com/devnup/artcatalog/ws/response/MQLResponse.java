package com.devnup.artcatalog.ws.response;

import com.devnup.artcatalog.ws.model.BaseModel;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * @author luiseduardobrito
 * @since 12/7/14.
 */
public class MQLResponse<T extends BaseModel> extends BaseResponse {

    @JsonProperty("result")
    List<T> result;

    @JsonProperty("cursor")
    String cursor;

    public MQLResponse() {
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }
}
