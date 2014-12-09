package com.devnup.artcatalog.ws.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * @author luiseduardobrito
 * @since 12/7/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VisualArtistModel extends BaseModel {

    @JsonProperty("id")
    String id;

    @JsonProperty("name")
    String name;

    @JsonProperty("type")
    String type;

    @JsonProperty("/common/topic/image")
    List<ImageModel> image;

    public void setArtForms(List<String> artForms) {
        this.artForms = artForms;
    }

    public List<String> getArtForms() {

        return artForms;
    }

    @JsonProperty("/visual_art/visual_artist/art_forms")
    List<String> artForms;

    VisualArtistModel() {
    }

    public List<ImageModel> getImage() {
        return image;
    }

    public void setImage(List<ImageModel> image) {
        this.image = image;
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
