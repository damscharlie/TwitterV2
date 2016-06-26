package nl.saxion.ap.twitterv2.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import nl.saxion.ap.twitterv2.R;
import nl.saxion.ap.twitterv2.model.StatusModel;
import nl.saxion.ap.twitterv2.network.OAuthMaster;
import nl.saxion.ap.twitterv2.object.User;

/**
 * Created by MindR on 25-Jun-16.
 */
public class AuthActivity extends Activity {
    private WebView webView;
    private OAuth1RequestToken requestToken;
    public static OAuth1AccessToken accessToken;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oauth);
        webView = (WebView) findViewById(R.id.activity_oauth_webViewWV);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith(OAuthMaster.OAUTH_CALLBACK_URL)) {
                    Uri uri = Uri.parse(url);
                    final String verifierToken = uri.getQueryParameter("oauth_verifier");
                    Log.i("verifytoken: ", verifierToken);

                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            accessToken = OAuthMaster.getInstance().getService().getAccessToken(requestToken, verifierToken);
                            Response response = OAuthMaster.getInstance().verifyCredentials(accessToken);
                            try {
                                User user = new User(new JSONObject(response.getBody()));
                                StatusModel.getInstance().setCurrentUser(user);
                            } catch (JSONException jsone) {
                                jsone.printStackTrace();
                            }

                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                            startActivity(intent);
                            AuthActivity.this.finish();
                        }
                    }.execute();
                    return true;
                }

                return false;
            }
        });

        loadAuth(webView);

    }

    public void loadAuth(final WebView webView) {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                requestToken = OAuthMaster.getInstance().getService().getRequestToken();
                return OAuthMaster.getInstance().getService().getAuthorizationUrl(requestToken);
            }

            @Override
            protected void onPostExecute(String s) {
                webView.loadUrl(s);
            }
        }.execute();
    }
}
