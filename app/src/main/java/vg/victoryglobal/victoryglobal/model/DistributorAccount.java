/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/24/17 5:50 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/24/17 5:50 AM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/*
 * Distributor account model from API response
 */
public class DistributorAccount implements Parcelable {

    public Integer status;
    public CurrentIncome current_income;
    public BankAccount bank_account;
    public Address address;
    public ContactInfo contact_info;
    public MlmValidity mlm_validity_matchingbonus;
    public ArrayList<MlmValidity> mlm_validity;
    public MlmEntryType mlm_entry_type;
    public MlmRank mlm_rank;
    public MatchingPairs matching_pairs;
    public ArrayList<DistributorPoint> mlm_member_points;
    public Sponsor sponsor;
    public Upline upline;
    public Account account;
    public Profile profile;


    protected DistributorAccount(Parcel in) {
        if (in.readByte() == 0) {
            status = null;
        } else {
            status = in.readInt();
        }
        current_income = in.readParcelable(CurrentIncome.class.getClassLoader());
        bank_account = in.readParcelable(BankAccount.class.getClassLoader());
        address = in.readParcelable(Address.class.getClassLoader());
        contact_info = in.readParcelable(ContactInfo.class.getClassLoader());
        mlm_validity_matchingbonus = in.readParcelable(MlmValidity.class.getClassLoader());
        mlm_validity = in.createTypedArrayList(MlmValidity.CREATOR);
        mlm_entry_type = in.readParcelable(MlmEntryType.class.getClassLoader());
        mlm_rank = in.readParcelable(MlmRank.class.getClassLoader());
        matching_pairs = in.readParcelable(MatchingPairs.class.getClassLoader());
        mlm_member_points = in.createTypedArrayList(DistributorPoint.CREATOR);
        sponsor = in.readParcelable(Sponsor.class.getClassLoader());
        upline = in.readParcelable(Upline.class.getClassLoader());
        account = in.readParcelable(Account.class.getClassLoader());
        profile = in.readParcelable(Profile.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (status == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(status);
        }
        dest.writeParcelable(current_income, flags);
        dest.writeParcelable(bank_account, flags);
        dest.writeParcelable(address, flags);
        dest.writeParcelable(contact_info, flags);
        dest.writeParcelable(mlm_validity_matchingbonus, flags);
        dest.writeTypedList(mlm_validity);
        dest.writeParcelable(mlm_entry_type, flags);
        dest.writeParcelable(mlm_rank, flags);
        dest.writeParcelable(matching_pairs, flags);
        dest.writeTypedList(mlm_member_points);
        dest.writeParcelable(sponsor, flags);
        dest.writeParcelable(upline, flags);
        dest.writeParcelable(account, flags);
        dest.writeParcelable(profile, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DistributorAccount> CREATOR = new Creator<DistributorAccount>() {
        @Override
        public DistributorAccount createFromParcel(Parcel in) {
            return new DistributorAccount(in);
        }

        @Override
        public DistributorAccount[] newArray(int size) {
            return new DistributorAccount[size];
        }
    };

    //other method
    public DistributorPoint findDistributorPointByName(String name)
    {
        for(DistributorPoint point : mlm_member_points) {
            if(point.attribute_code.equals(name)) {
                return point;
            }
        }
        return null;
    }
}
