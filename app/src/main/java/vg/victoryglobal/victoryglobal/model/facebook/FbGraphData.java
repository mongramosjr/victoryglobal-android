/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/10/17 1:54 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/10/17 1:43 PM
 */

package vg.victoryglobal.victoryglobal.model.facebook;

import java.util.ArrayList;

public class FbGraphData {

    ArrayList<FbGraphFeed> data =  new ArrayList<>();

    FbGraphPaging paging = new FbGraphPaging();

    public ArrayList<FbGraphFeed> getData() {
        return data;
    }

    public void setData(ArrayList<FbGraphFeed> data) {
        this.data = data;
    }

    public FbGraphPaging getPaging() {
        return paging;
    }

    public void setPaging(FbGraphPaging paging) {
        this.paging = paging;
    }
}
