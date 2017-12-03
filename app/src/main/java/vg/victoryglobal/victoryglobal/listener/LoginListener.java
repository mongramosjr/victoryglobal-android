/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/19/17 12:02 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/19/17 12:02 PM
 */

package vg.victoryglobal.victoryglobal.listener;

import vg.victoryglobal.victoryglobal.model.AccountLogin;

public interface LoginListener {

    void prepareLogin(AccountLogin account_login);

    void prepareRegister();

    void showInterstitialAdAfterLogin();
}
