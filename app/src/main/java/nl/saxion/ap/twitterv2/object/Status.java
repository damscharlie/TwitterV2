package nl.saxion.ap.twitterv2.object;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by MindR on 25-Jun-16.
 */
public class Status {
    private String created_at;
    private Entities entities;
    private int favorite_count;
    private boolean favorited;
    private long id;
    private String id_str;
    private String in_reply_to_screen_name;
    private long in_reply_to_status_id;
    private String in_reply_to_status_id_str;
    private long in_reply_to_user_id;
    private String in_reply_to_user_id_str;
    private boolean is_quote_status;
    private int retweet_count;
    private Status retweeted_status;
    private String source;
    private SpannableString text;
    private boolean truncated;
    private User user;
    private String imageUrl;
    private Bitmap imageBitmap;
    private boolean retweeted;

    public Status(JSONObject status) throws JSONException {
        createAttributesFromJSON(status);
    }

    private void createAttributesFromJSON(JSONObject status) throws JSONException {
        this.created_at = status.getString("created_at");
        this.id = status.getLong("id");
        this.id_str = status.getString("id_str");
        this.text = highlight(status.getString("text"));
        this.truncated = status.getBoolean("truncated");
        this.source = status.getString("source");
        this.in_reply_to_status_id = status.optLong("in_reply_to_status_id", 0);
        this.in_reply_to_status_id_str = status.optString("in_reply_to_status_id_str", null);
        this.in_reply_to_screen_name = status.optString("in_reply_to_screen_name", null);
        this.in_reply_to_user_id = status.optLong("in_reply_to_user_id", 0);
        this.in_reply_to_user_id_str = status.optString("in_reply_to_user_id_str", null);
        this.favorite_count = status.getInt("favorite_count");
        this.favorited = status.getBoolean("favorited");
        this.is_quote_status = status.getBoolean("is_quote_status");
        this.retweet_count = status.getInt("retweet_count");

        if (!retweeted) {
            retweeted = status.getBoolean("retweeted");
        }else this.retweeted_status = new Status(status);
        this.entities = new Entities(status.getJSONObject("entities"));
        this.user = new User(status.getJSONObject("user"));
        if (this.entities.getMedia().get(0) != null) {
            this.imageUrl = this.entities.getMedia().get(0).getMedia_url();
        }
    }

    public String getCreated_at() {
        return created_at;
    }

    public Entities getEntities() {
        return entities;
    }

    public int getFavorite_count() {
        return favorite_count;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public long getId() {
        return id;
    }

    public String getId_str() {
        return id_str;
    }

    public String getIn_reply_to_screen_name() {
        return in_reply_to_screen_name;
    }

    public long getIn_reply_to_status_id() {
        return in_reply_to_status_id;
    }

    public String getIn_reply_to_status_id_str() {
        return in_reply_to_status_id_str;
    }

    public long getIn_reply_to_user_id() {
        return in_reply_to_user_id;
    }

    public String getIn_reply_to_user_id_str() {
        return in_reply_to_user_id_str;
    }

    public boolean is_quote_status() {
        return is_quote_status;
    }

    public int getRetweet_count() {
        return retweet_count;
    }

    public Status getRetweeted_status() {
        return retweeted_status;
    }

    public String getSource() {
        return source;
    }

    public SpannableString getText() {
        return text;
    }

    public boolean isTruncated() {
        return truncated;
    }

    public User getUser() {
        return user;
    }

    public SpannableString highlight(String text) {
        SpannableString hashText = new SpannableString(text);

        Matcher matcherHashtag = Pattern.compile("#([A-Za-z0-9_-]+)").matcher(hashText);
        while (matcherHashtag.find()) {
            hashText.setSpan(new ForegroundColorSpan(Color.CYAN), matcherHashtag.start(), matcherHashtag.end(), 0);
        }
        Matcher matcherUrl = Pattern.compile("http([A-Za-z0-9://._-]+)").matcher(hashText);
        while (matcherUrl.find()) {
            hashText.setSpan(new ForegroundColorSpan(Color.GREEN), matcherUrl.start(), matcherUrl.end(), 0);
        }
        Matcher matcherUserMention = Pattern.compile("@([A-Za-z0-9_-]+)").matcher(hashText);
        while (matcherUserMention.find()) {
            hashText.setSpan(new ForegroundColorSpan(Color.RED), matcherUserMention.start(), matcherUserMention.end(), 0);
        }
        return hashText;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void addImageBitmap(Bitmap bitmap) {
        this.imageBitmap = bitmap;
    }
}
