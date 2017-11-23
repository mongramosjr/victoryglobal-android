/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/24/17 6:28 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/24/17 6:28 AM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/*
 * Validity model from API response
 */
public class MlmValidity implements Parcelable {
    public String attribute_code;
    public Date date_start;
    public Date date_end;


    protected MlmValidity(Parcel in) {
        attribute_code = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(attribute_code);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MlmValidity> CREATOR = new Creator<MlmValidity>() {
        @Override
        public MlmValidity createFromParcel(Parcel in) {
            return new MlmValidity(in);
        }

        @Override
        public MlmValidity[] newArray(int size) {
            return new MlmValidity[size];
        }
    };
}
