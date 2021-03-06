/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/14/17 7:41 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/14/17 7:38 PM
 */

package vg.victoryglobal.victoryglobal.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.adapter.RegisterAccountStepperAdapter;
import vg.victoryglobal.victoryglobal.model.MlmAccount;
import vg.victoryglobal.victoryglobal.model.MlmAccountRequest;
import vg.victoryglobal.victoryglobal.model.MlmResponseError;
import vg.victoryglobal.victoryglobal.model.PickupCenter;
import vg.victoryglobal.victoryglobal.model.PickupCenterRequest;
import vg.victoryglobal.victoryglobal.model.RegisterAccount;
import vg.victoryglobal.victoryglobal.model.RegisterAccountRequest;

public class RegisterAccountMlmInfo extends Fragment implements BlockingStep {

    public View currentView;

    TextInputLayout inputLayoutActivateCode;
    TextInputLayout inputLayoutUplineId;
    TextInputLayout inputLayoutSponsorId;

    Spinner mlmAccountId;
    TextView mlmAccountIdLabel;
    LinearLayout mlmAccountIdLayout;

    TextInputEditText activationCode;
    TextInputEditText uplineId;
    TextInputEditText sponsorId;
    Spinner mlmLocation;

    Spinner pickupCenterId;
    TextView pickupCenterIdLabel;
    LinearLayout pickupCenterIdLayout;

    RegisterAccountRequest registerAccountRequest;

    PickupCenterRequest pickupCenterRequest;

    MlmAccountRequest mlmAccountRequest;

