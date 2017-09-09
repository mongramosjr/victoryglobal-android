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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.model.ActivateCodeRequest;
import vg.victoryglobal.victoryglobal.model.MlmResponseError;

public class ActivateCodeVerify extends Fragment implements BlockingStep {

    TextInputLayout inputLayoutActivateCode;
    TextInputLayout inputLayoutDistributorId;
    TextInputEditText activateCode;
    TextInputEditText mlmMemberId;
    ActivateCodeRequest activateCodeRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activateCodeRequest = ActivateCodeRequest.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activate_code_verify, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Log.e("ActivateCodeVerify", "onViewCreated");

        // get textview and textinputlayout
        inputLayoutActivateCode = view.findViewById(R.id.activate_code_textinputlayout);
        inputLayoutDistributorId = view.findViewById(R.id.distributor_id_textinputlayout);
        activateCode = view.findViewById(R.id.activation_code);
        mlmMemberId = view.findViewById(R.id.mlm_member_id);

        displayEnteredText();

        // display error
        displayError();

        activateCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateEditText(s, inputLayoutActivateCode, R.string.ui_no_activation_code);
            }
        });

        mlmMemberId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateEditText(s, inputLayoutDistributorId, R.string.ui_no_distributor);
                validateAccountNumber(s, inputLayoutDistributorId, R.string.ui_length_distributor);
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
        if(validateEditText(activateCode.getText(), inputLayoutActivateCode, R.string.ui_no_activation_code)==false){
            status = false;
        }
        if(validateEditText(mlmMemberId.getText(), inputLayoutDistributorId, R.string.ui_no_distributor)==false){
            status = false;
        }
        if(validateAccountNumber(mlmMemberId.getText(), inputLayoutDistributorId, R.string.ui_length_distributor)==false){
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

        //display error
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

        activateCodeRequest.resetErrorCodes();

        //singleton class variable, save the encoded data
        activateCodeRequest.getActivateCode().setActivationCode(activateCode.getText().toString());
        activateCodeRequest.getActivateCode().setMlmMemberId(Integer.parseInt(mlmMemberId.getText().toString()));

        //using SharedPreferences
        //SharedPreferences shared_pref = getActivity().getSharedPreferences("activationCodePref", 0);
        //SharedPreferences.Editor editor = shared_pref.edit();
        //editor.putString("activation_code", activateCode.getText().toString());
        //if(mlmMemberId.getText().length()>0) {
        //    editor.putInt("mlm_member_id", Integer.parseInt(mlmMemberId.getText().toString()));
        //}
        // Commit the edits!
        //editor.commit();



        callback.getStepperLayout().showProgress(getString(R.string.progress_message));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.goToNextStep();
                callback.getStepperLayout().hideProgress();
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
        ArrayList<MlmResponseError> mlm_response_errors = activateCodeRequest.getMlmResponseErrors();

        for (int i = 0; i < mlm_response_errors.size(); i++) {
            MlmResponseError res = mlm_response_errors.get(i);
            if(res.getFieldName() == "mlm_member_id") {
                inputLayoutDistributorId.setError(res.getErrMessage());
            }else if(res.getFieldName() == "activation_code") {
                inputLayoutActivateCode.setError(res.getErrMessage());
            }
        }
    }

    private void displayEnteredText(){
        //using class variable  within singleton
        if(activateCodeRequest.getActivateCode().getActivationCode() != null) {
            if (activateCodeRequest.getActivateCode().getActivationCode().length() > 0) {
                activateCode.setText(activateCodeRequest.getActivateCode().getActivationCode());
            }
        }

        if(activateCodeRequest.getActivateCode().getMlmMemberId() != 0 ) {
            mlmMemberId.setText(String.valueOf(activateCodeRequest.getActivateCode().getMlmMemberId()));
        }
    }
}
