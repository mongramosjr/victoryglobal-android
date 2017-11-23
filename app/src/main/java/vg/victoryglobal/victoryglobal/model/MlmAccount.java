/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/9/17 9:19 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/9/17 8:49 PM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MlmAccount implements Parcelable {
    private String id;
    private String name;

    //to be used in spinner
    public MlmAccount(String id, String name) {
        this.id = id;
        this.name = name;
    }

    protected MlmAccount(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MlmAccount> CREATOR = new Creator<MlmAccount>() {
        @Override
        public MlmAccount createFromParcel(Parcel in) {
            return new MlmAccount(in);
        }

        @Override
        public MlmAccount[] newArray(int size) {
            return new MlmAccount[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    //to display object as a string in spinner
    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MlmAccount){
            MlmAccount c = (MlmAccount)obj;
            if(c.getName().equals(name) && c.getId().equals(id)) return true;
        }

        return false;
    }

}
