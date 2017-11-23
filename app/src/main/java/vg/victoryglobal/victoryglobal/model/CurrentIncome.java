/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/24/17 6:36 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/24/17 6:36 AM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;


/*
 * Current Income model from API response
 */
public class CurrentIncome implements Parcelable {
    public Float total_amount;
    public Date date_start;
    public Date date_end;

    protected CurrentIncome(Parcel in) {
        if (in.readByte() == 0) {
            total_amount = null;
        } else {
            total_amount = in.readFloat();
        }
    }

    public static final Creator<CurrentIncome> CREATOR = new Creator<CurrentIncome>() {
        @Override
        public CurrentIncome createFromParcel(Parcel in) {
            return new CurrentIncome(in);
        }

        @Override
        public CurrentIncome[] newArray(int size) {
            return new CurrentIncome[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (total_amount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(total_amount);
        }
    }
}
