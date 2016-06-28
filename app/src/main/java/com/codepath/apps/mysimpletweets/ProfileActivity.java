package com.codepath.apps.mysimpletweets;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.codepath.apps.mysimpletweets.fragments.UserTimelineFragment;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {

    TwitterClient client;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        client = TwitterApplication.getRestClient();
        // Get the account info
        client.getUserInfo(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                user = User.fromJSON(response);
                // My current user account's info
                getSupportActionBar().setTitle(user.getScreenName());
            }
        });
        String screenName = getIntent().getStringExtra("screen_name");
        // Create the user timeline fragment
        if(savedInstanceState == null){

        }
        UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);
        // Display user timeline fragment within this activity (dynamically)
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flContainer, fragmentUserTimeline);
        ft.commit(); // changes the fragments

    }
}
