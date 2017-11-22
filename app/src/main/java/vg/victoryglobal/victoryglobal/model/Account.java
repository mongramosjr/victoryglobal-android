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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Account implements Parcelable {

    public Integer id;
    public Integer mlm_account_id;
    public Integer customer_id;
    public Integer mlm_sponsor;
    public Integer mlm_upline;
    public Integer mlm_entry_type;
    public Integer mlm_rank;
    public boolean account_status;
    public Date date_last_active;
    public Integer pickup_center_id;
    public Integer bank_account_id;
    public String country_code;



    public Account(){

    }

    public Account(int id){
        this.id = id;
    }

    protected Account(Parcel in) {
        id = in.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(country_code);
    }

    //setter and getter
    public String createdTimeFormatted(Date date_time)
    {
        String display_date;

        //2017-09-18T08:02:39+0000
        //yyyy-MM-dd'T'HH:mm:ssZ
        String myFormat = "yyyy-MM-dd'T'HH:mm:ssZ"; //In which you need put here
        SimpleDateFormat sdformat = new SimpleDateFormat(myFormat, Locale.US);

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date_time);

        myFormat = "MMM dd, yyyy 'at' hh:mm a"; //In which you need put here
        sdformat = new SimpleDateFormat(myFormat, Locale.US);


        display_date = sdformat.format(calendar.getTime());


        return display_date;


    }

}
