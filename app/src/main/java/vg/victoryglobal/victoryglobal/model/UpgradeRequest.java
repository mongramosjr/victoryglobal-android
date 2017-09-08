/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/9/17 7:01 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/9/17 6:59 AM
 */

package vg.victoryglobal.victoryglobal.model;

class UpgradeRequest {

    private UpgradeAccount upgradeAccount;

    private static final UpgradeRequest ourInstance = new UpgradeRequest();

    static UpgradeRequest getInstance() {
        return ourInstance;
    }

    private UpgradeRequest() {
    }

    public UpgradeAccount getUpgradeAccount() {
        return upgradeAccount;
    }

    public void setUpgradeAccount(UpgradeAccount upgradeAccount) {
        this.upgradeAccount = upgradeAccount;
    }
}
