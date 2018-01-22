/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/24/17 6:32 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/24/17 6:32 AM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * Address model from API response
 */
public class Address implements Parcelable {
    public String street;
    public String city;
    public String region;
    public Integer post_code;
    public String country_code;

    public Address() {

    }

    protected Address(Parcel in) {
        street = in.readString();
        city = in.readString();
        region = in.readString();
        if (in.readByte() == 0) {
            post_code = null;
        } else {
            post_code = in.readInt();
        }
        country_code = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(street);
        dest.writeString(city);
        dest.writeString(region);
        if (post_code == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(post_code);
        }
        dest.writeString(country_code);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };


    //other methods
    public String addressFormatted()
    {
        String result = "";
        if(street != null) result = result.concat(street);
        if(city != null) {
            if(street != null) result = result.concat("\n" + city);
            else result = result.concat(city);
        }
        if(region != null){
            if(city != null) result = result.concat(", " + region);
            else if(street != null && city == null) result = result.concat("\n" + region);
            else result = result.concat(region);
        }
        if(post_code != null){
            if(region != null) result = result.concat(" " + post_code.toString());
            else if(region == null && city != null) result = result.concat(", " + post_code.toString());
            else if(region == null && city == null && street != null ) result = result.concat("\n" + post_code.toString());
            else result = result.concat(post_code.toString());
        }
        if(country_code != null) {
            if(result.length() > 0 ) result = result.concat("\n" + country_code);
            else result = result.concat(country_code);
        }

        return result;

    }
}
