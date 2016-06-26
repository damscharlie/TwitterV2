package nl.saxion.ap.twitterv2.entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import nl.saxion.ap.twitterv2.object.Sizes;

/**
 * Created by MindR on 25-Jun-16.
 */
public class Media {
    private String display_url;
    private String expanded_url;
    private long id;
    private String id_str;
    private int[] indices = new int[2];
    private String media_url;
    private String media_url_https;
    private Sizes sizes;
    private long source_status_id;
    private String source_status_id_str;
    private String type;
    private String url;
    private int source_user_id;
    private String source_user_id_str;

    public Media(JSONArray mediaJSONArray) throws JSONException {
        for (int i = 0; i < mediaJSONArray.length(); i++) {

            JSONObject media = mediaJSONArray.getJSONObject(i);

            this.display_url = media.getString("display_url");
            this.expanded_url = media.getString("expanded_url");
            this.id = media.getLong("id");
            this.id_str = media.getString("id_str");
            JSONArray indices = media.getJSONArray("indices");
            for (int j = 0; j < indices.length(); j++) {
                this.indices[j] = indices.getInt(j);
            }
            this.media_url = media.getString("media_url");
            this.media_url_https = media.getString("media_url_https");
            this.source_status_id = media.optLong("source_status_id",0);
            this.source_status_id_str = media.optString("source_status_id_str",null);
            this.type = media.getString("type");
            this.url = media.getString("url");
            this.source_status_id = media.optInt("source_user_id",0);
            this.source_status_id_str = media.optString("source_status_id_str",null);
            this.sizes = new Sizes(media.getJSONObject("sizes"));
        }
    }

    public String getDisplay_url() {
        return display_url;
    }

    public String getExpanded_url() {
        return expanded_url;
    }

    public long getId() {
        return id;
    }

    public String getId_str() {
        return id_str;
    }

    public int[] getIndices() {
        return indices;
    }

    public String getMedia_url() {
        return media_url;
    }

    public String getMedia_url_https() {
        return media_url_https;
    }

    public Sizes getSizes() {
        return sizes;
    }

    public long getSource_status_id() {
        return source_status_id;
    }

    public String getSource_status_id_str() {
        return source_status_id_str;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public int getSource_user_id() {
        return source_user_id;
    }

    public String getSource_user_id_str() {
        return source_user_id_str;
    }

}
