package nl.saxion.ap.twitterv2.object;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nl.saxion.ap.twitterv2.entities.Hashtag;
import nl.saxion.ap.twitterv2.entities.Media;
import nl.saxion.ap.twitterv2.entities.Symbol;
import nl.saxion.ap.twitterv2.entities.URL;
import nl.saxion.ap.twitterv2.entities.UserMentions;

public class Entities {
    private ArrayList<Hashtag> hashtags = new ArrayList<>();
    private ArrayList<Media> media = new ArrayList<>();
    private ArrayList<URL> urls = new ArrayList<>();
    private ArrayList<UserMentions> user_mentions = new ArrayList<>();
    private ArrayList<Symbol> symbols = new ArrayList<>();

    private void addHashtag(Hashtag hashtag) {
        this.hashtags.add(hashtag);
    }

    private void addMedia(Media media) {
        this.media.add(media);
    }

    private void addUrl(URL url) {
        this.urls.add(url);
    }

    private void addUserMentions(UserMentions userMentions) {
        this.user_mentions.add(userMentions);
    }

    private void addSymbols(Symbol symbol) {
        this.symbols.add(symbol);
    }

    public Entities(JSONObject entities) throws JSONException {

        JSONArray hashtagJSONArray = entities.optJSONArray("hashtags");
        if (hashtagJSONArray == null) {
            addHashtag(null);
        } else addHashtag(new Hashtag(hashtagJSONArray));


        JSONArray symbolJSONArray = entities.optJSONArray("symbols");
        if (symbolJSONArray == null) {
            addSymbols(null);
        } else addSymbols(new Symbol(symbolJSONArray));


        JSONArray userMentionsJSONArray = entities.optJSONArray("user_mentions");
        if (userMentionsJSONArray == null) {
            addUserMentions(null);
        } else {
            addUserMentions(new UserMentions(userMentionsJSONArray));
        }

        JSONArray urlJSONArray = entities.optJSONArray("urls");
        if (urlJSONArray == null) {
            addUrl(null);
        } else addUrl(new URL(urlJSONArray));

        JSONArray mediaJSONArray = entities.optJSONArray("media");
        if (mediaJSONArray == null) {
            addMedia(null);
        } else addMedia(new Media(mediaJSONArray));

    }

    public ArrayList<Hashtag> getHashtags() {
        return hashtags;
    }

    public ArrayList<Media> getMedia() {
        return media;
    }

    public ArrayList<URL> getUrls() {
        return urls;
    }

    public ArrayList<UserMentions> getUser_mentions() {
        return user_mentions;
    }

    public ArrayList<Symbol> getSymbols() {
        return symbols;
    }
}
