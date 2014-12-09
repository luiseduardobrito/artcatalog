package com.devnup.artcatalog.ws.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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

    @JsonProperty("/people/person/date_of_birth")
    Date birthDate = null;

    @JsonProperty("type")
    String type = "/visual_art/visual_artist";

    @JsonProperty("/common/topic/image")
    List<FreebaseReferenceModel> image = new ArrayList<>();

    @JsonProperty("/visual_art/visual_artist/art_forms")
    List<FreebaseReferenceModel> artForms = new ArrayList<>();

    @JsonProperty("/visual_art/visual_artist/artworks")
    List<VisualArtworkModel> artworks = new ArrayList<>();

    @JsonProperty("/visual_art/visual_artist/associated_periods_or_movements")
    List<FreebaseReferenceModel> periods = new ArrayList<>();

    @JsonProperty("/people/person/place_of_birth")
    List<FreebaseReferenceModel> placeOfBirth = new ArrayList<>();

    @JsonProperty("/people/person/nationality")
    List<FreebaseReferenceModel> nationality = new ArrayList<>();

    public static String toFreebaseQuery() {

        VisualArtistModel artist = new VisualArtistModel();

        artist.artForms.add(new FreebaseReferenceModel());
        artist.artworks.add(new VisualArtworkModel());
        artist.periods.add(new FreebaseReferenceModel());
        artist.placeOfBirth.add(new FreebaseReferenceModel());
        artist.nationality.add(new FreebaseReferenceModel());

        ObjectMapper mapper = new ObjectMapper();

        try {
            return "[" + mapper.writeValueAsString(artist) + "]";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

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

    public List<VisualArtworkModel> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<VisualArtworkModel> artworks) {
        this.artworks = artworks;
    }

    public List<FreebaseReferenceModel> getPeriods() {
        return periods;
    }

    public void setPeriods(List<FreebaseReferenceModel> periods) {
        this.periods = periods;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public List<FreebaseReferenceModel> getPlaceOfBirth() {
        return this.placeOfBirth;
    }

    public void setPlaceOfBirth(List<FreebaseReferenceModel> placeBirth) {
        this.placeOfBirth = placeBirth;
    }

    public List<FreebaseReferenceModel> getNationality() {
        return nationality;
    }

    public void setNationality(List<FreebaseReferenceModel> nationality) {
        this.nationality = nationality;
    }
}
