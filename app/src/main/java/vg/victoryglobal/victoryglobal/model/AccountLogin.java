/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/19/17 9:53 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/22/17 9:44 AM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AccountLogin implements Parcelable {

    //request params
    private int mlmMemberId = 0;
    private String password = "";

    //response data
    private String session = "";
    private String authToken = "";
    private boolean status = false;

    public AccountLogin(){

    }

    public AccountLogin(int mlmMemberId){
        this.mlmMemberId = mlmMemberId;
    }

    protected AccountLogin(Parcel in) {
        mlmMemberId = in.readInt();
    }

    public static final Creator<AccountLogin> CREATOR = new Creator<AccountLogin>() {
        @Override
        public AccountLogin createFromParcel(Parcel in) {
            return new AccountLogin(in);
        }

        @Override
        public AccountLogin[] newArray(int size) {
            return new AccountLogin[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mlmMemberId);
        parcel.writeString(session);
        parcel.writeString(authToken);
    }

    //setter and getter
    public int getMlmMemberId(){ return mlmMemberId; }
    public void setMlmMemberId(int mlmMemberId) {this.mlmMemberId = mlmMemberId; }

    public String getPassword() { return password; }
    public void setPassword(String password) {this.password = password; }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }



    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }
}
