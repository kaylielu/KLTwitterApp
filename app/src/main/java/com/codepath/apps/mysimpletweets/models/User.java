package com.codepath.apps.mysimpletweets.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kaylie on 6/27/16.
 */
@org.parceler.Parcel
public class User {

    // list attributes
    public String name;
    public long uid;
    public String screenName;
    public String profileImageUrl;
    public String tagline;
    public int followersCount;
    public int followingsCount;

    protected User(Parcel in) {
        name = in.readString();
        uid = in.readLong();
        screenName = in.readString();
        profileImageUrl = in.readString();
        tagline = in.readString();
        followersCount = in.readInt();
        followingsCount = in.readInt();
    }

//    public static final Creator<User> CREATOR = new Creator<User>() {
//        @Override
//        public User createFromParcel(Parcel in) {
//            return new User(in);
//        }
//
//        @Override
//        public User[] newArray(int size) {
//            return new User[size];
//        }
//    };

    public User() {
    }

    public String getTagline() {
        return tagline;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFollowingsCount() {
        return followingsCount;
    }

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
            u.tagline = json.getString("description");
            u.followersCount = json.getInt("followers_count");
            u.followingsCount = json.getInt("friends_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Return a user
        return u;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(name);
//        dest.writeLong(uid);
//        dest.writeString(screenName);
//        dest.writeString(profileImageUrl);
//        dest.writeString(tagline);
//        dest.writeInt(followersCount);
//        dest.writeInt(followingsCount);
//
//
//    }
}
