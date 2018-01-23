/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/24/17 6:34 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/24/17 6:34 AM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/*
 * Bank Account model from API response
 */
public class BankAccount implements Parcelable {
    public String bank_name;
    public String account_number;
    public Date modified;

    public BankAccount(){}

    protected BankAccount(Parcel in) {
        bank_name = in.readString();
        account_number = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bank_name);
        dest.writeString(account_number);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BankAccount> CREATOR = new Creator<BankAccount>() {
        @Override
        public BankAccount createFromParcel(Parcel in) {
            return new BankAccount(in);
        }

        @Override
        public BankAccount[] newArray(int size) {
            return new BankAccount[size];
        }
    };
}
