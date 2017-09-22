/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/14/17 7:41 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/14/17 7:38 PM
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.model.MlmAccount;
import vg.victoryglobal.victoryglobal.model.MlmAccountRequest;
import vg.victoryglobal.victoryglobal.model.MlmResponseError;
import vg.victoryglobal.victoryglobal.model.PickupCenter;
import vg.victoryglobal.victoryglobal.model.PickupCenterRequest;
import vg.victoryglobal.victoryglobal.model.RegisterAccountRequest;

public class RegisterAccountMlmInfo extends Fragment implements BlockingStep {

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
            registerAccountRequest.getRegisterAccount().setPickupCenterId(Integer.parseInt(pickup_center.getId()));
            registerAccountRequest.getRegisterAccount().setPickupCenterName(pickup_center_name);
        }

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
            if(registerAccountRequest.getRegisterAccount().getMlmAccountId() != 0 ) {

                String mlm_account_id_name = registerAccountRequest.getRegisterAccount().getMlmAccountName();
                int position = adapterMlmAccount.getPosition(mlm_account_id_name);
                mlmAccountId.setSelection(position);
            }

        }else{
            mlmAccountId.setVisibility(View.INVISIBLE);
            mlmAccountIdLabel.setVisibility(View.INVISIBLE);
            mlmAccountIdLayout.setVisibility(View.INVISIBLE);
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
            if(registerAccountRequest.getRegisterAccount().getPickupCenterId() != 0 ) {

                String pickup_center_name = registerAccountRequest.getRegisterAccount().getPickupCenterName();
                int position = adapterPickupCenter.getPosition(pickup_center_name);
                pickupCenterId.setSelection(position);
            }

        }else{
            pickupCenterId.setVisibility(View.INVISIBLE);
            pickupCenterIdLabel.setVisibility(View.INVISIBLE);
            pickupCenterIdLayout.setVisibility(View.INVISIBLE);
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
            String mlm_location_name = registerAccountRequest.getRegisterAccount().getMlmLocationName();
            int position = adapterMlmLocation.getPosition(mlm_location_name);
            mlmLocation.setSelection(position);
        }


    }

}
