/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/19/17 9:57 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 10/29/17 7:03 PM
 */

package vg.victoryglobal.victoryglobal.model;

import java.util.ArrayList;

public class AccountLoginRequest {

    private AccountLogin accountLogin  = new AccountLogin();

    private ArrayList<MlmResponseError> mlmResponseErrors = new ArrayList<>();

    private boolean success = false;

    private static final AccountLoginRequest ourInstance = new AccountLoginRequest();

    public static AccountLoginRequest getInstance() {
        return ourInstance;
    }

    private AccountLoginRequest() {
    }

    //setter and getter
    public AccountLogin getAccountLogin() {
        return accountLogin;
    }

    public void setAccountLogin(AccountLogin accountLogin) {
        this.accountLogin = accountLogin;
    }

    public void setMlmResponseErrors(ArrayList<MlmResponseError> mlmResponseErrors) {
        this.mlmResponseErrors = mlmResponseErrors;
    }

    public ArrayList<MlmResponseError> getMlmResponseErrors() {
        return mlmResponseErrors;
    }

    public synchronized void reset() {
        setAccountLogin(new AccountLogin());
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
