/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/9/17 9:19 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/9/17 8:45 PM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * Pickup center model from API response
 */
public class PickupCenter implements Parcelable {
    public Integer id;
    public String frontend_label;
    public String city;
    public String region;
    public String country_code;


    //to be used in spinner
    public PickupCenter(Integer id, String frontend_label) {
        this.id = id;
        this.frontend_label = frontend_label;
    }


    protected PickupCenter(Parcel in) {
        id = in.readInt();
        frontend_label = in.readString();
        city = in.readString();
        region = in.readString();
        country_code = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(frontend_label);
        dest.writeString(city);
        dest.writeString(region);
        dest.writeString(country_code);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PickupCenter> CREATOR = new Creator<PickupCenter>() {
        @Override
        public PickupCenter createFromParcel(Parcel in) {
            return new PickupCenter(in);
        }

        @Override
        public PickupCenter[] newArray(int size) {
            return new PickupCenter[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return frontend_label;
    }

    public void setName(String frontend_label) {
        this.frontend_label = frontend_label;
    }


    //to display object as a string in spinner
    @Override
    public String toString() {
        return frontend_label;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PickupCenter){
            PickupCenter c = (PickupCenter )obj;
            if(c.getName().equals(frontend_label) && c.getId().equals(id)) return true;
        }

        return false;
    }

}
