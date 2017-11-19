/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/19/17 12:45 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/19/17 12:45 PM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Date;

public class DistributorAccount implements Parcelable {

    private int id;
    private int mlmAccountId;
    private int customerId;
    private int mlmSponsor;
    private int mlmUpline;
    private int mlmEntryType;
    private int mlmRank;
    private boolean accountStatus;
    private Date dateLastActive;
    private PickupCenter pickupCenterId;
    private int bankAccountId;
    private String country_code;

    public DistributorAccount(){

    }

    public DistributorAccount(int id){
        this.id = id;
    }

    protected DistributorAccount(Parcel in) {
        id = in.readInt();
    }

    public static final Creator<DistributorAccount> CREATOR = new Creator<DistributorAccount>() {
        @Override
        public DistributorAccount createFromParcel(Parcel in) {
            return new DistributorAccount(in);
        }

        @Override
        public DistributorAccount[] newArray(int size) {
            return new DistributorAccount[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(mlmSponsor);
        parcel.writeString(country_code);
    }

    //setter and getter

}
