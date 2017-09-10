/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/10/17 1:54 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/10/17 11:58 AM
 */

package vg.victoryglobal.victoryglobal.model.facebook;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by mong on 9/10/17.
 */

public class FbGraphFeed implements Parcelable {


    String id;
    String story;
    String createdTime;
    String message;
    String fullPicture;
    String icon;
    ArrayList<FbGraphWithTags> withTags;
    FbGraphFrom from;


    protected FbGraphFeed(Parcel in) {
        id = in.readString();
        story = in.readString();
        createdTime = in.readString();
        message = in.readString();
        fullPicture = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FbGraphFeed> CREATOR = new Creator<FbGraphFeed>() {
        @Override
        public FbGraphFeed createFromParcel(Parcel in) {
            return new FbGraphFeed(in);
        }

        @Override
        public FbGraphFeed[] newArray(int size) {
            return new FbGraphFeed[size];
        }
    };

    //setter and getter


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getStory() {
        return story;
    }
    public void setStory(String story) {
        this.story = story;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getFullPicture() {
        return fullPicture;
    }

    public void setFullPicture(String fullPicture) {
        this.fullPicture = fullPicture;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public ArrayList<FbGraphWithTags> getWithTags() {
        return withTags;
    }

    public void setWithTags(ArrayList<FbGraphWithTags> withTags) {
        this.withTags = withTags;
    }

    public void setFrom(FbGraphFrom from) {
        this.from = from;
    }

    public FbGraphFrom getFrom() {
        return from;
    }
}

