/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 1/22/18 7:53 PM
 *
 * Copyright (c) 2018 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 1/22/18 7:53 PM
 */

package vg.victoryglobal.victoryglobal.listener;

import vg.victoryglobal.victoryglobal.model.DistributorAccountResponse;

public interface OnProfileUpdateListener {
    void prepareProfile(DistributorAccountResponse distributor_account);
}
