/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/9/17 9:19 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/9/17 2:53 PM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ActivateCode implements Parcelable {

    private String activationCode = "";
    private int mlmMemberId = 0;
    private String activationCodeName = "";
    private String memberName = "";


    public ActivateCode(){

    }

    public ActivateCode(int mlmMemberId, String activationCode){
        this.activationCode = activationCode;
        this.mlmMemberId = mlmMemberId;
    }

    public ActivateCode(Parcel in) {
        mlmMemberId = in.readInt();
        activationCode = in.readString();
    }

    public static final Creator<ActivateCode> CREATOR = new Creator<ActivateCode>() {
        @Override
        public ActivateCode createFromParcel(Parcel in) {
            return new ActivateCode(in);
        }

        @Override
        public ActivateCode[] newArray(int size) {
            return new ActivateCode[size];
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
