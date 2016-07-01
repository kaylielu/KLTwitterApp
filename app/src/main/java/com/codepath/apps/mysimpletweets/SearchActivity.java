package com.codepath.apps.mysimpletweets;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.codepath.apps.mysimpletweets.fragments.SearchTweetsFragment;

public class SearchActivity extends AppCompatActivity {

    SearchTweetsFragment newFragment;
    //create instance of fragment
    //statically probably because you don't need to pass arguments or change to a different search
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        if (savedInstanceState == null) {
            newFragment = new SearchTweetsFragment();
            Bundle arguments = new Bundle();
            arguments.putString("query", (getIntent().getStringExtra("query")));
            newFragment.setArguments(arguments);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
            ft.replace(R.id.flSearch, newFragment);
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
            ft.commit();

        }
    }
}
