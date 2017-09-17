/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/17/17 2:52 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/17/17 2:32 PM
 */

package vg.victoryglobal.victoryglobal.model;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterAccountRequest {

    private RegisterAccount registerAccount  = new RegisterAccount();

    private ArrayList<MlmResponseError> mlmResponseErrors = new ArrayList<>();

    private boolean success = false;
    private boolean successPersonalInfo = false;
    private boolean successAddressAndContact = false;
    private boolean successMlmInfo = false;
    private boolean successSecurity = false;

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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccessPersonalInfo() {
        return successPersonalInfo;
    }

    public void setSuccessPersonalInfo(boolean successPersonalInfo) {
        this.successPersonalInfo = successPersonalInfo;
    }

    public boolean isSuccessAddressAndContact() {
        return successAddressAndContact;
    }

    public void setSuccessAddressAndContact(boolean successAddressAndContact) {
        this.successAddressAndContact = successAddressAndContact;
    }

    public void setSuccessMlmInfo(boolean successMlmInfo) {
        this.successMlmInfo = successMlmInfo;
    }

    public boolean isSuccessMlmInfo() {
        return successMlmInfo;
    }

    public void setSuccessSecurity(boolean successSecurity) {
        this.successSecurity = successSecurity;
    }

    public boolean isSuccessSecurity() {
        return successSecurity;
    }


}
