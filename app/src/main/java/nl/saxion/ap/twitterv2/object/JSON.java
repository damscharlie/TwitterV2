package nl.saxion.ap.twitterv2.object;

import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import nl.saxion.ap.twitterv2.activity.MainActivity;

/**
 * Created by MindR on 26-Jun-16.
 */
public class JSON {
    private static final String TAG = "JSONParser";
    public JSON() {
    }

    public ArrayList<Status> createObjects(JSONObject json) throws JSONException {
        Status newStatus = null;
        ArrayList<Status> statuses = new ArrayList<>();

        JSONArray statusesArray = json.getJSONArray("statuses");

        for (int i = 0; i < statusesArray.length(); i++) {
            JSONObject JSONStatus = statusesArray.getJSONObject(i);
            newStatus = new Status(JSONStatus);
            statuses.add(newStatus);
            Log.d(TAG, "Status nr " + (i + 1) + " has been created.");
        }

        return statuses;
    }

    public String readAssetIntoString(String filename) throws IOException {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            InputStream is = MainActivity.getContext().getAssets().open(filename, AssetManager.ACCESS_BUFFER);
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
