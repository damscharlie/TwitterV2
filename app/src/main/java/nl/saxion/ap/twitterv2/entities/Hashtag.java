package nl.saxion.ap.twitterv2.entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by MindR on 25-Jun-16.
 */
public class Hashtag {
    private int[] indices = new int[2];
    private String text;

    public Hashtag(JSONArray hashtagsJSONArray) throws JSONException {
        for (int i = 0; i < hashtagsJSONArray.length(); i++) {

            JSONObject hashtag = hashtagsJSONArray.getJSONObject(i);

            this.text = hashtag.getString("text");

            JSONArray indices = hashtag.getJSONArray("indices");
            for (int j = 0; j < indices.length(); j++) {
                this.indices[j] = indices.getInt(j);
            }
        }
    }

    public int[] getIndices() {
        return indices;
    }

    public String getText() {
        return text;
    }
}
