/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/9/17 7:01 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/9/17 6:59 AM
 */

package vg.victoryglobal.victoryglobal.adapter;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.fragment.ActivateCodeConfirm;
import vg.victoryglobal.victoryglobal.fragment.ActivateCodeVerify;
import vg.victoryglobal.victoryglobal.fragment.RegisterAccountFragment;

public class ActivateCodeStepperAdapter extends AbstractFragmentStepAdapter {

    public ActivateCodeStepperAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {

        if(position == 0) {
            //step = ActivateCodeVerify.class;
            final ActivateCodeVerify stepVerify = new ActivateCodeVerify();
            return stepVerify;
        }else if(position == 1){
            //step = ActivateCodeConfirm.class;
            final ActivateCodeConfirm stepConfirm = new ActivateCodeConfirm();
            return stepConfirm;
        }else{
            final ActivateCodeVerify stepVerify = new ActivateCodeVerify();
            return stepVerify;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        //Override this method to set Step title for the Tabs, not necessary for other stepper types
        return new StepViewModel.Builder(context)
                .setTitle("Stepper") //can be a CharSequence instead
                .create();
    }
}
