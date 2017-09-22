/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/10/17 1:54 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/10/17 1:44 PM
 */

package vg.victoryglobal.victoryglobal.model.facebook;

import java.util.ArrayList;

public class FbGraphPaging {

    String next;
    FbGraphPagingCursors cursors =  new FbGraphPagingCursors();

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public void setCursors(FbGraphPagingCursors cursors) {
        this.cursors = cursors;
    }

    public FbGraphPagingCursors getCursors() {
        return cursors;
    }


}
