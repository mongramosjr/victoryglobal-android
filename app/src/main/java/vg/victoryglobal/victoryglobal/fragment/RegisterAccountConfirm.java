/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/14/17 7:41 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/13/17 8:28 PM
 */

package vg.victoryglobal.victoryglobal.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.sql.Date;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.model.ActivateCode;
import vg.victoryglobal.victoryglobal.model.MlmAccountRequest;
import vg.victoryglobal.victoryglobal.model.MlmResponseError;
import vg.victoryglobal.victoryglobal.model.RegisterAccount;
import vg.victoryglobal.victoryglobal.model.RegisterAccountRequest;
import vg.victoryglobal.victoryglobal.model.UpgradeAccountRequest;

public class RegisterAccountConfirm extends Fragment implements BlockingStep {



    TextView firstName;
    TextView lastName;
    TextView activationCode;

    TextView sponsorId;
    TextView uplineId;

    TextView password;
    TextView verifyPassword;

    TextView middleName;

    TextView dateOfBirth;
    TextView maritalStatus;
    TextView gender;
    TextView taxNumber;
    TextView socialSecurityNumber;

    TextView street;
    TextView city;
    TextView region;
    TextView postalCode;
    TextView countryCode;
    TextView email;
    TextView telephone;
    TextView mobileNumber;

    TextView mlmAccountName;
    TextView mlmAccountLabel;
    TextView mlmLocation;
    TextView pickupCenterId;

    TextView activationCodeName;
    TextView uplineName;
    TextView sponsorName;

    RegisterAccountRequest registerAccountRequest;
    MlmAccountRequest mlmAccountRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerAccountRequest = RegisterAccountRequest.getInstance();
        mlmAccountRequest = MlmAccountRequest.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.register_account_confirm, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // get View
        firstName = view.findViewById(R.id.first_name);
        lastName = view.findViewById(R.id.last_name);
        activationCode = view.findViewById(R.id.activation_code);

        sponsorId  = view.findViewById(R.id.sponsor_id);
        uplineId  = view.findViewById(R.id.upline_id);

        password  = view.findViewById(R.id.password);
        verifyPassword  = view.findViewById(R.id.verify_password);

        middleName  = view.findViewById(R.id.middle_name);

        dateOfBirth  = view.findViewById(R.id.date_of_birth);
        maritalStatus  = view.findViewById(R.id.marital_status);
        gender  = view.findViewById(R.id.gender);
        taxNumber  = view.findViewById(R.id.tax_number);
        socialSecurityNumber  = view.findViewById(R.id.social_security_number);

        street  = view.findViewById(R.id.street);
        city  = view.findViewById(R.id.city);
        region  = view.findViewById(R.id.region);
        postalCode = view.findViewById(R.id.postal_code);
        countryCode = view.findViewById(R.id.country_code);
        email = view.findViewById(R.id.email);
        telephone = view.findViewById(R.id.telephone);
        mobileNumber = view.findViewById(R.id.mobile_number);

        mlmAccountName = view.findViewById(R.id.mlm_account_name);
        mlmAccountLabel = view.findViewById(R.id.mlm_account_name_label);

        mlmLocation = view.findViewById(R.id.mlm_location);
        pickupCenterId  = view.findViewById(R.id.pickup_center_id);

        activationCodeName = view.findViewById(R.id.activation_code_name);
        uplineName = view.findViewById(R.id.upline_name);
        sponsorName = view.findViewById(R.id.sponsor_name);

