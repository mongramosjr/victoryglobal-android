/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/14/17 7:41 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/13/17 8:27 PM
 */

package vg.victoryglobal.victoryglobal.model;

import java.util.ArrayList;

public class RegisterAccountRequest {

    private RegisterAccount registerAccount  = new RegisterAccount();

    private ArrayList<MlmResponseError> mlmResponseErrors = new ArrayList<>();

    private Boolean success = false;
    private Boolean successPersonalInfo = false;
    private Boolean successAddressAndContact = false;
    private Boolean successMlmInfo = false;
    private Boolean successSecurity = false;
    private Boolean successNew = false;

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
        setRegisterAccount(new RegisterAccount());
    }
    public synchronized void resetErrorCodes() {
        mlmResponseErrors.clear();
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean isSuccessPersonalInfo() {
        return successPersonalInfo;
    }

    public void setSuccessPersonalInfo(Boolean successPersonalInfo) {
        this.successPersonalInfo = successPersonalInfo;
    }

    public Boolean isSuccessAddressAndContact() {
        return successAddressAndContact;
    }

    public void setSuccessAddressAndContact(Boolean successAddressAndContact) {
        this.successAddressAndContact = successAddressAndContact;
    }

    public void setSuccessMlmInfo(Boolean successMlmInfo) {
        this.successMlmInfo = successMlmInfo;
    }

    public Boolean isSuccessMlmInfo() {
        return successMlmInfo;
    }

    public void setSuccessSecurity(Boolean successSecurity) {
        this.successSecurity = successSecurity;
    }

    public Boolean isSuccessSecurity() {
        return successSecurity;
    }

    public Boolean isSuccessNew() {
        return successNew;
    }

    public void setSuccessNew(Boolean successNew) {
        this.successNew = successNew;
    }
}
