package nl.saxion.ap.twitterv2.asyncTask;

import android.os.AsyncTask;
import android.util.Log;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import nl.saxion.ap.twitterv2.activity.MainActivity;
import nl.saxion.ap.twitterv2.network.OAuthMaster;
import nl.saxion.ap.twitterv2.object.JSON;

/**
 * Created by MindR on 26-Jun-16.
 */
public class DoSearchAsyncTask extends AsyncTask<String, Void, String> {
    private static final String TAG = "DoSearchAsyncTask";
    private MainActivity mainActivity;

    public DoSearchAsyncTask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.d(TAG, "We started searching in the background");
        String searchResults = startSearching(strings[0]);
        if (searchResults != null) {
            Log.d(TAG, searchResults);
        }
        return searchResults;
    }

    private String startSearching(String keyword) {
        String finalResponse;
        String encodedSearch = null;
        try {
            encodedSearch = URLEncoder.encode(keyword, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.getMessage());
        }
        final OAuthRequest request = new OAuthRequest(Verb.GET, ("https://api.twitter.com/1.1/search/tweets.json?q=" + encodedSearch), OAuthMaster.getInstance().getService());
        OAuthMaster.getInstance().getService().signRequest(OAuthMaster.getInstance().getAccessToken(), request); // the access token from step 4
        final Response response = request.send();
        finalResponse = response.getBody();
        return finalResponse;
    }

    @Override
    protected void onPostExecute(String result) {
        JSON jsonParser = new JSON();
        ArrayList<nl.saxion.ap.twitterv2.object.Status> statusesArray = new ArrayList<>();

        try {
            JSONObject newJSONObj = new JSONObject(result);
            statusesArray = jsonParser.createObjects(newJSONObj);
        } catch (JSONException e) {
            Log.e(TAG, "couldn't parse search results -> " + e.getMessage());
        } finally {
            mainActivity.showSearchResults(statusesArray);
        }

    }
}