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

/*
 * User model from API response
 */
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
        frontend_label = in.readString();
        first_name = in.readString();
        middle_name = in.readString();
        last_name = in.readString();
        email = in.readString();
        if (in.readByte() == 0) {
            username = null;
        } else {
            username = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(frontend_label);
        dest.writeString(first_name);
        dest.writeString(middle_name);
        dest.writeString(last_name);
        dest.writeString(email);
        if (username == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(username);
        }
    }

    @Override
    public int describeContents() {
        return 0;
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
}
