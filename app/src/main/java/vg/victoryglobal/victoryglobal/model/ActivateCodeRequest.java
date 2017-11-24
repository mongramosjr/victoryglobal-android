/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/12/17 2:08 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/12/17 1:35 PM
 */

package vg.victoryglobal.victoryglobal.model;

import java.util.ArrayList;

public class ActivateCodeRequest {

    private ActivateCode activateCode = new ActivateCode();

    private ArrayList<MlmResponseError> mlmResponseErrors = new ArrayList<>();

    private Boolean success = false;

    //private AtomicReference<ActivateCode> codeActivationAtomicReference = new AtomicReference<ActivateCode>();


    //eager loading singeton
    //private static final ActivateCodeRequest onlyInstance = new ActivateCodeRequest();
    //
    //public synchronized static ActivateCodeRequest getInstance() {
    //    return onlyInstance;
    //}

    //lazy loading singleton
    private static class Loader {
        static final ActivateCodeRequest onlyInstance = new ActivateCodeRequest();
    }

    public static ActivateCodeRequest getInstance(){
        return Loader.onlyInstance;
    }

    private ActivateCodeRequest() {
    }

    //setter and getter
    public void setActivateCode(ActivateCode activateCode) {
        this.activateCode = activateCode;
    }

    public ActivateCode getActivateCode() {
        return activateCode;
    }

    public void setMlmResponseErrors(ArrayList<MlmResponseError> mlmResponseErrors) {
        this.mlmResponseErrors = mlmResponseErrors;
    }

    public ArrayList<MlmResponseError> getMlmResponseErrors() {
        return mlmResponseErrors;
    }

    public synchronized void reset() {
        activateCode = new ActivateCode();
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
}
