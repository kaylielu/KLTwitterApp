package com.codepath.apps.mysimpletweets.models;

import android.text.format.DateUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by kaylie on 6/27/16.
 */

/*

  {
    "coordinates": null,
    "truncated": false,
    "created_at": "Tue Aug 28 21:16:23 +0000 2012",
    "favorited": false,
    "id_str": "240558470661799936",
    "in_reply_to_user_id_str": null,
    "entities": {
      "urls": [
 
      ],
      "hashtags": [
 
      ],
      "user_mentions": [
 
      ]
    },
{

}


 */

    //Parse the JSON + store the data, encapsulate state logic or display logic
@Parcel
public class Tweet {

    // list out attributes
    private String body;
    private long uid; // unique id for the tweet
    private User user; // store imbedded user object
    private String createdAt;

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public User getUser(){
        return user;
    }
    public String getCreatedAt() {
        return createdAt;
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return getTwitterVersion(relativeDate);
    }

    // Method to get only the number and the first letter of the time unit so that it looks like twitter's
    public static String getTwitterVersion(String time){
        Log.d("DEBUG", time);
        String returnTime = "";
        int numIndex = time.indexOf(' ');

        if(numIndex != -1) {
            returnTime = time.substring(0, numIndex) + time.substring(numIndex + 1, numIndex + 2);

        }else{
            returnTime = "1d";
        }
        return returnTime;

    }
    // Deserialize the JSON amd build Tweet objects
    // Tweet.fromJSON{"{...)"}-> <Tweet>
    public static Tweet fromJSON(JSONObject jsonObject){
        Tweet tweet = new Tweet();
        // Extract the values from the json, store them
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            String time = jsonObject.getString("created_at");
            tweet.createdAt = getRelativeTimeAgo(time);
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // Return the tweet object
        return tweet;

    }

    // Tweet.fromJSONArray([{..}{..}=? List<Tweet>
    public static ArrayList<Tweet>fromJSONArray(JSONArray jsonArray){
        ArrayList<Tweet>tweets = new ArrayList<>();
        // Iterate the json array and create tweets
        for( int i = 0; i <jsonArray.length(); i++){

            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                if(tweet != null){
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }

        }
        return tweets;

    }



}
