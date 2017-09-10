/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/10/17 1:54 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/10/17 1:40 PM
 */

package vg.victoryglobal.victoryglobal.model.facebook;

/**
 * Created by mong on 9/10/17.
 */

public class FbGraphPagingCursors {

    String before;
    String after;

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }
}
