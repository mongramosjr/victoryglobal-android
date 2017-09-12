/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/12/17 2:54 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/12/17 2:16 PM
 */

package vg.victoryglobal.victoryglobal.model;

import java.util.ArrayList;

public class UpgradeAccountRequest {

    private UpgradeAccount upgradeAccount =  new UpgradeAccount();

    private ArrayList<MlmResponseError> mlmResponseErrors = new ArrayList<>();

    private boolean success = false;

    //eager loading singeton
    private static final UpgradeAccountRequest ourInstance = new UpgradeAccountRequest();
    public static UpgradeAccountRequest getInstance() {
        return ourInstance;
    }

    private UpgradeAccountRequest() {
    }

    //setter and getter
    public UpgradeAccount getUpgradeAccount() {
        return upgradeAccount;
    }

    public void setUpgradeAccount(UpgradeAccount upgradeAccount) {
        this.upgradeAccount = upgradeAccount;
    }


    public void setMlmResponseErrors(ArrayList<MlmResponseError> mlmResponseErrors) {
        this.mlmResponseErrors = mlmResponseErrors;
    }

    public ArrayList<MlmResponseError> getMlmResponseErrors() {
        return mlmResponseErrors;
    }

    public synchronized void reset() {
        upgradeAccount = new UpgradeAccount();
    }
    public synchronized void resetErrorCodes() {
        mlmResponseErrors.clear();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}