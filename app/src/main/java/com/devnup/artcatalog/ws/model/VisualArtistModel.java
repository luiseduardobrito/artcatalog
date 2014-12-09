package com.devnup.artcatalog.ws.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luiseduardobrito
 * @since 12/7/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VisualArtistModel extends BaseModel {

    @JsonProperty("id")
    String id = null;

    @JsonProperty("name")
    String name = null;

    @JsonProperty("type")
    String type = "\"/visual_art/visual_artist\"";

    @JsonProperty("/common/topic/image")
    List<FreebaseReferenceModel> image = new ArrayList<>();

    @JsonProperty("/visual_art/visual_artist/art_forms")
    List<FreebaseReferenceModel> artForms = new ArrayList<>();

    @JsonProperty("/visual_art/visual_artist/artworks")
    List<FreebaseReferenceModel> artworks = new ArrayList<>();

    @JsonProperty("/visual_art/visual_artist/associated_periods_or_movements")
    List<FreebaseReferenceModel> periods = new ArrayList<>();

    public VisualArtistModel() {
    }

    public List<FreebaseReferenceModel> getImage() {
        return image;
    }

    public void setImage(List<FreebaseReferenceModel> image) {
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

    public void setArtForms(List<FreebaseReferenceModel> artForms) {
        this.artForms = artForms;
    }

    public List<FreebaseReferenceModel> getArtForms() {
        return artForms;
    }

    public List<FreebaseReferenceModel> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<FreebaseReferenceModel> artworks) {
        this.artworks = artworks;
    }

    public List<FreebaseReferenceModel> getPeriods() {
        return periods;
    }

    public void setPeriods(List<FreebaseReferenceModel> periods) {
        this.periods = periods;
    }
}
