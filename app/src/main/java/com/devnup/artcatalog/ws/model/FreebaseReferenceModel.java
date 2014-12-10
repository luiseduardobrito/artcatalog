package com.devnup.artcatalog.ws.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * @author luiseduardobrito
 * @since 12/8/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FreebaseReferenceModel extends BaseModel {

    @JsonProperty("id")
    String id = null;

    @JsonProperty("mid")
    String mid = null;

    @JsonProperty("name")
    String name = null;

    @JsonProperty("optional")
    Boolean optional = true;

    public FreebaseReferenceModel() {
    }

    public static String toFreebaseQuery() {

        FreebaseReferenceModel model = new FreebaseReferenceModel();
        ObjectMapper mapper = new ObjectMapper();

        try {
            return "[" + mapper.writeValueAsString(model) + "]";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String toFreebaseQuery(String mid) {

        FreebaseReferenceModel model = new FreebaseReferenceModel();
        model.mid = mid;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getOptional() {
        return optional;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }
}
