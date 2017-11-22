/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/23/17 3:30 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/23/17 3:30 AM
 */

package vg.victoryglobal.victoryglobal.model;

import java.util.ArrayList;

public class PayoutReportsRequest {
    private static final PayoutReportsRequest onlyInstance = new PayoutReportsRequest();

    private ArrayList<PayoutReport> payoutReports = new ArrayList<>();

    private ArrayList<MlmResponseError> mlmResponseErrors = new ArrayList<>();

    private boolean success = false;


    public static PayoutReportsRequest getInstance() {
        return onlyInstance;
    }

    private PayoutReportsRequest() {
    }

    //setter and getter

    public ArrayList<MlmResponseError> getMlmResponseErrors() {
        return mlmResponseErrors;
    }

    public void setMlmResponseErrors(ArrayList<MlmResponseError> mlmResponseErrors) {
        this.mlmResponseErrors = mlmResponseErrors;
    }

    public ArrayList<PayoutReport> getPayoutReports() {
        return payoutReports;
    }

    public void setPayoutReports(ArrayList<PayoutReport> payoutReports) {
        this.payoutReports = payoutReports;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
