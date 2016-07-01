package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by kaylie on 6/27/16.
 */

// Taking the Tweet objects and turning them into Views displayed int eh list
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    private Context context;
    private TwitterClient client;

    ImageView ivRetweet;

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
        this.context = context;
        client = TwitterApplication.getRestClient();
    }

    // Override and setup custom template


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the tweet
        final Tweet tweet = getItem(position);
        // Find or inflate the template
        // ViewHolder pattern

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);

        }
        // Find the subivews to fill with data in teh template
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserHandle);
        ImageView ivMedia = (ImageView) convertView.findViewById(R.id.ivMedia);
        ivRetweet = (ImageView) convertView.findViewById(R.id.ivRetweet);
        RelativeLayout rlTweet = (RelativeLayout) convertView.findViewById(R.id.rlTweet);

        // populate data into subviews
        tvName.setText(tweet.getUser().getName());
        tvUserName.setText(tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());
        tvTime.setText(tweet.getCreatedAt());
        ivProfileImage.setImageResource(android.R.color.transparent); // clear out old image for recucleview
        tvUserName.setText(" @" + tweet.getUser().getScreenName());

        ivMedia.setImageResource(0);
        if (!TextUtils.isEmpty(tweet.getImageUrl())) {
            Picasso.with(getContext()).load(tweet.getImageUrl()).transform(new RoundedCornersTransformation(15, 0)).into(ivMedia);
        }


        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).transform(new RoundedCornersTransformation(10, 0)).into(ivProfileImage);
        ivProfileImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent profile = new Intent(getContext(), ProfileActivity.class);
                profile.putExtra("user", Parcels.wrap(tweet.getUser()));
                context.startActivity(profile);

            }

        });

        ivRetweet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ivRetweet.setImageResource(R.drawable.ic_retweeted);
                retweet(tweet.getUid());


            }

        });


        //return the view to be inserted into teh list
        return convertView;
    }

    public void retweet(long id){

        client.retweet(id, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                error.printStackTrace();
            }
        });

    }
}