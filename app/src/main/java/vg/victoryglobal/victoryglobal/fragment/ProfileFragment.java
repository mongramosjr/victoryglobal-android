/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/19/17 8:50 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/18/17 7:58 PM
 */

package vg.victoryglobal.victoryglobal.fragment;

import android.app.Fragment;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.model.AuthLoginRequest;

public class ProfileFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public View currentView;

    private SwipeRefreshLayout swipeRefreshLayout;

    AuthLoginRequest authLoginRequest;

    // TextView
    TextView location0BalanceBinary;
    TextView location1BalanceBinary;
    TextView binary;
    TextView unilevel;
    TextView groupUnilevel;

    TextView maritalStatus;
    TextView gender;
    TextView dateOfBirth;
    TextView placeOfBirth;
    TextView taxNumber;
    TextView socialSecurityNumber;
    TextView spouseName;
    TextView occupation;
    TextView domicile;
    TextView nationality;

    TextView currentIncomeTotalAmount;

    TextView bankName;
    TextView accountNumber;

    TextView email;
    TextView phone;
    TextView fax;
    TextView mobileNumber;

    TextView address;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authLoginRequest = AuthLoginRequest.getAuthLoginRequest("main");
        setRetainInstance(true);
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile_fragment, container, false);

    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        currentView = view;

        location0BalanceBinary = view.findViewById(R.id.location0_balance_bv_binary);
        location1BalanceBinary = view.findViewById(R.id.location1_balance_bv_binary);
        binary = view.findViewById(R.id.bv_binary);
        unilevel = view.findViewById(R.id.bv_unilevel);
        groupUnilevel = view.findViewById(R.id.group_bv_unilevel);

        maritalStatus = view.findViewById(R.id.marital_status);
        gender = view.findViewById(R.id.gender);
        dateOfBirth = view.findViewById(R.id.date_of_birth);
        placeOfBirth = view.findViewById(R.id.place_of_birth);
        taxNumber = view.findViewById(R.id.tax_number);
        socialSecurityNumber = view.findViewById(R.id.social_security_number);
        spouseName = view.findViewById(R.id.spouse_name);
        occupation = view.findViewById(R.id.occupation);
        //domicile = view.findViewById(R.id.domicile);
        nationality = view.findViewById(R.id.nationality);

        currentIncomeTotalAmount = view.findViewById(R.id.current_income_total_amount);

        bankName = view.findViewById(R.id.bank_name);
        accountNumber = view.findViewById(R.id.account_number);

        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        fax = view.findViewById(R.id.fax);
        mobileNumber = view.findViewById(R.id.mobile_number);

        address = view.findViewById(R.id.address);


        swipeRefreshLayout = view.findViewById(R.id.profile_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);


    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);

        account(currentView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2000L);
    }

    private void accountCallback(View view, String response_data) {

    }


    private void account(final View view) {

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        String mlm_member_id = String.valueOf(authLoginRequest.getAccountLogin().getMlmMemberId());
        String session = authLoginRequest.getAccountLogin().getSession();
        String auth_token = authLoginRequest.getAccountLogin().getAuthToken();

        String url = getString(R.string.api_url) + getString(R.string.api_account);

        JSONObject post_data = new JSONObject();
        try {
            post_data.put("session", session);
            post_data.put("auth_token", auth_token);
            post_data.put("mlm_member_id", mlm_member_id);
        }catch(JSONException ex) {

            //callback_code.getStepperLayout().hideProgress();
            Toast.makeText(getActivity().getApplicationContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();
            Log.e("Profile", ex.getMessage());
            return;
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST,
                        url,
                        post_data,
                        new com.android.volley.Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.e("Profile", "Response: " + response.toString());
                                //authAccountCallback(response.toString());
                            }
                        },
                        new com.android.volley.Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Do nothing
                                Log.e("Profile", "onErrorResponse: " + error.toString());
                                Toast.makeText(getActivity().getApplicationContext(), R.string.ui_unexpected_response, Toast.LENGTH_LONG).show();
                                /*new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        callback_code.getStepperLayout().hideProgress();
                                    }
                                }, 2000L);
                                */
                            }
                        }
                );

        // 6 minutes
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 144,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        queue.add(jsObjRequest);
    }
}
