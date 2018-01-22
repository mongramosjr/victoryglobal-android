/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 12/17/17 8:12 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 12/17/17 8:12 PM
 */

package vg.victoryglobal.victoryglobal.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.listener.TextInputDatePicker;
import vg.victoryglobal.victoryglobal.model.AuthLoginRequest;
import vg.victoryglobal.victoryglobal.model.DistributorAccountRequest;
import vg.victoryglobal.victoryglobal.model.DistributorAccountResponse;
import vg.victoryglobal.victoryglobal.model.Gender;
import vg.victoryglobal.victoryglobal.utils.DateTimeFormat;

public class ProfileInfoEditFragment extends DialogFragment {


    TextInputEditText dateOfBirth;
    TextInputEditText placeOfBirth;
    TextInputEditText occupation;

    Spinner maritalStatusSpinner;
    Spinner genderSpinner;

    String maritalStatus;
    Gender gender = new Gender();

    TextInputEditText taxNumber;
    TextInputEditText socialSecurityNumber;

    TextInputEditText nationality;
    TextInputEditText spouseName;


    ArrayAdapter<CharSequence> adapterGender;
    ArrayAdapter<CharSequence> adapterMaritalStatus;

    AuthLoginRequest authLoginRequest;

    DistributorAccountRequest distributorAccountRequest;

    public ProfileInfoEditFragment() {
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
        View view = inflater.inflate(R.layout.profile_info_edit, container);


        getDialog().setTitle(R.string.profile);

        placeOfBirth = view.findViewById(R.id.place_of_birth);
        dateOfBirth = view.findViewById(R.id.date_of_birth);
        TextInputDatePicker dateOfBirthPicker = new TextInputDatePicker(dateOfBirth, this.getActivity().getApplicationContext(), getActivity());

        occupation = view.findViewById(R.id.occupation);

        maritalStatusSpinner = view.findViewById(R.id.marital_status);
        genderSpinner = view.findViewById(R.id.gender);

        taxNumber = view.findViewById(R.id.tax_number);
        socialSecurityNumber = view.findViewById(R.id.social_security_number);

        nationality = view.findViewById(R.id.nationality);
        spouseName = view.findViewById(R.id.spouse_name);

        // Create an ArrayAdapter using the string array and a default spinner layout
        adapterGender = ArrayAdapter.createFromResource(view.getContext(),
                R.array.gender_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterGender.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        // Apply the adapter to the spinner
        genderSpinner.setAdapter(adapterGender);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                gender = new Gender(String.valueOf(pos),
                        adapterView.getItemAtPosition(pos).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Create an ArrayAdapter using the string array and a default spinner layout
        adapterMaritalStatus = ArrayAdapter.createFromResource(view.getContext(),
                R.array.marital_status_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterMaritalStatus.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        // Apply the adapter to the spinner
        maritalStatusSpinner.setAdapter(adapterMaritalStatus);

        maritalStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                maritalStatus = adapterView.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
        DateTimeFormat dateTimeFormat = new DateTimeFormat();

        if(distributor_account.profile != null) {
            if(distributor_account.profile.marital_status != null) {
                List<String> marital_status_list = Arrays.asList(getResources().getStringArray(R.array.marital_status_array));
                Integer pos = marital_status_list.indexOf(distributor_account.profile.marital_status);
                if(pos != null) {
                    maritalStatusSpinner.setSelection(pos);
                }
            }
            if(distributor_account.profile.gender != null) {
                genderSpinner.setSelection(distributor_account.profile.gender);
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


    }
}
