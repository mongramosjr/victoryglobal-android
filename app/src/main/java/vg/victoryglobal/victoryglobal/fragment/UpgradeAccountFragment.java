package vg.victoryglobal.victoryglobal.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stepstone.stepper.StepperLayout;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.adapter.ActivateCodeStepperAdapter;
import vg.victoryglobal.victoryglobal.adapter.UpgradeAccountStepperAdapter;

public class UpgradeAccountFragment extends Fragment {

    public View currentView;
    private StepperLayout mStepperLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.upgrade_account_fragment, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        currentView = view;

        mStepperLayout = view.findViewById(R.id.upgrade_account_stepper_layout);

        UpgradeAccountStepperAdapter upgradeAccountStepperAdapter
                = new UpgradeAccountStepperAdapter(getChildFragmentManager(), getContext());

        mStepperLayout.setAdapter(upgradeAccountStepperAdapter);

    }

}