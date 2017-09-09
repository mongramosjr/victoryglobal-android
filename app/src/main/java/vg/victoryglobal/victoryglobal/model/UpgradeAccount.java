/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/9/17 9:19 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/9/17 2:42 PM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UpgradeAccount implements Parcelable {

    private String activationCode = "";
    private int mlmMemberId = 0;

    private String activationCodeName = "";
    private String memberName = "";

    public UpgradeAccount(){

    }

    public UpgradeAccount(int mlmMemberId, String activationCode){
        this.activationCode = activationCode;
        this.mlmMemberId = mlmMemberId;
    }

    // "De-parcel object
    public UpgradeAccount(Parcel in) {
        mlmMemberId = in.readInt();
        activationCode = in.readString();
    }

    public static final Creator<UpgradeAccount> CREATOR = new Creator<UpgradeAccount>() {
        @Override
        public UpgradeAccount createFromParcel(Parcel in) {
            return new UpgradeAccount(in);
        }

        @Override
        public UpgradeAccount[] newArray(int size) {
            return new UpgradeAccount[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mlmMemberId);
        parcel.writeString(activationCode);
    }

    //setter and getter
    public String getActivationCode(){return activationCode; }
    public void setActivationCode(String activationCode) {this.activationCode = activationCode; }

    public int getMlmMemberId(){return mlmMemberId; }
    public void setMlmMemberId(int mlmMemberId) {this.mlmMemberId = mlmMemberId; }

    public String getActivationCodeName() {
        return activationCodeName;
    }

    public void setActivationCodeName(String activationCodeName) {
        this.activationCodeName = activationCodeName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberName() {
        return memberName;
    }

}
