/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/21/17 12:02 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/19/17 12:55 PM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AuthLogin implements Parcelable {

    public Integer status;
    public String auth_token;
    public String session;
    public Account account = new Account();
    public User user = new User();


    public AuthLogin(){

    }

    public AuthLogin(String session){
        this.session = session;
    }

    protected AuthLogin(Parcel in) {
        session = in.readString();
    }

    public static final Creator<AuthLogin> CREATOR = new Creator<AuthLogin>() {
        @Override
        public AuthLogin createFromParcel(Parcel in) {
            return new AuthLogin(in);
        }

        @Override
        public AuthLogin[] newArray(int size) {
            return new AuthLogin[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(status);
        parcel.writeString(session);
        parcel.writeString(auth_token);
    }

    //setter and getter


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAuthToken() {
        return auth_token;
    }

    public void setAuthToken(String authToken) {
        this.auth_token = authToken;
    }

    public String getSession() {
        return session;
    }
    public void setSession(String session) {
        this.session = session;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
