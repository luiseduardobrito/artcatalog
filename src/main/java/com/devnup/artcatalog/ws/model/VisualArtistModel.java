package com.devnup.artcatalog.ws.model;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author luiseduardobrito
 * @since 12/7/14.
 */
public class VisualArtistModel extends BaseModel {

    @JsonProperty("id")
    String id;

    @JsonProperty("name")
    String name;

    @JsonProperty("type")
    String type;

    VisualArtistModel() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
