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

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import vg.victoryglobal.victoryglobal.fragment.RegisterAccountAddressAndContact;
import vg.victoryglobal.victoryglobal.fragment.RegisterAccountConfirm;
import vg.victoryglobal.victoryglobal.fragment.RegisterAccountMlmInfo;
import vg.victoryglobal.victoryglobal.fragment.RegisterAccountPersonalInfo;
import vg.victoryglobal.victoryglobal.fragment.RegisterAccountSecurity;

public class RegisterAccountStepperAdapter extends AbstractFragmentStepAdapter {

    public RegisterAccountStepperAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {

        final RegisterAccountPersonalInfo stepPersonalInfo;
        final RegisterAccountAddressAndContact stepAddressAndContact;
        final RegisterAccountMlmInfo stepVerify;
        final RegisterAccountConfirm stepConfirm;
        final RegisterAccountSecurity stepSecurity;



        if(position == 0) {
            //step = ActivateCodeVerify.class;
            stepPersonalInfo = new RegisterAccountPersonalInfo();
            return stepPersonalInfo;
        }else if(position == 1){
            //step = RegisterAccountAddressAndContact.class;
            stepAddressAndContact = new RegisterAccountAddressAndContact();
            return stepAddressAndContact;
        }else if(position == 2){
            //step = RegisterAccountMlmInfo.class;
            stepVerify = new RegisterAccountMlmInfo();
            return stepVerify;
        }else if(position == 3){
            //step = RegisterAccountSecurity.class;
            stepSecurity = new RegisterAccountSecurity();
            return stepSecurity;
        }else if(position == 4){
            //step = RegisterAccountConfirm.class;
            stepConfirm = new RegisterAccountConfirm();
            return stepConfirm;
        }else{
            stepPersonalInfo = new RegisterAccountPersonalInfo();
            return stepPersonalInfo;
        }
    }

    @Override
    public int getCount() {
        return 5;
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
