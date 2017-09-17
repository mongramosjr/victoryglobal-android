/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/17/17 2:31 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/17/17 12:40 PM
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
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

public class RegisterAccountPersonalInfo extends Fragment implements BlockingStep {

    TextInputLayout inputLayoutFirstName;
    TextInputLayout inputLayoutLastName;


    TextInputEditText firstName;
    TextInputEditText middleName;
    TextInputEditText lastName;

    TextInputEditText dateOfBirth;
    Spinner maritalStatus;
    Spinner gender;

    TextInputEditText taxNumber;
    TextInputEditText socialSecurityNumber;

    RegisterAccountRequest registerAccountRequest;

    ArrayAdapter<CharSequence> adapterGender;

    ArrayAdapter<CharSequence> adapterMaritalStatus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerAccountRequest = RegisterAccountRequest.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.register_account_personalinfo, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {



        inputLayoutFirstName = view.findViewById(R.id.first_name_textinputlayout);
        inputLayoutLastName = view.findViewById(R.id.last_name_textinputlayout);


        firstName = view.findViewById(R.id.first_name);
        middleName = view.findViewById(R.id.middle_name);
        lastName = view.findViewById(R.id.last_name);

        dateOfBirth = view.findViewById(R.id.date_of_birth);
        TextInputDatePicker dateOfBirthPicker = new TextInputDatePicker(dateOfBirth, this.getContext());

        maritalStatus = view.findViewById(R.id.marital_status);
        gender = view.findViewById(R.id.gender);

        taxNumber = view.findViewById(R.id.tax_number);
        socialSecurityNumber = view.findViewById(R.id.social_security_number);


        // Create an ArrayAdapter using the string array and a default spinner layout
        adapterGender = ArrayAdapter.createFromResource(view.getContext(),
                R.array.gender_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterGender.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        // Apply the adapter to the spinner
        gender.setAdapter(adapterGender);

        // Create an ArrayAdapter using the string array and a default spinner layout
        adapterMaritalStatus = ArrayAdapter.createFromResource(view.getContext(),
                R.array.marital_status_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterMaritalStatus.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        // Apply the adapter to the spinner
        maritalStatus.setAdapter(adapterMaritalStatus);

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
                if(!registerAccountRequest.isSuccessPersonalInfo()) {
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
                if(!registerAccountRequest.isSuccessPersonalInfo()) {
                    validateEditText(s, inputLayoutLastName, R.string.ui_no_last_name);
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

    private boolean validateAllEditText() {

        boolean status = true;
        if(validateEditText(firstName.getText(), inputLayoutFirstName, R.string.ui_no_first_name)==false){
            status = false;
        }
        if(validateEditText(lastName.getText(), inputLayoutLastName, R.string.ui_no_last_name)==false){
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

        callback.getStepperLayout().showProgress(getString(R.string.progress_message));

        registerAccountRequest.resetErrorCodes();


        //singleton class variable, save the encoded data
        registerAccountRequest.getRegisterAccount().setFirstName(firstName.getText().toString());
        registerAccountRequest.getRegisterAccount().setMiddleName(middleName.getText().toString());
        registerAccountRequest.getRegisterAccount().setLastName(lastName.getText().toString());

        //TODO:
        //registerAccountRequest.getRegisterAccount().
        //        setDateOfBirth(Date.valueOf(dateOfBirth.getText().toString()));

        int position_marital_status = maritalStatus.getSelectedItemPosition();
        CharSequence marital_status = adapterMaritalStatus.getItem(position_marital_status);
        registerAccountRequest.getRegisterAccount().setMaritalStatus(marital_status.toString());

        int position_gender = gender.getSelectedItemPosition();
        CharSequence gender_name = adapterGender.getItem(position_gender);
        registerAccountRequest.getRegisterAccount().setGender(position_gender);
        registerAccountRequest.getRegisterAccount().setGenderName(gender_name.toString());


        registerAccountRequest.getRegisterAccount().setTaxNumber(taxNumber.getText().toString());
        registerAccountRequest.getRegisterAccount().setSocialSecurityNumber(socialSecurityNumber.getText().toString());

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
            if(res.getFieldName() == "first_name") {
                inputLayoutFirstName.setError(res.getErrMessage());
            }else if(res.getFieldName() == "last_name") {
                inputLayoutLastName.setError(res.getErrMessage());
            }

        }
    }

    private void displayEnteredText(){

        if(registerAccountRequest.isSuccess() && registerAccountRequest.isSuccessPersonalInfo()){

            firstName.setText("");
            middleName.setText("");
            lastName.setText("");
            dateOfBirth.setText("");

            maritalStatus.setSelection(0);

            gender.setSelection(0);

            taxNumber.setText("");
            socialSecurityNumber.setText("");


            registerAccountRequest.setSuccessPersonalInfo(false);
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
        if(registerAccountRequest.getRegisterAccount().getDateOfBirth() != null) {
            if (registerAccountRequest.getRegisterAccount().getDateOfBirth().toString().length() > 0) {
                dateOfBirth.setText(registerAccountRequest.getRegisterAccount().getDateOfBirth().toString());
            }
        }

        if(registerAccountRequest.getRegisterAccount().getGender() != 0 ) {
            String gender_name = registerAccountRequest.getRegisterAccount().getGenderName();
            int position = adapterGender.getPosition(gender_name);
            gender.setSelection(position);
        }


        if(registerAccountRequest.getRegisterAccount().getMaritalStatus() != null) {
            if (registerAccountRequest.getRegisterAccount().getMaritalStatus().length() > 0) {
                String marital_status_name = registerAccountRequest.getRegisterAccount().getMaritalStatus();
                int position = adapterMaritalStatus.getPosition(marital_status_name);
                maritalStatus.setSelection(position);
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

    }
}
