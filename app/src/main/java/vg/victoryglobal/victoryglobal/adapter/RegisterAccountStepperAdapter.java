package vg.victoryglobal.victoryglobal.adapter;


import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import vg.victoryglobal.victoryglobal.fragment.RegisterAccountConfirm;
import vg.victoryglobal.victoryglobal.fragment.RegisterAccountVerify;

public class RegisterAccountStepperAdapter extends AbstractFragmentStepAdapter {

    public RegisterAccountStepperAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {

        if(position == 0) {
            //step = ActivateCodeVerify.class;
            final RegisterAccountVerify stepVerify = new RegisterAccountVerify();
            return stepVerify;
        }else if(position == 1){
            //step = ActivateCodeConfirm.class;
            final RegisterAccountConfirm stepConfirm = new RegisterAccountConfirm();
            return stepConfirm;
        }else{
            final RegisterAccountVerify stepVerify = new RegisterAccountVerify();
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
