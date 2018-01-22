/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/24/17 6:20 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/24/17 6:20 AM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;

/*
 * Sponsor model from API response
 */
public class Sponsor extends BaseMlmMember {

    public Profile mlm_account;

    protected Sponsor(Parcel in) {
        super(in);
        mlm_account = in.readParcelable(Profile.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(mlm_account, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Sponsor> CREATOR = new Creator<Sponsor>() {
        @Override
        public Sponsor createFromParcel(Parcel in) {
            return new Sponsor(in);
        }

        @Override
        public Sponsor[] newArray(int size) {
            return new Sponsor[size];
        }
    };
}
