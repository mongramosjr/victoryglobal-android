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
    private Integer mlmMemberId = 0;
    private String password = "";

    //response data
    private String session = "";
    private String authToken = "";
    private Boolean status = false;

    public AccountLogin(){
    }

    public AccountLogin(int mlmMemberId){
        this.mlmMemberId = mlmMemberId;
    }

    protected AccountLogin(Parcel in) {
        if (in.readByte() == 0) {
            mlmMemberId = null;
        } else {
            mlmMemberId = in.readInt();
        }
        password = in.readString();
        session = in.readString();
        authToken = in.readString();
        byte tmpStatus = in.readByte();
        status = tmpStatus == 0 ? null : tmpStatus == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mlmMemberId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(mlmMemberId);
        }
        dest.writeString(password);
        dest.writeString(session);
        dest.writeString(authToken);
        dest.writeByte((byte) (status == null ? 0 : status ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
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

    //setter and getter
    public Integer getMlmMemberId(){ return mlmMemberId; }
    public void setMlmMemberId(Integer mlmMemberId) {this.mlmMemberId = mlmMemberId; }

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

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean isStatus() {
        return status;
    }
}
