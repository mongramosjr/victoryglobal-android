/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/18/17 7:33 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/18/17 6:19 PM
 */

package vg.victoryglobal.victoryglobal.fragment;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.listener.LoginListener;
import vg.victoryglobal.victoryglobal.model.AccountLogin;
import vg.victoryglobal.victoryglobal.model.AuthLoginRequest;
import vg.victoryglobal.victoryglobal.model.MlmResponseError;

public class AccountLoginFragment extends Fragment {

    public View currentView;

    TextInputLayout inputLayoutPassword;
    TextInputLayout inputLayoutDistributorId;
    TextInputEditText password;
    TextInputEditText mlmMemberId;

    ProgressBar simpleProgressBar;

    private LoginListener loginListener;

    AuthLoginRequest authLoginRequest;

    @Override
    public void onAttach(Context context)
    {

        super.onAttach(context);

        if(getActivity() instanceof LoginListener){
            loginListener = (LoginListener) getActivity();
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authLoginRequest = AuthLoginRequest.getAuthLoginRequest("main", getActivity().getApplicationContext());
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.account_login_fragment, container, false);



    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        currentView = view;

        // get textview and textinputlayout
        inputLayoutPassword = view.findViewById(R.id.password_textinputlayout);
        inputLayoutDistributorId = view.findViewById(R.id.distributor_id_textinputlayout);
        password = view.findViewById(R.id.password);
        mlmMemberId = view.findViewById(R.id.mlm_member_id);

        simpleProgressBar = view.findViewById(R.id.simpleProgressBar);


        final Button button = view.findViewById(R.id.login_button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                if(!validateAllEditText()){
                    return;
                }

                simpleProgressBar.setVisibility(View.VISIBLE);

               try {
                    InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow((null == getActivity().getCurrentFocus()) ? null : getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }catch (Exception e) {
                    Log.e("AccountLoginFragment", e.getMessage());
                }

                authLoginRequest.reset();

                //singleton class variable, save the encoded data
                authLoginRequest.getAccountLogin().setPassword(password.getText().toString());
                authLoginRequest.getAccountLogin().setMlmMemberId(Integer.parseInt(mlmMemberId.getText().toString()));

                authAccount(currentView, authLoginRequest.getAccountLogin());
            }
        });

