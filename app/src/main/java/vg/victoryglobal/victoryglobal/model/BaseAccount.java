/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/30/17 10:26 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/30/17 10:26 AM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class BaseAccount implements Parcelable {

    public Integer id;
    public Integer mlm_rank;
    public Integer mlm_entry_type;
    public String country_code;


    public BaseAccount(){

    }

    protected BaseAccount(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            mlm_rank = null;
        } else {
            mlm_rank = in.readInt();
        }
        if (in.readByte() == 0) {
            mlm_entry_type = null;
        } else {
            mlm_entry_type = in.readInt();
        }
        country_code = in.readString();
        byte tmpIs_active = in.readByte();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        if (mlm_rank == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(mlm_rank);
        }
        if (mlm_entry_type == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(mlm_entry_type);
        }
        dest.writeString(country_code);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
