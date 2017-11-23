/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 10/29/17 3:56 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/22/17 9:46 AM
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
import vg.victoryglobal.victoryglobal.model.MlmAccount;
import vg.victoryglobal.victoryglobal.model.MlmAccountRequest;
import vg.victoryglobal.victoryglobal.model.MlmResponseError;
import vg.victoryglobal.victoryglobal.model.RegisterAccount;
import vg.victoryglobal.victoryglobal.model.RegisterAccountRequest;

public class RegisterAccountNew extends Fragment implements BlockingStep {

    public View currentView;

    TextInputLayout inputLayoutPassword;
    TextInputLayout inputLayoutVerifyPassword;

    TextInputLayout inputLayoutFirstName;
    TextInputLayout inputLayoutLastName;


    TextInputEditText password;
    TextInputEditText verifyPassword;

    TextInputEditText firstName;
    TextInputEditText middleName;
    TextInputEditText lastName;

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
        return inflater.inflate(R.layout.register_account_new, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        currentView = view;

        inputLayoutPassword = view.findViewById(R.id.password_textinputlayout);
        inputLayoutVerifyPassword = view.findViewById(R.id.verify_password_textinputlayout);

        inputLayoutFirstName = view.findViewById(R.id.first_name_textinputlayout);
        inputLayoutLastName = view.findViewById(R.id.last_name_textinputlayout);


        firstName = view.findViewById(R.id.first_name);
        middleName = view.findViewById(R.id.middle_name);
        lastName = view.findViewById(R.id.last_name);


        password = view.findViewById(R.id.password);
        verifyPassword = view.findViewById(R.id.verify_password);

        //display text
        displayEnteredText();

        //display error
        displayError();


        //listener
        firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!registerAccountRequest.isSuccessNew()) {
                    validateEditText(s, inputLayoutFirstName, R.string.ui_no_first_name);
                }
            }
        });

        lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!registerAccountRequest.isSuccessNew()) {
                    validateEditText(s, inputLayoutLastName, R.string.ui_no_last_name);
                }
            }
        });

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
                if(!registerAccountRequest.isSuccessNew()){
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
                if(!registerAccountRequest.isSuccessNew()){
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

        if(!validateEditText(firstName.getText(), inputLayoutFirstName, R.string.ui_no_first_name)){
            status = false;
        }
        if(!validateEditText(lastName.getText(), inputLayoutLastName, R.string.ui_no_last_name)){
            status = false;
        }

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
            Log.e("RegisterAccountNew", e.getMessage());
        }

        //reset all saved error codes
        registerAccountRequest.resetErrorCodes();

        //check mlm_account_id selection
        boolean is_name_change = false;
        if(registerAccountRequest.getRegisterAccount().getFirstName() != null) {
            String first_name = registerAccountRequest.getRegisterAccount().getFirstName();
            if(!first_name.equals(firstName.getText().toString())){
                is_name_change =  true;
            }
        }
        if(registerAccountRequest.getRegisterAccount().getLastName() != null) {
            String first_name = registerAccountRequest.getRegisterAccount().getLastName();
            if(!first_name.equals(lastName.getText().toString())){
                is_name_change =  true;
            }
        }
        if(is_name_change){
            //remove mlm_account_id selection
            mlmAccountRequest.reset();
            registerAccountRequest.getRegisterAccount().setMlmAccountId(0);
        }


        //singleton class variable, save the encoded data
        registerAccountRequest.getRegisterAccount().setFirstName(firstName.getText().toString());
        registerAccountRequest.getRegisterAccount().setMiddleName(middleName.getText().toString());
        registerAccountRequest.getRegisterAccount().setLastName(lastName.getText().toString());

        registerAccountRequest.getRegisterAccount().setPassword(password.getText().toString());
        registerAccountRequest.getRegisterAccount().setVerifyPassword(verifyPassword.getText().toString());


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.getStepperLayout().hideProgress();
                callback.goToNextStep();
            }
        }, 2000L);
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
            }else if(res.getFieldName().equals("first_name")) {
                inputLayoutFirstName.setError(res.getErrMessage());
            }else if(res.getFieldName().equals("last_name")) {
                inputLayoutLastName.setError(res.getErrMessage());
            }

        }
    }

    private void displayEnteredText(){

        if(registerAccountRequest.isSuccess() && registerAccountRequest.isSuccessNew()){

            firstName.setText("");
            middleName.setText("");
            lastName.setText("");
            password.setText("");
            verifyPassword.setText("");

            registerAccountRequest.setSuccess(false);
            registerAccountRequest.setSuccessNew(false);
        }

        //set the text
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


}
