package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.fragments.ComposeTweetFragment;
import com.codepath.apps.mysimpletweets.fragments.HomeTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.MentionsTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.TweetsListFragment;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity implements ComposeTweetFragment.ComposeTweetListener {

    ComposeTweetFragment composeFragment;
    SmartFragmentStatePagerAdapter newAdapter;
    // Instance of the progress action-view
    public static MenuItem miActionProgressItem;


    //@BindView(R.id.timeline_toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        composeFragment = new ComposeTweetFragment();

        // Get the viewpager
        ViewPager vpPager = (ViewPager)findViewById(R.id.viewpager);
        // Set the viewpager adapter for the adapter

        newAdapter = new TweetsPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(newAdapter);
        //newAdapter.get
        // Find the pager tabstrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip)findViewById(R.id.tabs);

        tabStrip.setViewPager(vpPager);
        // Attach the tabtrip to the viewpager

        Toolbar toolbar = (Toolbar)findViewById(R.id.timeline_toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public void onFinishComposeTweet(Tweet tweet) {
        // Inside `onActivityResult` in the main `TweetsActivity`
// Pass new tweet into the home timeline and add to top of the list
        HomeTimelineFragment fragmentHomeTweets =
                (HomeTimelineFragment) newAdapter.getRegisteredFragment(0);
        fragmentHomeTweets.addOne(tweet);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here

                Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                i.putExtra("query", query);
                startActivity(i);

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

        // Expand the search view and request focus
        searchItem.expandActionView();
        searchView.requestFocus();

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();


        return super.onOptionsItemSelected(item);

    }

    public void onProfileView(MenuItem mi){
        // launch the profile view
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    private void showComposeDialog(){

        FragmentManager fm = getSupportFragmentManager();
        ComposeTweetFragment fragment = ComposeTweetFragment.newInstance("Title");
        fragment.show(fm, "fragment?");


    }


    public void onComposeTweet(MenuItem mi){

        showComposeDialog();
//
    }




    // Return the order of the fragments in the view pager
    public class TweetsPagerAdapter extends SmartFragmentStatePagerAdapter{

        private String tabTitles[] = {"Home", "Mentions"};

        // Adapter ges the manager insert or remove fragment from activity
        public TweetsPagerAdapter(FragmentManager fm){
            super(fm);
        }

        // The order and creation of fragments within the pager
        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return new HomeTimelineFragment();
            }else if(position == 1){
                return new MentionsTimelineFragment();
            }else {
                return null;
            }
        }

        // Reform the title
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }

}
