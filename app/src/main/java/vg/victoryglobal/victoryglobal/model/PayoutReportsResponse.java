/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/27/17 6:52 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/27/17 6:52 AM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class PayoutReportsResponse implements Parcelable{
    public ArrayList<PayoutReport> payout_reports;
    public Paging paging;
    public Integer status;


    public PayoutReportsResponse()
    {
       payout_reports = new ArrayList<>();
    }

    protected PayoutReportsResponse(Parcel in) {
        payout_reports = new ArrayList<>();
        payout_reports = in.createTypedArrayList(PayoutReport.CREATOR);
        paging = in.readParcelable(Paging.class.getClassLoader());
        if (in.readByte() == 0) {
            status = null;
        } else {
            status = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(payout_reports);
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

    public static final Creator<PayoutReportsResponse> CREATOR = new Creator<PayoutReportsResponse>() {
        @Override
        public PayoutReportsResponse createFromParcel(Parcel in) {
            return new PayoutReportsResponse(in);
        }

        @Override
        public PayoutReportsResponse[] newArray(int size) {
            return new PayoutReportsResponse[size];
        }
    };

    //setter and getter

    public ArrayList<PayoutReport> getPayout_reports() {
        return payout_reports;
    }
}
