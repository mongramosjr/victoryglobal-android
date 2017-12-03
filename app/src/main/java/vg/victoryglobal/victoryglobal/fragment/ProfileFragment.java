/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/19/17 8:50 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/18/17 7:58 PM
 */

package vg.victoryglobal.victoryglobal.fragment;

import android.app.Fragment;
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
import org.json.JSONTokener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.listener.LogoutListener;
import vg.victoryglobal.victoryglobal.model.AuthLoginRequest;
import vg.victoryglobal.victoryglobal.model.DistributorAccountResponse;
import vg.victoryglobal.victoryglobal.model.DistributorAccountRequest;
import vg.victoryglobal.victoryglobal.model.DistributorPoint;
import vg.victoryglobal.victoryglobal.utils.DateTimeFormat;
import vg.victoryglobal.victoryglobal.utils.RoundedMetricPrefixFormat;

public class ProfileFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public View currentView;

    private SwipeRefreshLayout swipeRefreshLayout;

    AuthLoginRequest authLoginRequest;

    DistributorAccountRequest distributorAccountRequest;

    // TextView
    TextView profileFullname;
    TextView profileDistributorId;
    TextView profileMlmRank;

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
    //TextView domicile;
    TextView nationality;

    TextView currentIncomeTotalAmount;

    TextView bankName;
    TextView accountNumber;

    TextView email;
    TextView phone;
    TextView fax;
    TextView mobileNumber;

    TextView address;

    LogoutListener logoutListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authLoginRequest = AuthLoginRequest.getAuthLoginRequest("main");
        distributorAccountRequest = DistributorAccountRequest.getInstance();
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
        return inflater.inflate(R.layout.profile_fragment, container, false);

    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        currentView = view;

        profileFullname = view.findViewById(R.id.profile_fullname);
        profileDistributorId = view.findViewById(R.id.profile_distributor_id);

        profileMlmRank = view.findViewById(R.id.profile_mlm_rank);

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

        if(distributorAccountRequest.isSuccess() && distributorAccountRequest.getDistributorAccountResponse() != null) {
            DistributorAccountResponse distributor_account = distributorAccountRequest.getDistributorAccountResponse();
            prepareProfile(distributor_account);
        }else{
            account(currentView);
        }


    }

    @Override
    public void onRefresh() {

        swipeRefreshLayout.setRefreshing(true);

        account(currentView);

    }


    //other methods
    private void prepareProfile(DistributorAccountResponse distributor_account)
    {
        DistributorPoint point = null;

        String full_name = authLoginRequest.getAuthLogin().getUser().frontend_label;

        String distributor_id_label =
                distributor_account.account.country_code
                + "-"
                + String.format("%09d", distributor_account.account.id);

        String mlm_rank = distributor_account.mlm_rank.frontend_label;

        profileFullname.setText(full_name);

        profileDistributorId.setText(distributor_id_label);

        profileMlmRank.setText(mlm_rank);


        DateTimeFormat dateTimeFormat = new DateTimeFormat();

        //TODO: find alternative
        RoundedMetricPrefixFormat formatter = new RoundedMetricPrefixFormat();

        NumberFormat nf = NumberFormat.getIntegerInstance(Locale.US);
        nf.setParseIntegerOnly(true);

        point = distributor_account.findDistributorPointByName("location0_balance_bv_binary");
        if(point!=null)
        {
            location0BalanceBinary.setText(formatter.format(point.value));
        }
        point = distributor_account.findDistributorPointByName("location1_balance_bv_binary");
        if(point!=null)
        {
            location1BalanceBinary.setText(formatter.format(point.value));
        }
        point = distributor_account.findDistributorPointByName("bv_binary");
        if(point!=null)
        {
            binary.setText(nf.format(point.value));
        }
        point = distributor_account.findDistributorPointByName("bv_unilevel");
        if(point!=null)
        {
            unilevel.setText(nf.format(point.value));
        }
        point = distributor_account.findDistributorPointByName("group_bv_unilevel");
        if(point!=null)
        {
            groupUnilevel.setText(nf.format(point.value));
        }

        if(distributor_account.profile != null) {
            if(distributor_account.profile.marital_status != null) {
                maritalStatus.setText(distributor_account.profile.marital_status);
            }
            if(distributor_account.profile.gender != null) {
                //R.array.gender_array
                List<String> gender_list = Arrays.asList(getResources().getStringArray(R.array.gender_array));
                String gender_name = gender_list.get(distributor_account.profile.gender);
                gender.setText(gender_name);
            }
            if(distributor_account.profile.date_of_birth != null) {
                dateOfBirth.setText(dateTimeFormat.createdTimeFormatted(distributor_account.profile.date_of_birth));
            }
            if(distributor_account.profile.place_of_birth != null) {
                placeOfBirth.setText(distributor_account.profile.place_of_birth);
            }
            if(distributor_account.profile.tax_number !=null) {
                taxNumber.setText(distributor_account.profile.tax_number);
            }
            if(distributor_account.profile.social_security_number != null) {
                socialSecurityNumber.setText(distributor_account.profile.social_security_number);
            }
            if(distributor_account.profile.spouse_name != null) {
                spouseName.setText(distributor_account.profile.spouse_name);
            }
            if(distributor_account.profile.occupation != null){
            occupation.setText(distributor_account.profile.occupation);
            }
            //domicile.setText(distributor_account.profile.domicile);
            if(distributor_account.profile.nationality != null) {
                nationality.setText(distributor_account.profile.nationality);
            }
        }

        if(distributor_account.current_income !=null) {
            if(distributor_account.current_income.total_amount == null)
                distributor_account.current_income.total_amount = 0.00f;

            DecimalFormat df = new DecimalFormat("###,##0.00");
            String total_amount = df.format(distributor_account.current_income.total_amount);
            currentIncomeTotalAmount.setText(total_amount);
        }

        if(distributor_account.bank_account != null) {
            bankName.setText(distributor_account.bank_account.bank_name);
            accountNumber.setText(distributor_account.bank_account.account_number);
        }

        if(distributor_account.contact_info != null) {
            email.setText(distributor_account.contact_info.email);
            phone.setText(distributor_account.contact_info.telephone);
            fax.setText(distributor_account.contact_info.fax);
            mobileNumber.setText(distributor_account.contact_info.mobile_number);
        }

        if(distributor_account.address != null) {
            address.setText(distributor_account.address.addressFormatted());
        }

    }

    private void accountCallback(View view, String response_data) {

        try {
            JSONObject object = (JSONObject) new JSONTokener(response_data).nextValue();
            int status = object.getInt("status");

            //Log.e("accountCallback ", "Status: " + String.valueOf(status));

            if(status == 200 ){

                if(distributorAccountRequest.saveDistributorAccount(response_data)){
                    prepareProfile(distributorAccountRequest.getDistributorAccountResponse());
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
            Log.e("ProfileFragment", "AccountCallback: (4) " + e.getMessage());
            Toast.makeText(getActivity().getApplicationContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();
        }

        swipeRefreshLayout.setRefreshing(false);

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

            Toast.makeText(getActivity().getApplicationContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();
            Log.e("ProfileResponse", ex.getMessage());

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }, 2000L);

            return;
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST,
                        url,
                        post_data,
                        new com.android.volley.Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                //Log.e("DistributorAccountResponse", "Response: " + response.toString());
                                accountCallback(view, response.toString());

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(false);
                                    }
                                }, 2000L);

                            }
                        },
                        new com.android.volley.Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Do nothing
                                Log.e("ProfileResponse", "onErrorResponse: " + error.toString());
                                Toast.makeText(getActivity().getApplicationContext(), R.string.ui_unexpected_response, Toast.LENGTH_LONG).show();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(false);
                                    }
                                }, 2000L);
                            }
                        }
                );

        // 6 minutes
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 144,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        queue.add(jsObjRequest);
    }
}
