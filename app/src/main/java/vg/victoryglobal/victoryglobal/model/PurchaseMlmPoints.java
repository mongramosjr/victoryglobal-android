/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/28/17 6:18 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/28/17 6:18 AM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PurchaseMlmPoints extends BaseMlmPoint {

    public Integer invoice_id;


    protected PurchaseMlmPoints(Parcel in) {
        super(in);
        if (in.readByte() == 0) {
            invoice_id = null;
        } else {
            invoice_id = in.readInt();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        if (invoice_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(invoice_id);
        }
    }
    public static final Creator<PurchaseMlmPoints> CREATOR = new Creator<PurchaseMlmPoints>() {
        @Override
        public PurchaseMlmPoints createFromParcel(Parcel in) {
            return new PurchaseMlmPoints(in);
        }

        @Override
        public PurchaseMlmPoints[] newArray(int size) {
            return new PurchaseMlmPoints[size];
        }
    };

}
