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
import vg.victoryglobal.victoryglobal.model.RegisterAccount;
import vg.victoryglobal.victoryglobal.model.RegisterAccountRequest;

public class RegisterAccountSecurity extends Fragment implements BlockingStep {

    public View currentView;

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

        currentView = view;

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
                    validateVerifyPassword(s, inputLayoutVerifyPassword, password.getText(), R.string.ui_valid_verify_password);
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

    private boolean validateVerifyPassword(Editable s, TextInputLayout t,
                                           Editable passwd, @StringRes int resId) {
        if (!TextUtils.isEmpty(s)) {

            if(s.toString().equals(passwd.toString())) {
                t.setError(null);
                return true;
            }else{
                t.setError(getString(R.string.ui_valid_verify_password));
                return false;

            }
        }
        else{
            t.setError(getString(resId));
            return false;
        }
    }

    private boolean validateAllEditText() {

        boolean status = true;

        if(!validateEditText(password.getText(), inputLayoutPassword, R.string.ui_no_password)){
            status = false;
        }
        if(!validatePasword(password.getText(), inputLayoutPassword, R.string.ui_valid_password)){
            status = false;
        }

        if(!validateEditText(verifyPassword.getText(), inputLayoutVerifyPassword, R.string.ui_no_verify_password)){
            status = false;
        }
        if(!validateVerifyPassword(verifyPassword.getText(), inputLayoutVerifyPassword, password.getText(), R.string.ui_valid_verify_password)){
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
            Log.e("RegisterAccountSec", e.getMessage());
        }

        //reset all saved error codes
        registerAccountRequest.resetErrorCodes();

        //singleton class variable, save the encoded data
        registerAccountRequest.getRegisterAccount().setPassword(password.getText().toString());
        registerAccountRequest.getRegisterAccount().setVerifyPassword(verifyPassword.getText().toString());

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
            if(res.getFieldName().equals("password")) {
                inputLayoutPassword.setError(res.getErrMessage());
            }else if(res.getFieldName().equals("confirm_password")) {
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

    private void accountRegistrationCheckFirstCallback(String response_data, final StepperLayout.OnNextClickedCallback callback_register)
    {
        boolean has_similar_account = false;

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
            Log.e("RegisterAccountSec", "accountRegistrationCheckFirstCallback: (4) " + e.getMessage());
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
