package nl.saxion.ap.twitterv2.entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by MindR on 25-Jun-16.
 */
public class URL {
    private String display_url;
    private String expanded_url;
    private int[] indices = new int[2];
    private String url;


    public URL(JSONArray urlsJSONArray) throws JSONException {
        for (int i = 0; i < urlsJSONArray.length(); i++) {

            JSONObject urls = urlsJSONArray.getJSONObject(i);

            this.url = urls.getString("url");
            this.display_url = urls.getString("display_url");
            this.expanded_url = urls.getString("expanded_url");
            JSONArray indices = urls.getJSONArray("indices");
            for (int j = 0; j < indices.length(); j++) {
                this.indices[j] = indices.getInt(j);
            }
        }
    }

    public String getDisplay_url() {
        return display_url;
    }

    public String getExpanded_url() {
        return expanded_url;
    }

    public int[] getIndices() {
        return indices;
    }

    public String getUrl() {
        return url;
    }
}
