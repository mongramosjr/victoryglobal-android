/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/14/17 7:41 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/13/17 8:42 PM
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

import vg.victoryglobal.victoryglobal.fragment.RegisterAccountAddressAndContact;
import vg.victoryglobal.victoryglobal.fragment.RegisterAccountConfirm;
import vg.victoryglobal.victoryglobal.fragment.RegisterAccountMlmInfo;
import vg.victoryglobal.victoryglobal.fragment.RegisterAccountNew;
import vg.victoryglobal.victoryglobal.fragment.RegisterAccountPersonalInfo;
import vg.victoryglobal.victoryglobal.fragment.RegisterAccountSecurity;
import vg.victoryglobal.victoryglobal.fragment.RegisterAccountSubscribe;

public class RegisterAccountStepperAdapter extends AbstractFragmentStepAdapter {


    ArrayList<Object> stepFragment = new ArrayList<>();

    HashMap<String , Integer> stepFragmentPosition = new HashMap<>();

    class StepFragment{
        Step step;
        int position;
    }

    public RegisterAccountStepperAdapter(FragmentManager fm, Context context) {
        super(fm, context);

        final RegisterAccountPersonalInfo stepPersonalInfo = new RegisterAccountPersonalInfo();
        final RegisterAccountNew stepNew = new RegisterAccountNew();
        final RegisterAccountAddressAndContact stepAddressAndContact  = new RegisterAccountAddressAndContact();
        final RegisterAccountMlmInfo stepMlmInfo  = new RegisterAccountMlmInfo();
        final RegisterAccountConfirm stepConfirm  = new RegisterAccountConfirm();
        final RegisterAccountSecurity stepSecurity = new RegisterAccountSecurity();
        final RegisterAccountSubscribe stepSubscribe = new RegisterAccountSubscribe();

        registerStepFragment(0, stepNew);
        registerStepFragment(1, stepMlmInfo);
        registerStepFragment(2, stepSubscribe);
        //registerStepFragment(3, stepPersonalInfo);
        //registerStepFragment(4, stepAddressAndContact);
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
