/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/10/17 1:54 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/10/17 1:44 PM
 */

package vg.victoryglobal.victoryglobal.model.facebook;

/**
 * Created by mong on 9/10/17.
 */

public class FbGraph {

    FbGraphData feed  = new FbGraphData();

    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FbGraphData getFeed() {
        return feed;
    }

    public void setData(FbGraphData feed) {
        this.feed = feed;
    }
}
