/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/9/17 9:19 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/9/17 9:19 PM
 */

package vg.victoryglobal.victoryglobal.model;

import java.util.ArrayList;

public class RegisterAccountRequest {

    private RegisterAccount registerAccount  = new RegisterAccount();

    private ArrayList<MlmResponseError> mlmResponseErrors = new ArrayList<>();

    private static final RegisterAccountRequest ourInstance = new RegisterAccountRequest();

    public static RegisterAccountRequest getInstance() {
        return ourInstance;
    }

    private RegisterAccountRequest() {
    }

    //setter and getter
    public RegisterAccount getRegisterAccount() {
        return registerAccount;
    }

    public void setRegisterAccount(RegisterAccount registerAccount) {
        this.registerAccount = registerAccount;
    }

    public void setMlmResponseErrors(ArrayList<MlmResponseError> mlmResponseErrors) {
        this.mlmResponseErrors = mlmResponseErrors;
    }

    public ArrayList<MlmResponseError> getMlmResponseErrors() {
        return mlmResponseErrors;
    }

    public synchronized void reset() {
        registerAccount = new RegisterAccount();
    }
    public synchronized void resetErrorCodes() {
        mlmResponseErrors.clear();
    }
}