        final TextView link_registration = view.findViewById(R.id.link_registration);
        String TAG = "yourLogCatTag";
        link_registration.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View viewIn) {

                loginListener.prepareRegister();
                return true;
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
                if(!authLoginRequest.isSuccess()) {
                    validateEditText(s, inputLayoutDistributorId, R.string.ui_no_distributor);
                    validateAccountNumber(s, inputLayoutDistributorId, R.string.ui_length_distributor);
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!authLoginRequest.isSuccess()) {
                    validateEditText(s, inputLayoutPassword, R.string.ui_no_password);
                    validatePasword(s, inputLayoutPassword, R.string.ui_valid_password);
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

        if(!validateEditText(mlmMemberId.getText(), inputLayoutDistributorId, R.string.ui_no_distributor)){
            status = false;
        }
        if(!validateAccountNumber(mlmMemberId.getText(), inputLayoutDistributorId, R.string.ui_length_distributor)){
            status = false;
        }

        if(!validateEditText(password.getText(), inputLayoutPassword, R.string.ui_no_password)){
            status = false;
        }
        if(!validatePasword(password.getText(), inputLayoutPassword, R.string.ui_valid_password)){
            status = false;
        }

        return status;
    }

    private boolean validatePasword(Editable s, TextInputLayout t, @StringRes int resId) {
        if (!TextUtils.isEmpty(s)) {
            t.setError(null);
            return true;
        }
        else{
            t.setError(getString(resId));
            return false;
        }
    }

    private void displayError() {
        ArrayList<MlmResponseError> mlm_response_errors = authLoginRequest.getMlmResponseErrors();

        for (int i = 0; i < mlm_response_errors.size(); i++) {
            MlmResponseError res = mlm_response_errors.get(i);
            if(res.getFieldName().equals("mlm_member_id")) {
                inputLayoutDistributorId.setError(res.getErrMessage());
            }else if(res.getFieldName().equals("password")) {
                inputLayoutPassword.setError(res.getErrMessage());
            }
        }
    }

    //private void authAccountCallback(String response_data, final StepperLayout.OnNextClickedCallback callback_code)
    private void authAccountCallback(final View view, String response_data)
    {
        try {
            JSONObject object = (JSONObject) new JSONTokener(response_data).nextValue();
            int status = object.getInt("status");

            //Log.e("AccountLogin ", "Status: " + String.valueOf(status));

            if(status == 200 ){

                JSONObject user = object.getJSONObject("user");
                JSONObject account = object.getJSONObject("account");
                String session = object.getString("session");
                String auth_token = object.getString("auth_token");

                //singleton class variable, save the response auth data
                // save auth token to sqlite/SharedPreferences
                authLoginRequest.saveAuthSession(response_data);


                Toast.makeText(getActivity().getApplicationContext(), R.string.login_succesful, Toast.LENGTH_SHORT).show();

                // go to next page
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //callback_code.getStepperLayout().hideProgress();
                        //callback_code.goToNextStep();
                        simpleProgressBar.setVisibility(View.INVISIBLE);
                        loginListener.prepareLogin(authLoginRequest.getAccountLogin());
                    }
                }, 2000L);



            }else if(status == 402 ) {

                String message = object.getString("message");

                if (object.has("error")) {
                    String error = object.getString("error");

                    MlmResponseError err = new MlmResponseError();
                    err.setFieldName(error);
                    err.setErrMessage(message);
                    authLoginRequest.getMlmResponseErrors().add(err);

                    if (error.equals("mlm_member_id")) {
                        inputLayoutDistributorId.setError(message);
                    } else if (error.equals("password")) {
                        inputLayoutPassword.setError(message);
                    }
                } else {

                    Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }

                /*
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callback_code.getStepperLayout().hideProgress();
                    }
                }, 2000L);
                */

            }else if(status == 401 ){

                    String message = object.getString("message");

                    if(object.has("error")) {
                        String error = object.getString("error");

                        MlmResponseError err = new MlmResponseError();
                        err.setFieldName(error);
                        err.setErrMessage(message);
                        authLoginRequest.getMlmResponseErrors().add(err);

                        if (error.equals("mlm_member_id")) {
                            inputLayoutDistributorId.setError(message);
                        } else if (error.equals("password")) {
                            inputLayoutPassword.setError(message);
                        }
                    }else{

                        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }

                /*
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callback_code.getStepperLayout().hideProgress();
                    }
                }, 2000L);
                */
            }else{
                Toast.makeText(getActivity().getApplicationContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();

                /*
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callback_code.getStepperLayout().hideProgress();
                    }
                }, 2000L);
                */
            }
        } catch (JSONException e) {
            //do nothing
            Log.e("AccountLogin", "AccountLoginCallback: (4) " + e.getMessage());
            Toast.makeText(getActivity().getApplicationContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();

            /*
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    callback_code.getStepperLayout().hideProgress();
                }
            }, 2000L);
            */
        }


    }

    //private void authAccount(AccountLogin account_login, final StepperLayout.OnNextClickedCallback callback_code) {
    private void authAccount(final View view, AccountLogin account_login) {

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        String url = getString(R.string.api_url) + getString(R.string.api_account_login);

        JSONObject post_data = new JSONObject();
        try {
            post_data.put("username", account_login.getMlmMemberId());
            post_data.put("password", account_login.getPassword());
        }catch(JSONException ex) {

            //callback_code.getStepperLayout().hideProgress();
            Toast.makeText(getActivity().getApplicationContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();
            Log.e("AccountLogin", ex.getMessage());
            return;
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST,
                        url,
                        post_data,
                        new com.android.volley.Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                //Log.e("AccountLogin", "Response: " + response.toString());
                                //authAccountCallback(response.toString(), callback_code);
                                authAccountCallback(view, response.toString());
                            }
                        },
                        new com.android.volley.Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Do nothing
                                Log.e("AccountLogin", "onErrorResponse: " + error.toString());
                                Toast.makeText(getActivity().getApplicationContext(), R.string.ui_unexpected_response, Toast.LENGTH_LONG).show();
                                /*new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        callback_code.getStepperLayout().hideProgress();
                                    }
                                }, 2000L);
                                */
                            }
                        }
                );

        // 6 minutes
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 144,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        queue.add(jsObjRequest);
    }
}
