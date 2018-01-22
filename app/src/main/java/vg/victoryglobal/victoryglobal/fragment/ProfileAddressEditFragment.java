/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 12/18/17 6:10 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 12/18/17 5:55 AM
 */

package vg.victoryglobal.victoryglobal.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.hendraanggrian.countrydialog.Country;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.listener.OnProfileUpdateListener;
import vg.victoryglobal.victoryglobal.listener.TextInputCountryPicker;
import vg.victoryglobal.victoryglobal.model.Address;
import vg.victoryglobal.victoryglobal.model.AuthLoginRequest;
import vg.victoryglobal.victoryglobal.model.CountryCode;
import vg.victoryglobal.victoryglobal.model.DistributorAccountRequest;
import vg.victoryglobal.victoryglobal.model.DistributorAccountResponse;
import vg.victoryglobal.victoryglobal.model.ProfileAddressEditRequestQueue;


public class ProfileAddressEditFragment extends DialogFragment
        implements vg.victoryglobal.victoryglobal.network.http.client.Response.JsonListener,
        vg.victoryglobal.victoryglobal.network.http.client.Response.ErrorListener{


    TextInputEditText street;
    TextInputEditText city;
    TextInputEditText postalCode;


    TextInputEditText countryCodePicker;
    TextInputEditText region;

    CountryCode countryCode = new CountryCode();

    AuthLoginRequest authLoginRequest;
    DistributorAccountRequest distributorAccountRequest;
    ProfileAddressEditRequestQueue profileAddressEditRequestQueue;

    private OnProfileUpdateListener callback;

    public ProfileAddressEditFragment() {
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
        profileAddressEditRequestQueue = ProfileAddressEditRequestQueue.getInstance();

        profileAddressEditRequestQueue.getRequest().url = getString(R.string.api_url) + getString(R.string.api_profileaddress_update);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_address_edit, container);
        getDialog().setTitle(R.string.address);

        street = view.findViewById(R.id.street);
        city = view.findViewById(R.id.city);
        postalCode = view.findViewById(R.id.postal_code);
        region = view.findViewById(R.id.region);
        countryCodePicker = view.findViewById(R.id.country_code);
        TextInputCountryPicker countryPicker = new TextInputCountryPicker(countryCodePicker, this.getActivity().getApplicationContext(), getActivity(), countryCode);


        final Button dismiss = view.findViewById(R.id.action_cancel);
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
                profileAddressEdit(v);
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

        if(distributor_account.address != null) {
            if(distributor_account.address.street != null) {
                street.setText(distributor_account.address.street);
            }
            if(distributor_account.address.city != null) {
                city.setText(distributor_account.address.city);
            }
            if(distributor_account.address.post_code != null) {
                postalCode.setText(String.valueOf(distributor_account.address.post_code));
            }
            if(distributor_account.address.region != null) {
                region.setText(distributor_account.address.region);
            }

            if(distributor_account.address.country_code != null && !distributor_account.address.country_code.isEmpty()) {
                Country country = Country.fromIsoCode(distributor_account.address.country_code);
                if (country != null) {
                    countryCodePicker.setText(country.getName(getActivity().getApplicationContext()));
                }
            }
        }
    }

    private void profileAddressEdit(final View view) {

        String mlm_member_id = String.valueOf(authLoginRequest.getAccountLogin().getMlmMemberId());
        String session = authLoginRequest.getAccountLogin().getSession();
        String auth_token = authLoginRequest.getAccountLogin().getAuthToken();

        profileAddressEditRequestQueue.getRequest().setAuthorization(mlm_member_id, session, auth_token);

        profileAddressEditRequestQueue.getRequest().addParameter("street", street.getText().toString());
        profileAddressEditRequestQueue.getRequest().addParameter("city", city.getText().toString());
        profileAddressEditRequestQueue.getRequest().addParameter("post_code", postalCode.getText().toString());
        profileAddressEditRequestQueue.getRequest().addParameter("region", region.getText().toString());

        if(countryCodePicker.getText().length()>0) {
            Map<String, String> countries = new HashMap<>();
            for (String iso : Locale.getISOCountries()) {
                Locale l = new Locale("en", iso);
                countries.put(l.getDisplayCountry(), iso);
            }

            String iso = countries.get(countryCodePicker.getText().toString());
            profileAddressEditRequestQueue.getRequest().addParameter("country_code", iso.toString());
        }

        vg.victoryglobal.victoryglobal.network.http.client.RequestQueue.NewJsonRequestQueue(
                getActivity().getApplicationContext(),
                profileAddressEditRequestQueue.getRequest(),
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
                profileAddressEditRequestQueue.onSuccess(responseData);

                DistributorAccountResponse distributor_account = distributorAccountRequest.getDistributorAccountResponse();

                if(distributor_account.address == null) {
                    distributorAccountRequest.getDistributorAccountResponse().address = new Address();
                }

                if(street.getText().length()>0) {
                    distributor_account.address.street = street.getText().toString();
                }
                if(city.getText().length()>0) {
                    distributor_account.address.city = city.getText().toString();
                }
                if(postalCode.getText()!=null) {
                    distributor_account.address.post_code = Integer.valueOf(postalCode.getText().toString());
                }
                if(region.getText().length()>0) {
                    distributor_account.address.region = region.getText().toString();
                }
                if(countryCodePicker.getText().length()>0) {

                    Map<String, String> countries = new HashMap<>();
                    for (String iso : Locale.getISOCountries()) {
                        Locale l = new Locale("en", iso);
                        countries.put(l.getDisplayCountry(), iso);
                    }
                    String iso = countries.get(countryCodePicker.getText().toString());
                    distributor_account.address.country_code = iso.toString();
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
            Log.e("ProfileAddressEdit", "onResponse: (4) " + e.getMessage());
            Toast.makeText(getActivity().getApplicationContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();
        }

        dismiss();
    }

    @Override
    public void onErrorResponse(String responseData, Integer statusCode) {
        Log.e("ProfileAddressEdit", "onErrorResponse: " + responseData);
        Toast.makeText(getActivity().getApplicationContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();
        dismiss();
    }
}
