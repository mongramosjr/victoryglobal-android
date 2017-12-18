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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.hendraanggrian.countrydialog.Country;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.CountryPickerListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.listener.TextInputCountryPicker;
import vg.victoryglobal.victoryglobal.listener.TextInputDatePicker;
import vg.victoryglobal.victoryglobal.model.AuthLoginRequest;
import vg.victoryglobal.victoryglobal.model.CountryCode;
import vg.victoryglobal.victoryglobal.model.DistributorAccountRequest;
import vg.victoryglobal.victoryglobal.model.DistributorAccountResponse;
import vg.victoryglobal.victoryglobal.utils.DateTimeFormat;
import vg.victoryglobal.victoryglobal.utils.RoundedMetricPrefixFormat;

public class ProfileAddressEditFragment extends DialogFragment {


    TextInputEditText street;
    TextInputEditText city;
    TextInputEditText postalCode;


    TextInputEditText countryCodePicker;
    TextInputEditText region;

    CountryCode countryCode = new CountryCode();

    AuthLoginRequest authLoginRequest;

    DistributorAccountRequest distributorAccountRequest;

    public ProfileAddressEditFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
        authLoginRequest = AuthLoginRequest.getAuthLoginRequest("main");
        distributorAccountRequest = DistributorAccountRequest.getInstance();
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


        Button dismiss = view.findViewById(R.id.action_cancel);
        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
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
            street.setText(distributor_account.address.street);
            city.setText(distributor_account.address.city);
            postalCode.setText(distributor_account.address.post_code);
            region.setText(distributor_account.address.region);

            Country country = Country.fromIsoCode(distributor_account.address.country_code);
            if(country!=null) {
                countryCodePicker.setText(country.getName(getActivity().getApplicationContext()));
            }
        }
    }
}
