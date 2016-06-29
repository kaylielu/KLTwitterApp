package com.codepath.apps.mysimpletweets.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cz.msebera.android.httpclient.Header;

public class ComposeTweetFragment extends Fragment {

    @BindView(R.id.btnSubmitTweet) Button submit;
    @BindView(R.id.etTweetBody) EditText tweetText;
    @BindView(R.id.tvName)
    TextView name;
    @BindView(R.id.tvScreenname)TextView screenname;
    private String tweet;
    private Unbinder unbinder;
    private TwitterClient client;

    public ComposeTweetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        //postTweet();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_compose_tweet, container, false);
        unbinder = ButterKnife.bind(this, view);
        tweetText = (EditText) view.findViewById(R.id.etTweetBody);
        Log.d("DEBUG", "onCreateView");
        return view;

    }

    @Override public void onDestroyView(){
        super.onDestroyView();
        unbinder.unbind();
    }

    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tweet = tweetText.getText().toString();
                client.setTweet(tweet);
                postTweet();
            }


        });

    }
    public void postTweet(){
        client.postTweet(new JsonHttpResponseHandler() {
            // success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                //JSON HERE
                // DESERIALIZE JSON
                // CREATE MODELS and add them to the adapter
                // LOAD THE MODEL DATA INTO LISTVIEW
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }


}
