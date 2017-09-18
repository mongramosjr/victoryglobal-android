/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/12/17 2:08 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/10/17 4:32 PM
 */

package vg.victoryglobal.victoryglobal.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.ProfileTracker;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.adapter.FbGraphFeedAdapter;
import vg.victoryglobal.victoryglobal.model.facebook.FbGraphFeed;
import vg.victoryglobal.victoryglobal.model.facebook.FbGraphFeedRequest;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, FbGraphFeedAdapter.FacebookGraphFeedAdapterListener {

    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    private ArrayList<FbGraphFeed> feeds = new ArrayList<>();
    private RecyclerView recyclerView;
    private FbGraphFeedAdapter fbGraphFeedAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ActionMode actionMode;

    FbGraphFeedRequest fbGraphFeedRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        //AppEventsLogger.activateApp(getContext(), "344323752657014");
        fbGraphFeedRequest = FbGraphFeedRequest.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        recyclerView = view.findViewById(R.id.facebook_feeds_recycler_view);
        swipeRefreshLayout = view.findViewById(R.id.facebook_feeds_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        feeds = fbGraphFeedRequest.getFeeds();

        fbGraphFeedAdapter = new FbGraphFeedAdapter(view.getContext(), feeds, this);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(view.getContext(), 1, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(fbGraphFeedAdapter);

        getPageFeeds();
    }

    /**
     * Adding few albums for testing
     */
    private void getPageFeeds() {

        Log.e("MONNNNNNNNNGGGGGGGG", "MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");

        swipeRefreshLayout.setRefreshing(true);

        //TODO: get from server after the last id
        // 1. if empty singleton, pull from the server, save to sqlite and singleton,
        // notify adapter
        // 2. if singleton is not empty and no network, dont notify adapter
        // 3. if singleton is not empty,pull from the server starting from the last id,
        // append to sqlite and singleton if has results, notify adapter.
        // otherwise dont notify adapter

        feeds = fbGraphFeedRequest.getFeeds();

        if(feeds.size() == 0 ){

            Log.e("MONNNNNNNNNGGGGGGGG", "oooooooooooooooooooooooooooooooo");


            FbGraphFeed();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(feeds.size()>0){

                        Log.e("MONNNNNNNNNGGGGGGGG", "oooooooooooooooooooooooooooooooo");

                        fbGraphFeedAdapter.notifyDataSetChanged();
                    }
                }
            }, 6000L);


        }else{

            Log.e("MONNNNNNNNNGGGGGGGG", "--------------------++++++++++++++++-------------------");

            FbGraphFeed();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(feeds.size()>0){

                        Log.e("MONNNNNNNNNGGGGGGGG", "--------------------++++++++++++++++-------------------");

                        fbGraphFeedAdapter.notifyDataSetChanged();
                    }
                }
            }, 6000L);


        }

        swipeRefreshLayout.setRefreshing(false);
    }

    public void refreshFeeds(String next){

        Log.e("MONNNNNNNNNGGGGGGGG", "MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");

        swipeRefreshLayout.setRefreshing(true);

        //TODO: get from server using next
        if(feeds.size() == 0 ){

            Log.e("MONNNNNNNNNGGGGGGGG", "00000000000000000000000000000000000000000");

            FbGraphFeed();

            if(feeds.size()>0){
                Log.e("MONNNNNNNNNGGGGGGGG", "00000000000000000000000000000000000000000");

                fbGraphFeedAdapter.notifyDataSetChanged();
            }
        }else{

            Log.e("MONNNNNNNNNGGGGGGGG", "+++++++++++++++++++++++++++++");

            FbGraphFeed();

            if(feeds.size()>0){

                Log.e("MONNNNNNNNNGGGGGGGG", "+++++++++++++++++++++++++++++");

                fbGraphFeedAdapter.notifyDataSetChanged();
            }

        }

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {

        String next = FbGraphFeedRequest.getInstance().getNext();
        refreshFeeds(next);
    }

    @Override
    public void onIconClicked(int position) {
            }

    @Override
    public void onIconImportantClicked(int position) {

    }

    @Override
    public void onMessageRowClicked(int position) {

    }

    @Override
    public void onRowLongClicked(int position) {

    }



    private void FbGraphFeedVolleyCallback(JSONObject response_data) {
        try {
            JSONObject fb_feed = response_data.getJSONObject("posts");



        } catch (JSONException e) {
            //do nothing
            Log.e("FbGraphFeedCallback", "(4) " + e.getMessage());


        }


    }

    private void FbGraphFeedVolley(){

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = getString(R.string.api_url).toString() + getString(R.string.api_code_registration).toString();

        //url = "https://graph.facebook.com/329922417180457?fields=feed{id,story,created_time,message,full_picture,from,with_tags,icon}&access_token=EAAE5KR73AHYBABZC0ztDYf1y6Aoyrga6hEpCPJK4CV898v6mnuDQBZAv39DwOnpuPkMmnjjTpbC0s7I8bYGFXtjfjDoI0GfkBPWc0VsjVZACjCKUdidhk8odcoBFqIIM1SlgWZBRA0d2jwSqFAUC5ke7Iqwx1Uz4AnkKp0IVHwZDZD&limit=20";
        url = "https://graph.facebook.com/188501987866116?fields=posts{id,story,from,with_tags,description,type,source,created_time,message,full_picture}&access_token=EAAE5KR73AHYBABZC0ztDYf1y6Aoyrga6hEpCPJK4CV898v6mnuDQBZAv39DwOnpuPkMmnjjTpbC0s7I8bYGFXtjfjDoI0GfkBPWc0VsjVZACjCKUdidhk8odcoBFqIIM1SlgWZBRA0d2jwSqFAUC5ke7Iqwx1Uz4AnkKp0IVHwZDZD";
        //url = "https://graph.facebook.com/188501987866116";

        JSONObject post_data = new JSONObject();
        try {
            post_data.put("access_token", "EAAE5KR73AHYBABZC0ztDYf1y6Aoyrga6hEpCPJK4CV898v6mnuDQBZAv39DwOnpuPkMmnjjTpbC0s7I8bYGFXtjfjDoI0GfkBPWc0VsjVZACjCKUdidhk8odcoBFqIIM1SlgWZBRA0d2jwSqFAUC5ke7Iqwx1Uz4AnkKp0IVHwZDZD");
            post_data.put("limit", 20);
            post_data.put("fields", "posts{id,story,from,with_tags,description,type,source,created_time,message,full_picture}");
        } catch (JSONException ex) {

            Log.e("FbGraphFeedVolley", ex.getMessage());
            return;
        }

        //JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, post_data, new com.android.volley.Response.Listener<JSONObject>() {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("FbGraphFeedVolley", "Response: " + response.toString());
                FbGraphFeedVolleyCallback(response);
            }
        }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Do nothing
                Log.e("FbGraphFeedVolley", "onErrorResponse: " + error.toString());

            }
        });

        // 6 minutes
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 144,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        queue.add(jsObjRequest);

    }

    private void FbGraphFeedCallBack(GraphResponse response) {
        JSONObject graphObject = response.getJSONObject();


        if(graphObject.has("posts")){

            JSONObject graphFeed = null;
            try {
                graphFeed = graphObject.getJSONObject("posts");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(graphFeed.has("data")){
                try {
                    JSONArray graphData = graphFeed.getJSONArray("data");

                    for (int i = 0; i < graphData.length(); ++i) {
                        JSONObject feed = graphData.getJSONObject(i);



                        String id;
                        String story = null;
                        JSONObject from_obj = null;
                        JSONObject with_tags_obj = null;
                        String description = null;
                        String type = null;
                        String source = null;
                        String created_time = null;
                        String message = null;
                        String full_picture = null;
                        FbGraphFeed fbGraphFeed = new FbGraphFeed();

                        id = feed.getString("id");

                        if(feed.has("story")) {
                            story = feed.getString("story");
                        }
                        if(feed.has("with_tags")) {
                            with_tags_obj = feed.getJSONObject("from");
                            fbGraphFeed.setWithTags(fbGraphFeed.withTagsJSON(with_tags_obj));
                        }

                        if(feed.has("description")) {
                            description = feed.getString("description");
                        }

                        if(feed.has("source")) {
                            source = feed.getString("source");
                        }

                        if(feed.has("type")) {
                            type = feed.getString("type");
                        }

                        created_time = feed.getString("created_time");

                        if(feed.has("message")) {
                            message = feed.getString("message");
                        }

                        if(feed.has("full_picture")) {
                            full_picture = feed.getString("full_picture");
                        }

                        if(feed.has("from")) {
                            from_obj = feed.getJSONObject("from");
                            fbGraphFeed.setFrom(fbGraphFeed.fromJSON(from_obj));
                        }

                        fbGraphFeed.setCreatedTime(created_time);
                        fbGraphFeed.setId(id);
                        fbGraphFeed.setStory(story);
                        fbGraphFeed.setMessage(message);
                        fbGraphFeed.setFullPicture(full_picture);

                        fbGraphFeed.setDescription(description);
                        fbGraphFeed.setSource(source);
                        fbGraphFeed.setType(type);

                        fbGraphFeedRequest.getFeeds().add(fbGraphFeed);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }

        //feeds = fbGraphFeedRequest.getFeeds();

        if(feeds.size()>0) {

            Log.e("FbGraphFeed", "data: " + String.valueOf(feeds.size()));

            for (int i = feeds.size() - 2; i < feeds.size(); ++i) {
                FbGraphFeed feed = feeds.get(i);

                Log.e("FbGraphFeed", "data: " + feed.getCreatedTime());
                Log.e("FbGraphFeed", "data: " + feed.getMessage());
            }
        }

    }


    private void FbGraphFeed()

    {
        //AccessToken at = AccessToken.getCurrentAccessToken();

        AccessToken at = new AccessToken(
                "EAAE5KR73AHYBABZC0ztDYf1y6Aoyrga6hEpCPJK4CV898v6mnuDQBZAv39DwOnpuPkMmnjjTpbC0s7I8bYGFXtjfjDoI0GfkBPWc0VsjVZACjCKUdidhk8odcoBFqIIM1SlgWZBRA0d2jwSqFAUC5ke7Iqwx1Uz4AnkKp0IVHwZDZD",
                "344323752657014", "188501987866116", null, null, null, null, null);

        Bundle fb_bundle=  new Bundle();
        fb_bundle.putString("fields", "posts{id,story,from,with_tags,description,type,source,created_time,message,full_picture}");
        //fb_bundle.putString("access_token", "beb1cb49909f66e289e31ee89eb694f5");
        //fb_bundle.putString("access_token", "344323752657014|536afccd5b2195e51fb935d22629d306");
        fb_bundle.putInt("limit", 10);

        fbGraphFeedRequest.getFeeds().clear();

                /* make the API call */
        new GraphRequest(
                at,
                //"/329922417180457?fields=feed{id,story,from,with_tags,icon,created_time,message,full_picture}&limit=20",
                "188501987866116?fields=posts{id,story,from,with_tags,description,type,source,created_time,message,full_picture}",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        FbGraphFeedCallBack(response);
                    }
                }).executeAsync();
    }






}
