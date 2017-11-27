/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/27/17 12:41 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/27/17 12:41 PM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class PurchasesResponse implements Parcelable {

    public ArrayList<Purchase> purchases;
    public Paging paging;
    public Integer status;

    public PurchasesResponse() {
        purchases = new ArrayList<>();
    }

    protected PurchasesResponse(Parcel in) {
        purchases = in.createTypedArrayList(Purchase.CREATOR);
        paging = in.readParcelable(Paging.class.getClassLoader());
        if (in.readByte() == 0) {
            status = null;
        } else {
            status = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(purchases);
        dest.writeParcelable(paging, flags);
        if (status == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(status);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PurchasesResponse> CREATOR = new Creator<PurchasesResponse>() {
        @Override
        public PurchasesResponse createFromParcel(Parcel in) {
            return new PurchasesResponse(in);
        }

        @Override
        public PurchasesResponse[] newArray(int size) {
            return new PurchasesResponse[size];
        }
    };

    //setter and getter


    public ArrayList<Purchase> getPurchases() {
        return purchases;
    }
}
