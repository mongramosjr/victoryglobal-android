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
import android.support.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/*
 * Account model from API response
 */
public class Account implements Parcelable {

    public Integer id;
    public Integer mlm_account_id;
    public Integer customer_id;
    public Integer mlm_sponsor;
    public Integer mlm_upline;
    public Integer mlm_entry_type;
    public Integer mlm_rank;
    public Boolean account_status;
    public Date date_last_active;
    public Integer pickup_center_id;
    public Integer bank_account_id;
    public String country_code;
    public Boolean is_active;



    public Account(){

    }

    public Account(Integer id){
        this.id = id;
    }

    protected Account(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            mlm_account_id = null;
        } else {
            mlm_account_id = in.readInt();
        }
        if (in.readByte() == 0) {
            customer_id = null;
        } else {
            customer_id = in.readInt();
        }
        if (in.readByte() == 0) {
            mlm_sponsor = null;
        } else {
            mlm_sponsor = in.readInt();
        }
        if (in.readByte() == 0) {
            mlm_upline = null;
        } else {
            mlm_upline = in.readInt();
        }
        if (in.readByte() == 0) {
            mlm_entry_type = null;
        } else {
            mlm_entry_type = in.readInt();
        }
        if (in.readByte() == 0) {
            mlm_rank = null;
        } else {
            mlm_rank = in.readInt();
        }
        account_status = in.readByte() != 0;
        if (in.readByte() == 0) {
            pickup_center_id = null;
        } else {
            pickup_center_id = in.readInt();
        }
        if (in.readByte() == 0) {
            bank_account_id = null;
        } else {
            bank_account_id = in.readInt();
        }
        country_code = in.readString();
        byte tmpIs_active = in.readByte();
        is_active = tmpIs_active == 0 ? null : tmpIs_active == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        if (mlm_account_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(mlm_account_id);
        }
        if (customer_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(customer_id);
        }
        if (mlm_sponsor == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(mlm_sponsor);
        }
        if (mlm_upline == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(mlm_upline);
        }
        if (mlm_entry_type == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(mlm_entry_type);
        }
        if (mlm_rank == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(mlm_rank);
        }
        dest.writeByte((byte) (account_status ? 1 : 0));
        if (pickup_center_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(pickup_center_id);
        }
        if (bank_account_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(bank_account_id);
        }
        dest.writeString(country_code);
        dest.writeByte((byte) (is_active == null ? 0 : is_active ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    //setter and getter
}
