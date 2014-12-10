package com.devnup.artcatalog.ws.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luiseduardobrito
 * @since 12/8/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FreebaseLocationReferenceModel extends BaseModel {

    @JsonProperty("id")
    String id = null;

    @JsonProperty("mid")
    String mid = null;

    @JsonProperty("location")
    List<FreebaseReferenceModel> location = new ArrayList<>();

    @JsonProperty("optional")
    Boolean optional = true;

    public FreebaseLocationReferenceModel() {
    }

    public static String toFreebaseQuery() {

        FreebaseLocationReferenceModel model = new FreebaseLocationReferenceModel();
        model.location.add(new FreebaseReferenceModel());
        ObjectMapper mapper = new ObjectMapper();

        try {
            return "[" + mapper.writeValueAsString(model) + "]";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String toFreebaseQuery(String mid) {

        FreebaseLocationReferenceModel model = new FreebaseLocationReferenceModel();
        model.mid = mid;
        model.location.add(new FreebaseReferenceModel());
        ObjectMapper mapper = new ObjectMapper();

        try {
            return "[" + mapper.writeValueAsString(model) + "]";
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

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public List<FreebaseReferenceModel> getLocation() {
        return location;
    }

    public void setLocation(List<FreebaseReferenceModel> location) {
        this.location = location;
    }

    public Boolean getOptional() {
        return optional;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }
}
