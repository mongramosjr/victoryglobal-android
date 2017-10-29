/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/14/17 7:41 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/13/17 8:03 PM
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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.adapter.RegisterAccountStepperAdapter;
import vg.victoryglobal.victoryglobal.adapter.UpgradeAccountStepperAdapter;
import vg.victoryglobal.victoryglobal.model.MlmResponseError;
import vg.victoryglobal.victoryglobal.model.UpgradeAccount;
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

        callback.getStepperLayout().showProgress(getString(R.string.progress_message));

        upgradeRegistration(getView(), upgradeAccountRequest.getUpgradeAccount(), callback);

    }

    @Override
    @UiThread
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
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

    private void upgradeRegistrationCallback(View view, String response_data,
                                          final StepperLayout.OnCompleteClickedCallback callback_upgrade) {
        try {
            JSONObject object = (JSONObject) new JSONTokener(response_data).nextValue();
            int status = object.getInt("status");
            String message = object.getString("message");

            Log.e("UpgradeAccountConfirm ", "Status: " + String.valueOf(status));

            if (status == 200) {

                //clear request data and error response
                upgradeAccountRequest.resetErrorCodes();
                upgradeAccountRequest.reset();
                upgradeAccountRequest.setSuccess(true);


                // complete
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callback_upgrade.complete();
                    }
                }, 2000L);

                Snackbar success = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
                //message_show.setAction();
                success.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callback_upgrade.getStepperLayout().hideProgress();
                        UpgradeAccountStepperAdapter upAcctAdptr
                                =  (UpgradeAccountStepperAdapter) callback_upgrade.getStepperLayout().getAdapter();
                        int position = upAcctAdptr.positionStepFragment("UpgradeAccountVerify");
                        callback_upgrade.getStepperLayout().setCurrentStepPosition(position);
                    }
                }, 2000L);

            } else if (status == 402) {
                Log.e("UpgradeAccountConfirm", "upgradeRegistrationCallback: " + message);

                object.has("error");

                if (object.has("error")) {
                    String error = object.getString("error");
                    Log.e("UpgradeAccountConfirm", "upgradeRegistrationCallback: (1) " + error + ": " + message);

                    MlmResponseError err = new MlmResponseError();
                    err.setFieldName(error);
                    err.setErrMessage(message);
                    upgradeAccountRequest.getMlmResponseErrors().add(err);

                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            callback_upgrade.getStepperLayout().hideProgress();
                            callback_upgrade.getStepperLayout().onBackClicked();
                        }
                    }, 2000L);


                    //callback.getStepperLayout().onBackClicked();
                } else if (object.has("exception")) {
                    String exception = object.getString("exception");

                    Log.e("UpgradeAccountConfirm", "upgradeRegistrationCallback: (2) " + exception);
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            callback_upgrade.getStepperLayout().hideProgress();
                            callback_upgrade.getStepperLayout().onBackClicked();
                        }
                    }, 2000L);
                }

            } else {

                Log.e("UpgradeAccountConfirm", "upgradeRegistrationCallback: (3) Unexpected error");

                Toast.makeText(getContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callback_upgrade.getStepperLayout().hideProgress();
                        callback_upgrade.getStepperLayout().onBackClicked();
                    }
                }, 2000L);
            }
        } catch (JSONException e) {
            //do nothing
            Log.e("UpgradeAccountConfirm", "upgradeRegistrationCallback: (4) " + e.getMessage());
            Toast.makeText(getContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    callback_upgrade.getStepperLayout().hideProgress();
                    callback_upgrade.getStepperLayout().onBackClicked();
                }
            }, 2000L);
        }


    }


    private void upgradeRegistration(final View view, UpgradeAccount upgrade_account, final StepperLayout.OnCompleteClickedCallback callback_upgrade) {

        RequestQueue queue = Volley.newRequestQueue(getContext());

        String url = getString(R.string.api_url) + getString(R.string.api_upgrade_registration);

        JSONObject post_data = new JSONObject();
        try {
            post_data.put("mlm_member_id", upgrade_account.getMlmMemberId());
            post_data.put("activation_code", upgrade_account.getActivationCode());
        } catch (JSONException ex) {

            callback_upgrade.getStepperLayout().hideProgress();
            Toast.makeText(getContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();
            Log.e("UpgradeAccountConfirm", ex.getMessage());
            return;
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, post_data, new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("UpgradeAccountConfirm", "Response: " + response.toString());
                upgradeRegistrationCallback(view, response.toString(), callback_upgrade);
            }
        }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Do nothing
                Log.e("UpgradeAccountConfirm", "onErrorResponse: " + error.toString());
                callback_upgrade.getStepperLayout().hideProgress();
                Toast.makeText(getContext(), R.string.ui_unexpected_response, Toast.LENGTH_LONG).show();

            }
        });

        // 6 minutes
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 144,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        queue.add(jsObjRequest);
    }
}
