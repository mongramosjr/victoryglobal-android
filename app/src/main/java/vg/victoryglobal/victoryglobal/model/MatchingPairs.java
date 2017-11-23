/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/24/17 6:23 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/24/17 6:23 AM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;


/*
 * Matching Pairs model from API response
 */
public class MatchingPairs implements Parcelable {

    public Float matching_pairs;
    public Float matching_pairs_percentage;
    public Boolean matching_pairs_status;
    public Integer matching_pairs_remaining;
    public Integer mlm_member_id;

    protected MatchingPairs(Parcel in) {
        if (in.readByte() == 0) {
            matching_pairs = null;
        } else {
            matching_pairs = in.readFloat();
        }
        if (in.readByte() == 0) {
            matching_pairs_percentage = null;
        } else {
            matching_pairs_percentage = in.readFloat();
        }
        byte tmpMatching_pairs_status = in.readByte();
        matching_pairs_status = tmpMatching_pairs_status == 0 ? null : tmpMatching_pairs_status == 1;
        if (in.readByte() == 0) {
            matching_pairs_remaining = null;
        } else {
            matching_pairs_remaining = in.readInt();
        }
        if (in.readByte() == 0) {
            mlm_member_id = null;
        } else {
            mlm_member_id = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (matching_pairs == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(matching_pairs);
        }
        if (matching_pairs_percentage == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(matching_pairs_percentage);
        }
        dest.writeByte((byte) (matching_pairs_status == null ? 0 : matching_pairs_status ? 1 : 2));
        if (matching_pairs_remaining == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(matching_pairs_remaining);
        }
        if (mlm_member_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(mlm_member_id);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MatchingPairs> CREATOR = new Creator<MatchingPairs>() {
        @Override
        public MatchingPairs createFromParcel(Parcel in) {
            return new MatchingPairs(in);
        }

        @Override
        public MatchingPairs[] newArray(int size) {
            return new MatchingPairs[size];
        }
    };
}
