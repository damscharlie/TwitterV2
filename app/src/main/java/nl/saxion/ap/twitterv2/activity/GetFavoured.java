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
import android.widget.ListView;

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
public class GetFavoured extends Activity {
    private String result;
    private StatusAdapter favouredAdapter;
    private static final String TAG = "My favoured";
    private Bitmap profileBitmap;
    private Bitmap bitmap;
    private ListView favouredList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoured);

        favouredList = (ListView) findViewById(R.id.favoured_list);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.twitter.com/1.1/favorites/list.json", OAuthMaster.getInstance().getService());

                request.addParameter("user_id", StatusModel.getInstance().getCurrentUser().getId() + "");
                OAuthMaster.getInstance().getService().signRequest(AuthActivity.accessToken, request);
                Response response = request.send();

                if (response.isSuccessful()) {
                    result = response.getBody();
                    System.out.println("result: " + result);

                    try {
                        JSONArray statusesArray = new JSONArray(result);
                        if (statusesArray.length() >= 0) {
                            for (int i = 0; i < statusesArray.length(); i++) {
                                JSONObject JSONStatus = statusesArray.getJSONObject(i);

                                nl.saxion.ap.twitterv2.object.Status newStatus = new nl.saxion.ap.twitterv2.object.Status(JSONStatus);

                                StatusModel.getInstance().addUserFavoured(newStatus);

                                System.out.println("Status favoured nr " + (i + 1) + " has been created.");
                                System.out.println(StatusModel.getInstance().getUserFavoured().size() +"SHIT");
                            }
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
                parsePictures();
                favouredAdapter.notifyDataSetChanged();


            }
        }.execute();

        favouredAdapter = new StatusAdapter(GetFavoured.this, 0, StatusModel.getInstance().getUserFavoured());

        favouredList.setAdapter(favouredAdapter);
        favouredAdapter.notifyDataSetChanged();

    }

    private void parsePictures() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            for (int i = 0; i < StatusModel.getInstance().getUserFavoured().size(); i++) {
                Status status = StatusModel.getInstance().getUserFavoured().get(i);
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
    @Override
    public void onBackPressed() {
        Intent intent =new Intent(GetFavoured.this,MainActivity.class);
        startActivity(intent);
        StatusModel.getInstance().getUserFavoured().clear();;

    }

}