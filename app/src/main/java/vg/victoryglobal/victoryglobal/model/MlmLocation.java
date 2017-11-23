/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/9/17 9:19 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/9/17 8:54 PM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MlmLocation implements Parcelable{
    private String id;
    private String name;

    //to be used in spinner
    public MlmLocation(String id, String name) {
        this.id = id;
        this.name = name;
    }


    protected MlmLocation(Parcel in) {
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

    public static final Creator<MlmLocation> CREATOR = new Creator<MlmLocation>() {
        @Override
        public MlmLocation createFromParcel(Parcel in) {
            return new MlmLocation(in);
        }

        @Override
        public MlmLocation[] newArray(int size) {
            return new MlmLocation[size];
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
        if(obj instanceof MlmLocation){
            MlmLocation c = (MlmLocation)obj;
            if(c.getName().equals(name) && c.getId().equals(id)) return true;
        }

        return false;
    }

}
