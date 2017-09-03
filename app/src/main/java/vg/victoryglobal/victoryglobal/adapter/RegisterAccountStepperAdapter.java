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
import vg.victoryglobal.victoryglobal.fragment.RegisterAccountPersonalInfo;
import vg.victoryglobal.victoryglobal.fragment.RegisterAccountSecurity;
import vg.victoryglobal.victoryglobal.fragment.RegisterAccountVerify;

public class RegisterAccountStepperAdapter extends AbstractFragmentStepAdapter {

    public RegisterAccountStepperAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {

        if(position == 0) {
            //step = ActivateCodeVerify.class;
            final RegisterAccountPersonalInfo stepPersonalInfo = new RegisterAccountPersonalInfo();
            return stepPersonalInfo;
        }else if(position == 1){
            //step = RegisterAccountAddressAndContact.class;
            final RegisterAccountAddressAndContact stepAddressAndContact = new RegisterAccountAddressAndContact();
            return stepAddressAndContact;
        }else if(position == 2){
            //step = RegisterAccountVerify.class;
            final RegisterAccountVerify stepVerify = new RegisterAccountVerify();
            return stepVerify;
        }else if(position == 3){
            //step = RegisterAccountSecurity.class;
            final RegisterAccountSecurity stepSecurity = new RegisterAccountSecurity();
            return stepSecurity;
        }else if(position == 4){
            //step = RegisterAccountConfirm.class;
            final RegisterAccountConfirm stepConfirm = new RegisterAccountConfirm();
            return stepConfirm;
        }else{
            final RegisterAccountPersonalInfo stepPersonalInfo = new RegisterAccountPersonalInfo();
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
