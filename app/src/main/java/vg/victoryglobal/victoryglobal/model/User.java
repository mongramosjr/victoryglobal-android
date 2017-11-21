/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/21/17 12:16 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/21/17 12:16 AM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Date;

public class User implements Parcelable {

    public String frontend_label;
    public String first_name;
    public String middle_name;
    public String last_name;
    public String email;
    public Integer username;

    public User(){

    }

    public User(int username){
        this.username = username;
    }

    protected User(Parcel in) {
        username = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(username);
    }

    //setter and getter

}
