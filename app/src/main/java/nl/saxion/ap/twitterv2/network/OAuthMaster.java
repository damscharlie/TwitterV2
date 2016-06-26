package nl.saxion.ap.twitterv2.network;

import android.util.Base64;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import nl.saxion.ap.twitterv2.model.StatusModel;

/**
 * Created by Ahmed on 16.06.2016.
 */

public class OAuthMaster extends TwitterApi {

    public static final String OAUTH_CALLBACK_URL = "https://github.com/edress185";
    private static OAuthMaster instance = null;
    private static final String URL_VERIFY_CREDENTIALS = "https://api.twitter.com/1.1/account/verify_credentials.json";

    private OAuth10aService service;
    private OAuth1AccessToken accessToken;

    public OAuthMaster() {
    }

    public static OAuthMaster getInstance() {

        if (instance == null) {
            instance = new OAuthMaster();
        }

        return instance;
    }


    public void oAuthGetter() {

        HttpURLConnection connection = null;
        BufferedOutputStream out = null;
        String token = "";


        try {
            //Prepare request

            URL url = new URL("https://api.twitter.com/oauth2/token");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            //Encoding the API key and secret

            String authString = URLEncoder.encode(StatusModel.getApiKey(), "UTF-8") + ":" +
                    URLEncoder.encode(StatusModel.getApiSecret(), "UTF-8");


            //Applying base64 encoding on the encoded string

            String authStringBase64 = Base64.encodeToString(
                    authString.getBytes("UTF-8"),
                    Base64.NO_WRAP);


            connection.setRequestProperty("Authorization", "Basic " + authStringBase64);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            connection.setDoOutput(true);
            byte[] body = "grant_type=client_credentials".getBytes("UTF-8");

            connection.setFixedLengthStreamingMode(body.length);
            out = new BufferedOutputStream(connection.getOutputStream());
            out.write(body);
            out.close();

            //Checking responseCode to see if everything went as planned
            int response = connection.getResponseCode();
            if (response == 200) {
                InputStream is = connection.getInputStream();
                token = IOUtils.toString(is, "UTF-8");
            } else {
                System.out.println(connection.getResponseMessage());
            }

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        } finally {
            connection.disconnect();
        }

        StatusModel.getInstance().setAccess_token(parseOauthToken(token));

    }

    public String parseOauthToken(String token) {

        String access_token = "";
        try {
            JSONObject tokenObject = new JSONObject(token);
            access_token = tokenObject.getString("access_token");
        } catch (JSONException jsonE) {
            System.out.println(jsonE.getMessage());
        }
        return access_token;
    }

    public Response verifyCredentials(OAuth1AccessToken token) {
        accessToken = token;
        final OAuthRequest request = new OAuthRequest(Verb.GET, URL_VERIFY_CREDENTIALS, getService());
        getService().signRequest(token, request);
        return request.send();
    }

    public OAuth10aService getService() {

        return (service == null ? service = new ServiceBuilder()
                .apiKey(StatusModel.getApiKey())
                .apiSecret(StatusModel.getApiSecret())
                .callback(OAUTH_CALLBACK_URL)
                .build(OAuthMaster.getInstance()) : service);

    }
    public OAuth1AccessToken getAccessToken(){
        return accessToken;
    }

}

