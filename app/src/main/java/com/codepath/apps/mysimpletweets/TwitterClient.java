package com.codepath.apps.mysimpletweets;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "OYmVEPYWXqf1TbHOT3bBoScZy";       // Change this
	public static final String REST_CONSUMER_SECRET = "AoYpDXcvccVdQn1wnkEkbM8Xha8ZvMwZMGhoTDt92t8vy7myIz"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://klsimpletweets"; // Change this (here and in manifest)


	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}


	// METHOD - ENDPOINT
	// HOME TIMELINE - Gets us the home timeline

	//Get home timeline https://api.twitter.com/1.1/statuses/home_timeline.json
	//count=25
	//since_id=1
	public void getHomeTimeline(AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		//Specify the params
		RequestParams params = new RequestParams();
		params.put("count", 25);
		params.put("since_id", 1);
		// Execute the request
		getClient().get(apiUrl, params, handler);

	}

	public void getMentionsTimeline(AsyncHttpResponseHandler handler ) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		//Specify the params
		RequestParams params = new RequestParams();
		params.put("count", 25);
		// Execute the request
		getClient().get(apiUrl, params, handler);
	}

	public void getUserTimeline(String screenName, AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count",25);
		params.put("screen_name", screenName);
		getClient().get(apiUrl, params, handler);

	}

	public void getUserInfo(AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("account/verify_credentials.json");
		getClient().get(apiUrl, null, handler);

	}
	// Compose tweet

	public void postTweet(String tweet, AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status", tweet);
		getClient().post(apiUrl, params, handler);
	}

	public void searchTweets(String query, AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("search/tweets.json");
		RequestParams params = new RequestParams();
		params.put("q", query);
		getClient().get(apiUrl, params, handler);
	}

	public void retweet(long uid, AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/retweet/" + uid + ".json");
		Log.d("DEBUG", apiUrl);
		getClient().post(apiUrl, handler);

	}


	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}