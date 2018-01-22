/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/30/17 11:15 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/30/17 11:15 AM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

public class GenealogyStructurePerLevel implements Parcelable{

    HashMap<String, GenealogyAccount> mlm_members = new HashMap<>();


    protected GenealogyStructurePerLevel(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GenealogyStructurePerLevel> CREATOR = new Creator<GenealogyStructurePerLevel>() {
        @Override
        public GenealogyStructurePerLevel createFromParcel(Parcel in) {
            return new GenealogyStructurePerLevel(in);
        }

        @Override
        public GenealogyStructurePerLevel[] newArray(int size) {
            return new GenealogyStructurePerLevel[size];
        }
    };

    public HashMap<String, GenealogyAccount> getMlm_members() {
        return mlm_members;
    }

    public void setMlm_members(HashMap<String, GenealogyAccount> mlm_members) {
        this.mlm_members = mlm_members;
    }
}
