package nl.saxion.ap.twitterv2.asyncTask;

import android.os.AsyncTask;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;

import nl.saxion.ap.twitterv2.activity.AuthActivity;
import nl.saxion.ap.twitterv2.network.OAuthMaster;

/**
 * Created by MindR on 26-Jun-16.
 */
public class TweetAsyncTask extends AsyncTask<String, Void, Void> {
    private static String text ;


    public TweetAsyncTask(String text) {
        this.text=text;

    }

    @Override
    protected Void doInBackground(String... params) {



        final OAuthRequest request = new OAuthRequest(Verb.POST,
                "https://api.twitter.com/1.1/statuses/update.json",
                OAuthMaster.getInstance().getService());
        System.out.println(text);
        request.addParameter("status",text);
        OAuthMaster.getInstance().getService().signRequest(AuthActivity.accessToken, request);
        Response response = request.send();

        if (!response.isSuccessful()) {
            System.out.println("NOT SUCCESSFUL");

        }

        return null;
    }

}