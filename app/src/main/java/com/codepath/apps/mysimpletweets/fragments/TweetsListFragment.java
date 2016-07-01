package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cz.msebera.android.httpclient.Header;

/**
 * Created by kaylie on 6/27/16.
 */
public class TweetsListFragment extends Fragment {

    protected ArrayList<Tweet> tweets;
    protected TweetsArrayAdapter aTweets;
    @BindView (R.id.lvTweets) ListView lvTweets;
    private Unbinder unbinder;
    // inflation logic

    public TwitterClient client;

    protected SwipeRefreshLayout swipeContainer;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        unbinder = ButterKnife.bind(this, v);
        lvTweets.setAdapter(aTweets);
        client = new TwitterClient(getContext());


        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                Log.d("DEBUG", "entered onrefresh");
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchTimelineAsync(0);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return v;

    }

    public void fetchTimelineAsync(int page) {

        Log.d("DEBUG", "entered fetchTimelineAsync");
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        client.getHomeTimeline( new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                // Remember to CLEAR OUT old items before appending in the new ones
                aTweets.clear();
                // ...the data has come back, add new items to your adapter...
                aTweets.addAll(Tweet.fromJSONArray(json));
                aTweets.notifyDataSetChanged();
                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false);
            }

            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable e) {
                Log.d("DEBUG", "Fetch timeline error: " + e.toString());
            }
        });
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<>();
        // Create the arraylist (data source)
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);

    }

    public void addAll(List<Tweet> tweets){
        aTweets.addAll(tweets);
    }


    public void clear(){
        aTweets.clear();
    }
    public void addOne(Tweet tweet){
        aTweets.insert(tweet, 0);
    }


    @Override public void onDestroyView(){
        super.onDestroyView();
        unbinder.unbind();
    }
}
