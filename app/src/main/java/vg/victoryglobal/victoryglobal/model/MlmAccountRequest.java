/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/9/17 9:19 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/9/17 9:01 PM
 */

package vg.victoryglobal.victoryglobal.model;

import java.util.ArrayList;

public class MlmAccountRequest {

    private ArrayList<MlmAccount> mlmAccounts = new ArrayList<>();

    private static final MlmAccountRequest ourInstance = new MlmAccountRequest();

    public static MlmAccountRequest getInstance() {
        return ourInstance;
    }

    private MlmAccountRequest() {
    }

    //setter and getter

    public void setMlmAccounts(ArrayList<MlmAccount> mlmAccounts) {
        this.mlmAccounts = mlmAccounts;
    }

    public ArrayList<MlmAccount> getMlmAccounts() {
        return mlmAccounts;
    }

    public synchronized void reset() {
        mlmAccounts.clear();
    }
}
