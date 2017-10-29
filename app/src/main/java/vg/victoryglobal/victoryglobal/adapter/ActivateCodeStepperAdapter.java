/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/9/17 7:01 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/9/17 6:59 AM
 */

package vg.victoryglobal.victoryglobal.adapter;


import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import java.util.ArrayList;
import java.util.HashMap;

import vg.victoryglobal.victoryglobal.fragment.ActivateCodeConfirm;
import vg.victoryglobal.victoryglobal.fragment.ActivateCodeVerify;

public class ActivateCodeStepperAdapter extends AbstractFragmentStepAdapter {

    ArrayList<Object> stepFragment = new ArrayList<>();

    HashMap<String , Integer> stepFragmentPosition = new HashMap<>();

    public ActivateCodeStepperAdapter(FragmentManager fm, Context context) {
        super(fm, context);

        final ActivateCodeVerify stepVerify = new ActivateCodeVerify();
        final ActivateCodeConfirm stepConfirm = new ActivateCodeConfirm();

        registerStepFragment(0, stepVerify);
        registerStepFragment(1, stepConfirm);
    }

    public void registerStepFragment(int index,  Object o)
    {
        stepFragment.add(index, o);
        String step_name = o.getClass().getSimpleName();
        stepFragmentPosition.put(step_name, index);
    }

    public int positionStepFragment(String name)
    {
        Integer position = stepFragmentPosition.get(name);
        if(position != null)
        {
            return position.intValue();
        }else{
            return -1;
        }
    }

    @Override
    public Step createStep(int position) {
        return (BlockingStep) stepFragment.get(position);
    }

    @Override
    public int getCount() {
        return stepFragment.size();
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
