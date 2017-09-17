/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/17/17 2:31 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/17/17 11:33 AM
 */

package vg.victoryglobal.victoryglobal.model;

import java.util.ArrayList;
import java.util.HashMap;

public class MlmAccountRequest {

    private ArrayList<MlmAccount> mlmAccounts = new ArrayList<>();

    private ArrayList<String> mlmAccountStr = new ArrayList<>();
    HashMap<String , MlmAccount> mlmAccountHsh = new HashMap<>();

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
        mlmAccountStr =  new ArrayList<>();
        mlmAccountHsh = new HashMap<>();
    }

    public ArrayList<String> getMlmAccountStr() {
        return mlmAccountStr;
    }

    public void setMlmAccountStr(ArrayList<String> mlmAccountStr) {
        this.mlmAccountStr = mlmAccountStr;
    }

    public HashMap<String, MlmAccount> getMlmAccountHsh() {
        return mlmAccountHsh;
    }

    public void setMlmAccountHsh(HashMap<String, MlmAccount> mlmAccountHsh) {
        this.mlmAccountHsh = mlmAccountHsh;
    }
}
