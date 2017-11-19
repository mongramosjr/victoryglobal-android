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

import java.sql.Date;

public class AccountLogin implements Parcelable {

    private int mlmMemberId = 0;
    private String password = "";

    private String session = "";
    private String authToken = "";
    private String email = "";
    private String fullname = "";
    private String first_name = "";
    private String middle_name = "";
    private String last_name = "";
    private String username = "";

    private DistributorAccount account;

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
        parcel.writeString(fullname);
        parcel.writeString(email);
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

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setAccount(DistributorAccount account) {
        this.account = account;
    }

    public DistributorAccount getAccount() {
        return account;
    }
}
