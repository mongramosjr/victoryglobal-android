/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/17/17 2:31 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/17/17 2:29 PM
 */

package vg.victoryglobal.victoryglobal.fragment;

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
import android.widget.Spinner;
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

import java.sql.Date;
import java.util.ArrayList;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.model.ActivateCode;
import vg.victoryglobal.victoryglobal.model.MlmAccountRequest;
import vg.victoryglobal.victoryglobal.model.MlmResponseError;
import vg.victoryglobal.victoryglobal.model.RegisterAccount;
import vg.victoryglobal.victoryglobal.model.RegisterAccountRequest;

public class RegisterAccountSecurity extends Fragment implements BlockingStep {

    TextInputLayout inputLayoutPassword;
    TextInputLayout inputLayoutVerifyPassword;


    TextInputEditText password;
    TextInputEditText verifyPassword;

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
        return inflater.inflate(R.layout.register_account_security, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        inputLayoutPassword = view.findViewById(R.id.password_textinputlayout);
        inputLayoutVerifyPassword = view.findViewById(R.id.verify_password_textinputlayout);


        password = view.findViewById(R.id.password);
        verifyPassword = view.findViewById(R.id.verify_password);

        //display text
        displayEnteredText();

        //display error
        displayError();

        //listener
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!registerAccountRequest.isSuccessSecurity()){
                    validateEditText(s, inputLayoutPassword, R.string.ui_no_password);
                    validatePasword(s, inputLayoutPassword, R.string.ui_valid_password);
                }
            }
        });

        verifyPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!registerAccountRequest.isSuccessSecurity()){
                    validateEditText(s, inputLayoutVerifyPassword, R.string.ui_no_verify_password);
                    validateVerifyPassword(s, inputLayoutVerifyPassword, R.string.ui_valid_verify_password);
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

    private boolean validatePasword(Editable s, TextInputLayout t, @StringRes int resId) {
        if (!TextUtils.isEmpty(s)) {
            t.setError(null);
            return true;
        }
        else{
            t.setError(getString(resId));
            return false;
        }
    }

    private boolean validateVerifyPassword(Editable s, TextInputLayout t, @StringRes int resId) {
        if (!TextUtils.isEmpty(s)) {
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

        if(validateEditText(password.getText(), inputLayoutPassword, R.string.ui_no_password)==false){
            status = false;
        }
        if(validatePasword(password.getText(), inputLayoutPassword, R.string.ui_valid_password)==false){
            status = false;
        }

        if(validateEditText(verifyPassword.getText(), inputLayoutVerifyPassword, R.string.ui_no_verify_password)==false){
            status = false;
        }
        if(validateVerifyPassword(verifyPassword.getText(), inputLayoutVerifyPassword, R.string.ui_valid_verify_password)==false){
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

        if(validateAllEditText()==false){
            return;
        }

        //reset all saved error codes
        registerAccountRequest.resetErrorCodes();

        //singleton class variable, save the encoded data
        registerAccountRequest.getRegisterAccount().setPassword(password.getText().toString());
        registerAccountRequest.getRegisterAccount().setVerifyPassword(verifyPassword.getText().toString());


        callback.getStepperLayout().showProgress(getString(R.string.progress_message));

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
            if(res.getFieldName() == "password") {
                inputLayoutPassword.setError(res.getErrMessage());
            }else if(res.getFieldName() == "confirm_password") {
                inputLayoutVerifyPassword.setError(res.getErrMessage());
            }

        }
    }

    private void displayEnteredText(){

        if(registerAccountRequest.isSuccess() && registerAccountRequest.isSuccessSecurity()){

            password.setText("");
            verifyPassword.setText("");

            registerAccountRequest.setSuccess(false);
            registerAccountRequest.setSuccessSecurity(false);
        }

        //set the text
        if(registerAccountRequest.getRegisterAccount().getPassword() != null) {
            if (registerAccountRequest.getRegisterAccount().getPassword().length() > 0) {
                password.setText(registerAccountRequest.getRegisterAccount().getPassword());
            }
        }
        if(registerAccountRequest.getRegisterAccount().getVerifyPassword() != null ) {
            if (registerAccountRequest.getRegisterAccount().getVerifyPassword().length() > 0) {
                verifyPassword.setText(registerAccountRequest.getRegisterAccount().getVerifyPassword());
            }
        }

    }

    private void accountRegistrationCheckFirstCallback(String response_data, final StepperLayout.OnNextClickedCallback callback_upgrade)
    {
        try {
            JSONObject object = (JSONObject) new JSONTokener(response_data).nextValue();
            int status = object.getInt("status");
            String message = object.getString("message");

            Log.e("RegisterAccountSec ", "Status: " + String.valueOf(status));

            if(status == 200 ){

                JSONObject activation_code = object.getJSONObject("activation_code");
                JSONObject sponsor = object.getJSONObject("sponsor");
                JSONObject upline = object.getJSONObject("upline");

                if(object.has("similar_account")) {

                    if(object.get("similar_account").toString()=="false") {
                        Log.e("MONGNGNG False", object.get("similar_account").toString());
                        //JSONArray similar_account = object.getJSONArray("similar_account");
                    }else{
                        Log.e("MONGNGNG Array", object.get("similar_account").toString());
                    }
                }


                //save
                //singleton class variable, save the encoded data
                registerAccountRequest.getRegisterAccount().setActivationCodeName(activation_code.getString("name"));
                registerAccountRequest.getRegisterAccount().setSponsorName(sponsor.getString("name"));
                registerAccountRequest.getRegisterAccount().setUplineName(upline.getString("name"));
                //registerAccountRequest.getRegisterAccount().setMlmAccountName(similar_account.getString("name"));




                // go to next page
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callback_upgrade.getStepperLayout().hideProgress();
                        callback_upgrade.goToNextStep();
                    }
                }, 2000L);



            }else if(status == 402 ){

                if(object.has("error")) {
                    String error = object.getString("error");

                    MlmResponseError err = new MlmResponseError();
                    err.setFieldName(error);
                    err.setErrMessage(message);
                    registerAccountRequest.getMlmResponseErrors().add(err);

                    //TODO: if error, go to a particular page
                    if (error.equals("activation_code")) {
                        //inputLayoutActivateCode.setError(message);
                    }
                }else{

                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callback_upgrade.getStepperLayout().hideProgress();
                    }
                }, 2000L);


            }else{
                Toast.makeText(getContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callback_upgrade.getStepperLayout().hideProgress();
                    }
                }, 2000L);
            }
        } catch (JSONException e) {
            //do nothing
            Log.e("RegisterAccountSec", "accountRegistrationCheckFirstCallback: (4) " + e.getMessage());
            Toast.makeText(getContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    callback_upgrade.getStepperLayout().hideProgress();
                }
            }, 2000L);
        }


    }


    private void accountRegistrationCheckFirst(RegisterAccount register_account, final StepperLayout.OnNextClickedCallback callback_upgrade) {

        RequestQueue queue = Volley.newRequestQueue(getContext());

        String url = getString(R.string.api_url).toString() + getString(R.string.api_account_registration_checkfirst).toString();

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

        }catch(JSONException ex) {

            callback_upgrade.getStepperLayout().hideProgress();
            Toast.makeText(getContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();
            Log.e("RegisterAccountSec", ex.getMessage());
            return;
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST,
                        url,
                        post_data,
                        new com.android.volley.Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.e("RegisterAccountSec", "Response: " + response.toString());
                                accountRegistrationCheckFirstCallback(response.toString(), callback_upgrade);
                            }
                        },
                        new com.android.volley.Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Do nothing
                                Log.e("RegisterAccountSec", "onErrorResponse: " + error.toString());
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
