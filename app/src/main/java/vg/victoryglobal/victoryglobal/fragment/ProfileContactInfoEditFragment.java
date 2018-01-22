/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 12/18/17 6:09 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 12/18/17 5:55 AM
 */

package vg.victoryglobal.victoryglobal.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.model.AuthLoginRequest;
import vg.victoryglobal.victoryglobal.model.DistributorAccountRequest;
import vg.victoryglobal.victoryglobal.model.DistributorAccountResponse;

public class ProfileContactInfoEditFragment extends DialogFragment {


    TextInputLayout inputLayoutEmail;
    TextInputLayout inputLayoutTelephone;
    TextInputLayout inputLayoutMobileNumber;
    TextInputLayout inputLayoutFax;

    TextInputEditText email;
    TextInputEditText telephone;
    TextInputEditText mobileNumber;
    TextInputEditText fax;

    AuthLoginRequest authLoginRequest;

    DistributorAccountRequest distributorAccountRequest;

    public ProfileContactInfoEditFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
        authLoginRequest = AuthLoginRequest.getAuthLoginRequest("main");
        distributorAccountRequest = DistributorAccountRequest.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_contact_info_edit, container);
        getDialog().setTitle(R.string.contact_info);

        inputLayoutEmail = view.findViewById(R.id.email_textinputlayout);
        inputLayoutTelephone = view.findViewById(R.id.telephone_textinputlayout);
        inputLayoutMobileNumber = view.findViewById(R.id.mobile_number_textinputlayout);
        inputLayoutFax = view.findViewById(R.id.fax_textinputlayout);

        email = view.findViewById(R.id.email);
        telephone = view.findViewById(R.id.telephone);

        mobileNumber = view.findViewById(R.id.mobile_number);
        fax = view.findViewById(R.id.fax);

        Button dismiss = view.findViewById(R.id.action_cancel);
        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
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
        if(distributor_account.contact_info != null) {
            email.setText(distributor_account.contact_info.email);
            telephone.setText(distributor_account.contact_info.telephone);
            fax.setText(distributor_account.contact_info.fax);
            mobileNumber.setText(distributor_account.contact_info.mobile_number);
        }

    }
}
