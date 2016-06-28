package com.codepath.apps.mysimpletweets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.codepath.apps.mysimpletweets.fragments.UserTimelineFragment;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        String screenName = getIntent().getStringExtra("screen_name");
        // Create the user timeline fragment
        UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);
        // Display user fragment within this activity (dynamically)

    }
}
