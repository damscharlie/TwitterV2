package nl.saxion.ap.twitterv2.model;

import java.util.ArrayList;

import nl.saxion.ap.twitterv2.object.Status;
import nl.saxion.ap.twitterv2.object.User;

/**
 * Created by MindR on 25-Jun-16.
 */
public class StatusModel {
    private static final String API_KEY = ("3mKnxmIbSrvVASWagijckUIa6");
    private static final String API_SECRET = ("jKahq81fRq3a0WBi0eVqAj084SBUzMWnGk4boDPMfn32lHDszh");
    private static String CALLBACK_URL = ("https://github.com/damscharlie");
    private String accessToken;
    private User currentUser;

    private ArrayList<Status> tweets;
    private ArrayList<Status> userTweets = new ArrayList<>();
    private ArrayList<Status> userFavoured = new ArrayList<>();
    private ArrayList<Status> newsfeed= new ArrayList<>();

    private ArrayList<Status> searchResults;
    private ArrayList<Status> statuses;

    //instance
    private static StatusModel myInstance = new StatusModel();
    public static StatusModel getInstance() {
        return myInstance;
    }

    //favooured
    public ArrayList<Status> getUserFavoured() {
        return userFavoured;
    }
    public void addUserFavoured(Status tweet) {
        userFavoured.add(tweet);
    }

    //api
    public static String getApiKey() {
        return API_KEY;
    }
    public static String getApiSecret() {
        return API_SECRET;
    }

    //tokens
    public String getAccess_token() {
        return accessToken;
    }
    public void setAccess_token(String access_token) {
        this.accessToken = access_token;
    }

    //tweets
    public void addTweet(Status tweet) {
        tweets.add(tweet);
    }
    public void addUsersTweets(Status tweet) {
        userTweets.add(tweet);
    }

    //newsfeed
    public ArrayList<Status> getNewsfeed() {
        return newsfeed;
    }
    public void addNewsfeedTweets(Status tweet) {
        newsfeed.add(tweet);
    }

    //status
    private StatusModel() {
        statuses = new ArrayList<>();
    }
    public ArrayList<Status> getUserTweets() {
        return userTweets;
    }

    public Status getStatus(int position) {
        return statuses.get(position);
    }
    public void addStatus(Status status) {
        statuses.add(status);
    }
    public void addStatusArray(ArrayList<Status> statusesArray) {

        for (Status s : statusesArray) {
            if (!statuses.contains(s)) {
                addStatus(s);

            }
        }
    }
    public ArrayList getStatuses() {
        return statuses;
    }
    public ArrayList<Status> getSearchResults() {
        return searchResults;
    }

    //search
    public void addSSearchResults(ArrayList<Status> searchResults) {
        this.searchResults = new ArrayList<>();
        for (Status s : searchResults) {
            if (!this.searchResults.contains(s)) {
                this.searchResults.add(s);
            }
        }
    }
    public Status getSearchResult(int index) {
        return searchResults.get(index);
    }

    //user
    public User getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
