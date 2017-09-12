/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/12/17 2:08 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/10/17 4:32 PM
 */

package vg.victoryglobal.victoryglobal.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.adapter.FbGraphFeedAdapter;
import vg.victoryglobal.victoryglobal.model.facebook.FbGraph;
import vg.victoryglobal.victoryglobal.model.facebook.FbGraphFeed;
import vg.victoryglobal.victoryglobal.network.facebook.FbGraphClient;
import vg.victoryglobal.victoryglobal.network.facebook.FbGraphInterface;

import static com.facebook.FacebookSdk.getApplicationContext;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, FbGraphFeedAdapter.FacebookGraphFeedAdapterListener {

    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    private List<FbGraphFeed> feeds = new ArrayList<>();
    private RecyclerView recyclerView;
    private FbGraphFeedAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ActionMode actionMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        AppEventsLogger.activateApp(getContext(), "344323752657014");
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

        recyclerView = view.findViewById(R.id.home_recycler_view);
        swipeRefreshLayout = view.findViewById(R.id.home_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        mAdapter = new FbGraphFeedAdapter(view.getContext(), feeds, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        // show loader and fetch messages
        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        getFeed();
                    }
                }
        );



    }

    @Override
    public void onRefresh() {
        // swipe refresh is performed, fetch the messages again
        getFbAppToken();
        getFeed();
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

    private void getFbAppToken(){
        Bundle fb_bundle=  new Bundle();
        fb_bundle.putString("client_id", "344323752657014");
        fb_bundle.putString("client_secret", "536afccd5b2195e51fb935d22629d306");
        fb_bundle.putString("grant_type", "client_credentials");

                /* make the API call */
        new GraphRequest(
                null,
                "/oauth/access_token",
                fb_bundle,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
            /* handle the result */
                        Log.e("Token","Response::" + String.valueOf(response.getJSONObject()));

                    }
                },
                "2.10"

        ).executeAsync();
    }


    private void getFbGraphFeed()

    {
        //AccessToken at = AccessToken.getCurrentAccessToken();
        AccessToken at = new AccessToken("EAAE5KR73AHYBAJnvHcvEwuf7WIdSsyfyvZCG5HJZAs3h7VkYeAe9eGaHuZCwwUqmkFew01V29jIVq1vdu2GBJsEBvMpiZBygIgCZBN2IgwUh0ekW6BKMQZBaF5Mm5ZABu7cs980ZBD3iQCWi8hkhUbQZCjQJRQ67RdSXdufxpWyiqgwZDZD",
                "344323752657014", "188501987866116", null, null, null, null, null);

        //new AccessToken("beb1cb49909f66e289e31ee89eb694f5")



        Bundle fb_bundle=  new Bundle();
        fb_bundle.putString("fields", "feed{id,story,from,with_tags,icon,created_time,message,full_picture}");
        fb_bundle.putString("access_token", "beb1cb49909f66e289e31ee89eb694f5");
        //fb_bundle.putString("access_token", "344323752657014|536afccd5b2195e51fb935d22629d306");
        fb_bundle.putInt("limit", 10);

                /* make the API call */
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/188501987866116",
                fb_bundle,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
            /* handle the result */
                        Log.e("MOOOONG","Response::" + String.valueOf(response.getJSONObject()));

                    }
                },
                "2.10"

        ).executeAsync();
    }


    /**
     * Fetches mail messages by making HTTP request
     * url: http://api.androidhive.info/json/inbox.json
     */
    private void getFeed() {


        getFbAppToken();

        getFbGraphFeed();

        swipeRefreshLayout.setRefreshing(true);

        FbGraphInterface apiService =
                FbGraphClient.getClient().create(FbGraphInterface.class);

        Call<FbGraph> call = apiService.getFeed();
        call.enqueue(new Callback<FbGraph>() {
            @Override
            public void onResponse(Call<FbGraph> call, Response<FbGraph> response) {
                // clear the inbox
                feeds.clear();

                // add all the messages
                // messages.addAll(response.body());
                //feeds.addAll(response.body().getFeed().getData());

                Log.e("sdfgsdg", String.valueOf(response.raw().body().toString()));

                //for(FbGraphFeed feed : response.body().getFeed().getData()){
                //    feeds.add(feed);
                //}

                mAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }



            @Override
            public void onFailure(Call<FbGraph> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Unable to fetch json: " + t.getMessage(), Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }



}
