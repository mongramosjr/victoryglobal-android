/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/24/17 6:27 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/24/17 6:27 AM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * Distributor rank model from API response
 */
public class MlmRank implements Parcelable {
    public Integer id;
    public String rank;
    public String frontend_label;

    protected MlmRank(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        rank = in.readString();
        frontend_label = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(rank);
        dest.writeString(frontend_label);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MlmRank> CREATOR = new Creator<MlmRank>() {
        @Override
        public MlmRank createFromParcel(Parcel in) {
            return new MlmRank(in);
        }

        @Override
        public MlmRank[] newArray(int size) {
            return new MlmRank[size];
        }
    };
}
