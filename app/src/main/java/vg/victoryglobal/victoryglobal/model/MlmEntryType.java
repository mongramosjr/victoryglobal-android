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

/*
 * Entry Type model from API response
 */
public class MlmEntryType implements Parcelable {
    public Integer id;
    public String frontend_label;

    protected MlmEntryType(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        frontend_label = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(frontend_label);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MlmEntryType> CREATOR = new Creator<MlmEntryType>() {
        @Override
        public MlmEntryType createFromParcel(Parcel in) {
            return new MlmEntryType(in);
        }

        @Override
        public MlmEntryType[] newArray(int size) {
            return new MlmEntryType[size];
        }
    };
}
