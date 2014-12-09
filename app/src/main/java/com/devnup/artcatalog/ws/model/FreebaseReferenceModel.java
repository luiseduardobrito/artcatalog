package com.devnup.artcatalog.ws.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author luiseduardobrito
 * @since 12/8/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FreebaseReferenceModel extends BaseModel {

    @JsonProperty("id")
    String id;

    @JsonProperty("mid")
    String mid;

    @JsonProperty("name")
    String name;

    FreebaseReferenceModel() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
