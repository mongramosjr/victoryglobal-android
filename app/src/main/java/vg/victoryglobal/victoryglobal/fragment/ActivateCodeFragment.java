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
import vg.victoryglobal.victoryglobal.adapter.ActivateCodeStepperAdapter;

public class ActivateCodeFragment extends Fragment {

    public View currentView;

    private StepperLayout mStepperLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activate_code_fragment, container, false);

    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        currentView = view;

        mStepperLayout = view.findViewById(R.id.activate_code_stepper_layout);

        ActivateCodeStepperAdapter activateCodeStepperAdapter
                = new ActivateCodeStepperAdapter(getChildFragmentManager(), getContext());

        mStepperLayout.setAdapter(activateCodeStepperAdapter);

    }
}
