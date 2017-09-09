/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/9/17 9:19 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/9/17 8:13 PM
 */

package vg.victoryglobal.victoryglobal.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.model.ActivateCodeRequest;
import vg.victoryglobal.victoryglobal.model.UpgradeAccountRequest;

public class UpgradeAccountConfirm extends Fragment implements BlockingStep {

    TextView activationCode;
    TextView mlmMemberId;

    TextView activationCodeName;
    TextView memberName;
    
    UpgradeAccountRequest upgradeAccountRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        upgradeAccountRequest = UpgradeAccountRequest.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.upgrade_account_confirm, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // get View
        activationCode = view.findViewById(R.id.activation_code);
        mlmMemberId = view.findViewById(R.id.mlm_member_id);
        activationCodeName = view.findViewById(R.id.activation_code_name);
        memberName = view.findViewById(R.id.member_name);

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
        upgradeAccountRequest.resetErrorCodes();
        upgradeAccountRequest.reset();

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

    public void displayEnteredText(){
        //using class variable  within singleton
        if(upgradeAccountRequest.getUpgradeAccount().getActivationCode() != null) {
            if (upgradeAccountRequest.getUpgradeAccount().getActivationCode().length() > 0) {
                activationCode.setText(upgradeAccountRequest.getUpgradeAccount().getActivationCode());
            }
        }
        if (upgradeAccountRequest.getUpgradeAccount().getMlmMemberId() != 0) {
            mlmMemberId.setText(String.valueOf(upgradeAccountRequest.getUpgradeAccount().getMlmMemberId()));
        }
        if(upgradeAccountRequest.getUpgradeAccount().getActivationCodeName() != null) {
            if (upgradeAccountRequest.getUpgradeAccount().getActivationCodeName().length() > 0) {
                activationCodeName.setText(upgradeAccountRequest.getUpgradeAccount().getActivationCodeName());
            }
        }
        if(upgradeAccountRequest.getUpgradeAccount().getMemberName() != null) {
            if (upgradeAccountRequest.getUpgradeAccount().getMemberName().length() > 0) {
                memberName.setText(upgradeAccountRequest.getUpgradeAccount().getMemberName());
            }
        }
    }
}
