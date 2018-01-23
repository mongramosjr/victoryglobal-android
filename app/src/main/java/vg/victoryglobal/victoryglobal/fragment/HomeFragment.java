/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/12/17 2:08 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/10/17 4:32 PM
 */

package vg.victoryglobal.victoryglobal.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import android.app.Fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.ProfileTracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.adapter.FbGraphFeedAdapter;
import vg.victoryglobal.victoryglobal.model.facebook.FbGraphFeed;
import vg.victoryglobal.victoryglobal.model.facebook.FbGraphFeedRequest;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, FbGraphFeedAdapter.FacebookGraphFeedAdapterListener {

    public View currentView;

    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    private ArrayList<FbGraphFeed> feeds = new ArrayList<>();
    private RecyclerView recyclerView;
    private FbGraphFeedAdapter fbGraphFeedAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ActionMode actionMode;

    FbGraphFeedRequest fbGraphFeedRequest;

    private Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        //AppEventsLogger.activateApp(getContext(), "344323752657014");
        fbGraphFeedRequest = FbGraphFeedRequest.getInstance();

        setRetainInstance(true);

        GenerateAppAccessToken();
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

        currentView = view;

        recyclerView = view.findViewById(R.id.facebook_feeds_recycler_view);
        swipeRefreshLayout = view.findViewById(R.id.facebook_feeds_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        feeds = fbGraphFeedRequest.getFeeds();

        fbGraphFeedAdapter = new FbGraphFeedAdapter(view.getContext(), feeds, this);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());

        int news_feeds_grid_columns = getResources().getInteger(R.integer.news_feeds_grid_columns);

        RecyclerView.LayoutManager mLayoutManager
               // = new GridLayoutManager(view.getContext(), news_feeds_grid_columns, LinearLayoutManager.VERTICAL, false);
            = new StaggeredGridLayoutManager(news_feeds_grid_columns, LinearLayoutManager.VERTICAL);

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



        if(fbGraphFeedRequest.getFeeds().size() == 0 ){

            swipeRefreshLayout.setRefreshing(true);
            FbGraphFeed();

        }else{

            //FbGraphFeed();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }, 1000L);


        }
    }

    public void refreshFeeds(String next){

        swipeRefreshLayout.setRefreshing(true);

        //NOTICE: get from server using next
        // 1. if empty singleton, pull from the server, save to sqlite and singleton,
        // notify adapter
        // 2. if singleton is not empty and no network, dont notify adapter
        // 3. if singleton is not empty,pull from the server starting from the last id,
        // append to sqlite and singleton if has results, notify adapter.
        // otherwise dont notify adapter
        if(fbGraphFeedRequest.getFeeds().size() == 0 ){
            FbGraphFeed();
        }else{
            FbGraphFeed();
        }
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

            Log.e("FbGraphFeedCallback", "Response " + fb_feed.toString());

        } catch (JSONException e) {
            //do nothing
            Log.e("FbGraphFeedCallback", "(4) " + e.getMessage());


        }


    }

    private void FbGraphFeedVolley(){

        RequestQueue queue = Volley.newRequestQueue(this.getActivity().getApplicationContext());
        String url; // = getString(R.string.api_url) + getString(R.string.api_code_registration);

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

        if(graphObject == null){

            FacebookRequestError fb_request_error = response.getError();
            Log.e("FbGraphFeedCallBack", "Error: " + fb_request_error.toString());
            swipeRefreshLayout.setRefreshing(false);

            //Note: Toast inside of GraphRequest executeAsync inside of fragment causes crashes
            final Activity activity = getActivity();
            if(activity != null) {
                Toast.makeText(activity.getApplicationContext(), R.string.ui_fb_exception, Toast.LENGTH_LONG).show();
            }
            return;
        }

        boolean has_updates = false;


        if(graphObject.has("posts")){

            JSONObject graphFeed = null;
            try {
                graphFeed = graphObject.getJSONObject("posts");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(graphFeed != null && graphFeed.has("data")){
                try {
                    JSONArray graphData = graphFeed.getJSONArray("data");

                    for (int i =  graphData.length() - 1 ; i >= 0; --i) {
                        JSONObject feed = graphData.getJSONObject(i);



                        String id;
                        String story = null;
                        JSONObject from_obj;
                        JSONObject with_tags_obj;
                        String description = null;
                        String type = null;
                        String source = null;
                        String created_time;
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

                        if(!fbGraphFeedRequest.getFeedsHsh().containsKey(id)){

                            has_updates = true;

                            fbGraphFeedRequest.getFeedsHsh().put(id, fbGraphFeed);
                            fbGraphFeedRequest.getFeeds().add(0, fbGraphFeed);
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        if(has_updates)
        {
            if(fbGraphFeedRequest.getFeeds().size()>0){

                fbGraphFeedAdapter.notifyDataSetChanged();
            }

        }

        swipeRefreshLayout.setRefreshing(false);
    }


    private void FbGraphFeed()

    {
        //AccessToken at = AccessToken.getCurrentAccessToken();

        FacebookSdk.setClientToken(getString(R.string.facebook_client_token));

        String access_token = null;

        if(fbGraphFeedRequest.getAccessToken() != null) {
            access_token = fbGraphFeedRequest.getAccessToken();
        }else{
            access_token = getString(R.string.facebook_app_id) + "|" + getString(R.string.facebook_app_secret);
        }

        AccessToken at = new AccessToken(
                access_token,
                getString(R.string.facebook_app_id), getString(R.string.facebook_page_id), null, null, null, null, null);
        String graph_path = getString(R.string.facebook_page_id) + "?fields=posts{id,story,from,with_tags,description,type,source,created_time,message,full_picture}";
        /*
        Bundle fb_bundle=  new Bundle();
        fb_bundle.putString("fields", "posts{id,story,from,with_tags,description,type,source,created_time,message,full_picture}");
        //fb_bundle.putString("access_token", "beb1cb49909f66e289e31ee89eb694f5");
        //fb_bundle.putString("access_token", "344323752657014|536afccd5b2195e51fb935d22629d306");
        fb_bundle.putInt("limit", 10);
        */

        /* make the API call */
        new GraphRequest(
                at,
                graph_path,
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        FbGraphFeedCallBack(response);
                    }
                }).executeAsync();
    }


    private void GenerateAppAccessTokenCallback(JSONObject response){
        try {
            fbGraphFeedRequest.setAccessToken(response.getString("access_token"));
        } catch (JSONException e) {
            Log.e("AppAccessToken", "error: " + e.getMessage() );
        }
    }


    private void GenerateAppAccessToken(){

        String url; // = getString(R.string.api_url) + getString(R.string.api_code_registration);

        String graph_path = "/oauth/access_token?client_id=" + getString(R.string.facebook_app_id)
                + "&client_secret=" + getString(R.string.facebook_app_secret)
                + "&grant_type=client_credentials";

        url = "https://graph.facebook.com" + graph_path;

        /*
        //NOTE: Using GraphRequest
        AccessToken at = new AccessToken(
                getString(R.string.facebook_app_id) + "|" + getString(R.string.facebook_app_secret),
                getString(R.string.facebook_app_id), getString(R.string.fb_user_id_mong), null, null, null, null, null);

        new GraphRequest(
                at,
                graph_path,
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        GenerateAppAccessTokenCallback(response);
                    }
                }).executeAsync();
        */

        //NOTE: Using Volley
        RequestQueue queue = Volley.newRequestQueue(this.getActivity().getApplicationContext());

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                GenerateAppAccessTokenCallback(response);
            }
        }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Do nothing
                Log.e("GenerateAppAccessToken", "onErrorResponse: " + error.toString());

            }
        });

        // 6 minutes
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 144,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        queue.add(jsObjRequest);


    }






}
