/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/30/17 10:34 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/30/17 10:34 AM
 */

package vg.victoryglobal.victoryglobal.model;


import android.os.Parcel;
import android.os.Parcelable;

public class GenealogyAccount extends BaseAccount {
    //implements Parcelable {

    public Integer mlm_upline;
    public Integer mlm_sponsor;
    public Integer mlm_location;

    public String full_name;
    public String sponsor_name;



    protected GenealogyAccount(Parcel in) {
        super(in);
        if (in.readByte() == 0) {
            mlm_upline = null;
        } else {
            mlm_upline = in.readInt();
        }
        if (in.readByte() == 0) {
            mlm_sponsor = null;
        } else {
            mlm_sponsor = in.readInt();
        }
        if (in.readByte() == 0) {
            mlm_location = null;
        } else {
            mlm_location = in.readInt();
        }
        full_name = in.readString();
        sponsor_name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        if (mlm_upline == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(mlm_upline);
        }
        if (mlm_sponsor == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(mlm_sponsor);
        }
        if (mlm_location == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(mlm_location);
        }

        dest.writeString(full_name);
        dest.writeString(sponsor_name);
    }

    public static final Creator<GenealogyAccount> CREATOR = new Creator<GenealogyAccount>() {
        @Override
        public GenealogyAccount createFromParcel(Parcel in) {
            return new GenealogyAccount(in);
        }

        @Override
        public GenealogyAccount[] newArray(int size) {
            return new GenealogyAccount[size];
        }
    };
}

/*
"children": [],
"mlm_location": 0,
"children_grouped": [
    [],
    []
],
"mlm_member_id": 7467,
"mlm_rank": 1,
"mlm_upline": 7260,
"mlm_sponsor": 7260,
"mlm_entry_type": 1500,
"full_name": "Elizabeth Aguinaldo Hernanado",
"sponsor_name": "Gloria Lagasca Munar"
*/

