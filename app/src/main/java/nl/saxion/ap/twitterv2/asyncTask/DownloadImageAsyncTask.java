package nl.saxion.ap.twitterv2.asyncTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by MindR on 26-Jun-16.
 */
public class DownloadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
    private Bitmap bitmap;
    int i = 0;
    private static final String DEBUG_TAG = "DownloadImageAsyncTask";

    @Override
    protected Bitmap doInBackground(String... urls) {
        try {
            return downloadUrl(urls[0]);
        } catch (IOException e) {
            return null;
        }
    }
// Given a URL, establishes an HttpUrlConnection and retrieves
// the web page content as a InputStream, which it returns as
// a string.
    private Bitmap downloadUrl(String myUrl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();
            // Convert the InputStream into a string
            bitmap = BitmapFactory.decodeStream(is);
            return  bitmap;
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }

        }
    }
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
    }


}
