/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/23/17 1:56 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/23/17 1:56 PM
 */

package vg.victoryglobal.victoryglobal.model;

import java.util.ArrayList;

public class PurchasesRequest {
    private static final PurchasesRequest onlyInstance = new PurchasesRequest();

    private ArrayList<Purchase> purchases = new ArrayList<>();

    private ArrayList<MlmResponseError> mlmResponseErrors = new ArrayList<>();

    private boolean success = false;

    public static PurchasesRequest getInstance() {
        return onlyInstance;
    }

    private PurchasesRequest() {
    }

    //setter and getter
    public ArrayList<MlmResponseError> getMlmResponseErrors() {
        return mlmResponseErrors;
    }

    public void setMlmResponseErrors(ArrayList<MlmResponseError> mlmResponseErrors) {
        this.mlmResponseErrors = mlmResponseErrors;
    }

    public ArrayList<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(ArrayList<Purchase> purchases) {
        this.purchases = purchases;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
