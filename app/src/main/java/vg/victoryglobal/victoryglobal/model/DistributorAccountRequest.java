/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/24/17 8:00 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/24/17 8:00 AM
 */

package vg.victoryglobal.victoryglobal.model;

import com.google.gson.Gson;

import java.util.ArrayList;

public class DistributorAccountRequest {
    private static final DistributorAccountRequest onlyInstance = new DistributorAccountRequest();

    public static DistributorAccountRequest getInstance() {
        return onlyInstance;
    }

    private DistributorAccountResponse distributorAccountResponse;

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

    public void setDistributorAccountResponse(DistributorAccountResponse distributorAccountResponse) {
        this.distributorAccountResponse = distributorAccountResponse;
    }

    public DistributorAccountResponse getDistributorAccountResponse() {
        return distributorAccountResponse;
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
        distributorAccountResponse = gson.fromJson(response_data, DistributorAccountResponse.class);
        if(distributorAccountResponse == null) {
            setSuccess(false);
            return false;
        }
        setSuccess(true);
        return true;
    }

    public void reset()
    {
        distributorAccountResponse = null;
    }
}
