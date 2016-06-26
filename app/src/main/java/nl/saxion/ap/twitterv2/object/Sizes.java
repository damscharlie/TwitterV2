package nl.saxion.ap.twitterv2.object;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by MindR on 25-Jun-16.
 */
public class Sizes {
    private Size thumb;
    private Size large;
    private Size medium;
    private Size small;

    public Sizes(JSONObject sizes) throws JSONException {
        this.medium = new Size(sizes.getJSONObject("medium"));
        this.small = new Size(sizes.getJSONObject("small"));
        this.thumb = new Size(sizes.getJSONObject("thumb"));
        this.large = new Size(sizes.getJSONObject("large"));
    }

    public Size getThumb() {
        return thumb;
    }

    public Size getLarge() {
        return large;
    }

    public Size getMedium() {
        return medium;
    }

    public Size getSmall() {
        return small;
    }
}
