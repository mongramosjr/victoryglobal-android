/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/22/17 7:26 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/22/17 7:26 PM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/*
 * Payout report model from API response
 */
public class PayoutReport implements Parcelable {

    public Integer id;

    public Float total_amount;

    public Date date_start;

    public Date date_end;

    public String payout_term;

    public String reference_code;

    public Date created;


    protected PayoutReport(Parcel in) {
        id = in.readInt();
        total_amount = in.readFloat();
        payout_term = in.readString();
        reference_code = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeFloat(total_amount);
        dest.writeString(payout_term);
        dest.writeString(reference_code);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PayoutReport> CREATOR = new Creator<PayoutReport>() {
        @Override
        public PayoutReport createFromParcel(Parcel in) {
            return new PayoutReport(in);
        }

        @Override
        public PayoutReport[] newArray(int size) {
            return new PayoutReport[size];
        }
    };

    //setter and getter
    public Date getDate_end() {
        return date_end;
    }

    public void setDate_end(Date date_end) {
        this.date_end = date_end;
    }

    public Date getDate_start() {
        return date_start;
    }

    public void setDate_start(Date date_start) {
        this.date_start = date_start;
    }

    public float getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(float total_amount) {
        this.total_amount = total_amount;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getPayout_term() {
        return payout_term;
    }

    public void setPayout_term(String payout_term) {
        this.payout_term = payout_term;
    }

    public String getReference_code() {
        return reference_code;
    }

    public void setReference_code(String reference_code) {
        this.reference_code = reference_code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
