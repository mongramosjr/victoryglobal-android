/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/17/17 2:52 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/17/17 2:32 PM
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

import java.util.ArrayList;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.model.ActivateCode;
import vg.victoryglobal.victoryglobal.model.MlmResponseError;
import vg.victoryglobal.victoryglobal.model.UpgradeAccount;
import vg.victoryglobal.victoryglobal.model.UpgradeAccountRequest;

public class UpgradeAccountVerify extends Fragment implements BlockingStep {

    TextInputLayout inputLayoutActivateCode;
    TextInputLayout inputLayoutDistributorId;
    TextInputEditText activationCode;
    TextInputEditText mlmMemberId;
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
        return inflater.inflate(R.layout.upgrade_account_verify, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // get textview and textinputlayout
        inputLayoutActivateCode = view.findViewById(R.id.activate_code_textinputlayout);
        inputLayoutDistributorId = view.findViewById(R.id.distributor_id_textinputlayout);
        activationCode = view.findViewById(R.id.activation_code);
        mlmMemberId = view.findViewById(R.id.mlm_member_id);

        //set the text
        displayEnteredText();

        // show if have errors
        displayError();

        activationCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!upgradeAccountRequest.isSuccess()) {
                    validateEditText(s, inputLayoutActivateCode, R.string.ui_no_activation_code);
                }
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
                if(!upgradeAccountRequest.isSuccess()) {
                    validateEditText(s, inputLayoutDistributorId, R.string.ui_no_distributor);
                    validateAccountNumber(s, inputLayoutDistributorId, R.string.ui_length_distributor);
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
        if(validateEditText(activationCode.getText(), inputLayoutActivateCode, R.string.ui_no_activation_code)==false){
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

        upgradeAccountRequest.resetErrorCodes();

        //singleton class variable, save the encoded data
        upgradeAccountRequest.getUpgradeAccount().setActivationCode(activationCode.getText().toString());
        upgradeAccountRequest.getUpgradeAccount().setMlmMemberId(Integer.parseInt(mlmMemberId.getText().toString()));


        callback.getStepperLayout().showProgress(getString(R.string.progress_message));

        upgradeCheckFirst(upgradeAccountRequest.getUpgradeAccount(), callback);
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
        ArrayList<MlmResponseError> mlm_response_errors = upgradeAccountRequest.getMlmResponseErrors();

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

        if(upgradeAccountRequest.isSuccess()){

            activationCode.setText("");
            mlmMemberId.setText("");
            upgradeAccountRequest.setSuccess(false);
        }

        //using class variable  within singleton
        if(upgradeAccountRequest.getUpgradeAccount().getActivationCode() != null) {
            if (upgradeAccountRequest.getUpgradeAccount().getActivationCode().length() > 0) {
                activationCode.setText(upgradeAccountRequest.getUpgradeAccount().getActivationCode());
            }
        }
        if(upgradeAccountRequest.getUpgradeAccount().getMlmMemberId() != 0 ) {
            mlmMemberId.setText(String.valueOf(upgradeAccountRequest.getUpgradeAccount().getMlmMemberId()));
        }

    }

    private void upgradeCheckFirstCallback(String response_data, final StepperLayout.OnNextClickedCallback callback_upgrade)
    {
        try {
            JSONObject object = (JSONObject) new JSONTokener(response_data).nextValue();
            int status = object.getInt("status");
            String message = object.getString("message");

            Log.e("UpgradeAccountVerify ", "Status: " + String.valueOf(status));

            if(status == 200 ){

                JSONObject activation_code = object.getJSONObject("activation_code");
                JSONObject member = object.getJSONObject("member");
                //save
                //singleton class variable, save the encoded data
                upgradeAccountRequest.getUpgradeAccount().setActivationCodeName(activation_code.getString("name"));
                upgradeAccountRequest.getUpgradeAccount().setMemberName(member.getString("name"));




                // go to next page
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callback_upgrade.getStepperLayout().hideProgress();
                        callback_upgrade.goToNextStep();
                    }
                }, 2000L);



            }else if(status == 402 ){

                if(object.has("error")) {
                    String error = object.getString("error");

                    MlmResponseError err = new MlmResponseError();
                    err.setFieldName(error);
                    err.setErrMessage(message);
                    upgradeAccountRequest.getMlmResponseErrors().add(err);

                    if (error.equals("mlm_member_id")) {
                        inputLayoutDistributorId.setError(message);
                    } else if (error.equals("activation_code")) {
                        inputLayoutActivateCode.setError(message);
                    }
                }else{

                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callback_upgrade.getStepperLayout().hideProgress();
                    }
                }, 2000L);


            }else{
                Toast.makeText(getContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callback_upgrade.getStepperLayout().hideProgress();
                    }
                }, 2000L);
            }
        } catch (JSONException e) {
            //do nothing
            Log.e("UpgradeAccountVerify", "upgradeCheckFirstCallback: (4) " + e.getMessage());
            Toast.makeText(getContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    callback_upgrade.getStepperLayout().hideProgress();
                }
            }, 2000L);
        }


    }


    private void upgradeCheckFirst(UpgradeAccount upgrade_account, final StepperLayout.OnNextClickedCallback callback_upgrade) {

        RequestQueue queue = Volley.newRequestQueue(getContext());

        String url = getString(R.string.api_url).toString() + getString(R.string.api_upgrade_checkfirst).toString();

        JSONObject post_data = new JSONObject();
        try {
            post_data.put("mlm_member_id", upgrade_account.getMlmMemberId());
            post_data.put("activation_code", upgrade_account.getActivationCode());
        }catch(JSONException ex) {

            callback_upgrade.getStepperLayout().hideProgress();
            Toast.makeText(getContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();
            Log.e("UpgradeAccountVerify", ex.getMessage());
            return;
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST,
                        url,
                        post_data,
                        new com.android.volley.Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.e("UpgradeAccountVerify", "Response: " + response.toString());
                                upgradeCheckFirstCallback(response.toString(), callback_upgrade);
                            }
                        },
                        new com.android.volley.Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Do nothing
                                Log.e("UpgradeAccountVerify", "onErrorResponse: " + error.toString());
                                Toast.makeText(getContext(), R.string.ui_unexpected_response, Toast.LENGTH_LONG).show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        callback_upgrade.getStepperLayout().hideProgress();
                                    }
                                }, 2000L);
                            }
                        }
                );

        // 6 minutes
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 144,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(jsObjRequest);
    }
}
