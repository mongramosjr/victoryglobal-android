/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/24/17 6:31 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/24/17 6:31 AM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;


/*
 * Contact Info model from API response
 */
public class ContactInfo implements Parcelable {
    public String email;
    public String telephone;
    public String mobile_number;
    public String fax;

    public ContactInfo(){}

    protected ContactInfo(Parcel in) {
        email = in.readString();
        telephone = in.readString();
        mobile_number = in.readString();
        fax = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(telephone);
        dest.writeString(mobile_number);
        dest.writeString(fax);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ContactInfo> CREATOR = new Creator<ContactInfo>() {
        @Override
        public ContactInfo createFromParcel(Parcel in) {
            return new ContactInfo(in);
        }

        @Override
        public ContactInfo[] newArray(int size) {
            return new ContactInfo[size];
        }
    };
}
