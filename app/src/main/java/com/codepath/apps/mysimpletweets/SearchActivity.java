package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.codepath.apps.mysimpletweets.fragments.SearchTweetsFragment;

public class SearchActivity extends AppCompatActivity {

    SearchTweetsFragment newFragment;
    public String query;
    Toolbar toolbar;
    //create instance of fragment
    //statically probably because you don't need to pass arguments or change to a different search
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        toolbar = (Toolbar)findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        Log.d("DEBUG TITLE", this.getSupportActionBar().getTitle().toString());

        newFragment = new SearchTweetsFragment();
        String query = getIntent().getStringExtra("query");

        getSupportActionBar().setTitle(query);
        launchFragment(query);



    }

    public void launchFragment(String query){

        toolbar.setTitle(query);
        newFragment = new SearchTweetsFragment();
        Bundle arguments = new Bundle();
        arguments.putString("query", query);
        newFragment.setArguments(arguments);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
        ft.replace(R.id.flSearch, newFragment);
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
        ft.commit();


    }

    public void onProfileView(MenuItem mi){
        // launch the profile view
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                toolbar.setTitle(query);
                launchFragment(query);

                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }

    public void endFrag(View view) {

        getSupportFragmentManager().beginTransaction().
                remove(getSupportFragmentManager().findFragmentById(R.id.flSearch)).commit();
    }


}
