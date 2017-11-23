/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/24/17 6:17 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/24/17 6:17 AM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * Upline model from API response
 */
public class Upline implements Parcelable {
    public Integer id;
    public Profile mlm_account;

    protected Upline(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        mlm_account = in.readParcelable(Profile.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeParcelable(mlm_account, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Upline> CREATOR = new Creator<Upline>() {
        @Override
        public Upline createFromParcel(Parcel in) {
            return new Upline(in);
        }

        @Override
        public Upline[] newArray(int size) {
            return new Upline[size];
        }
    };
}
