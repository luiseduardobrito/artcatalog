package com.devnup.artcatalog.ws.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author luiseduardobrito
 * @since 12/7/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VisualArtworkModel extends BaseModel {

    @JsonProperty("id")
    String id = null;

    @JsonProperty("mid")
    String mid = null;

    @JsonProperty("name")
    String name = null;

    public VisualArtworkModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
}
