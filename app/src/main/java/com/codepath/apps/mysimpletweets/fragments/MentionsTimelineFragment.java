package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MentionsTimelineFragment extends TweetsListFragment {

    private TwitterClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Connect adapter to list view
        client = TwitterApplication.getRestClient(); // singleton client
        populateTimeline();

    }

    // Send an api request to get the timeline json
    // fill the listview by creating the tweet objects from the json
    private void populateTimeline() {
        client.getMentionsTimeline(new JsonHttpResponseHandler() {
            // success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                //JSON HERE
                // DESERIALIZE JSON
                // CREATE MODELS and add them to the adapter
                // LOAD THE MODEL DATA INTO LISTVIEW
                addAll(Tweet.fromJSONArray(json));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }


}
