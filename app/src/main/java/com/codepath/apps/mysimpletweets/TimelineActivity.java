package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity implements ComposeTweetFragment.ComposeTweetListener {

    ComposeTweetFragment composeFragment;

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
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
        // Find the pager tabstrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip)findViewById(R.id.tabs);

        tabStrip.setViewPager(vpPager);
        // Attach the tabtrip to the viewpager

        Toolbar toolbar = (Toolbar)findViewById(R.id.timeline_toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public void onFinishComposeTweet(String inputText) {
        Toast.makeText(this, "Hi, " + inputText, Toast.LENGTH_SHORT).show();
    }


    // [] == JSONArray
    // {} == JSONObject

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
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
//        // Begin the transaction
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//// Replace the contents of the container with the new fragment
//        ft.add(R.id.flComposeTweet, composeFragment );
//// or ft.add(R.id.your_placeholder, new FooFragment());
//// Complete the changes added above
//        ft.commit();
    }

    // Return the order of the fragments in the view pager
    public class TweetsPagerAdapter extends FragmentPagerAdapter{

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
