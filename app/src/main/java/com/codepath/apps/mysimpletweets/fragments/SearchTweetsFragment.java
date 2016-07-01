package com.codepath.apps.mysimpletweets.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by kaylie on 6/27/16.
 */
public class SearchTweetsFragment extends TweetsListFragment{

    private TwitterClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String query = getArguments().getString("query");

        Log.d("DEBUG", query);
        // Connect adapter to list view
        client = TwitterApplication.getRestClient(); // singleton client
        performSearch(query);

    }


    // Send an api request to get the timeline json
    // fill the listview by creating the tweet objects from the json
    private void performSearch(String query) {

        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("Loading...");
        pd.setMessage("Please wait.");
        pd.setCancelable(false);


        pd.show();

        Log.d("DEBUG", "search tweets");
        client.searchTweets(query, new JsonHttpResponseHandler() {
            // success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("DEBUG", json.toString());
                clear();
                //JSON HERE
                // DESERIALIZE JSON
                // CREATE MODELS and add them to the adapter
                // LOAD THE MODEL DATA INTO LISTVIEW
                addAll(Tweet.fromJSONArray(json));
                pd.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                pd.dismiss();
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }

}