    ArrayAdapter<String> adapterPickupCenter;
    ArrayAdapter<String> adapterMlmAccount;
    ArrayAdapter<CharSequence> adapterMlmLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerAccountRequest = RegisterAccountRequest.getInstance();
        pickupCenterRequest = PickupCenterRequest.getInstance();
        mlmAccountRequest = MlmAccountRequest.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.register_account_mlminfo, container, false);

    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        currentView = view;

        pickupCenterRequest.PickupCenters(getContext());

        // get textview and textinputlayout
        inputLayoutActivateCode = view.findViewById(R.id.activate_code_textinputlayout);
        inputLayoutUplineId = view.findViewById(R.id.upline_id_textinputlayout);
        inputLayoutSponsorId = view.findViewById(R.id.sponsor_id_textinputlayout);

        mlmAccountId = view.findViewById(R.id.mlm_account_id);
        mlmAccountIdLabel =  view.findViewById(R.id.mlm_account_id_label);
        mlmAccountIdLayout =  view.findViewById(R.id.mlm_account_id_layout);

        activationCode = view.findViewById(R.id.activation_code);
        uplineId = view.findViewById(R.id.upline_id);
        sponsorId = view.findViewById(R.id.sponsor_id);
        mlmLocation = view.findViewById(R.id.mlm_location);

        pickupCenterId = view.findViewById(R.id.pickup_center_id);
        pickupCenterIdLabel =  view.findViewById(R.id.pickup_center_id_label);
        pickupCenterIdLayout =  view.findViewById(R.id.pickup_center_id_layout);

        // Create an ArrayAdapter using the string array and a default spinner layout
        //ArrayAdapter<CharSequence> adapterMlmAccount = ArrayAdapter.createFromResource(view.getContext(),
        //        R.array.mlm_location_array, android.R.layout.simple_spinner_item);

        // Create an ArrayAdapter using the string array and a default spinner layout
        adapterMlmLocation = ArrayAdapter.createFromResource(view.getContext(),
                R.array.mlm_location_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterMlmLocation.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        // Apply the adapter to the spinner
        mlmLocation.setAdapter(adapterMlmLocation);



        // Create an ArrayAdapter using the string array and a default spinner layout
        //ArrayAdapter<CharSequence> adapterPickupCenter = ArrayAdapter.createFromResource(view.getContext(),
        //        R.array.mlm_location_array, android.R.layout.simple_spinner_item);

        //display text
        displayEnteredText();

        //display error
        displayError();


        //listener
        activationCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!registerAccountRequest.isSuccessMlmInfo()) {
                    validateEditText(s, inputLayoutActivateCode, R.string.ui_no_activation_code);
                }
            }
        });

        uplineId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!registerAccountRequest.isSuccessMlmInfo()) {
                    validateEditText(s, inputLayoutUplineId, R.string.ui_no_distributor);
                    validateAccountNumber(s, inputLayoutUplineId, R.string.ui_length_distributor);
                }
            }
        });

        sponsorId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!registerAccountRequest.isSuccessMlmInfo()) {
                    validateEditText(s, inputLayoutSponsorId, R.string.ui_no_distributor);
                    validateAccountNumber(s, inputLayoutSponsorId, R.string.ui_length_distributor);
                }
            }
        });



    }

    private boolean validateEditText(Editable s, TextInputLayout t, @StringRes int resId) {
        if (!TextUtils.isEmpty(s)) {
            t.setError(null);
            return true;
        }
        else{
            t.setError(getString(resId));
            return false;
        }
    }

    private boolean validateAccountNumber(Editable s, TextInputLayout t, @StringRes int resId) {

        if (!TextUtils.isEmpty(s) && s.toString().length() <= 9) {
            t.setError(null);
            return true;
        }
        else{
            t.setError(getString(resId));
            return false;
        }
    }

    private boolean validateAllEditText() {

        boolean status = true;
        if(!validateEditText(activationCode.getText(), inputLayoutActivateCode, R.string.ui_no_activation_code)){
            status = false;
        }
        if(!validateEditText(uplineId.getText(), inputLayoutUplineId, R.string.ui_no_distributor)){
            status = false;
        }
        if(!validateAccountNumber(sponsorId.getText(), inputLayoutSponsorId, R.string.ui_length_distributor)){
            status = false;
        }

        return status;
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

        // show if have errors
        displayError();



    }

    @Override
    public void onError(@NonNull VerificationError error) {
        //handle error inside of the fragment, e.g. show error on EditText
    }

    @Override
    @UiThread
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {

        if(!validateAllEditText()){
            return;
        }

        callback.getStepperLayout().showProgress(getString(R.string.progress_message));

        try {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow((null == getActivity().getCurrentFocus()) ? null : getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }catch (Exception e) {
            Log.e("RegisterAccountMlmInfo", e.getMessage());
        }

        registerAccountRequest.resetErrorCodes();

        //singleton class variable, save the encoded data
        if(mlmAccountId.isShown()) {
            int position_mlm_account = mlmAccountId.getSelectedItemPosition();
            String mlm_account_name = adapterMlmAccount.getItem(position_mlm_account);
            MlmAccount mlm_account = mlmAccountRequest.getMlmAccountHsh().get(mlm_account_name);
            registerAccountRequest.getRegisterAccount().setMlmAccountId(Integer.parseInt(mlm_account.getId()));
            registerAccountRequest.getRegisterAccount().setMlmAccountName(mlm_account_name);
        }else{
            registerAccountRequest.getRegisterAccount().setMlmAccountId(0);
        }

        registerAccountRequest.getRegisterAccount().setActivationCode(activationCode.getText().toString());

        registerAccountRequest.getRegisterAccount().setUplineId(Integer.parseInt(uplineId.getText().toString()));

        registerAccountRequest.getRegisterAccount().setSponsorId(Integer.parseInt(sponsorId.getText().toString()));

        int position_mlm_location = mlmLocation.getSelectedItemPosition();
        CharSequence mlm_location_name = adapterMlmLocation.getItem(position_mlm_location);
        registerAccountRequest.getRegisterAccount().setMlmLocation(position_mlm_location);
        registerAccountRequest.getRegisterAccount().setMlmLocationName(
                mlm_location_name != null ? mlm_location_name.toString() : null);

        if(pickupCenterId.isShown()) {
            int position_pickup_center = pickupCenterId.getSelectedItemPosition();
            String pickup_center_name = adapterPickupCenter.getItem(position_pickup_center);
            PickupCenter pickup_center = pickupCenterRequest.getPickupCentersHsh().get(pickup_center_name);
            registerAccountRequest.getRegisterAccount().setPickupCenterId(pickup_center.getId());
            registerAccountRequest.getRegisterAccount().setPickupCenterName(pickup_center_name);
        }

        accountRegistrationCheckFirst(registerAccountRequest.getRegisterAccount(), callback);

    }

    @Override
    @UiThread
    public void onCompleteClicked(final StepperLayout.OnCompleteClickedCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.complete();
            }
        }, 2000L);
    }

    @Override
    @UiThread
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }

    private void displayError() {

        ArrayList<MlmResponseError> mlm_response_errors = registerAccountRequest.getMlmResponseErrors();

        for (int i = 0; i < mlm_response_errors.size(); i++) {
            MlmResponseError res = mlm_response_errors.get(i);
            if(res.getFieldName().equals("upline_id")) {
                inputLayoutUplineId.setError(res.getErrMessage());
            }else if(res.getFieldName().equals("sponsor_id")) {
                inputLayoutSponsorId.setError(res.getErrMessage());
            }else if(res.getFieldName().equals("activation_code")) {
                inputLayoutActivateCode.setError(res.getErrMessage());
            }

        }
    }

    private void displayEnteredText(){

        if(registerAccountRequest.isSuccess() && registerAccountRequest.isSuccessMlmInfo()){

            mlmAccountId.setSelection(0);


            activationCode.setText("");
            uplineId.setText("");
            sponsorId.setText("");

            mlmLocation.setSelection(0);
            pickupCenterId.setSelection(0);

            registerAccountRequest.setSuccess(false);
            registerAccountRequest.setSuccessMlmInfo(false);
        }

        if(!mlmAccountRequest.getMlmAccountStr().isEmpty()) {
            mlmAccountId.setVisibility(View.VISIBLE);
            mlmAccountIdLabel.setVisibility(View.VISIBLE);
            mlmAccountIdLayout.setVisibility(View.VISIBLE);
            adapterMlmAccount = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_spinner_item, mlmAccountRequest.getMlmAccountStr());
            // Specify the layout to use when the list of choices appears
            adapterMlmAccount.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            // Apply the adapter to the spinner
            mlmAccountId.setAdapter(adapterMlmAccount);


            //set the text
            if(registerAccountRequest.getRegisterAccount().getMlmAccountId()  != null ) {

                String mlm_account_id_name = registerAccountRequest.getRegisterAccount().getMlmAccountName();
                int position = adapterMlmAccount.getPosition(mlm_account_id_name);
                mlmAccountId.setSelection(position);
            }

        }else{
            mlmAccountId.setVisibility(View.GONE);
            mlmAccountIdLabel.setVisibility(View.GONE);
            mlmAccountIdLayout.setVisibility(View.GONE);
        }

        if(!pickupCenterRequest.getPickupCentersStr().isEmpty()) {
            pickupCenterId.setVisibility(View.VISIBLE);
            pickupCenterIdLabel.setVisibility(View.VISIBLE);
            pickupCenterIdLayout.setVisibility(View.VISIBLE);
            adapterPickupCenter = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_spinner_item, pickupCenterRequest.getPickupCentersStr());
            // Specify the layout to use when the list of choices appears
            adapterPickupCenter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            // Apply the adapter to the spinner
            pickupCenterId.setAdapter(adapterPickupCenter);

            //set the text
            if(registerAccountRequest.getRegisterAccount().getPickupCenterId()  != null ) {

                String pickup_center_name = registerAccountRequest.getRegisterAccount().getPickupCenterName();
                int position = adapterPickupCenter.getPosition(pickup_center_name);
                pickupCenterId.setSelection(position);
            }

        }else{
            pickupCenterId.setVisibility(View.GONE);
            pickupCenterIdLabel.setVisibility(View.GONE);
            pickupCenterIdLayout.setVisibility(View.GONE);
        }



        if(registerAccountRequest.getRegisterAccount().getActivationCode() != null) {
            if (registerAccountRequest.getRegisterAccount().getActivationCode().length() > 0) {
                activationCode.setText(registerAccountRequest.getRegisterAccount().getActivationCode());
            }
        }
        if(registerAccountRequest.getRegisterAccount().getUplineId()  != null ) {
            uplineId.setText(String.valueOf(registerAccountRequest.getRegisterAccount().getUplineId()));
        }
        if(registerAccountRequest.getRegisterAccount().getSponsorId() != null ) {
            sponsorId.setText(String.valueOf(registerAccountRequest.getRegisterAccount().getSponsorId()));
        }

        if(registerAccountRequest.getRegisterAccount().getMlmLocation() != null) {
            String mlm_location_name = registerAccountRequest.getRegisterAccount().getMlmLocationName();
            int position = adapterMlmLocation.getPosition(mlm_location_name);
            mlmLocation.setSelection(position);
        }


    }


    private void accountRegistrationCheckFirstCallback(String response_data, final StepperLayout.OnNextClickedCallback callback_register)
    {
        boolean has_similar_account = false;

        try {
            JSONObject object = (JSONObject) new JSONTokener(response_data).nextValue();
            int status = object.getInt("status");
            String message = object.getString("message");

            Log.e("RegisterAccountMlmInfo ", "Status: " + String.valueOf(status));

            if(status == 200 ){

                JSONObject activation_code = object.getJSONObject("activation_code");
                JSONObject sponsor = object.getJSONObject("sponsor");
                JSONObject upline = object.getJSONObject("upline");

                if(object.has("similar_account")) {

                    if(object.get("similar_account").toString().equals("false")) {
                        has_similar_account = false;
                    }else{
                        JSONArray similar_account = object.getJSONArray("similar_account");

                        mlmAccountRequest.getMlmAccountStr().clear();
                        mlmAccountRequest.getMlmAccountHsh().clear();
                        mlmAccountRequest.getMlmAccounts().clear();

                        for (int i = 0; i < similar_account.length(); ++i) {
                            JSONObject similar_account_json = similar_account.getJSONObject(i);

                            MlmAccount mlm_account =  new MlmAccount(
                                    similar_account_json.getString("id"),
                                    similar_account_json.getString("first_name")
                                            + " " + similar_account_json.getString("last_name"));

                            mlmAccountRequest.getMlmAccountStr().add(
                                    similar_account_json.getString("first_name")
                                            + " " + similar_account_json.getString("last_name"));
                            mlmAccountRequest.getMlmAccountHsh().put(
                                    similar_account_json.getString("first_name")
                                            + " " + similar_account_json.getString("last_name"),
                                    mlm_account);
                            mlmAccountRequest.getMlmAccounts().add(mlm_account);
                        }
                        has_similar_account = true;
                    }
                }


                //save
                //singleton class variable, save the encoded data
                registerAccountRequest.getRegisterAccount().setActivationCodeName(activation_code.getString("name"));
                registerAccountRequest.getRegisterAccount().setSponsorName(sponsor.getString("name"));
                registerAccountRequest.getRegisterAccount().setUplineName(upline.getString("name"));
                //registerAccountRequest.getRegisterAccount().setMlmAccountName(similar_account.getString("name"));


                if(has_similar_account && registerAccountRequest.getRegisterAccount().getMlmAccountId()==0){

                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            callback_register.getStepperLayout().hideProgress();
                            RegisterAccountStepperAdapter regAcctAdptr
                                    =  (RegisterAccountStepperAdapter) callback_register.getStepperLayout().getAdapter();
                            int position = regAcctAdptr.positionStepFragment("RegisterAccountMlmInfo");
                            callback_register.getStepperLayout().setCurrentStepPosition(position);
                        }
                    }, 2000L);
                }else {

                    // go to next page
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            callback_register.getStepperLayout().hideProgress();
                            callback_register.goToNextStep();
                        }
                    }, 2000L);
                }



            }else if(status == 402 ){

                if(object.has("error")) {
                    String error = object.getString("error");

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
                                RegisterAccountStepperAdapter regAcctAdptr
                                        =  (RegisterAccountStepperAdapter) callback_register.getStepperLayout().getAdapter();
                                int position = regAcctAdptr.positionStepFragment("RegisterAccountMlmInfo");
                                callback_register.getStepperLayout().setCurrentStepPosition(position);
                            }
                        }, 2000L);
                    }else if (error.equals("sponsor_id")) {
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                callback_register.getStepperLayout().hideProgress();
                                RegisterAccountStepperAdapter regAcctAdptr
                                        =  (RegisterAccountStepperAdapter) callback_register.getStepperLayout().getAdapter();
                                int position = regAcctAdptr.positionStepFragment("RegisterAccountMlmInfo");
                                callback_register.getStepperLayout().setCurrentStepPosition(position);
                            }
                        }, 2000L);
                    }else if (error.equals("upline_id")) {
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                callback_register.getStepperLayout().hideProgress();
                                RegisterAccountStepperAdapter regAcctAdptr
                                        =  (RegisterAccountStepperAdapter) callback_register.getStepperLayout().getAdapter();
                                int position = regAcctAdptr.positionStepFragment("RegisterAccountMlmInfo");
                                callback_register.getStepperLayout().setCurrentStepPosition(position);
                            }
                        }, 2000L);
                    }else if (error.equals("password")) {
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                callback_register.getStepperLayout().hideProgress();
                                RegisterAccountStepperAdapter regAcctAdptr
                                        =  (RegisterAccountStepperAdapter) callback_register.getStepperLayout().getAdapter();
                                int position = regAcctAdptr.positionStepFragment("RegisterAccountNew");
                                callback_register.getStepperLayout().setCurrentStepPosition(position);
                            }
                        }, 2000L);
                    }else if (error.equals("first_name")) {
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                callback_register.getStepperLayout().hideProgress();
                                RegisterAccountStepperAdapter regAcctAdptr
                                        =  (RegisterAccountStepperAdapter) callback_register.getStepperLayout().getAdapter();
                                int position = regAcctAdptr.positionStepFragment("RegisterAccountNew");
                                callback_register.getStepperLayout().setCurrentStepPosition(position);
                            }
                        }, 2000L);
                    }else if (error.equals("last_name")) {
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                callback_register.getStepperLayout().hideProgress();
                                RegisterAccountStepperAdapter regAcctAdptr
                                        =  (RegisterAccountStepperAdapter) callback_register.getStepperLayout().getAdapter();
                                int position = regAcctAdptr.positionStepFragment("RegisterAccountNew");
                                callback_register.getStepperLayout().setCurrentStepPosition(position);
                            }
                        }, 2000L);
                    }else{
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                callback_register.getStepperLayout().hideProgress();
                            }
                        }, 2000L);
                    }
                }else{

                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            callback_register.getStepperLayout().hideProgress();
                        }
                    }, 2000L);
                }
            }else{
                Toast.makeText(getContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callback_register.getStepperLayout().hideProgress();
                    }
                }, 2000L);
            }
        } catch (JSONException e) {
            //do nothing
            Log.e("RegisterAccountMlmInfo", "accountRegistrationCheckFirstCallback: (4) " + e.getMessage());
            Toast.makeText(getContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    callback_register.getStepperLayout().hideProgress();
                }
            }, 2000L);
        }


    }





    private void accountRegistrationCheckFirst(RegisterAccount register_account, final StepperLayout.OnNextClickedCallback callback_upgrade) {

        RequestQueue queue = Volley.newRequestQueue(getContext());

        String url = getString(R.string.api_url) + getString(R.string.api_account_registration_checkfirst);

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

            if(register_account.getMaritalStatus() != null) {
                if (!register_account.getMaritalStatus().isEmpty()) {
                    post_data.put("marital_status", register_account.getMaritalStatus());
                }
            }

            if(register_account.getGender() != null ) {
                post_data.put("gender", register_account.getGender());
            }

            if(register_account.getTaxNumber() != null) {
                if (!register_account.getTaxNumber().isEmpty()) {
                    post_data.put("tax_number", register_account.getTaxNumber());
                }
            }

            if(register_account.getSocialSecurityNumber() != null) {
                if (!register_account.getSocialSecurityNumber().isEmpty()) {
                    post_data.put("social_security_number", register_account.getSocialSecurityNumber());
                }
            }

            if(register_account.getStreet() != null) {
                if (!register_account.getStreet().isEmpty()) {
                    post_data.put("street", register_account.getStreet());
                }
            }

            if(register_account.getCity() != null) {
                if (!register_account.getCity().isEmpty()) {
                    post_data.put("city", register_account.getCity());
                }
            }

            if(register_account.getRegion() != null) {
                if (!register_account.getRegion().isEmpty()) {
                    post_data.put("region", register_account.getRegion());
                }
            }

            if(register_account.getPostalCode() != null) {
                if (!register_account.getPostalCode().isEmpty()) {
                    post_data.put("postal_code", register_account.getPostalCode());
                }
            }

            if(register_account.getCountryCode() != null) {
                if (!register_account.getCountryCode().isEmpty()) {
                    post_data.put("country_code", register_account.getCountryCode());
                }
            }

            if(register_account.getEmail() != null) {
                if (!register_account.getEmail().isEmpty()) {
                    post_data.put("email", register_account.getEmail());
                }
            }

            if(register_account.getTelephone() != null) {
                if (!register_account.getTelephone().isEmpty()) {
                    post_data.put("telephone", register_account.getTelephone());
                }
            }
            if(register_account.getMobileNumber() != null) {
                if (!register_account.getMobileNumber().isEmpty()) {
                    post_data.put("mobile_number", register_account.getMobileNumber());
                }
            }

            post_data.put("mlm_account_id", register_account.getMlmAccountId());
            post_data.put("mlm_location", register_account.getMlmLocation());
            post_data.put("pickup_center_id", register_account.getPickupCenterId());

        }catch(JSONException ex) {

            callback_upgrade.getStepperLayout().hideProgress();
            Toast.makeText(getContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();
            Log.e("RegisterAccountMlmInfo", ex.getMessage());
            return;
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST,
                        url,
                        post_data,
                        new com.android.volley.Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.e("RegisterAccountMlmInfo", "Response: " + response.toString());
                                accountRegistrationCheckFirstCallback(response.toString(), callback_upgrade);
                            }
                        },
                        new com.android.volley.Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Do nothing
                                Log.e("RegisterAccountMlmInfo", "onErrorResponse: " + error.toString());
                                Toast.makeText(getContext(), R.string.ui_unexpected_response, Toast.LENGTH_LONG).show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        callback_upgrade.getStepperLayout().hideProgress();
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
