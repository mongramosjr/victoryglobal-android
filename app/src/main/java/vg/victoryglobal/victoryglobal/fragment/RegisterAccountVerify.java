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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.model.MlmResponseError;
import vg.victoryglobal.victoryglobal.model.RegisterAccountRequest;
import vg.victoryglobal.victoryglobal.model.UpgradeAccountRequest;

public class RegisterAccountVerify extends Fragment implements BlockingStep {

    TextInputLayout inputLayoutActivateCode;
    TextInputLayout inputLayoutUplineId;
    TextInputLayout inputLayoutSponsorId;

    Spinner mlmAccountId;
    TextInputEditText activationCode;
    TextInputEditText uplineId;
    TextInputEditText sponsorId;
    Spinner mlmLocation;
    Spinner pickupCenterId;

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
        return inflater.inflate(R.layout.register_account_verify, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


        // get textview and textinputlayout
        inputLayoutActivateCode = view.findViewById(R.id.activate_code_textinputlayout);
        inputLayoutUplineId = view.findViewById(R.id.upline_id_textinputlayout);
        inputLayoutSponsorId = view.findViewById(R.id.sponsor_id_textinputlayout);

        mlmAccountId = view.findViewById(R.id.mlm_account_id);
        activationCode = view.findViewById(R.id.activation_code);
        uplineId = view.findViewById(R.id.upline_id);
        sponsorId = view.findViewById(R.id.sponsor_id);
        mlmLocation = view.findViewById(R.id.mlm_location);
        pickupCenterId = view.findViewById(R.id.pickup_center_id);

        //spinner.setOnItemSelectedListener(this);
        //spinnerMlmAccount.setVisibility(View.INVISIBLE);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterMlmAccount = ArrayAdapter.createFromResource(view.getContext(),
                R.array.mlm_location_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterMlmAccount.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        // Apply the adapter to the spinner
        mlmAccountId.setAdapter(adapterMlmAccount);



        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterMlmLocation = ArrayAdapter.createFromResource(view.getContext(),
                R.array.mlm_location_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterMlmLocation.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        // Apply the adapter to the spinner
        mlmLocation.setAdapter(adapterMlmLocation);



        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterPickupCenter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.mlm_location_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterPickupCenter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        // Apply the adapter to the spinner
        pickupCenterId.setAdapter(adapterPickupCenter);

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
                validateEditText(s, inputLayoutActivateCode, R.string.ui_no_activation_code);
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
                validateEditText(s, inputLayoutUplineId, R.string.ui_no_distributor);
                validateAccountNumber(s, inputLayoutUplineId, R.string.ui_length_distributor);
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
                validateEditText(s, inputLayoutSponsorId, R.string.ui_no_distributor);
                validateAccountNumber(s, inputLayoutSponsorId, R.string.ui_length_distributor);
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
        if(validateEditText(activationCode.getText(), inputLayoutActivateCode, R.string.ui_no_activation_code)==false){
            status = false;
        }
        if(validateEditText(uplineId.getText(), inputLayoutUplineId, R.string.ui_no_distributor)==false){
            status = false;
        }
        if(validateAccountNumber(sponsorId.getText(), inputLayoutSponsorId, R.string.ui_length_distributor)==false){
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
        //registerAccountRequest.getRegisterAccount().setMlmAccountId(mlmAccountId.getText().toString());
        registerAccountRequest.getRegisterAccount().setActivationCode(activationCode.getText().toString());
        registerAccountRequest.getRegisterAccount().setUplineId(Integer.parseInt(uplineId.getText().toString()));
        registerAccountRequest.getRegisterAccount().setSponsorId(Integer.parseInt(sponsorId.getText().toString()));
        //registerAccountRequest.getRegisterAccount().setMlmLocation(mlmLocation.getText().toString());
        //registerAccountRequest.getRegisterAccount().setPickupCenterId(pickupCenterId.getText().toString());

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
            if(res.getFieldName() == "upline_id") {
                inputLayoutUplineId.setError(res.getErrMessage());
            }else if(res.getFieldName() == "sponsor_id") {
                inputLayoutSponsorId.setError(res.getErrMessage());
            }else if(res.getFieldName() == "activation_code") {
                inputLayoutActivateCode.setError(res.getErrMessage());
            }

        }
    }

    private void displayEnteredText(){

        //set the text

        if(registerAccountRequest.getRegisterAccount().getMlmLocation() != 0 ) {
            //mlmLocation.setText(registerAccountRequest.getRegisterAccount().getMlmLocation());
        }
        if(registerAccountRequest.getRegisterAccount().getActivationCode() != null) {
            if (registerAccountRequest.getRegisterAccount().getActivationCode().length() > 0) {
                activationCode.setText(registerAccountRequest.getRegisterAccount().getActivationCode());
            }
        }
        if(registerAccountRequest.getRegisterAccount().getUplineId() != 0 ) {
            uplineId.setText(String.valueOf(registerAccountRequest.getRegisterAccount().getUplineId()));
        }
        if(registerAccountRequest.getRegisterAccount().getSponsorId() != 0 ) {
            sponsorId.setText(String.valueOf(registerAccountRequest.getRegisterAccount().getSponsorId()));
        }
        if(registerAccountRequest.getRegisterAccount().getMlmLocation() != 0 ) {
            //mlmLocation.setText(registerAccountRequest.getRegisterAccount().getMlmLocation());
        }
        if(registerAccountRequest.getRegisterAccount().getPickupCenterId() != 0 ) {
            //pickupCenterId.setText(String.valueOf(registerAccountRequest.getRegisterAccount().getPickupCenterId()));
        }
    }

}
