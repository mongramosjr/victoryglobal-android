/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/19/17 12:35 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/19/17 12:05 PM
 */

package vg.victoryglobal.victoryglobal.listener;

import vg.victoryglobal.victoryglobal.model.AccountLogin;

/**
 * Created by mong on 11/19/17.
 */

public interface LogoutListener {

    void prepareLogout(AccountLogin account_login);
}
