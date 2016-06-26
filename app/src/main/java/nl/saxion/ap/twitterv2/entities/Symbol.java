package nl.saxion.ap.twitterv2.entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by MindR on 25-Jun-16.
 */
public class Symbol {
    private String text;
    private int[] indices = new int[2];

    public Symbol(JSONArray symbolsJSONArray) throws JSONException {
        for (int i = 0; i < symbolsJSONArray.length(); i++) {

            JSONObject symbol = symbolsJSONArray.getJSONObject(i);

            this.text = symbol.getString("text");
            JSONArray indices = symbol.getJSONArray("indices");
            for (int j = 0; j < indices.length(); j++) {
                this.indices[j] = indices.getInt(j);
            }
        }
    }

    public String getText() {
        return text;
    }

}
