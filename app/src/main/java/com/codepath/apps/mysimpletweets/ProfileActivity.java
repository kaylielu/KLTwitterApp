package com.codepath.apps.mysimpletweets;

import android.app.ProgressDialog;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.fragments.UserTimelineFragment;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ProfileActivity extends AppCompatActivity {

    TwitterClient client;
    User user;
    private Bundle savedInstanceState;
    @BindView (R.id.profile_toolbar) Toolbar toolbar;
    String screenName;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        Parcelable p = getIntent().getParcelableExtra("user");
        client = TwitterApplication.getRestClient();
        setSupportActionBar(toolbar);

//        final ProgressDialog pd = new ProgressDialog(getApplicationContext());
//        pd.setTitle("Loading...");
//        pd.setMessage("Please wait.");
//        pd.setCancelable(false);
//
//        pd.show();
        if (p == null) {

            // Get the account info
            client.getUserInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    user = User.fromJSON(response);
                    Log.d("USER", user.getName() + " " + user.getProfileImageUrl() + " " + user.getTagline());
                    populateProfileHeader(user, savedInstanceState);

//                    pd.dismiss();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    throwable.printStackTrace();
//                    pd.dismiss();
                }
            });

        }else{

            Log.d("DEBUG", "clicked on other profile");
            user = Parcels.unwrap(p);
            populateProfileHeader(user, savedInstanceState);
//            pd.dismiss();

        }



        // My current user account's info




    }

    private void populateProfileHeader(User user, Bundle savedInstanceState) {
        this.user = user;
        this.savedInstanceState = savedInstanceState;
        if(getSupportActionBar()!= null){
            getSupportActionBar().setTitle("@" + user.getScreenName());
        }

        screenName = user.getScreenName();
        // Create the user timeline fragment
        if(savedInstanceState == null) {


            UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);
            // Display user timeline fragment within this activity (dynamically)
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, fragmentUserTimeline);
            ft.commit(); // changes the fragments
        }



        TextView tvName = (TextView) findViewById(R.id.tvFullName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);

        ImageView ivBackgroundImage = (ImageView) findViewById(R.id.ivBackground);
        Picasso.with(this).load(user.getBackgroundImageUrl()).into(ivBackgroundImage);
        tvName.setText(user.getName());
        tvTagline.setText(user.getTagline());
        tvFollowers.setText(user.getFollowersCount() + " Followers");
        tvFollowing.setText(user.getFollowingsCount() + " Following");
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        ivProfileImage.setVisibility(View.VISIBLE);
        Picasso.with(getApplicationContext()).load(user.getProfileImageUrl()).transform(new RoundedCornersTransformation(10,0)).into(ivProfileImage);

    }
}
