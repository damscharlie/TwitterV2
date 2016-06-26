package nl.saxion.ap.twitterv2.object;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by MindR on 25-Jun-16.
 */
public class User {
    private boolean contributors_enabled;
    private String created_at;
    private boolean default_profile;
    private boolean default_profile_image;
    private String description;
    private Entities entities;
    private int favourites_count;
    private boolean follow_request_sent;
    private boolean following;
    private int followers_count;
    private int friends_count;
    private boolean geo_enabled;
    private long id;
    private String id_str;
    private boolean is_translator;
    private boolean is_translator_enabled;
    private String language;
    private int listed_count;
    private String location;
    private String name;
    private boolean notifications;
    private String profile_background_color;
    private String profile_background_image_url;
    private String profile_background_image_url_https;
    private boolean profile_background_tile;
    private String profile_banner_url;
    private String profile_image_url;
    private String profile_image_url_https;
    private String profile_link_color;
    private String profile_sidebar_border_color;
    private String profile_sidebar_fill_color;
    private String profile_text_color;
    private boolean profile_use_background_image;
    private boolean has_extended_profile;
    private boolean protected_tweet;
    private String screen_name;
    private int statuses_count;
    private String time_zone;
    private String url;
    private int utc_offset;
    private boolean verified;
    private Bitmap imageBitmap;

    public User(JSONObject user) throws JSONException {
        this.contributors_enabled = user.getBoolean("contributors_enabled");
        this.created_at = user.getString("created_at");
        this.default_profile = user.getBoolean("default_profile");
        this.default_profile_image = user.getBoolean("default_profile_image");
        this.description = user.getString("description");
        this.entities = new Entities(user.getJSONObject("entities"));
        this.favourites_count = user.getInt("favourites_count");
        this.follow_request_sent = user.getBoolean("follow_request_sent");
        this.following = user.getBoolean("following");
        this.followers_count = user.getInt("followers_count");
        this.friends_count = user.getInt("friends_count");
        this.geo_enabled = user.getBoolean("geo_enabled");
        this.id = user.getLong("id");
        this.id_str = user.getString("id_str");
        this.is_translator = user.getBoolean("is_translator");
        this.is_translator_enabled = user.optBoolean("is_translator_enabled", false);
        this.language = user.getString("lang");
        this.listed_count = user.getInt("listed_count");
        this.location = user.getString("location");
        this.name = user.getString("name");
        this.notifications = user.getBoolean("notifications");
        this.profile_background_color = user.getString("profile_background_color");
        this.profile_background_image_url = user.getString("profile_background_image_url");
        this.profile_background_image_url_https = user.getString("profile_background_image_url_https");
        this.profile_background_tile = user.getBoolean("profile_background_tile");
        this.profile_banner_url = user.optString("profile_banner_url",null);
        this.profile_image_url = user.getString("profile_image_url");
        this.profile_image_url_https = user.getString("profile_image_url_https");
        this.profile_link_color = user.getString("profile_link_color");
        this.profile_sidebar_border_color = user.getString("profile_sidebar_border_color");
        this.profile_sidebar_fill_color = user.getString("profile_sidebar_fill_color");
        this.profile_text_color = user.getString("profile_text_color");
        this.profile_use_background_image = user.getBoolean("profile_use_background_image");
        this.has_extended_profile = user.getBoolean("has_extended_profile");
        this.protected_tweet = user.getBoolean("protected");
        this.screen_name = user.getString("screen_name");
        this.statuses_count = user.getInt("statuses_count");
        this.time_zone = user.getString("time_zone");
        this.url = user.getString("url");
        this.utc_offset = user.optInt("utc_offset", 0);
        this.verified = user.getBoolean("verified");

    }

    public boolean isContributors_enabled() {
        return contributors_enabled;
    }

    public String getCreated_at() {
        return created_at;
    }

    public boolean isDefault_profile() {
        return default_profile;
    }

    public boolean isDefault_profile_image() {
        return default_profile_image;
    }

    public String getDescription() {
        return description;
    }

    public Entities getEntities() {
        return entities;
    }

    public int getFavourites_count() {
        return favourites_count;
    }

    public boolean isFollow_request_sent() {
        return follow_request_sent;
    }

    public boolean isFollowing() {
        return following;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public int getFriends_count() {
        return friends_count;
    }

    public boolean isGeo_enabled() {
        return geo_enabled;
    }

    public long getId() {
        return id;
    }

    public String getId_str() {
        return id_str;
    }

    public boolean is_translator() {
        return is_translator;
    }

    public boolean is_translator_enabled() {
        return is_translator_enabled;
    }

    public String getLanguage() {
        return language;
    }

    public int getListed_count() {
        return listed_count;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public boolean isNotifications() {
        return notifications;
    }

    public String getProfile_background_color() {
        return profile_background_color;
    }

    public String getProfile_background_image_url() {
        return profile_background_image_url;
    }

    public String getProfile_background_image_url_https() {
        return profile_background_image_url_https;
    }

    public boolean isProfile_background_tile() {
        return profile_background_tile;
    }

    public String getProfile_banner_url() {
        return profile_banner_url;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public String getProfile_image_url_https() {
        return profile_image_url_https;
    }

    public String getProfile_link_color() {
        return profile_link_color;
    }

    public String getProfile_sidebar_border_color() {
        return profile_sidebar_border_color;
    }

    public String getProfile_sidebar_fill_color() {
        return profile_sidebar_fill_color;
    }

    public String getProfile_text_color() {
        return profile_text_color;
    }

    public boolean isProfile_use_background_image() {
        return profile_use_background_image;
    }

    public boolean isHas_extended_profile() {
        return has_extended_profile;
    }

    public boolean isProtected_tweet() {
        return protected_tweet;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public int getStatuses_count() {
        return statuses_count;
    }

    public String getTime_zone() {
        return time_zone;
    }

    public String getUrl() {
        return url;
    }

    public int getUtc_offset() {
        return utc_offset;
    }

    public boolean isVerified() {
        return verified;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void addImageBitmap(Bitmap bitmap) {
        this.imageBitmap = bitmap;
    }

}
