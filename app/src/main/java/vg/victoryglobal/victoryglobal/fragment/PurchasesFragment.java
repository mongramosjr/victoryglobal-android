/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/18/17 7:58 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/18/17 7:53 PM
 */

package vg.victoryglobal.victoryglobal.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.adapter.PurchasesAdapter;
import vg.victoryglobal.victoryglobal.listener.LogoutListener;
import vg.victoryglobal.victoryglobal.model.AuthLoginRequest;
import vg.victoryglobal.victoryglobal.model.Purchase;
import vg.victoryglobal.victoryglobal.model.PurchasesRequest;

public class PurchasesFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener, PurchasesAdapter.PurchasesAdapterListener{

    public View currentView;

    private ArrayList<Purchase> purchases = new ArrayList<>();
    private PurchasesAdapter purchasesAdapter;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;


    PurchasesRequest purchasesRequest;

    AuthLoginRequest authLoginRequest;

    LogoutListener logoutListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authLoginRequest = AuthLoginRequest.getAuthLoginRequest("main");
        purchasesRequest = PurchasesRequest.getInstance();
        setRetainInstance(true);

        if(getActivity() instanceof LogoutListener){
            logoutListener = (LogoutListener) getActivity();
        }
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.purchases_fragment, container, false);

    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        currentView = view;

        recyclerView = view.findViewById(R.id.purchases_recycler_view);
        swipeRefreshLayout = view.findViewById(R.id.purchases_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        purchases = purchasesRequest.getPurchaseList();

        purchasesAdapter = new PurchasesAdapter(currentView.getContext(), purchases, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(purchasesAdapter);

        fetchPurchases();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);

        purchases(currentView);

    }

    private void fetchPurchases() {
        if(purchasesRequest.getPurchaseList().size() == 0 ){

            swipeRefreshLayout.setRefreshing(true);
            purchases(currentView);
        }
    }

    public ArrayList<Purchase> getPurchases() {
        return purchases;
    }


    private void purchasesCallback(View view, String response_data) {

        Boolean has_updates = false;

        try {
            JSONObject object = (JSONObject) new JSONTokener(response_data).nextValue();
            int status = object.getInt("status");

            //Log.e("purchasesCallback ", "Status: " + String.valueOf(status));

            if(status == 200 ){
                if(purchasesRequest.savePurchases(response_data))
                {
                    has_updates = true;
                }
            }else if(status == 402 ) {
                //TODO: redirect/show to error page
                String message = object.getString("message");
                Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }else if(status == 401 ){
                String message = object.getString("message");
                //force logout

                Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
                logoutListener.prepareLogout(authLoginRequest.getAccountLogin());

            }else{
                Toast.makeText(getActivity().getApplicationContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            //do nothing
            Log.e("PayoutReportsFragment", "purchasesCallback: (4) " + e.getMessage());
            Toast.makeText(getActivity().getApplicationContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();
        }

        if(has_updates)
        {
            if(purchasesRequest.getPurchaseList().size()>0){
                purchasesAdapter.notifyDataSetChanged();
            }
        }

        swipeRefreshLayout.setRefreshing(false);
    }


    private void purchases(final View view) {

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        String mlm_member_id = String.valueOf(authLoginRequest.getAccountLogin().getMlmMemberId());
        String session = authLoginRequest.getAccountLogin().getSession();
        String auth_token = authLoginRequest.getAccountLogin().getAuthToken();

        String url = getString(R.string.api_url) + getString(R.string.api_purchases);

        JSONObject post_data = new JSONObject();
        try {
            post_data.put("session", session);
            post_data.put("auth_token", auth_token);
            post_data.put("mlm_member_id", mlm_member_id);
        }catch(JSONException ex) {

            //callback_code.getStepperLayout().hideProgress();
            Toast.makeText(getActivity().getApplicationContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();
            Log.e("Purchase", ex.getMessage());
            return;
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST,
                        url,
                        post_data,
                        new com.android.volley.Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                //Log.e("Purchase", "Response: " + response.toString());
                                purchasesCallback(view, response.toString());
                            }
                        },
                        new com.android.volley.Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Do nothing
                                Log.e("Purchase", "onErrorResponse: " + error.toString());
                                Toast.makeText(getActivity().getApplicationContext(), R.string.ui_unexpected_response, Toast.LENGTH_LONG).show();
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }
                );

        // 6 minutes
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 144,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        queue.add(jsObjRequest);
    }


    @Override
    public void onMessageRowClicked(int position) {
        
    }

    @Override
    public void onRowLongClicked(int position) {

    }
}
