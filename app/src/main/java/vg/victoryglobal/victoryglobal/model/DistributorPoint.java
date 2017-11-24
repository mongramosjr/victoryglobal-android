/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/24/17 6:22 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/24/17 6:22 AM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * Distributor Points model from API response
 */
public class DistributorPoint implements Parcelable {

    public Integer id;
    public String attribute_code;
    public Float value;

    protected DistributorPoint(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        attribute_code = in.readString();
        if (in.readByte() == 0) {
            value = null;
        } else {
            value = in.readFloat();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(attribute_code);
        if (value == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(value);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DistributorPoint> CREATOR = new Creator<DistributorPoint>() {
        @Override
        public DistributorPoint createFromParcel(Parcel in) {
            return new DistributorPoint(in);
        }

        @Override
        public DistributorPoint[] newArray(int size) {
            return new DistributorPoint[size];
        }
    };
}
