/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/14/17 7:41 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/13/17 8:33 PM
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

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.model.MlmResponseError;
import vg.victoryglobal.victoryglobal.model.RegisterAccountRequest;

public class RegisterAccountAddressAndContact extends Fragment implements BlockingStep {

    TextInputLayout inputLayoutEmail;
    TextInputLayout inputLayoutTelephone;
    TextInputLayout inputLayoutMobileNumber;

    TextInputEditText street;
    TextInputEditText city;
    TextInputEditText postalCode;


    TextInputEditText countryCode;
    TextInputEditText region;

    TextInputEditText email;
    TextInputEditText telephone;
    TextInputEditText mobileNumber;

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
        return inflater.inflate(R.layout.register_account_addressandcontact, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // get textview and textinputlayout
        inputLayoutEmail = view.findViewById(R.id.email_textinputlayout);
        inputLayoutTelephone = view.findViewById(R.id.telephone_textinputlayout);
        inputLayoutMobileNumber = view.findViewById(R.id.mobile_number_textinputlayout);

        street = view.findViewById(R.id.street);
        city = view.findViewById(R.id.city);
        postalCode = view.findViewById(R.id.postal_code);
        region = view.findViewById(R.id.region);
        countryCode = view.findViewById(R.id.country_code);

        email = view.findViewById(R.id.email);
        telephone = view.findViewById(R.id.telephone);
        mobileNumber = view.findViewById(R.id.mobile_number);

        //display text
        displayEnteredText();

        //display error
        displayError();


        //listener
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!registerAccountRequest.isSuccessAddressAndContact()) {
                    validateEmail(s, inputLayoutEmail, R.string.ui_invalid_email);
                }
            }
        });

        telephone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!registerAccountRequest.isSuccessAddressAndContact()) {
                    validatePhone(s, inputLayoutTelephone, R.string.ui_invalid_telephone);
                }
            }
        });

        mobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!registerAccountRequest.isSuccessAddressAndContact()) {
                    validateEditText(s, inputLayoutMobileNumber, R.string.ui_no_mobile_number);
                    validatePhone(s, inputLayoutMobileNumber, R.string.ui_invalid_mobile_number);
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

    private boolean validateEmail(Editable s, TextInputLayout t, @StringRes int resId) {

        if (!TextUtils.isEmpty(s)) {
            t.setError(null);
            return true;
        }
        else{
            t.setError(getString(resId));
            return false;
        }
    }

    private boolean validatePhone(Editable s, TextInputLayout t, @StringRes int resId) {

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

        if(!validateEmail(email.getText(), inputLayoutEmail, R.string.ui_invalid_email)){
            status = false;
        }
        if(!validatePhone(telephone.getText(), inputLayoutTelephone, R.string.ui_invalid_telephone)){
            status = false;
        }

        if(!validateEditText(mobileNumber.getText(), inputLayoutMobileNumber, R.string.ui_no_mobile_number)){
            status = false;
        }
        if(!validatePhone(mobileNumber.getText(), inputLayoutMobileNumber, R.string.ui_invalid_mobile_number)){
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

        //display Text
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
            Log.e("RegisterAccountAddr", e.getMessage());
        }

        registerAccountRequest.resetErrorCodes();

        //singleton class variable, save the encoded data

        registerAccountRequest.getRegisterAccount().setStreet(street.getText().toString());
        registerAccountRequest.getRegisterAccount().setCity(city.getText().toString());
        registerAccountRequest.getRegisterAccount().setPostalCode(postalCode.getText().toString());
        registerAccountRequest.getRegisterAccount().setRegion(region.getText().toString());
        registerAccountRequest.getRegisterAccount().setCountryCode(countryCode.getText().toString());

        registerAccountRequest.getRegisterAccount().setEmail(email.getText().toString());
        registerAccountRequest.getRegisterAccount().setTelephone(telephone.getText().toString());
        registerAccountRequest.getRegisterAccount().setMobileNumber(mobileNumber.getText().toString());

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
            if(res.getFieldName().equals("email")) {
                inputLayoutEmail.setError(res.getErrMessage());
            }else if(res.getFieldName().equals("telephone")) {
                inputLayoutTelephone.setError(res.getErrMessage());
            }else if(res.getFieldName().equals("mobile_number")) {
                inputLayoutMobileNumber.setError(res.getErrMessage());
            }

        }
    }

    private void displayEnteredText(){

        if(registerAccountRequest.isSuccess() && registerAccountRequest.isSuccessAddressAndContact()){

            street.setText("");
            city.setText("");
            region.setText("");
            postalCode.setText("");
            countryCode.setText("");

            email.setText("");

            telephone.setText("");
            mobileNumber.setText("");


            registerAccountRequest.setSuccessAddressAndContact(false);
        }

        //set the text
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
    }
}
