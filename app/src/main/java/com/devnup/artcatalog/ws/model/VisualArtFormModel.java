package com.devnup.artcatalog.ws.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luiseduardobrito
 * @since 12/11/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VisualArtFormModel extends FreebaseTypedReferenceModel {

    @JsonProperty("id")
    String id = null;

    @JsonProperty("mid")
    String mid = null;

    @JsonProperty("name")
    String name = null;

    @JsonProperty("type")
    String type = "/visual_art/visual_art_form";

    @JsonProperty("/visual_art/visual_art_form/artists")
    List<FreebaseReferenceModel> artists = new ArrayList<>();

    @JsonProperty("/visual_art/visual_art_form/artworks")
    List<FreebaseReferenceModel> artworks = new ArrayList<>();

    @JsonProperty("/wikipedia/topic/en_id")
    List<String> wikipedia = new ArrayList<>();

    public static String toFreebaseQuery() {

        VisualArtFormModel artform = new VisualArtFormModel();
        artform.artists.add(new FreebaseReferenceModel());
        artform.artworks.add(new FreebaseReferenceModel());

        ObjectMapper mapper = new ObjectMapper();

        try {
            return "[" + mapper.writeValueAsString(artform) + "]";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String toFreebaseQuery(String mid) {

        VisualArtFormModel artform = new VisualArtFormModel();
        artform.mid = mid;
        artform.artists.add(new FreebaseReferenceModel());
        artform.artworks.add(new FreebaseReferenceModel());

        ObjectMapper mapper = new ObjectMapper();

        try {
            return "[" + mapper.writeValueAsString(artform) + "]";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getMid() {
        return mid;
    }

    @Override
    public void setMid(String mid) {
        this.mid = mid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    public List<String> getWikipedia() {
        return wikipedia;
    }

    public void setWikipedia(List<String> wikipedia) {
        this.wikipedia = wikipedia;
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
}