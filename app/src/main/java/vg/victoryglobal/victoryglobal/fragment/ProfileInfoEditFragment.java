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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.listener.OnProfileUpdateListener;
import vg.victoryglobal.victoryglobal.listener.TextInputDatePicker;
import vg.victoryglobal.victoryglobal.model.Address;
import vg.victoryglobal.victoryglobal.model.AuthLoginRequest;
import vg.victoryglobal.victoryglobal.model.DistributorAccountRequest;
import vg.victoryglobal.victoryglobal.model.DistributorAccountResponse;
import vg.victoryglobal.victoryglobal.model.Gender;
import vg.victoryglobal.victoryglobal.model.Profile;
import vg.victoryglobal.victoryglobal.model.ProfileInfoEditRequestQueue;
import vg.victoryglobal.victoryglobal.utils.DateTimeFormat;

public class ProfileInfoEditFragment extends DialogFragment
        implements vg.victoryglobal.victoryglobal.network.http.client.Response.JsonListener,
        vg.victoryglobal.victoryglobal.network.http.client.Response.ErrorListener{


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
    ProfileInfoEditRequestQueue profileInfoEditRequestQueue;

    private OnProfileUpdateListener callback;

    public ProfileInfoEditFragment() {
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

        profileInfoEditRequestQueue = ProfileInfoEditRequestQueue.getInstance();

        profileInfoEditRequestQueue.getRequest().url = getString(R.string.api_url) + getString(R.string.api_profileinfo_update);
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

        Button saveBtn = view.findViewById(R.id.action_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                profileInfoEdit(v);
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

    private void profileInfoEdit(final View view) {

        String mlm_member_id = String.valueOf(authLoginRequest.getAccountLogin().getMlmMemberId());
        String session = authLoginRequest.getAccountLogin().getSession();
        String auth_token = authLoginRequest.getAccountLogin().getAuthToken();

        profileInfoEditRequestQueue.getRequest().setAuthorization(mlm_member_id, session, auth_token);

        profileInfoEditRequestQueue.getRequest().addParameter("marital_status", maritalStatusSpinner.getSelectedItem().toString());
        profileInfoEditRequestQueue.getRequest().addParameter("gender", String.valueOf(genderSpinner.getSelectedItemPosition()));

        profileInfoEditRequestQueue.getRequest().addParameter("date_of_birth", dateOfBirth.getText().toString());
        profileInfoEditRequestQueue.getRequest().addParameter("place_of_birth", placeOfBirth.getText().toString());
        profileInfoEditRequestQueue.getRequest().addParameter("tax_number", taxNumber.getText().toString());
        profileInfoEditRequestQueue.getRequest().addParameter("social_security_number", socialSecurityNumber.getText().toString());
        profileInfoEditRequestQueue.getRequest().addParameter("spouse_name", spouseName.getText().toString());
        profileInfoEditRequestQueue.getRequest().addParameter("occupation", occupation.getText().toString());
        profileInfoEditRequestQueue.getRequest().addParameter("nationality", nationality.getText().toString());

        vg.victoryglobal.victoryglobal.network.http.client.RequestQueue.NewJsonRequestQueue(
                getActivity().getApplicationContext(),
                profileInfoEditRequestQueue.getRequest(),
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
                profileInfoEditRequestQueue.onSuccess(responseData);

                DistributorAccountResponse distributor_account = distributorAccountRequest.getDistributorAccountResponse();

                if(distributor_account.profile == null) {
                    distributorAccountRequest.getDistributorAccountResponse().profile = new Profile();
                }

                if(maritalStatusSpinner.isSelected()) {
                    distributor_account.profile.marital_status = maritalStatusSpinner.getSelectedItem().toString();
                }

                if(genderSpinner.isSelected()) {
                    distributor_account.profile.gender = genderSpinner.getSelectedItemPosition();
                }

                if(dateOfBirth.getText()!=null) {
                    SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-mm-dd", Locale.US);
                    Date date = new Date();
                    try {
                        date = sdformat.parse(dateOfBirth.getText().toString());
                    } catch (ParseException e) {
                        Log.e("DateTimeFormat", e.getMessage());
                    }
                    distributor_account.profile.date_of_birth = date;
                }
                if(placeOfBirth.getText().length()>0) {
                    distributor_account.profile.place_of_birth = placeOfBirth.getText().toString();
                }
                if(taxNumber.getText().length()>0) {
                    distributor_account.profile.tax_number = taxNumber.getText().toString();
                }
                if(socialSecurityNumber.getText().length()>0) {
                    distributor_account.profile.social_security_number = socialSecurityNumber.getText().toString();
                }
                if(spouseName.getText().length()>0) {
                    distributor_account.profile.spouse_name = spouseName.getText().toString();
                }
                if(occupation.getText().length()>0) {
                    distributor_account.profile.occupation = occupation.getText().toString();
                }
                if(nationality.getText().length()>0) {
                    distributor_account.profile.nationality = nationality.getText().toString();
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
        Log.e("ProfileInfoEdit", "onErrorResponse: " + responseData);
        Toast.makeText(getActivity().getApplicationContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();
        dismiss();
    }
}
