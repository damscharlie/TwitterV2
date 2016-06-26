package nl.saxion.ap.twitterv2.entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by MindR on 25-Jun-16.
 */
public class UserMentions {
    private long id;
    private String id_str;
    private int[] indices = new int[2];
    private String name;
    private String screen_name;

    public UserMentions(JSONArray user_mentions) throws JSONException {
        for (int i = 0; i < user_mentions.length(); i++) {

            JSONObject userMentions = user_mentions.getJSONObject(i);

            this.screen_name = userMentions.getString("screen_name");
            this.name = userMentions.getString("name");
            this.id = userMentions.getLong("id");
            this.id_str = userMentions.getString("id_str");
            JSONArray indices = userMentions.getJSONArray("indices");
            for (int j = 0; j < indices.length(); j++) {
                this.indices[j] = indices.getInt(j);
            }
        }
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

    public String getName() {
        return name;
    }

    public String getScreen_name() {
        return screen_name;
    }
}
