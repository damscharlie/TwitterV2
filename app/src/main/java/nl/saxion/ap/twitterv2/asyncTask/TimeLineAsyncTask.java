package nl.saxion.ap.twitterv2.asyncTask;

import android.os.AsyncTask;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import nl.saxion.ap.twitterv2.activity.MainActivity;
import nl.saxion.ap.twitterv2.model.StatusModel;
import nl.saxion.ap.twitterv2.network.OAuthMaster;

/**
 * Created by MindR on 26-Jun-16.
 */
public class TimeLineAsyncTask extends AsyncTask<Void, Void, Void> {
    String result;
    private MainActivity mainActivity;
    private static final String TAG = "Search Task";

    public TimeLineAsyncTask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if (StatusModel.getInstance().getNewsfeed()!=null){
            StatusModel.getInstance().getNewsfeed().clear();
        }

        final OAuthRequest request = new OAuthRequest(Verb.GET,
                ("https://api.twitter.com/1.1/statuses/home_timeline.json"),
                OAuthMaster.getInstance().getService());

        request.addParameter("user_id", StatusModel.getInstance().getCurrentUser().getId() + "");
        // the access token from step 4
        OAuthMaster.getInstance().getService().signRequest(OAuthMaster.getInstance().getAccessToken(), request);

        final Response response = request.send();
        result = response.getBody();
        if (response.isSuccessful()) {
            result = response.getBody();
            System.out.println("result: " + result);

            try {
                JSONArray statusesArray = new JSONArray(result);
                for (int i = 0; i < statusesArray.length(); i++) {
                    JSONObject JSONStatus = statusesArray.getJSONObject(i);
                    nl.saxion.ap.twitterv2.object.Status newStatus = new nl.saxion.ap.twitterv2.object.Status(JSONStatus);
                    System.out.println("TimeLine Statues nr " + (i + 1) + " has been created.");
                    StatusModel.getInstance().addNewsfeedTweets(newStatus);
                }
            } catch (JSONException e) {
                System.err.println(e.getMessage());
            }

        }
        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mainActivity.getStatusAdapter().notifyDataSetInvalidated();
    }
}
