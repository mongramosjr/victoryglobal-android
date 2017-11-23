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

/*
 * Auth login model from API response
 */
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
        if (in.readByte() == 0) {
            status = null;
        } else {
            status = in.readInt();
        }
        auth_token = in.readString();
        session = in.readString();
        account = in.readParcelable(Account.class.getClassLoader());
        user = in.readParcelable(User.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (status == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(status);
        }
        dest.writeString(auth_token);
        dest.writeString(session);
        dest.writeParcelable(account, flags);
        dest.writeParcelable(user, flags);
    }

    @Override
    public int describeContents() {
        return 0;
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
