/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 12/18/17 6:52 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 12/18/17 6:51 AM
 */

package vg.victoryglobal.victoryglobal.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Arrays;
import java.util.List;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.listener.OnProfileUpdateListener;
import vg.victoryglobal.victoryglobal.model.AuthLoginRequest;
import vg.victoryglobal.victoryglobal.model.BankAccount;
import vg.victoryglobal.victoryglobal.model.ContactInfo;
import vg.victoryglobal.victoryglobal.model.DistributorAccountRequest;
import vg.victoryglobal.victoryglobal.model.DistributorAccountResponse;
import vg.victoryglobal.victoryglobal.model.ProfileBankAccountEditRequestQueue;

public class ProfileBankAccountEditFragment extends DialogFragment
        implements vg.victoryglobal.victoryglobal.network.http.client.Response.JsonListener,
        vg.victoryglobal.victoryglobal.network.http.client.Response.ErrorListener{


    Spinner bankNameSpinner;
    TextInputEditText accountNumber;
    String bankName;

    ArrayAdapter<CharSequence> adapterBanks;

    AuthLoginRequest authLoginRequest;

    DistributorAccountRequest distributorAccountRequest;
    ProfileBankAccountEditRequestQueue profileBankAccountEditRequestQueue;

    private OnProfileUpdateListener callback;

    public ProfileBankAccountEditFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            callback = (OnProfileUpdateListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling Fragment must implement OnProfileUpdateListener");
        }

        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
        authLoginRequest = AuthLoginRequest.getAuthLoginRequest("main");
        distributorAccountRequest = DistributorAccountRequest.getInstance();

        profileBankAccountEditRequestQueue = ProfileBankAccountEditRequestQueue.getInstance();
        profileBankAccountEditRequestQueue.getRequest().url = getString(R.string.api_url) + getString(R.string.api_profilebankaccount_update);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_bank_account_edit, container);
        getDialog().setTitle(R.string.address);

        bankNameSpinner = view.findViewById(R.id.bank_name);
        accountNumber = view.findViewById(R.id.account_number);

        // Create an ArrayAdapter using the string array and a default spinner layout
        adapterBanks = ArrayAdapter.createFromResource(view.getContext(),
                R.array.bank_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterBanks.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        // Apply the adapter to the spinner
        bankNameSpinner.setAdapter(adapterBanks);

        bankNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                // An item was selected. You can retrieve the selected item using
                // adapterView.getItemAtPosition(pos)
                String[] banks = getResources().getStringArray(R.array.bank_keys);
                bankName = banks[pos];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Button dismiss = view.findViewById(R.id.action_cancel);
        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button saveBtn = view.findViewById(R.id.action_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                profileBankAccountEdit(v);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if(distributorAccountRequest.isSuccess() && distributorAccountRequest.getDistributorAccountResponse() != null) {
            DistributorAccountResponse distributor_account = distributorAccountRequest.getDistributorAccountResponse();
            prepareProfile(distributor_account);
        }
    }

    //other methods
    private void prepareProfile(DistributorAccountResponse distributor_account)
    {

        if(distributor_account.bank_account != null) {

            List<String> bank_keys_list = Arrays.asList(getResources().getStringArray(R.array.bank_keys));
            Integer pos = bank_keys_list.indexOf(distributor_account.bank_account.bank_name);
            if(pos != null) {
                bankNameSpinner.setSelection(pos);
            }
            accountNumber.setText(distributor_account.bank_account.account_number);
        }
    }

    private void profileBankAccountEdit(final View view) {

        String mlm_member_id = String.valueOf(authLoginRequest.getAccountLogin().getMlmMemberId());
        String session = authLoginRequest.getAccountLogin().getSession();
        String auth_token = authLoginRequest.getAccountLogin().getAuthToken();

        profileBankAccountEditRequestQueue.getRequest().setAuthorization(mlm_member_id, session, auth_token);

        List<String> bank_keys_list = Arrays.asList(getResources().getStringArray(R.array.bank_keys));
        String bank_name = bank_keys_list.get(bankNameSpinner.getSelectedItemPosition());

        profileBankAccountEditRequestQueue.getRequest().addParameter("bank_name", bank_name);
        profileBankAccountEditRequestQueue.getRequest().addParameter("account_number", accountNumber.getText().toString());

        vg.victoryglobal.victoryglobal.network.http.client.RequestQueue.NewJsonRequestQueue(
                getActivity().getApplicationContext(),
                profileBankAccountEditRequestQueue.getRequest(),
                this,
                this
        );

    }

    @Override
    public void onResponse(String responseData) {
        try {
            JSONObject object = (JSONObject) new JSONTokener(responseData).nextValue();
            int status = object.getInt("status");
            if(status == 200 ){
                profileBankAccountEditRequestQueue.onSuccess(responseData);

                DistributorAccountResponse distributor_account = distributorAccountRequest.getDistributorAccountResponse();

                if(distributor_account.bank_account == null) {
                    distributorAccountRequest.getDistributorAccountResponse().bank_account = new BankAccount();
                }

                if(bankNameSpinner.isSelected()) {

                    List<String> bank_keys_list = Arrays.asList(getResources().getStringArray(R.array.bank_keys));
                    Integer pos = bankNameSpinner.getSelectedItemPosition();

                    distributor_account.bank_account.bank_name = bank_keys_list.get(pos);
                }
                if(accountNumber.getText().length()>0) {
                    distributor_account.bank_account.account_number = accountNumber.getText().toString();
                }
                callback.prepareProfile(distributor_account);

            }else if(status == 402 ) {
                String message = object.getString("message");
                Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }else if(status == 401 ){
                String message = object.getString("message");
                Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();

            }else{
                Toast.makeText(getActivity().getApplicationContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();
            }
        }catch (JSONException e) {
            //do nothing
            Log.e("ProfileInfoEdit", "onResponse: (4) " + e.getMessage());
            Toast.makeText(getActivity().getApplicationContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();
        }

        dismiss();

    }

    @Override
    public void onErrorResponse(String responseData, Integer statusCode) {
        Log.e("ProfileBankAccountEdit", "onErrorResponse: " + responseData);
        Toast.makeText(getActivity().getApplicationContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();
        dismiss();
    }
}
