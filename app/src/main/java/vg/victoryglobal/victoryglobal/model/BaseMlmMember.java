/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/30/17 12:10 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/30/17 12:10 PM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class BaseMlmMember implements Parcelable {
    public Integer id;
    public Integer mlm_entry_type;
    public Integer mlm_rank;
    public String country_code;
    public String frontend_label;

    protected BaseMlmMember(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            mlm_entry_type = null;
        } else {
            mlm_entry_type = in.readInt();
        }
        if (in.readByte() == 0) {
            mlm_rank = null;
        } else {
            mlm_rank = in.readInt();
        }
        country_code = in.readString();
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
        if (mlm_entry_type == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(mlm_entry_type);
        }
        if (mlm_rank == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(mlm_rank);
        }
        dest.writeString(country_code);
        dest.writeString(frontend_label);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
