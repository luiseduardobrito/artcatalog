package com.devnup.artcatalog.ws;

/**
 * @author luiseduardobrito
 * @since 12/9/14.
 */
public class FreebaseUtil {

    public static String getQuery() {
        return "[{" +
                "  \"id\": null," +
                "  \"name\": null," +
                "  \"type\": \"/visual_art/visual_artist\"," +
                "  \"/visual_art/visual_artist/art_forms\": [{" +
                "    \"id\": null," +
                "    \"mid\": null," +
                "    \"name\": null" +
                "  }]," +
                "  \"/visual_art/visual_artist/artworks\": [{" +
                "    \"id\": null," +
                "    \"mid\": null," +
                "    \"name\": null" +
                "  }]," +
                "  \"/common/topic/image\": [{" +
                "    \"id\": null," +
                "    \"mid\": null," +
                "    \"name\": null" +
                "  }]," +
                "  \"/visual_art/visual_artist/associated_periods_or_movements\": [{" +
                "    \"id\": null," +
                "    \"mid\": null," +
                "    \"name\": null" +
                "  }]" +
                "}]";
    }

    public static String getImageURL(String id) {
        return "https://usercontent.googleapis.com/freebase/v1/image" + id + "?maxwidth=225&maxheight=225&mode=fillcropmid";
    }
}
