/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/18/17 9:43 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/18/17 9:43 AM
 */

package vg.victoryglobal.victoryglobal.model.facebook;


import java.util.ArrayList;

public class FbGraphFeedRequest {

    private ArrayList<FbGraphFeed> feeds = new ArrayList<>();

    String next = null;


    private static final FbGraphFeedRequest ourInstance = new FbGraphFeedRequest();

    public static FbGraphFeedRequest getInstance() {
        return ourInstance;
    }

    private FbGraphFeedRequest() {
    }

    public ArrayList<FbGraphFeed> getFeeds() {
        return feeds;
    }

    public void setFeeds(ArrayList<FbGraphFeed> feeds) {
        this.feeds = feeds;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
