package nl.saxion.ap.twitterv2.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.concurrent.ExecutionException;

import nl.saxion.ap.twitterv2.asyncTask.DownloadImageAsyncTask;
import nl.saxion.ap.twitterv2.R;
import nl.saxion.ap.twitterv2.fragment.SearchFragment;
import nl.saxion.ap.twitterv2.model.StatusModel;
import nl.saxion.ap.twitterv2.object.Status;

/**
 * Created by MindR on 26-Jun-16.
 */
public class SearchActivity extends AppCompatActivity implements SearchFragment.OnSearchResultListCallback {
    private SearchFragment searchFragment;
    private Bitmap profileBitmap;
    private Bitmap bitmap;
    private static final String TAG = "Search Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search_list);

        searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_search_listView);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            for (int i = 0; i < StatusModel.getInstance().getSearchResults().size(); i++) {
                Status status = StatusModel.getInstance().getSearchResult(i);
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
        searchFragment.refresh();
    }

    @Override
    public void onItemClicked(int position) {

    }
}
