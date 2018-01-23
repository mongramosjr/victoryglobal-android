/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 12/18/17 6:09 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 12/18/17 5:55 AM
 */

package vg.victoryglobal.victoryglobal.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.listener.OnProfileUpdateListener;
import vg.victoryglobal.victoryglobal.model.AuthLoginRequest;
import vg.victoryglobal.victoryglobal.model.ContactInfo;
import vg.victoryglobal.victoryglobal.model.DistributorAccountRequest;
import vg.victoryglobal.victoryglobal.model.DistributorAccountResponse;
import vg.victoryglobal.victoryglobal.model.Profile;
import vg.victoryglobal.victoryglobal.model.ProfileContactInfoEditRequestQueue;

public class ProfileContactInfoEditFragment extends DialogFragment
        implements vg.victoryglobal.victoryglobal.network.http.client.Response.JsonListener,
        vg.victoryglobal.victoryglobal.network.http.client.Response.ErrorListener{


    TextInputLayout inputLayoutEmail;
    TextInputLayout inputLayoutTelephone;
    TextInputLayout inputLayoutMobileNumber;
    TextInputLayout inputLayoutFax;

    TextInputEditText email;
    TextInputEditText telephone;
    TextInputEditText mobileNumber;
    TextInputEditText fax;

    AuthLoginRequest authLoginRequest;

    DistributorAccountRequest distributorAccountRequest;
    ProfileContactInfoEditRequestQueue profileContactInfoEditRequestQueue;

    private OnProfileUpdateListener callback;

    public ProfileContactInfoEditFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            callback = (OnProfileUpdateListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling Fragment must implement OnProfileUpdateListener");
        }

        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
        authLoginRequest = AuthLoginRequest.getAuthLoginRequest("main");
        distributorAccountRequest = DistributorAccountRequest.getInstance();
        profileContactInfoEditRequestQueue = ProfileContactInfoEditRequestQueue.getInstance();
        profileContactInfoEditRequestQueue.getRequest().url = getString(R.string.api_url) + getString(R.string.api_profilecontactinfo_update);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_contact_info_edit, container);
        getDialog().setTitle(R.string.contact_info);

        inputLayoutEmail = view.findViewById(R.id.email_textinputlayout);
        inputLayoutTelephone = view.findViewById(R.id.telephone_textinputlayout);
        inputLayoutMobileNumber = view.findViewById(R.id.mobile_number_textinputlayout);
        inputLayoutFax = view.findViewById(R.id.fax_textinputlayout);

        email = view.findViewById(R.id.email);
        telephone = view.findViewById(R.id.telephone);

        mobileNumber = view.findViewById(R.id.mobile_number);
        fax = view.findViewById(R.id.fax);

        Button dismiss = view.findViewById(R.id.action_cancel);
        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button saveBtn = view.findViewById(R.id.action_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                profileContactInfoEdit(v);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if(distributorAccountRequest.isSuccess() && distributorAccountRequest.getDistributorAccountResponse() != null) {
            DistributorAccountResponse distributor_account = distributorAccountRequest.getDistributorAccountResponse();
            prepareProfile(distributor_account);
        }
    }

    //other methods
    private void prepareProfile(DistributorAccountResponse distributor_account)
    {
        if(distributor_account.contact_info != null) {
            if(distributor_account.contact_info.email != null) {
                email.setText(distributor_account.contact_info.email);
            }
            if(distributor_account.contact_info.telephone != null) {
                telephone.setText(distributor_account.contact_info.telephone);
            }
            if(distributor_account.contact_info.fax != null) {
                fax.setText(distributor_account.contact_info.fax);
            }
            if(distributor_account.contact_info.mobile_number != null) {
                mobileNumber.setText(distributor_account.contact_info.mobile_number);
            }
        }

    }

    private void profileContactInfoEdit(final View view) {

        String mlm_member_id = String.valueOf(authLoginRequest.getAccountLogin().getMlmMemberId());
        String session = authLoginRequest.getAccountLogin().getSession();
        String auth_token = authLoginRequest.getAccountLogin().getAuthToken();

        profileContactInfoEditRequestQueue.getRequest().setAuthorization(mlm_member_id, session, auth_token);

        profileContactInfoEditRequestQueue.getRequest().addParameter("email", email.getText().toString());
        profileContactInfoEditRequestQueue.getRequest().addParameter("telephone", telephone.getText().toString());
        profileContactInfoEditRequestQueue.getRequest().addParameter("fax", fax.getText().toString());
        profileContactInfoEditRequestQueue.getRequest().addParameter("mobile_number", mobileNumber.getText().toString());

        vg.victoryglobal.victoryglobal.network.http.client.RequestQueue.NewJsonRequestQueue(
                getActivity().getApplicationContext(),
                profileContactInfoEditRequestQueue.getRequest(),
                this,
                this
        );

    }

    @Override
    public void onResponse(String responseData) {
        try {
            JSONObject object = (JSONObject) new JSONTokener(responseData).nextValue();
            int status = object.getInt("status");
            if(status == 200 ){
                profileContactInfoEditRequestQueue.onSuccess(responseData);

                DistributorAccountResponse distributor_account = distributorAccountRequest.getDistributorAccountResponse();

                if(distributor_account.contact_info == null) {
                    distributorAccountRequest.getDistributorAccountResponse().contact_info = new ContactInfo();
                }

                if(email.getText().length()>0) {
                    distributor_account.contact_info.email = email.getText().toString();
                }
                if(telephone.getText().length()>0) {
                    distributor_account.contact_info.telephone = telephone.getText().toString();
                }
                if(fax.getText().length()>0) {
                    distributor_account.contact_info.fax = fax.getText().toString();
                }
                if(mobileNumber.getText().length()>0) {
                    distributor_account.contact_info.mobile_number = mobileNumber.getText().toString();
                }
                callback.prepareProfile(distributor_account);

            }else if(status == 402 ) {
                String message = object.getString("message");
                Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }else if(status == 401 ){
                String message = object.getString("message");
                Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();

            }else{
                Toast.makeText(getActivity().getApplicationContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();
            }
        }catch (JSONException e) {
            //do nothing
            Log.e("ProfileInfoEdit", "onResponse: (4) " + e.getMessage());
            Toast.makeText(getActivity().getApplicationContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();
        }

        dismiss();

    }

    @Override
    public void onErrorResponse(String responseData, Integer statusCode) {
        Log.e("ProfileContactInfoEdit", "onErrorResponse: " + responseData);
        Toast.makeText(getActivity().getApplicationContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();
        dismiss();
    }
}
