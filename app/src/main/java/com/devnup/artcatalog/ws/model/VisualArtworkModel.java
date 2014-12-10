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
public class VisualArtworkModel extends FreebaseTypedReferenceModel {

    @JsonProperty("id")
    String id = null;

    @JsonProperty("mid")
    String mid = null;

    @JsonProperty("name")
    String name = null;

    @JsonProperty("type")
    String type = "/visual_art/artwork";

    @JsonProperty("/visual_art/artwork/date_begun")
    String dateBegun = null;

    @JsonProperty("/visual_art/artwork/date_completed")
    String dateCompleted = null;

    @JsonProperty("/visual_art/artwork/artist")
    List<FreebaseReferenceModel> artist = new ArrayList<>();

    @JsonProperty("/visual_art/artwork/art_subject")
    List<FreebaseReferenceModel> subjects = new ArrayList<>();

    @JsonProperty("/visual_art/artwork/dimensions_meters")
    ArtworkDimensions dimensions = new ArtworkDimensions();

    @JsonProperty("/visual_art/artwork/locations")
    List<FreebaseLocationReferenceModel> locations = new ArrayList<>();

    @JsonProperty("/visual_art/artwork/media")
    List<FreebaseReferenceModel> media = new ArrayList<>();

    @JsonProperty("/visual_art/artwork/period_or_movement")
    List<FreebaseReferenceModel> period = new ArrayList<>();

    public VisualArtworkModel() {
    }

    public static String toFreebaseQuery() {

        VisualArtworkModel artwork = new VisualArtworkModel();
        artwork.artist.add(new FreebaseReferenceModel());
        artwork.subjects.add(new FreebaseReferenceModel());

        FreebaseLocationReferenceModel locref = new FreebaseLocationReferenceModel();
        locref.location.add(new FreebaseReferenceModel());
        artwork.locations.add(locref);

        artwork.media.add(new FreebaseReferenceModel());
        artwork.period.add(new FreebaseReferenceModel());
        ObjectMapper mapper = new ObjectMapper();

        try {
            return "[" + mapper.writeValueAsString(artwork) + "]";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String toFreebaseQuery(String mid) {

        VisualArtworkModel artwork = new VisualArtworkModel();
        artwork.mid = mid;
        artwork.artist.add(new FreebaseReferenceModel());
        artwork.subjects.add(new FreebaseReferenceModel());

        FreebaseLocationReferenceModel locref = new FreebaseLocationReferenceModel();
        locref.location.add(new FreebaseReferenceModel());
        artwork.locations.add(locref);

        artwork.media.add(new FreebaseReferenceModel());
        artwork.period.add(new FreebaseReferenceModel());
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

    public String getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(String dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public List<FreebaseReferenceModel> getArtist() {
        return artist;
    }

    public void setArtist(List<FreebaseReferenceModel> artist) {
        this.artist = artist;
    }

    public List<FreebaseReferenceModel> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<FreebaseReferenceModel> subjects) {
        this.subjects = subjects;
    }

    public ArtworkDimensions getDimensions() {
        return dimensions;
    }

    public void setDimensions(ArtworkDimensions dimensions) {
        this.dimensions = dimensions;
    }

    public List<FreebaseLocationReferenceModel> getLocations() {
        return locations;
    }

    public void setLocations(List<FreebaseLocationReferenceModel> locations) {
        this.locations = locations;
    }

    public List<FreebaseReferenceModel> getMedia() {
        return media;
    }

    public void setMedia(List<FreebaseReferenceModel> media) {
        this.media = media;
    }

    public List<FreebaseReferenceModel> getPeriod() {
        return period;
    }

    public void setPeriod(List<FreebaseReferenceModel> period) {
        this.period = period;
    }
}
