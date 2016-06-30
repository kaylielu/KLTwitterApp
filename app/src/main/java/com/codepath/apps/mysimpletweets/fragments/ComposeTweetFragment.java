package com.codepath.apps.mysimpletweets.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cz.msebera.android.httpclient.Header;

public class ComposeTweetFragment extends DialogFragment {

    @BindView(R.id.btnSubmitTweet)
    Button submit;
    @BindView(R.id.etTweetBody)
    EditText tweetText;
    @BindView(R.id.tvCharLeft)
    TextView charLeft;
    @BindView(R.id.tvName)
    TextView name;
    @BindView(R.id.tvScreenname)
    TextView screenname;
    private String tweet;
    private Unbinder unbinder;
    private TwitterClient client;

    public ComposeTweetFragment() {
        // Required empty public constructor
    }


    // 1. Defines the listener interface with a method passing back data result.
    public interface ComposeTweetListener {
        void onFinishComposeTweet(Tweet tweet);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        //postTweet();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compose_tweet, container, false);
        unbinder = ButterKnife.bind(this, view);
        tweetText = (EditText) view.findViewById(R.id.etTweetBody);
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public static ComposeTweetFragment newInstance(String title) {
        ComposeTweetFragment frag = new ComposeTweetFragment();
        return frag;
    }


    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        final TextWatcher mTextEditorWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //This sets a textview to the current length
                charLeft.setText(String.valueOf(140 - s.length()));
            }

            public void afterTextChanged(Editable s) {
            }
        };

        tweetText.addTextChangedListener(mTextEditorWatcher);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tweet = tweetText.getText().toString();
                postTweet();


            }


        });

    }

    public void postTweet() {

        client.postTweet(tweet, new JsonHttpResponseHandler() {
            // success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                //JSON HERE
                // DESERIALIZE JSON
                // CREATE MODELS and add them to the adapter
                // LOAD THE MODEL DATA INTO LISTVIEW

                Tweet tweet = Tweet.fromJSON(json);

                // Return input text back to activity through the implemented listener
                ComposeTweetListener listener = (ComposeTweetListener) getActivity();
                listener.onFinishComposeTweet(Tweet.fromJSON(json));
                dismiss();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject
                    errorResponse) {

                Log.d("DEBUG", errorResponse.toString());
            }
        });


    }
}
