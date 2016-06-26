package nl.saxion.ap.twitterv2.network;

/**
 * Created by MindR on 26-Jun-16.
 */

import com.github.scribejava.core.builder.api.DefaultApi10a;
import com.github.scribejava.core.model.OAuth1RequestToken;

public class TwitterApi extends DefaultApi10a {

    //Singleton pattern
    private static TwitterApi instance;
    private static final String URL_REQUEST_TOKEN = "https://api.twitter.com/oauth/request_token";
    private static final String URL_ACCESS_TOKEN = "https://api.twitter.com/oauth/access_token";
    private static final String URL_TOKEN_AUTH = "https://api.twitter.com/oauth/authorize?oauth_token=";

    public TwitterApi(){}

    @Override
    public String getRequestTokenEndpoint() {
        return URL_REQUEST_TOKEN;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return URL_ACCESS_TOKEN;
    }

    public static TwitterApi getInstance(){
        if(instance == null){
            instance = new TwitterApi();
        }
        return instance;
    }

    @Override
    public String getAuthorizationUrl(OAuth1RequestToken requestToken) {
        return URL_TOKEN_AUTH + requestToken.getToken();
    }
}