package nl.saxion.ap.twitterv2.object;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by MindR on 25-Jun-16.
 */
public class Size {
    private int height;
    private String resize;
    private int width;

    public Size(JSONObject size)throws JSONException {
        this.height = size.getInt("h");
        this.width = size.getInt("w");
        this.resize = size.getString("resize");
    }

    public int getHeight() {
        return height;
    }

    public String getResize() {
        return resize;
    }

    public int getWidth() {
        return width;
    }
}
