package com.devnup.artcatalog.ws.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luiseduardobrito
 * @since 12/7/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VisualArtPeriodModel extends FreebaseTypedReferenceModel {

    @JsonProperty("id")
    String id = null;

    @JsonProperty("mid")
    String mid = null;

    @JsonProperty("name")
    String name = null;

    @JsonProperty("type")
    String type = "/visual_art/art_period_movement";

    @JsonProperty("/visual_art/art_period_movement/began_approximately")
    String dateBegun = null;

    @JsonProperty("/visual_art/art_period_movement/ended_approximately")
    String dateEnded = null;

    @JsonProperty("/visual_art/art_period_movement/associated_artists")
    List<FreebaseReferenceModel> artists = new ArrayList<>();

    @JsonProperty("/visual_art/art_period_movement/associated_artworks")
    List<FreebaseReferenceModel> artworks = new ArrayList<>();

    @JsonProperty("/wikipedia/topic/en_id")
    List<String> wikipedia = new ArrayList<>();

    public VisualArtPeriodModel() {
    }

    public static String toFreebaseQuery() {

        VisualArtPeriodModel artwork = new VisualArtPeriodModel();
        artwork.artists.add(new FreebaseReferenceModel());
        artwork.artworks.add(new FreebaseReferenceModel());

        ObjectMapper mapper = new ObjectMapper();

        try {
            return "[" + mapper.writeValueAsString(artwork) + "]";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String toFreebaseQuery(String mid) {

        VisualArtPeriodModel artwork = new VisualArtPeriodModel();
        artwork.mid = mid;
        artwork.artists.add(new FreebaseReferenceModel());
        artwork.artworks.add(new FreebaseReferenceModel());

        ObjectMapper mapper = new ObjectMapper();

        try {
            return "[" + mapper.writeValueAsString(artwork) + "]";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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

    private class ArtworkDimensions implements Serializable {

        @JsonProperty("/measurement_unit/dimensions/height_meters")
        Float height = null;

        @JsonProperty("/measurement_unit/dimensions/width_meters")
        Float width = null;

        @JsonProperty("/measurement_unit/dimensions/depth_meters")
        Float depth = null;

        public ArtworkDimensions() {
        }
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    public String getDateBegun() {
        return dateBegun;
    }

    public void setDateBegun(String dateBegun) {
        this.dateBegun = dateBegun;
    }

    public String getDateEnded() {
        return dateEnded;
    }

    public void setDateEnded(String dateEnded) {
        this.dateEnded = dateEnded;
    }

    public List<FreebaseReferenceModel> getArtists() {
        return artists;
    }

    public void setArtists(List<FreebaseReferenceModel> artists) {
        this.artists = artists;
    }

    public List<FreebaseReferenceModel> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<FreebaseReferenceModel> artworks) {
        this.artworks = artworks;
    }

    public List<String> getWikipedia() {
        return wikipedia;
    }

    public void setWikipedia(List<String> wikipedia) {
        this.wikipedia = wikipedia;
    }
}
