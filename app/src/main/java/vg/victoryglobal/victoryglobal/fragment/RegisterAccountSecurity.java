/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/9/17 9:19 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/9/17 9:19 PM
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.sql.Date;
import java.util.ArrayList;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.model.MlmResponseError;
import vg.victoryglobal.victoryglobal.model.RegisterAccountRequest;

public class RegisterAccountSecurity extends Fragment implements BlockingStep {

    TextInputLayout inputLayoutPassword;
    TextInputLayout inputLayoutVerifyPassword;


    TextInputEditText password;
    TextInputEditText verifyPassword;

    RegisterAccountRequest registerAccountRequest;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerAccountRequest = RegisterAccountRequest.getInstance();

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
                validateEditText(s, inputLayoutPassword, R.string.ui_no_password);
                validatePasword(s, inputLayoutPassword, R.string.ui_valid_password);
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
                validateEditText(s, inputLayoutVerifyPassword, R.string.ui_no_verify_password);
                validateVerifyPassword(s, inputLayoutVerifyPassword, R.string.ui_valid_verify_password);
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

        registerAccountRequest.resetErrorCodes();

        //singleton class variable, save the encoded data
        registerAccountRequest.getRegisterAccount().setPassword(password.getText().toString());
        registerAccountRequest.getRegisterAccount().setVerifyPassword(verifyPassword.getText().toString());


        Toast.makeText(this.getContext(), "Your custom back action. Here you should cancel currently running operations", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this.getContext(), "Your custom back action. Here you should cancel currently running operations", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this.getContext(), "Your custom back action. Here you should cancel currently running operations", Toast.LENGTH_SHORT).show();
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
}
