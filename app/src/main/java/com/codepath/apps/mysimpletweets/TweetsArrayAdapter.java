package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by kaylie on 6/27/16.
 */

// Taking the Tweet objects and turning them into Views displayed int eh list
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    private Context context;


    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context,0, tweets);
        this.context = context;
    }

    // Override and setup custom template


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the tweet
        final Tweet tweet = getItem(position);
        // Find or inflate the template
        // ViewHolder pattern

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);

        }
        // Find the subivews to fill with data in teh template
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvBody = (TextView)convertView.findViewById(R.id.tvBody);
        TextView tvTime = (TextView)convertView.findViewById(R.id.tvTime);
        TextView tvUserName = (TextView)convertView.findViewById(R.id.tvUserHandle);

        // populate data into subviews
        tvUserName.setText(tweet.getUser().getName());
        tvBody.setText(tweet.getBody());
        tvTime.setText(tweet.getCreatedAt());
        ivProfileImage.setImageResource(android.R.color.transparent); // clear out old image for recucleview
        tvUserName.setText(" @" + tweet.getUser().getScreenName());


        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).transform(new RoundedCornersTransformation(10,0)).into(ivProfileImage);
        ivProfileImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent profile = new Intent(getContext(), ProfileActivity.class);
                profile.putExtra("user", Parcels.wrap(tweet.getUser()));
                context.startActivity(profile);

            }

        });
        //return the view to be inserted into teh list
        return convertView;
    }
}
