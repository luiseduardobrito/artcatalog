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
public class FreebaseTypedReferenceModel extends BaseModel {

    @JsonProperty("id")
    String id = null;

    @JsonProperty("mid")
    String mid = null;

    @JsonProperty("type")
    String type = null;

    @JsonProperty("name")
    String name = null;

    FreebaseTypedReferenceModel() {
    }

    public static String toFreebaseQuery() {

        FreebaseTypedReferenceModel model = new FreebaseTypedReferenceModel();
        ObjectMapper mapper = new ObjectMapper();

        try {
            return "[" + mapper.writeValueAsString(model) + "]";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String toFreebaseQuery(String mid) {

        FreebaseTypedReferenceModel model = new FreebaseTypedReferenceModel();
        model.mid = mid;
        ObjectMapper mapper = new ObjectMapper();

        try {
            return "[" + mapper.writeValueAsString(model) + "]";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String toFreebaseQuery(String mid, String type) {

        FreebaseTypedReferenceModel model = new FreebaseTypedReferenceModel();

        model.mid = mid;
        model.type = type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
