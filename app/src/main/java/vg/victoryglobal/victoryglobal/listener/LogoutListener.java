/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/19/17 12:35 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/19/17 12:05 PM
 */

package vg.victoryglobal.victoryglobal.listener;

import vg.victoryglobal.victoryglobal.model.AccountLogin;

public interface LogoutListener {

    void prepareLogout(AccountLogin account_login);

    void showInterstitialAdAfterLogout();
}
