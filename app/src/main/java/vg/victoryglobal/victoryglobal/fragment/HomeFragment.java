/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/10/17 1:54 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/10/17 1:54 PM
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

    private List<FbGraphFeed> feeds = new ArrayList<>();
    private RecyclerView recyclerView;
    private FbGraphFeedAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ActionMode actionMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


    /**
     * Fetches mail messages by making HTTP request
     * url: http://api.androidhive.info/json/inbox.json
     */
    private void getFeed() {
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

                for(FbGraphFeed feed : response.body().getFeed().getData()){
                    feeds.add(feed);
                }

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
