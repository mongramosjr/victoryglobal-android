/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/24/17 6:12 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/24/17 6:12 AM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/*
 * Profile model from API response
 */
public class Profile implements Parcelable {

    public String marital_status;
    public Integer gender;
    public Date date_of_birth;
    public String place_of_birth;

    public String tax_number;
    public String social_security_number;

    public String maiden_name;
    public String spouse_name;
    public String occupation;

    public String domicile;
    public String nationality;

    public String modified;

    public String frontend_label;
    public String first_name;
    public String middle_name;
    public String last_name;


    protected Profile(Parcel in) {
        marital_status = in.readString();
        if (in.readByte() == 0) {
            gender = null;
        } else {
            gender = in.readInt();
        }
        place_of_birth = in.readString();
        tax_number = in.readString();
        social_security_number = in.readString();
        maiden_name = in.readString();
        spouse_name = in.readString();
        occupation = in.readString();
        domicile = in.readString();
        nationality = in.readString();
        modified = in.readString();
        frontend_label = in.readString();
        first_name = in.readString();
        middle_name = in.readString();
        last_name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(marital_status);
        if (gender == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(gender);
        }
        dest.writeString(place_of_birth);
        dest.writeString(tax_number);
        dest.writeString(social_security_number);
        dest.writeString(maiden_name);
        dest.writeString(spouse_name);
        dest.writeString(occupation);
        dest.writeString(domicile);
        dest.writeString(nationality);
        dest.writeString(modified);
        dest.writeString(frontend_label);
        dest.writeString(first_name);
        dest.writeString(middle_name);
        dest.writeString(last_name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    //other methods
    // other methods
    public String createdTimeFormatted(Date date_time)
    {
        if(date_time == null) return "";

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
