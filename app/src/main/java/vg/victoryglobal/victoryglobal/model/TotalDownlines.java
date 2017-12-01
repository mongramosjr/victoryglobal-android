/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/30/17 12:33 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/30/17 12:33 PM
 */

package vg.victoryglobal.victoryglobal.model;

import java.util.ArrayList;

public class TotalDownlines {
    public Integer count;
    public ArrayList<TotalDownlinesPerLocation> mlm_location;

    public class TotalDownlinesPerLocation{
        public Integer count;
    }



}
