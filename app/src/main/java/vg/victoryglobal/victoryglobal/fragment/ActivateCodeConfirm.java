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
import android.support.design.widget.Snackbar;
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

import java.util.ArrayList;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.model.ActivateCodeRequest;
import vg.victoryglobal.victoryglobal.model.MlmResponseError;

public class ActivateCodeConfirm extends Fragment implements BlockingStep {

    TextView activationCode;
    TextView mlmMemberId;

    TextView activationCodeName;
    TextView memberName;

    ActivateCodeRequest activateCodeRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.e("ActivateCodeConfirm", "Yow");
        activateCodeRequest = ActivateCodeRequest.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activate_code_confirm, container, false);

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
        activateCodeRequest.resetErrorCodes();
        activateCodeRequest.reset();

        Snackbar.make(this.getView(), "Your custom back action. Here you should cancel currently running operations", Toast.LENGTH_SHORT).show();
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

    private void displayEnteredText(){

        // using data from sharepref
        //SharedPreferences shared_pref = getActivity().getSharedPreferences("activationCodePref", 0);
        //String activation_code = shared_pref.getString("activation_code", "");
        //int mlm_member_id = shared_pref.getInt("mlm_member_id", 0);
        // setText
        //activateCode.setText(activation_code);
        //if(mlm_member_id>0) {
        //    mlmMemberId.setText(String.valueOf(mlm_member_id));
        //}

        //using class variable  within singleton
        if(activateCodeRequest.getActivateCode().getActivationCode() != null) {
            if (activateCodeRequest.getActivateCode().getActivationCode().length() > 0) {
                activationCode.setText(activateCodeRequest.getActivateCode().getActivationCode());
            }
        }

        if(activateCodeRequest.getActivateCode().getMlmMemberId() != 0 ) {
            mlmMemberId.setText(String.valueOf(activateCodeRequest.getActivateCode().getMlmMemberId()));
        }

        if(activateCodeRequest.getActivateCode().getActivationCodeName() != null) {
            if (activateCodeRequest.getActivateCode().getActivationCodeName().length() > 0) {
                activationCodeName.setText(activateCodeRequest.getActivateCode().getActivationCodeName());
            }
        }

        if(activateCodeRequest.getActivateCode().getMemberName() != null) {
            if (activateCodeRequest.getActivateCode().getMemberName().length() > 0) {
                memberName.setText(activateCodeRequest.getActivateCode().getMemberName());
            }
        }


    }
}
