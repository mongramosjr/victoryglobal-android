/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/9/17 7:01 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/9/17 6:59 AM
 */

package vg.victoryglobal.victoryglobal.model;

import java.util.concurrent.atomic.AtomicReference;

class CodeActivationRequest {

    private CodeActivation codeActivation = new CodeActivation();

    //private AtomicReference<CodeActivation> codeActivationAtomicReference = new AtomicReference<CodeActivation>();

    private static final CodeActivationRequest ourInstance = new CodeActivationRequest();

    static CodeActivationRequest getInstance() {
        return ourInstance;
    }

    private CodeActivationRequest() {
    }

    //setter and getter


    public void setCodeActivation(CodeActivation codeActivation) {
        this.codeActivation = codeActivation;
    }

    public CodeActivation getCodeActivation() {
        return codeActivation;
    }

    public synchronized void reset() {
        codeActivation = new CodeActivation();
    }


}
