/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/9/17 7:01 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/9/17 6:59 AM
 */

package vg.victoryglobal.victoryglobal.model;

class RegistrationRequest {

    private RegisterAccount registerAccount;

    private static final RegistrationRequest ourInstance = new RegistrationRequest();

    static RegistrationRequest getInstance() {
        return ourInstance;
    }

    private RegistrationRequest() {
    }

    public RegisterAccount getRegisterAccount() {
        return registerAccount;
    }

    public void setRegisterAccount(RegisterAccount registerAccount) {
        this.registerAccount = registerAccount;
    }
}
