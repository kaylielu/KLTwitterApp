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
import java.util.Calendar;
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
    private String imageUrl;

    public String getBody() {
        return body;
    }

    public String getImageUrl(){
        return imageUrl;
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

//    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
//    public static String getRelativeTimeAgo(String rawJsonDate) {
//        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
//        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
//        sf.setLenient(true);
//
//        String relativeDate = "";
//        try {
//            long dateMillis = sf.parse(rawJsonDate).getTime();
//            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
//                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        return getTimeDifference(relativeDate);
//    }

    public static String getTimeDifference(String rawJsonDate) {
        String time = "";
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat format = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        format.setLenient(true);
        try {
            long diff = (System.currentTimeMillis() - format.parse(rawJsonDate).getTime()) / 1000;
            if (diff < 5)
                time = "Just now";
            else if (diff < 60)
                time = String.format(Locale.ENGLISH, "%ds",diff);
            else if (diff < 60 * 60)
                time = String.format(Locale.ENGLISH, "%dm", diff / 60);
            else if (diff < 60 * 60 * 24)
                time = String.format(Locale.ENGLISH, "%dh", diff / (60 * 60));
            else if (diff < 60 * 60 * 24 * 30)
                time = String.format(Locale.ENGLISH, "%dd", diff / (60 * 60 * 24));
            else {
                Calendar now = Calendar.getInstance();
                Calendar then = Calendar.getInstance();
                then.setTime(format.parse(rawJsonDate));
                if (now.get(Calendar.YEAR) == then.get(Calendar.YEAR)) {
                    time = String.valueOf(then.get(Calendar.DAY_OF_MONTH)) + " "
                            + then.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US);
                } else {
                    time = String.valueOf(then.get(Calendar.DAY_OF_MONTH)) + " "
                            + then.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US)
                            + " " + String.valueOf(then.get(Calendar.YEAR) - 2000);
                }
            }
        }  catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
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
            tweet.createdAt = getTimeDifference(time);
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));

            JSONObject entities = jsonObject.getJSONObject("entities");

//            if (entities.length() != 0) {
//                tweet.imageUrl = entities.getJSONArray("media").getJSONObject(0).getString("media_url");
//                Log.d("DEBUG", tweet.imageUrl);
//            }else{
//                tweet.imageUrl = "";
//            }
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
