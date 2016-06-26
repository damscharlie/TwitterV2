package nl.saxion.ap.twitterv2.asyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;

import nl.saxion.ap.twitterv2.activity.AuthActivity;
import nl.saxion.ap.twitterv2.network.OAuthMaster;

/**
 * Created by MindR on 25-Jun-16.
 */
public class ReTweetAsyncTask extends AsyncTask<Void,Void,Void>{
    public static boolean isRetweeted;
    private static long id;
    private Context context;

    public ReTweetAsyncTask(long id, Context context) {
        this.id = id;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        final OAuthRequest request = new OAuthRequest(Verb.POST,
                "https://api.twitter.com/1.1/statuses/retweet/"+id+".json",
                OAuthMaster.getInstance().getService());

        OAuthMaster.getInstance().getService().signRequest(AuthActivity.accessToken, request);
        Response response = request.send();

        if (!response.isSuccessful()) {
            System.out.println("NOT SUCCESSFUL");
            isRetweeted =false;
        }else{
            isRetweeted =true;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (isRetweeted){
            Toast.makeText(context, "Tweet re-tweeted", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "*Tweet is already re-tweeted", Toast.LENGTH_SHORT).show();

        }
    }
}
