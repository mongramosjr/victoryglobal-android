/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/23/17 1:45 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/23/17 1:45 PM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/*
 * Purchase model from API response
 */
public class Purchase implements Parcelable {

    public Integer id;

    public Float grand_total;

    public Date date_posted;

    public Date created;

    protected Purchase(Parcel in) {
        id = in.readInt();
        grand_total = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeFloat(grand_total);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Purchase> CREATOR = new Creator<Purchase>() {
        @Override
        public Purchase createFromParcel(Parcel in) {
            return new Purchase(in);
        }

        @Override
        public Purchase[] newArray(int size) {
            return new Purchase[size];
        }
    };

    //setter and getter
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getDate_posted() {
        return date_posted;
    }

    public void setDate_posted(Date date_posted) {
        this.date_posted = date_posted;
    }

    public float getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(float grand_total) {
        this.grand_total = grand_total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // other methods
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
