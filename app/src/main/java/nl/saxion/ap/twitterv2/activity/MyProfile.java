package nl.saxion.ap.twitterv2.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import nl.saxion.ap.twitterv2.asyncTask.DownloadImageAsyncTask;
import nl.saxion.ap.twitterv2.R;
import nl.saxion.ap.twitterv2.view.StatusAdapter;
import nl.saxion.ap.twitterv2.model.StatusModel;
import nl.saxion.ap.twitterv2.network.OAuthMaster;
import nl.saxion.ap.twitterv2.object.Status;

/**
 * Created by MindR on 26-Jun-16.
 */
public class MyProfile extends Activity {

    private TextView userName;
    private TextView userEmail;
    private TextView followers;
    private ListView profileList;
    private ImageView profilePicture;
    private StatusAdapter profileAdapter;
    private Bitmap profileBitmap;
    private Bitmap bitmap;
    private static final String TAG = "My Profile";
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        userName = (TextView) findViewById(R.id.activity_myprofile_usernameTV);
        userEmail = (TextView) findViewById(R.id.activity_myprofile_userEmailTV);
        profilePicture = (ImageView) findViewById(R.id.user_profile_picture_iv);
        followers=(TextView)findViewById(R.id.activity_myprofile_followersTV);
        profileList = (ListView) findViewById(R.id.activity_myprofile_listViewLV);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.twitter.com/1.1/statuses/user_timeline.json", OAuthMaster.getInstance().getService());


                request.addParameter("user_id", StatusModel.getInstance().getCurrentUser().getId() + "");
                OAuthMaster.getInstance().getService().signRequest(AuthActivity.accessToken, request);
                Response response = request.send();

                if (response.isSuccessful()) {
                    result = response.getBody();
                    System.out.println("result: " + result);

                    try {
                        JSONArray statusesArray = new JSONArray(result);
                        for (int i = 0; i < statusesArray.length(); i++) {
                            JSONObject JSONStatus = statusesArray.getJSONObject(i);
                            nl.saxion.ap.twitterv2.object.Status newStatus = new nl.saxion.ap.twitterv2.object.Status(JSONStatus);
                            System.out.println("Status nr " + (i + 1) + " has been created.");
                            StatusModel.getInstance().addUsersTweets(newStatus);

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
                profileAdapter.notifyDataSetChanged();
                parsePictures();
                userName.setText(StatusModel.getInstance().getCurrentUser().getName());
                followers.setText(StatusModel.getInstance().getCurrentUser().getFollowers_count()+"");
                userEmail.setText(StatusModel.getInstance().getCurrentUser().getScreen_name());

                profilePicture.setImageBitmap(profileBitmap);
            }
        }.execute();

        profileAdapter = new StatusAdapter(MyProfile.this, 0, StatusModel.getInstance().getUserTweets());

        profileList.setAdapter(profileAdapter);


        profileAdapter.notifyDataSetChanged();

    }
    @Override
    public void onBackPressed() {
        Intent intent =new Intent(MyProfile.this,MainActivity.class);
        startActivity(intent);
        StatusModel.getInstance().getUserTweets().clear();;

    }

    private void parsePictures() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            for (int i = 0; i < StatusModel.getInstance().getUserTweets().size(); i++) {
                Status status = StatusModel.getInstance().getUserTweets().get(i);
                DownloadImageAsyncTask downloadImageAsyncTask = new DownloadImageAsyncTask();

                if (status.getUser().getProfile_image_url() != null) {
                    downloadImageAsyncTask.execute(status.getUser().getProfile_image_url());

                    if (downloadImageAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {

                        try {
                            profileBitmap = downloadImageAsyncTask.get();

                            if (profileBitmap != null) {
                                status.getUser().addImageBitmap(profileBitmap);
                            } else Log.e(TAG, "Profile Bitmap is null");

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Log.e(TAG, "The profile picture async task was interrupted");

                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }

                downloadImageAsyncTask = new DownloadImageAsyncTask();
                if (status.getImageUrl() != null) {
                    downloadImageAsyncTask.execute(status.getImageUrl());

                    if (downloadImageAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {

                        try {
                            bitmap = downloadImageAsyncTask.get();

                            if (bitmap != null) {
                                status.addImageBitmap(bitmap);
                            } else Log.e(TAG, "Bitmap is null");

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Log.e(TAG, "The async task was interrupted");

                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else {
            Log.e(TAG, "Connection could not be established");
        }
    }

    public static class TweetAsyncTask extends AsyncTask<String, Void, Void> {
        private static String text ;


        public TweetAsyncTask(String text) {
            this.text=text;

        }

        @Override
        protected Void doInBackground(String... params) {



            final OAuthRequest request = new OAuthRequest(Verb.POST, "https://api.twitter.com/1.1/statuses/update.json", OAuthMaster.getInstance().getService());
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
}