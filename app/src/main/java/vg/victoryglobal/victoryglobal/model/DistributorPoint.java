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
public class DistributorPoint extends BaseMlmPoint {

    protected DistributorPoint(Parcel in) {

        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
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