        //display text
        displayEnteredText();
    }

    @Override
    public VerificationError verifyStep() {
        //return null if the user can go to the next step, create a new VerificationError instance otherwise
        return null;
    }

    @Override
    public void onSelected() {
        //update UI when selected

        //display text
        displayEnteredText();

    }

    @Override
    public void onError(@NonNull VerificationError error) {
        //handle error inside of the fragment, e.g. show error on EditText
    }

    @Override
    @UiThread
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.goToNextStep();
            }
        }, 2000L);
    }

    @Override
    @UiThread
    public void onCompleteClicked(final StepperLayout.OnCompleteClickedCallback callback) {

        callback.getStepperLayout().showProgress(getString(R.string.progress_message));

        accountRegistration(registerAccountRequest.getRegisterAccount(), callback);

    }

    @Override
    @UiThread
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }

    void displayEnteredText(){

        if(registerAccountRequest.getRegisterAccount().getFirstName() != null) {
            if (registerAccountRequest.getRegisterAccount().getFirstName().length() > 0) {
                firstName.setText(registerAccountRequest.getRegisterAccount().getFirstName());
            }
        }
        if(registerAccountRequest.getRegisterAccount().getMiddleName() != null ) {
            if (registerAccountRequest.getRegisterAccount().getMiddleName().length() > 0) {
                middleName.setText(registerAccountRequest.getRegisterAccount().getMiddleName());
            }
        }

        if(registerAccountRequest.getRegisterAccount().getLastName() != null) {
            if (registerAccountRequest.getRegisterAccount().getLastName().length() > 0) {
                lastName.setText(registerAccountRequest.getRegisterAccount().getLastName());
            }
        }
        if(registerAccountRequest.getRegisterAccount().getDateOfBirth() != null) {
            if (registerAccountRequest.getRegisterAccount().getDateOfBirth().toString().length() > 0) {
                dateOfBirth.setText(registerAccountRequest.getRegisterAccount().getDateOfBirth().toString());
            }
        }

        if(registerAccountRequest.getRegisterAccount().getGenderName() != null) {
            if (registerAccountRequest.getRegisterAccount().getGenderName().length() > 0) {
                gender.setText(registerAccountRequest.getRegisterAccount().getGenderName());
            }
        }
        if(registerAccountRequest.getRegisterAccount().getMaritalStatus() != null) {
            if (registerAccountRequest.getRegisterAccount().getMaritalStatus().length() > 0) {
                maritalStatus.setText(registerAccountRequest.getRegisterAccount().getMaritalStatus());
            }
        }
        if(registerAccountRequest.getRegisterAccount().getTaxNumber() != null) {
            if (registerAccountRequest.getRegisterAccount().getTaxNumber().length() > 0) {
                taxNumber.setText(registerAccountRequest.getRegisterAccount().getTaxNumber());
            }
        }
        if(registerAccountRequest.getRegisterAccount().getSocialSecurityNumber() != null) {
            if (registerAccountRequest.getRegisterAccount().getSocialSecurityNumber().length() > 0) {
                socialSecurityNumber.setText(registerAccountRequest.getRegisterAccount().getSocialSecurityNumber());
            }
        }

        if(registerAccountRequest.getRegisterAccount().getStreet() != null) {
            if (registerAccountRequest.getRegisterAccount().getStreet().length() > 0) {
                street.setText(registerAccountRequest.getRegisterAccount().getStreet());
            }
        }
        if(registerAccountRequest.getRegisterAccount().getCity() != null) {
            if (registerAccountRequest.getRegisterAccount().getCity().length() > 0) {
                city.setText(registerAccountRequest.getRegisterAccount().getCity());
            }
        }
        if(registerAccountRequest.getRegisterAccount().getPostalCode() != null) {
            if (registerAccountRequest.getRegisterAccount().getPostalCode().length() > 0) {
                postalCode.setText(registerAccountRequest.getRegisterAccount().getPostalCode());
            }
        }
        if(registerAccountRequest.getRegisterAccount().getRegion() != null) {
            if (registerAccountRequest.getRegisterAccount().getRegion().length() > 0) {
                region.setText(registerAccountRequest.getRegisterAccount().getRegion());
            }
        }
        if(registerAccountRequest.getRegisterAccount().getCountryCode() != null) {
            if (registerAccountRequest.getRegisterAccount().getCountryCode().length() > 0) {
                countryCode.setText(registerAccountRequest.getRegisterAccount().getCountryCode());
            }
        }

        if(registerAccountRequest.getRegisterAccount().getEmail() != null) {
            if (registerAccountRequest.getRegisterAccount().getEmail().length() > 0) {
                email.setText(registerAccountRequest.getRegisterAccount().getEmail());
            }
        }
        if(registerAccountRequest.getRegisterAccount().getTelephone() != null) {
            if (registerAccountRequest.getRegisterAccount().getTelephone().length() > 0) {
                telephone.setText(registerAccountRequest.getRegisterAccount().getTelephone());
            }
        }
        if(registerAccountRequest.getRegisterAccount().getMobileNumber() != null) {
            if (registerAccountRequest.getRegisterAccount().getMobileNumber().length() > 0) {
                mobileNumber.setText(registerAccountRequest.getRegisterAccount().getMobileNumber());
            }
        }


        if(registerAccountRequest.getRegisterAccount().getMlmAccountId() != 0 ) {
            if(registerAccountRequest.getRegisterAccount().getMlmAccountName() != null) {
                if (registerAccountRequest.getRegisterAccount().getMlmAccountName().length() > 0) {
                    mlmAccountName.setVisibility(View.VISIBLE);
                    mlmAccountLabel.setVisibility(View.VISIBLE);
                    mlmAccountName.setText(registerAccountRequest.getRegisterAccount().getMlmAccountName());
                }
            }

        }else{
            mlmAccountName.setVisibility(View.INVISIBLE);
            mlmAccountLabel.setVisibility(View.INVISIBLE);
        }

        if(registerAccountRequest.getRegisterAccount().getActivationCode() != null) {
            if (registerAccountRequest.getRegisterAccount().getActivationCode().length() > 0) {
                activationCode.setText(registerAccountRequest.getRegisterAccount().getActivationCode());
            }

            if(registerAccountRequest.getRegisterAccount().getActivationCodeName() != null) {
                if (registerAccountRequest.getRegisterAccount().getActivationCodeName().length() > 0) {
                    activationCodeName.setText(registerAccountRequest.getRegisterAccount().getActivationCodeName());
                }
            }
        }
        if(registerAccountRequest.getRegisterAccount().getUplineId() != 0 ) {
            uplineId.setText(String.valueOf(registerAccountRequest.getRegisterAccount().getUplineId()));
            if(registerAccountRequest.getRegisterAccount().getUplineName() != null) {
                if (registerAccountRequest.getRegisterAccount().getUplineName().length() > 0) {
                    uplineName.setText(registerAccountRequest.getRegisterAccount().getUplineName());
                }
            }
        }
        if(registerAccountRequest.getRegisterAccount().getSponsorId() != 0 ) {
            sponsorId.setText(String.valueOf(registerAccountRequest.getRegisterAccount().getSponsorId()));
            if(registerAccountRequest.getRegisterAccount().getSponsorName() != null) {
                if (registerAccountRequest.getRegisterAccount().getSponsorName().length() > 0) {
                    sponsorName.setText(registerAccountRequest.getRegisterAccount().getSponsorName());
                }
            }
        }

        //if(registerAccountRequest.getRegisterAccount().getMlmLocation() != 0 ) {
            //mlmLocation.setText(String.valueOf(registerAccountRequest.getRegisterAccount().getMlmLocation()));
            if(registerAccountRequest.getRegisterAccount().getMlmLocationName() != null) {
                if (registerAccountRequest.getRegisterAccount().getMlmLocationName().length() > 0) {
                    mlmLocation.setText(registerAccountRequest.getRegisterAccount().getMlmLocationName());
                }
            }
        //}

        if(registerAccountRequest.getRegisterAccount().getPickupCenterId() != 0 ) {
            if(registerAccountRequest.getRegisterAccount().getPickupCenterName() != null) {
                if (registerAccountRequest.getRegisterAccount().getPickupCenterName().length() > 0) {
                    pickupCenterId.setText(registerAccountRequest.getRegisterAccount().getPickupCenterName());
                }
            }
        }


        if(registerAccountRequest.getRegisterAccount().getPassword() != null) {
            if (registerAccountRequest.getRegisterAccount().getPassword().length() > 0) {
                password.setText(registerAccountRequest.getRegisterAccount().getPassword());
            }
        }
        if(registerAccountRequest.getRegisterAccount().getVerifyPassword() != null) {
            if(registerAccountRequest.getRegisterAccount().getVerifyPassword().length() > 0) {
                verifyPassword.setText(String.valueOf(registerAccountRequest.getRegisterAccount().getVerifyPassword()));
            }
        }

    }

    private void accountRegistrationCallback(String response_data,
                                             final StepperLayout.OnCompleteClickedCallback callback_register) {
        try {
            JSONObject object = (JSONObject) new JSONTokener(response_data).nextValue();
            int status = object.getInt("status");
            String message = object.getString("message");

            Log.e("RegisterAccountConfirm ", "Status: " + String.valueOf(status));

            if (status == 200) {

                //clear request data and error response
                registerAccountRequest.resetErrorCodes();
                registerAccountRequest.reset();
                mlmAccountRequest.reset();

                registerAccountRequest.setSuccess(true);

                registerAccountRequest.setSuccessAddressAndContact(true);
                registerAccountRequest.setSuccessPersonalInfo(true);
                registerAccountRequest.setSuccessMlmInfo(true);
                registerAccountRequest.setSuccessSecurity(true);





                // complete
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callback_register.complete();
                    }
                }, 2000L);

                Snackbar success = Snackbar.make(getView(), message, Snackbar.LENGTH_LONG);
                //message_show.setAction();
                success.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callback_register.getStepperLayout().hideProgress();
                        callback_register.getStepperLayout().setCurrentStepPosition(0);
                    }
                }, 2000L);

            } else if (status == 402) {
                Log.e("RegisterAccountConfirm", "accountRegistrationCallback: " + message);

                object.has("error");

                if (object.has("error")) {
                    String error = object.getString("error");
                    Log.e("RegisterAccountConfirm", "accountRegistrationCallback: (1) " + error + ": " + message);

                    MlmResponseError err = new MlmResponseError();
                    err.setFieldName(error);
                    err.setErrMessage(message);
                    registerAccountRequest.getMlmResponseErrors().add(err);

                    //TODO: if error, go to a particular page
                    if (error.equals("activation_code")) {
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                callback_register.getStepperLayout().hideProgress();
                                callback_register.getStepperLayout().setCurrentStepPosition(2);
                            }
                        }, 2000L);
                    }else if (error.equals("sponsor_id")) {
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                callback_register.getStepperLayout().hideProgress();
                                callback_register.getStepperLayout().setCurrentStepPosition(2);
                            }
                        }, 2000L);
                    }else if (error.equals("upline_id")) {
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                callback_register.getStepperLayout().hideProgress();
                                callback_register.getStepperLayout().setCurrentStepPosition(2);
                            }
                        }, 2000L);
                    }else if (error.equals("password")) {
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                callback_register.getStepperLayout().hideProgress();
                                callback_register.getStepperLayout().setCurrentStepPosition(3);
                            }
                        }, 2000L);
                    }else if (error.equals("first_name")) {
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                callback_register.getStepperLayout().hideProgress();
                                callback_register.getStepperLayout().setCurrentStepPosition(0);
                            }
                        }, 2000L);
                    }else if (error.equals("last_name")) {
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                callback_register.getStepperLayout().hideProgress();
                                callback_register.getStepperLayout().setCurrentStepPosition(0);
                            }
                        }, 2000L);
                    }else{
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                callback_register.getStepperLayout().hideProgress();
                                callback_register.getStepperLayout().onBackClicked();
                            }
                        }, 2000L);
                    }
                } else if (object.has("exception")) {
                    String exception = object.getString("exception");

                    Log.e("RegisterAccountConfirm", "upgradeRegistrationCallback: (2) " + exception);
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            callback_register.getStepperLayout().hideProgress();
                            callback_register.getStepperLayout().onBackClicked();
                        }
                    }, 2000L);
                }

            } else {

                Log.e("RegisterAccountConfirm", "upgradeRegistrationCallback: (3) Unexpected error");

                Toast.makeText(getContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callback_register.getStepperLayout().hideProgress();
                        callback_register.getStepperLayout().onBackClicked();
                    }
                }, 2000L);
            }
        } catch (JSONException e) {
            //do nothing
            Log.e("RegisterAccountConfirm", "upgradeRegistrationCallback: (4) " + e.getMessage());
            Toast.makeText(getContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    callback_register.getStepperLayout().hideProgress();
                    callback_register.getStepperLayout().onBackClicked();
                }
            }, 2000L);
        }


    }


    private void accountRegistration(RegisterAccount register_account, final StepperLayout.OnCompleteClickedCallback callback_register) {

        RequestQueue queue = Volley.newRequestQueue(getContext());

        String url = getString(R.string.api_url) + getString(R.string.api_account_registration);

        JSONObject post_data = new JSONObject();
        try {
            post_data.put("first_name", register_account.getFirstName());
            post_data.put("last_name", register_account.getLastName());
            post_data.put("activation_code", register_account.getActivationCode());

            post_data.put("sponsor_id", register_account.getSponsorId());
            post_data.put("upline_id", register_account.getUplineId());

            post_data.put("password", register_account.getPassword());
            //post_data.put("verify_password", register_account.getVerifyPassword());


            if(!register_account.getMiddleName().isEmpty()) {
                post_data.put("middle_name", register_account.getMiddleName());
            }

            //if(!register_account.getDateOfBirth()..isEmpty()){
            //    post_data.put("date_of_birth", register_account.getDateOfBirth());
            //}

            if(!register_account.getMaritalStatus().isEmpty()) {
                post_data.put("marital_status", register_account.getMaritalStatus());
            }

            post_data.put("gender", register_account.getGender());


            if(!register_account.getTaxNumber().isEmpty()) {
                post_data.put("tax_number", register_account.getTaxNumber());
            }
            if(!register_account.getSocialSecurityNumber().isEmpty()) {
                post_data.put("social_security_number", register_account.getSocialSecurityNumber());
            }

            if(!register_account.getStreet().isEmpty()) {
                post_data.put("street", register_account.getStreet());
            }
            if(!register_account.getCity().isEmpty()) {
                post_data.put("city", register_account.getCity());
            }
            if(!register_account.getRegion().isEmpty()) {
                post_data.put("region", register_account.getRegion());
            }
            if(!register_account.getPostalCode().isEmpty()) {
                post_data.put("postal_code", register_account.getPostalCode());
            }
            if(!register_account.getCountryCode().isEmpty()) {
                post_data.put("country_code", register_account.getCountryCode());
            }
            post_data.put("email", register_account.getEmail());
            post_data.put("telephone", register_account.getTelephone());
            post_data.put("mobile_number", register_account.getMobileNumber());

            post_data.put("mlm_account_id", register_account.getMlmAccountId());
            post_data.put("mlm_location", register_account.getMlmLocation());
            post_data.put("pickup_center_id", register_account.getPickupCenterId());

        } catch (JSONException ex) {

            callback_register.getStepperLayout().hideProgress();
            Toast.makeText(getContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();
            Log.e("RegisterAccountConfirm", ex.getMessage());
            return;
        }

        Log.e("Volley", post_data.toString());


        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, post_data, new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("RegisterAccountConfirm", "Response: " + response.toString());
                accountRegistrationCallback(response.toString(), callback_register);
            }
        }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Do nothing
                Log.e("RegisterAccountConfirm", "onErrorResponse: " + error.toString());
                callback_register.getStepperLayout().hideProgress();
                Toast.makeText(getContext(), R.string.ui_unexpected_response, Toast.LENGTH_LONG).show();

            }
        });

        // 6 minutes
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 144,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(jsObjRequest);
    }
}
