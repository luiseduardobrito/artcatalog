package com.devnup.artcatalog.ws.model;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author luiseduardobrito
 * @since 12/8/14.
 */
public class ImageModel extends BaseModel {

    @JsonProperty("id")
    String id;

    @JsonProperty("mid")
    String mid;

    ImageModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
}
