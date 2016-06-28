package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaylie on 6/27/16.
 */

// Taking the Tweet objects and turning them into Views displayed int eh list
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {


    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context,0, tweets);
    }

    // Override and setup custom template


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the tweet
        Tweet tweet = getItem(position);
        // Find or inflate the template
        // ViewHolder pattern

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);

        }
        // Find the subivews to fill with data in teh template
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView)convertView.findViewById(R.id.tvBody);
        // populate data into subviews
        tvUserName.setText(tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());
        ivProfileImage.setImageResource(android.R.color.transparent); // clear out old image for recucleview
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);
        //return the view to be inserted into teh list
        return convertView;
    }
}
