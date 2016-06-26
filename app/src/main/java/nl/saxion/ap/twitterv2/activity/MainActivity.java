package nl.saxion.ap.twitterv2.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import nl.saxion.ap.twitterv2.R;
import nl.saxion.ap.twitterv2.asyncTask.DoSearchAsyncTask;
import nl.saxion.ap.twitterv2.asyncTask.DownloadImageAsyncTask;
import nl.saxion.ap.twitterv2.asyncTask.TimeLineAsyncTask;
import nl.saxion.ap.twitterv2.fragment.ListFragment;
import nl.saxion.ap.twitterv2.model.StatusModel;
import nl.saxion.ap.twitterv2.object.JSON;
import nl.saxion.ap.twitterv2.object.Status;
import nl.saxion.ap.twitterv2.view.StatusAdapter;

public class MainActivity extends AppCompatActivity implements ListFragment.OnStatusListCallback {
    private static final String TAG = "MainActivity";
    private static Context context;
    private JSONObject newJSONObj;
    private JSON usedJSON;
    private ListFragment listFragment;
    private EditText search;
    private EditText tweet;
    private TextView tweetDisplay;
    private String searchURL;
    private StatusAdapter sa;
    private Bitmap bitmap = null;
    private Bitmap profileBitmap = null;
    private String stringResponse;
    private String bearerToken;
    private String tweetText;

    public StatusAdapter getStatusAdapter() {
        return statusAdapter;
    }

    private StatusAdapter statusAdapter;
    TimeLineAsyncTask timeLine;

    public static Context getContext() {
        return context;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getKey key = new getKey();
        key.execute();

        context = getApplicationContext();
        usedJSON = new JSON();
        String jsonText = "";

        timeLine = new TimeLineAsyncTask(this);
        timeLine.execute();

        listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment_container);
        statusAdapter = listFragment.getStatusAdapter();

        try {
            jsonText = usedJSON.readAssetIntoString("JSON.json");
            Log.d(TAG, "JSON LOADED!");
        } catch (IOException IoE) {
            IoE.getMessage();
            Log.e(TAG, "JSON NOT LOADED!");
        }
        try {
            newJSONObj = new JSONObject(jsonText);
            Log.d(TAG, "JSON obj. created successfully");
            StatusModel.getInstance().addStatusArray(usedJSON.createObjects(newJSONObj));
            Log.d(TAG, "All Statuses have been created successfully");
        } catch (JSONException e) {
            Log.e(TAG, "JSON object could not be created!", e);
        }

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            for (int i = 0; i < StatusModel.getInstance().getNewsfeed().size(); i++) {
                Status status = StatusModel.getInstance().getNewsfeed().get(i);
                DownloadImageAsyncTask downloadImageTask = new DownloadImageAsyncTask();

                if (status.getUser().getProfile_image_url() != null) {
                    downloadImageTask.execute(status.getUser().getProfile_image_url());

                    if (downloadImageTask.getStatus() == AsyncTask.Status.RUNNING) {

                        try {
                            profileBitmap = downloadImageTask.get();

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

                downloadImageTask = new DownloadImageAsyncTask();
                if (status.getImageUrl() != null) {
                    downloadImageTask.execute(status.getImageUrl());

                    if (downloadImageTask.getStatus() == AsyncTask.Status.RUNNING) {

                        try {
                            bitmap = downloadImageTask.get();

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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        search = (EditText) findViewById(R.id.fragment_main_searchET);
        tweet = (EditText) findViewById(R.id.fragment_main_tweetET);

        //make do search
        DoSearchAsyncTask doSearchTask = new DoSearchAsyncTask(this);
        //noinspection SimplifiableIfStatement
        if (id == R.id.text_tweet) {

            if (tweet.getVisibility() == View.GONE) {
                tweet.setBackgroundColor(Color.GRAY);
                tweet.setVisibility(View.VISIBLE);
                tweet.setHint("Enter a tweet query! (less than 140 chars)");
            } else {
                tweet.setVisibility(View.GONE);
                if (tweet.getText().length() > 0 &&tweet.getText().length()<=140) {
                    tweetText=tweet.getText().toString();
                    MyProfile.TweetAsyncTask tweetTask = new MyProfile.TweetAsyncTask(tweetText);
                    tweetTask.execute(tweetText);
                    Toast.makeText(MainActivity.this, "Tweet Created successfully", Toast.LENGTH_SHORT).show();
                    statusAdapter.notifyDataSetChanged();

                    tweet.setText("");

                }else{
                    Toast.makeText(MainActivity.this, "*tweet characters' out of limits", Toast.LENGTH_SHORT).show();
                    tweet.setText("");
                }
            }
            return true;
        }
        if (id == R.id.favoured_list) {
            startActivity(new Intent(MainActivity.this,GetFavoured.class));
            return true;
        }
        if (id == R.id.myProfile) {
            startActivity(new Intent(MainActivity.this, MyProfile.class));
            return true;
        }
        if (id == R.id.search) {
            if (search.getVisibility() == View.GONE) {
                search.setBackgroundColor(Color.GRAY);
                search.setVisibility(View.VISIBLE);
                search.setHint("Enter a search query!");
            } else {
                search.setVisibility(View.GONE);
                if (search.getText().length() > 0 && doSearchTask.getStatus() != AsyncTask.Status.RUNNING) {
                    doSearchTask.execute((search.getText().toString()));
                    search.setText("");
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(int position) {
        listFragment.refresh();
    }

    public void showSearchResults(ArrayList<Status> statusesArray) {
        StatusModel.getInstance().addSSearchResults(statusesArray);
        startActivity(new Intent(MainActivity.this, SearchActivity.class));
    }

    public class getKey extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {

            try {

                URL url = new URL("https://api.twitter.com/oauth2/token");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");

                String authString =
                        URLEncoder.encode(StatusModel.getApiKey(), "UTF-8") + ":" + URLEncoder.encode(StatusModel.getApiSecret(), "UTF-8");

                String authStringBase64 = Base64.encodeToString(
                        authString.getBytes("UTF-8"),
                        Base64.NO_WRAP);

                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setDoInput(true);
                conn.setRequestProperty("Authorization", "Basic " + authStringBase64);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

                conn.setDoOutput(true);
                byte[] body = "grant_type=client_credentials".getBytes("UTF-8");
                conn.setFixedLengthStreamingMode(body.length);
                BufferedOutputStream os = new BufferedOutputStream(conn.getOutputStream());
                os.write(body);

                os.close();

                int responseCode = conn.getResponseCode();
                //check the response code
                System.out.println(responseCode + " errors");

                if (responseCode == 200) {
                    InputStream response = conn.getInputStream();
                    stringResponse = IOUtils.toString(response, "UTF-8");
                    System.out.println(stringResponse);
                }

                return stringResponse;

            } catch (IOException e) {
                System.out.println("error " + e.getMessage());
                e.printStackTrace();
            }
            return "";
        }
    }
}
