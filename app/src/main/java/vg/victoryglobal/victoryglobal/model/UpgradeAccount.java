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

    private String activationCode;
    private Integer mlmMemberId;

    private String activationCodeName;
    private String memberName;

    public UpgradeAccount(){

    }

    public UpgradeAccount(Integer mlmMemberId, String activationCode){
        this.activationCode = activationCode;
        this.mlmMemberId = mlmMemberId;
    }


    protected UpgradeAccount(Parcel in) {
        activationCode = in.readString();
        if (in.readByte() == 0) {
            mlmMemberId = null;
        } else {
            mlmMemberId = in.readInt();
        }
        activationCodeName = in.readString();
        memberName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(activationCode);
        if (mlmMemberId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(mlmMemberId);
        }
        dest.writeString(activationCodeName);
        dest.writeString(memberName);
    }

    @Override
    public int describeContents() {
        return 0;
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

    //setter and getter
    public String getActivationCode(){return activationCode; }
    public void setActivationCode(String activationCode) {this.activationCode = activationCode; }

    public Integer getMlmMemberId(){return mlmMemberId; }
    public void setMlmMemberId(Integer mlmMemberId) {this.mlmMemberId = mlmMemberId; }

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
