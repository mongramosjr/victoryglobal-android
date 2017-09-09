/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/9/17 9:19 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/9/17 8:12 PM
 */

package vg.victoryglobal.victoryglobal.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.sql.Date;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.model.RegisterAccountRequest;
import vg.victoryglobal.victoryglobal.model.UpgradeAccountRequest;

public class RegisterAccountConfirm extends Fragment implements BlockingStep {



    TextView firstName;
    TextView lastName;
    TextView activationCode;

    TextView sponsorId;
    TextView uplineId;

    TextView password;
    TextView verifyPassword;

    TextView middleName;

    TextView dateOfBirth;
    TextView maritalStatus;
    TextView gender;
    TextView taxNumber;
    TextView socialSecurityNumber;

    TextView street;
    TextView city;
    TextView region;
    TextView postalCode;
    TextView countryCode;
    TextView email;
    TextView telephone;
    TextView mobileNumber;

    TextView mlmAccountId;
    TextView mlmLocation;
    TextView pickupCenterId;

    TextView activationCodeName;
    TextView uplineName;
    TextView sponsorName;

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
        return inflater.inflate(R.layout.register_account_confirm, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // get View
        firstName = view.findViewById(R.id.first_name);
        lastName = view.findViewById(R.id.last_name);
        activationCode = view.findViewById(R.id.activation_code);

        sponsorId  = view.findViewById(R.id.sponsor_id);
        uplineId  = view.findViewById(R.id.upline_id);

        password  = view.findViewById(R.id.password);
        verifyPassword  = view.findViewById(R.id.verify_password);

        middleName  = view.findViewById(R.id.middle_name);

        dateOfBirth  = view.findViewById(R.id.date_of_birth);
        maritalStatus  = view.findViewById(R.id.marital_status);
        gender  = view.findViewById(R.id.gender);
        taxNumber  = view.findViewById(R.id.tax_number);
        socialSecurityNumber  = view.findViewById(R.id.social_security_number);

        street  = view.findViewById(R.id.street);
        city  = view.findViewById(R.id.city);
        region  = view.findViewById(R.id.region);
        postalCode = view.findViewById(R.id.postal_code);
        countryCode = view.findViewById(R.id.country_code);
        email = view.findViewById(R.id.email);
        telephone = view.findViewById(R.id.telephone);
        mobileNumber = view.findViewById(R.id.mobile_number);

        mlmAccountId = view.findViewById(R.id.mlm_account_id);
        mlmLocation = view.findViewById(R.id.mlm_location);
        pickupCenterId  = view.findViewById(R.id.pickup_center_id);

        activationCodeName = view.findViewById(R.id.activation_code_name);
        uplineName = view.findViewById(R.id.upline_name);
        sponsorName = view.findViewById(R.id.sponsor_name);

        //display text
        displayEnteredText();
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

    }

    @Override
    public void onError(@NonNull VerificationError error) {
        //handle error inside of the fragment, e.g. show error on EditText
    }

    @Override
    @UiThread
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
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

        //clear request data and error response
        registerAccountRequest.resetErrorCodes();
        registerAccountRequest.reset();

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

    void displayEnteredText(){

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
        if(registerAccountRequest.getRegisterAccount().getGender() != null) {
            if (registerAccountRequest.getRegisterAccount().getGender().length() > 0) {
                gender.setText(registerAccountRequest.getRegisterAccount().getGender());
            }
        }
        if(registerAccountRequest.getRegisterAccount().getMaritalStatus() != null) {
            if (registerAccountRequest.getRegisterAccount().getMaritalStatus().length() > 0) {
                maritalStatus.setText(registerAccountRequest.getRegisterAccount().getMaritalStatus());
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


        if(registerAccountRequest.getRegisterAccount().getMlmAccountId() != 0 ) {
            mlmAccountId.setText(String.valueOf(registerAccountRequest.getRegisterAccount().getMlmAccountId()));
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
            mlmLocation.setText(String.valueOf(registerAccountRequest.getRegisterAccount().getMlmLocation()));
        }
        if(registerAccountRequest.getRegisterAccount().getPickupCenterId() != 0 ) {
            pickupCenterId.setText(String.valueOf(registerAccountRequest.getRegisterAccount().getPickupCenterId()));
        }


        if(registerAccountRequest.getRegisterAccount().getPassword() != null) {
            if (registerAccountRequest.getRegisterAccount().getPassword().length() > 0) {
                password.setText(registerAccountRequest.getRegisterAccount().getPassword());
            }
        }
        if(registerAccountRequest.getRegisterAccount().getVerifyPassword() != null) {
            if(registerAccountRequest.getRegisterAccount().getVerifyPassword().length() > 0) {
                verifyPassword.setText(String.valueOf(registerAccountRequest.getRegisterAccount().getVerifyPassword()));
            }
        }




    }
}
