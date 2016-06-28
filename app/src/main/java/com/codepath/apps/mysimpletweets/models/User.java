package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kaylie on 6/27/16.
 */
public class User {

    // list attributes
    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    // deserialize the user json => user
    public static User fromJSON(JSONObject json){
        // extract and fill objects from json
        User u = new User();
        try {
            u.name = json.getString("name");
            u.uid = json.getLong("id");
            u.screenName = json.getString("screen_name");
            u.profileImageUrl = json.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Return a user
        return u;
    }
}
