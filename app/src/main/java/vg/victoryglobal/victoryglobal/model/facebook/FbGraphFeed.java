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
import android.text.format.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mong on 9/10/17.
 */

public class FbGraphFeed implements Parcelable {


    String id;
    String story;
    String createdTime;
    String message;
    String fullPicture;
    String source;
    String type;
    String description;
    ArrayList<FbGraphWithTags> withTags;
    FbGraphFrom from;

    public FbGraphFeed(){

    }


    public FbGraphFeed(Parcel in) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FbGraphFrom fromJSON(JSONObject jsonObject){

        String from_name, from_id;
        FbGraphFrom fbGraphFrom =  new FbGraphFrom();

        try {
            if(jsonObject.has("name")){
                fbGraphFrom.setName(jsonObject.getString("name"));
            }
            if(jsonObject.has("id")){
                    fbGraphFrom.setId(jsonObject.getString("id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return fbGraphFrom;
    }

    public ArrayList<FbGraphWithTags> withTagsJSON(JSONObject jsonObject){

        ArrayList<FbGraphWithTags> fbGraphWithTags = new ArrayList<>();

        try {
            if(jsonObject.has("data")){
                JSONArray data = jsonObject.getJSONArray("data");
                for (int i = 0; i < data.length(); ++i) {
                    JSONObject with_tag = data.getJSONObject(i);

                    FbGraphWithTags fbGraphWithTag = new FbGraphWithTags();

                    if(with_tag.has("name")){
                        fbGraphWithTag.setName(with_tag.getString("name"));
                    }
                    if(with_tag.has("id")){
                        fbGraphWithTag.setId(with_tag.getString("id"));
                    }

                    fbGraphWithTags.add(fbGraphWithTag);
                }
            }

        }catch(JSONException e){
            e.printStackTrace();
        }

        return fbGraphWithTags;
    }

    public String createdTimeFormatted()
    {
        String display_date = createdTime;
        String myFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"; //In which you need put here
        SimpleDateFormat sdformat = new SimpleDateFormat(myFormat, Locale.US);

        Calendar calendar = Calendar.getInstance();

        Date date;
        try {
            date = sdformat.parse(createdTime);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        myFormat = "MMM dd, yyyy 'at' hh:mm a"; //In which you need put here
        sdformat = new SimpleDateFormat(myFormat, Locale.US);


        display_date = sdformat.format(calendar.getTime());


        return display_date;


    }

}

