/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/9/17 7:01 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/9/17 6:59 AM
 */

package vg.victoryglobal.victoryglobal.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stepstone.stepper.StepperLayout;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.adapter.RegisterAccountStepperAdapter;
import vg.victoryglobal.victoryglobal.model.PickupCenterRequest;


public class RegisterAccountFragment extends Fragment {

    public View currentView;
    private StepperLayout mStepperLayout;
    PickupCenterRequest pickupCenterRequest;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pickupCenterRequest = PickupCenterRequest.getInstance();

        pickupCenterRequest.PickupCenters(getContext());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.register_account_fragment, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        currentView = view;

        mStepperLayout = view.findViewById(R.id.register_account_stepper_layout);

        RegisterAccountStepperAdapter registerAccountStepperAdapter
                = new RegisterAccountStepperAdapter(getChildFragmentManager(), getContext());

        mStepperLayout.setAdapter(registerAccountStepperAdapter);

    }






}
