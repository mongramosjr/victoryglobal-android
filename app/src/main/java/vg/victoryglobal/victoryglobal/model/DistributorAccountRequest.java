/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/24/17 8:00 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/24/17 8:00 AM
 */

package vg.victoryglobal.victoryglobal.model;

import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by mong on 11/24/17.
 */

public class DistributorAccountRequest {
    private static final DistributorAccountRequest onlyInstance = new DistributorAccountRequest();

    public static DistributorAccountRequest getInstance() {
        return onlyInstance;
    }

    private DistributorAccount distributorAccount;

    private ArrayList<MlmResponseError> mlmResponseErrors = new ArrayList<>();

    private Boolean success = false;

    private DistributorAccountRequest() {
    }

    //setter and getter

    public void setMlmResponseErrors(ArrayList<MlmResponseError> mlmResponseErrors) {
        this.mlmResponseErrors = mlmResponseErrors;
    }
    public ArrayList<MlmResponseError> getMlmResponseErrors() {
        return mlmResponseErrors;
    }

    public void setDistributorAccount(DistributorAccount distributorAccount) {
        this.distributorAccount = distributorAccount;
    }

    public DistributorAccount getDistributorAccount() {
        return distributorAccount;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    //other method
    public synchronized Boolean saveDistributorAccount(String response_data) {
        Gson gson = new Gson();
        distributorAccount = gson.fromJson(response_data, DistributorAccount.class);

        return true;
    }
}
