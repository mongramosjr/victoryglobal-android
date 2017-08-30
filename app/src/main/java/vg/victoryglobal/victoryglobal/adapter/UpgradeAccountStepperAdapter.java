package vg.victoryglobal.victoryglobal.adapter;


import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import vg.victoryglobal.victoryglobal.fragment.ActivateCodeConfirm;
import vg.victoryglobal.victoryglobal.fragment.ActivateCodeVerify;
import vg.victoryglobal.victoryglobal.fragment.UpgradeAccountConfirm;
import vg.victoryglobal.victoryglobal.fragment.UpgradeAccountVerify;

public class UpgradeAccountStepperAdapter extends AbstractFragmentStepAdapter {

    public UpgradeAccountStepperAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {

        if(position == 0) {
            //step = ActivateCodeVerify.class;
            final UpgradeAccountVerify stepVerify = new UpgradeAccountVerify();
            return stepVerify;
        }else if(position == 1){
            //step = ActivateCodeConfirm.class;
            final UpgradeAccountConfirm stepConfirm = new UpgradeAccountConfirm();
            return stepConfirm;
        }else{
            final UpgradeAccountVerify stepVerify = new UpgradeAccountVerify();
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
